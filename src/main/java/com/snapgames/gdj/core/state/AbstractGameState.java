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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.GameObject;
import com.snapgames.gdj.core.gfx.RenderHelper;

/**
 * An Abstract Game State to manage all states of the Game !
 */
public abstract class AbstractGameState implements GameState {

	/**
	 * Internal rendering layers. by default 3 layers are initialized.
	 */
	protected boolean[] layers = new boolean[3];

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

	/**
	 * Default constructor for the AbstractGameState
	 */
	public AbstractGameState() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.GameState#initialize(com.snapgames.gdj.
	 * gdj105.core.Game)
	 */
	@Override
	public void initialize(Game game) {
		debugFont = game.getRender().getFont().deriveFont(9f);
	}

	/**
	 * Add an object to the Object list and sort them according to their layer and
	 * priority.
	 * 
	 * @param object
	 */
	protected void addObject(AbstractGameObject object) {
		objects.add(object);
		objects.sort(new Comparator<GameObject>() {
			public int compare(GameObject o1, GameObject o2) {
				AbstractGameObject ago1 = (AbstractGameObject) o1;
				AbstractGameObject ago2 = (AbstractGameObject) o2;
				return (ago1.layer > ago2.layer ? -1 : (ago1.priority > ago2.priority ? -1 : 1));
			};
		});
	}

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
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_Q:
			game.setExit(true);
			break;
		case KeyEvent.VK_PAUSE:
		case KeyEvent.VK_P:
			game.requestPause();
			break;
		case KeyEvent.VK_F9:
		case KeyEvent.VK_D:
			game.setDebug(!game.isDebug());
			break;
		case KeyEvent.VK_S:
			game.captureScreenshot();
			break;
		case KeyEvent.VK_NUMPAD1:

		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.GameState#render(com.snapgames.gdj.gdj105
	 * .core.Game, java.awt.Graphics2D)
	 */
	public void render(Game game, Graphics2D g) {
		if (!objects.isEmpty()) {
			for (GameObject o : objects) {
				if (layers[o.getLayer() - 1]) {
					o.draw(game, g);
					if (game.isDebug()) {
						RenderHelper.drawDebug(g, o, debugFont);
					}
				}
			}
		}
	}
}
