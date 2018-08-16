/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ104
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.state;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.GameObject;
import com.snapgames.gdj.core.entity.Layer;
import com.snapgames.gdj.core.gfx.DebugLevel;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.i18n.Messages;
import com.snapgames.gdj.core.ui.UIi18nReload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An Abstract Game State to manage all states of the Game !
 */
public abstract class AbstractGameState implements GameState {

	private static final Logger logger = LoggerFactory.getLogger(AbstractGameState.class);

	public Map<String, Integer> statistics = new ConcurrentHashMap<>();

	/**
	 * Referring GameStateManager
	 */
	protected GameStateManager gsm = null;

	/**
	 * Internal rendering layers. by default 4 layers (0->3) are initialized.
	 */
	protected Layer[] layers = new Layer[3];
	protected List<Layer> layersWith = new CopyOnWriteArrayList<>();
	protected List<Layer> layersWithoutCamera = new CopyOnWriteArrayList<>();

	/**
	 * embedded debug font to draw on screen debug information.
	 */
	protected Font debugFont;

	/**
	 * Background color (for clear purpose).
	 */
	protected Color backgroundColor = Color.BLACK;

	/**
	 * List of managed objects. Use a list that can put up with concurrent access.
	 */
	protected List<GameObject> objects = new CopyOnWriteArrayList<>();

	protected List<UIi18nReload> uis = new CopyOnWriteArrayList<>();

	/**
	 * Default constructor for the AbstractGameState
	 */
	public AbstractGameState() {
		super();
	}

	public AbstractGameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.GameState#initialize(com.snapgames.gdj.
	 * gdj105.core.Game)
	 */
	@Override
	public void initialize(Game game) {
		// activate needed layers
		resetLayers();
	}

	/**
	 * 
	 */
	protected void resetLayers() {
		for (int i = 0; i < layers.length; i++) {
			layers[i] = new Layer(true, true);
		}
		layersWith.clear();
		layersWithoutCamera.clear();
	}

	/**
	 * Add an object to the Object list and sort them according to their layer and
	 * priority.
	 * 
	 * @param object
	 */
	protected void addObject(AbstractGameObject object) {
		// add object to rendering list
		objects.add(object);
		// if this object contains translated text, it must be declared as is.
		if (object instanceof UIi18nReload) {
			uis.add((UIi18nReload) object);
		}
		// Sort all objects according to their layer and their own priority in the
		// layer.
		objects.sort(new Comparator<GameObject>() {
			public int compare(GameObject o1, GameObject o2) {
				AbstractGameObject ago1 = (AbstractGameObject) o1;
				AbstractGameObject ago2 = (AbstractGameObject) o2;
				return (ago1.layer > ago2.layer ? -1 : (ago1.priority > ago2.priority ? -1 : 1));
			};
		});
		// add object to a specific Layer.
		addObjectToLayer(object);
		// update internal statistics KPI.
		statistics.put("objectCount", objects.size());
		logger.debug("Add {} to the objects list", object.name);
	}

	/**
	 * Add an object to its specific layer (see
	 * {@link AbstractGameObject#getLayer()}).
	 * 
	 * @param object the GameObject to be added to its own layer.
	 */
	private void addObjectToLayer(AbstractGameObject object) {
		if (object.layer >= 0 && layers[object.layer] != null) {

			layers[object.layer].objects.add(object);
		}
		if (!layers[object.layer].moveWithCamera) {
			layersWithoutCamera.add(layers[object.layer]);
		} else {
			layersWith.add(layers[object.layer]);
		}
	}

	/**
	 * This is a specific delete method to remove all objects with a particular
	 * class, to cleanup the object stack.
	 * 
	 * @param clazz the object's class to be purged of.
	 */
	protected void removeAllObjectOfClass(Class<? extends AbstractGameObject> clazz) {
		List<GameObject> toBeDeleted = new ArrayList<>();
		for (GameObject o : objects) {
			if (o.getClass().equals(clazz)) {
				toBeDeleted.add(o);
			}
		}
		objects.removeAll(toBeDeleted);
	}

	/**
	 * The internal "free in memory" objects.
	 */
	public void dispose(Game game) {
		objects.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.GameState#keyTyped(com.snapgames.gdj.
	 * gdj105.core.Game, java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(Game game, KeyEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.GameState#keyPressed(com.snapgames.gdj.
	 * gdj105.core.Game, java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(Game game, KeyEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.GameState#keyReleased(com.snapgames.gdj.
	 * gdj105.core.Game, java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(Game game, KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Q:
			game.setExit(true);
			break;
		case KeyEvent.VK_F9:
		case KeyEvent.VK_D:
			DebugLevel debug = game.getDebug();
			int debugLevel = debug.getValue();
			debugLevel++;
			debugLevel = (debugLevel > DebugLevel.DEBUG_FPS_BOX_DIRECTION_ATTRS.getValue() ? 0 : debugLevel);
			game.setDebug(debug.setValue(debugLevel));
			break;
		case KeyEvent.VK_S:
			game.captureScreenshot();
			break;
		case KeyEvent.VK_L:
			Messages.switchLanguage();
			reloadUI();
			break;
		default:
			break;
		}
	}

	/**
	 * reload all messages for translated UI components.
	 */
	private void reloadUI() {
		Messages.reloadUIi18n(uis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.GameState#render(com.snapgames.gdj.gdj105
	 * .core.Game, java.awt.Graphics2D)
	 */
	public void render(Game game, Graphics2D g) {
		int renderedObjectCount = 0;
		if (!objects.isEmpty()) {
			for (GameObject o : objects) {
				Layer layer = layers[o.getLayer() - 1];
				if (layer.active) {
					renderedObjectCount++;
					o.draw(game, g);
					if (game.isDebug(DebugLevel.DEBUG_FPS) || o.isDebugInfoDisplayed()) {
						o.addDebugInfo(game);
						o.drawSpecialDebugInfo(game, g);
						o.getDebugInfo().clear();
					}

				}
			}
			statistics.put("renderedObjCount", renderedObjectCount);
			statistics.put("staticObjCount", objects.size());
		}

		if (game.isDebug(DebugLevel.DEBUG_FPS))

		{
			RenderHelper.drawShadowString(g, String.format("FPS:%03d, ROC:%04d, SOC:%04d, DBG:%d", game.framesPerSecond,
					statistics.get("renderedObjCount"), statistics.get("staticObjCount"), game.getDebug().getValue()),
					4, (int) (Game.HEIGHT * 0.93f), Color.BLUE, Color.BLACK);
		}

	}

}
