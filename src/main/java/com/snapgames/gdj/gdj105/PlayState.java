/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ105
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj105;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.Actions;
import com.snapgames.gdj.core.entity.Direction;
import com.snapgames.gdj.core.entity.DynamicObject;
import com.snapgames.gdj.core.entity.GameObject;
import com.snapgames.gdj.core.entity.Layer;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.state.GameState;
import com.snapgames.gdj.core.state.GameStateManager;
import com.snapgames.gdj.core.ui.TextObject;
import com.snapgames.gdj.gdj105.entity.EatBall;
import com.snapgames.gdj.gdj105.entity.Enemy;
import com.snapgames.gdj.gdj105.entity.Player;

/**
 * The Play State class defines default behavior for the playable game state.
 * This where all the rendering process, the default input processing and update
 * are performed.
 * 
 * @author Frédéric Delorme
 *
 */
public class PlayState extends AbstractGameState implements GameState {

	private static final Logger logger = LoggerFactory.getLogger(PlayState.class);

	/**
	 * objects to be animated on the game display.
	 */
	// Object moved by player
	private Player player = null;
	// list of other entities to demonstrate AbstractGameObject usage.
	private List<AbstractGameObject> entities = new CopyOnWriteArrayList<>();

	/**
	 * internal Font to draw any text on the screen !
	 */
	private Font font;
	private Font helpFont;

	/**
	 * Flag to display Help.
	 */
	private boolean isHelp = false;
	private Rectangle playZone;

	public PlayState() {
	}

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.gdj105.statemachine.GameState#initialize(com.snapgames.gdj.
	 * gdj104.Game)
	 */
	@Override
	public void initialize(Game game) {
		super.initialize(game);

		// prepare Fonts
		font = game.getGraphics().getFont();
		helpFont = font.deriveFont(8f);
		ResourceManager.add("font", font);
		ResourceManager.add("helpFont", helpFont);

		this.playZone = new Rectangle(0, 0, 1000, 1000);

		// Activate Layers
		layers = new Layer[5];
		resetLayers();
		layers[0].moveWithCamera = false;

		// prepare Game objects
		// player (layer 1)
		player = new Player("player", Game.WIDTH / 2, Game.HEIGHT / 2, 16, 16, 1, 1, Color.BLUE);

		addObject(player);

		// NPC
		generateEnemies(10);

		int marginLeft = (int) (Game.WIDTH * camera.getMargin() * 2);
		int marginTop = (int) (Game.HEIGHT * camera.getMargin() * 2);
		int marginRight = (int) (Game.WIDTH * (1 - camera.getMargin() * 2));
		int marginBottom = (int) (Game.HEIGHT * (1 - camera.getMargin() * 2));

		// HUD Definition (layer 1)
		scoreTextObject = new TextObject("score", marginLeft, marginTop, String.format("%06d", score), scoreFont, 1, 1,
				Color.WHITE);
		addObject(scoreTextObject);

		energy = new JaugeObject("energy", marginRight - 50, marginTop, 42, 4, 1, 1, new Color(1.0f, 0.0f, 0.0f, 0.7f));
		energy.minValue = 0;
		energy.maxValue = 100;
		energy.value = 90;
		addObject(energy);

		mana = new JaugeObject("mana", marginRight - 50, marginTop + 12, 42, 4, 1, 1,
				new Color(0.0f, 0.0f, 1.0f, 0.9f));
		mana.minValue = 0;
		mana.maxValue = 100;
		mana.value = 20;
		addObject(mana);

		itemContainers = new ItemContainerObject[4];
		for (int i = 0; i < itemContainers.length; i++) {
			itemContainers[i] = new ItemContainerObject("itContainer_" + i, marginRight - (12 + (i) * 22),
					marginBottom - 24, 22, 22);
			itemContainers[i].layer = 1;
			itemContainers[i].priority = 1;
			itemContainers[i].attributes.put("items", new Integer((int)(Math.random() * 10)+1));
			itemContainers[i].font = game.getFont().deriveFont(8.0f);
			addObject(itemContainers[i]);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.gdj105.statemachine.GameState#input(com.snapgames.gdj.
	 * gdj104.Game, com.snapgames.gdj.gdj105.InputHandler)
	 */
	@Override
	public void input(Game game, InputHandler input) {
		// left / right
		if (player != null) {
			if (input.getKeyPressed(KeyEvent.VK_SHIFT) && input.getKeyPressed(KeyEvent.VK_CONTROL)) {
				player.hSpeed = 0.4f;
				player.vSpeed = 0.4f;
			} else if (input.getKeyPressed(KeyEvent.VK_SHIFT)) {
				player.hSpeed = 0.1f;
				player.vSpeed = 0.1f;
			} else if (input.getKeyPressed(KeyEvent.VK_CONTROL)) {
				player.hSpeed = 0.2f;
				player.vSpeed = 0.2f;
			} else {
				player.hSpeed = 0.05f;
				player.vSpeed = 0.05f;
			}
			if (input.getKeyPressed(KeyEvent.VK_LEFT)) {
				player.dx = -player.hSpeed;
				player.action = Actions.WALK;
				player.direction = Direction.LEFT;
			} else if (input.getKeyPressed(KeyEvent.VK_RIGHT)) {
				player.dx = +player.hSpeed;
				player.action = Actions.WALK;
				player.direction = Direction.RIGHT;
			} else {
				if (player.dx != 0) {
					player.dx *= 0.980f;
				}
			}

			// up / down
			if (input.getKeyPressed(KeyEvent.VK_UP)) {
				player.dy = -player.vSpeed;
				player.action = Actions.UP;
				player.direction = Direction.UP;
			} else if (input.getKeyPressed(KeyEvent.VK_DOWN)) {
				player.action = Actions.DOWN;
				player.direction = Direction.DOWN;
				player.dy = +player.vSpeed;
			} else {
				if (player.dy != 0) {
					player.dy *= 0.980f;
				}
			}

			if (player.dx == 0.0f && player.dy == 0.0f) {
				player.action = Actions.IDLE;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.gdj105.statemachine.GameState#update(com.snapgames.gdj.
	 * gdj104.Game, long)
	 */
	@Override
	public void update(Game game, long dt) {
		// add all objects to QuadTree
		updateQuadTree(game, dt);

		float winborder = 4, wl = 4, wr = 4, wt = 4, wb = 4;
		// compute play zone borders.
		winborder = 4;
		wl = winborder;
		wt = winborder;
		if (playZone != null && player != null) {
			wr = (float) playZone.getWidth() - player.width - winborder;
			wb = (float) playZone.getHeight() - player.height - winborder;
			// player limit to playzone
			constrainPlayerTo(wl, wr, wt, wb);
		}
		// entities moving limit to playzone.
		constrainObjectTo();

	}

	/**
	 * @param game
	 * @param dt
	 */
	private void updateQuadTree(Game game, long dt) {
		for (GameObject o : objects) {
			o.update(game, dt);
		}
	}

	/**
	 * Constrain all object to play zone.
	 */
	private void constrainObjectTo() {
		for (AbstractGameObject o : entities) {
			if (o.x <= 0) {
				o.dx = -Math.signum(o.dx) * o.hSpeed;
				o.x = 1;
			}
			if (o.x >= playZone.getWidth()) {
				o.dx = -Math.signum(o.dx) * o.hSpeed;
				o.x = (int) playZone.getWidth() - 1;
			}
			if (o.y <= 0) {
				o.dy = -Math.signum(o.dy) * o.vSpeed;
				o.y = 1;
			}
			if (o.y >= playZone.getHeight()) {
				o.dy = -Math.signum(o.dy) * o.vSpeed;
				o.y = (int) playZone.getHeight() - 1;
			}
			if (o instanceof DynamicObject) {
				computeEntityAction((DynamicObject) o);
			}
		}
	}

	/**
	 * @param wl
	 * @param wr
	 * @param wt
	 * @param wb
	 */
	private void constrainPlayerTo(float wl, float wr, float wt, float wb) {
		if (player.x < wl)
			player.x = wl;
		if (player.y < wt)
			player.y = wt;
		if (player.x > wr)
			player.x = wr;
		if (player.y > wb)
			player.y = wb;
		if (player.dx != 0) {
			player.dx *= 0.980f;
		}
		if (player.dy != 0) {
			player.dy *= 0.980f;
		}
	}

	/**
	 * Manage collision from Player to other objects.
	 */
	private void manageCollision() {
		List<Sizeable> collisionList = new CopyOnWriteArrayList<>();
		quadTree.retrieve(collisionList, player);
		if (collisionList != null && !collisionList.isEmpty()) {
			for (Sizeable s : collisionList) {
				AbstractGameObject ago = (AbstractGameObject) s;
				if (player.rectangle.intersects(ago.rectangle)) {
					int d = 0;
					if (ago.getClass().equals(EatBall.class)) {
						d = (Integer) ago.attributes.get("power");
						// eat only if energy low.
						if (addValueToAttribute(player, "energy", d, 0, 100)) {
							objects.remove(ago);
						}
					}
					if (ago.getClass().equals(Enemy.class)) {
						addValueToAttribute(player, "energy", -1, 0, 100);
					}
				}
			}
		}
	}

	/**
	 * add <code>d</code> to attribute <code>name</code> from game object
	 * <code>ago</code> and verify <code>min</code> and <code>max</code> value. If
	 * value as been updated, return <code>true</code>, else <code>false</code>.
	 * 
	 * @param ago
	 * @param name
	 * @param d
	 * @param min
	 * @param max
	 * @return
	 */
	private boolean addValueToAttribute(AbstractGameObject ago, String name, int d, int min, int max) {
		if (ago != null && ago.attributes != null && ago.attributes.containsKey(name)) {
			int value = (Integer) ago.attributes.get(name);
			if (value + d < max && value + d > min) {
				value += d;
				player.attributes.put(name, value);
				return true;
			}
		} else {
			logger.error("GameObject {} does not have property named {}", ago.name, name);
		}
		return false;
	}

	private void computeEntityAction(DynamicObject o) {
		if (o.dx < 0 || o.dx > 0) {
			o.action = Actions.WALK;
		}
		if (o.dy < 0) {
			o.action = Actions.UP;
		}
		if (o.dy > 0) {
			o.action = Actions.DOWN;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.gdj105.statemachine.GameState#render(com.snapgames.gdj.
	 * gdj104.Game, java.awt.Graphics2D)
	 */
	@Override
	public void render(Game game, Graphics2D g) {
		super.render(game, g);

		// display Help if requested
		if (isHelp) {
			displayHelp(game, g, 10, 20, helpFont);
		}

		// Display Pause state
		if (game.isPause()) {
			drawPause(game, g);
		}

		if (game.isDebug(3)) {
			drawPlayZone(game, g);
		}
	}

	private void drawPlayZone(Game game, Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.drawRect(playZone.x, playZone.y, playZone.width, playZone.height);
	}

	/**
	 * draw the Pause label.
	 * 
	 * @param g
	 */
	private void drawPause(Game game, Graphics2D g) {
		String lblPause = "Pause";

		Font bck = ResourceManager.getFont("font");
		Font f = font.deriveFont(14.0f).deriveFont(Font.ITALIC);

		g.setFont(f);
		RenderHelper.drawShadowString(g, lblPause, Game.WIDTH / 2, Game.HEIGHT / 2, Color.WHITE, Color.BLACK,
				RenderHelper.TextPosition.CENTER, 3);
		g.setFont(bck);
		g.setColor(Color.WHITE);
		g.drawLine((Game.WIDTH / 2) - 30, (Game.HEIGHT / 2) + 2, (Game.WIDTH / 2) + 30, (Game.HEIGHT / 2) + 2);
		g.setColor(Color.BLACK);
		g.drawRect((Game.WIDTH / 2) - 31, (Game.HEIGHT / 2) + 1, 62, 2);

	}

	/**
	 * Display the Help panel.
	 * 
	 * @param game
	 * @param g
	 * @param x
	 * @param y
	 */
	public void displayHelp(Game game, Graphics2D g, int x, int y, Font helpFont) {
		g.setFont(helpFont);
		g.setColor(Color.WHITE);
		String[] text = { "[" + RenderHelper.showBoolean(layers[0].active) + "] 1: show/hide layer 1",
				"[" + RenderHelper.showBoolean(layers[1].active) + "] 2: show/hide layer 2",
				"[" + RenderHelper.showBoolean(layers[2].active) + "] 3: show/hide layer 3",
				"[" + game.getDebug() + "] D: display debug info",
				"[" + RenderHelper.showBoolean(game.isPause()) + "] P/PAUSE: pause the computation",
				"[" + RenderHelper.showBoolean(isHelp) + "] H: display this help", "   CTRL+S: save a screenshot",
				"   Q/ESCAPE: Escape the demo" };

		RenderHelper.display(g, x, y, debugFont, text);
	}

	@Override
	public void keyReleased(Game game, KeyEvent e) {
		super.keyReleased(game, e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_PAUSE:
		case KeyEvent.VK_P:
			game.requestPause();
			break;
		case KeyEvent.VK_NUMPAD1:
		case KeyEvent.VK_1:
			layers[0].active = !layers[0].active;
			break;
		case KeyEvent.VK_NUMPAD2:
		case KeyEvent.VK_2:
			layers[1].active = !layers[1].active;
			break;
		case KeyEvent.VK_NUMPAD3:
		case KeyEvent.VK_3:
			layers[2].active = !layers[2].active;
			break;
		case KeyEvent.VK_NUMPAD4:
		case KeyEvent.VK_4:
			layers[3].active = !layers[3].active;
			break;
		case KeyEvent.VK_PAGE_UP:
			generateEnemies(nbElem);
			break;
		case KeyEvent.VK_PAGE_DOWN:
			if (score - nbElem >= 0) {
				removeAllObjectOfClass(Enemy.class, nbElem);
				removeAllObjectOfClass(EatBall.class, nbElem);
			}
			break;
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_DELETE:
			removeAllObjectOfClass(Enemy.class);
			removeAllObjectOfClass(EatBall.class);
			score = 0;
			break;
		case KeyEvent.VK_H:
			isHelp = !isHelp;
			break;
		}
	}

	private void generateEnemies(int nb) {
		// NPC (layers 3 & 4)
		int halfNb = nb / 2;
		for (int i = 0; i < nb; i++) {

			AbstractGameObject entity = null;
			if (i < halfNb) {
				entity = new Enemy("enemy_" + i, Game.WIDTH / 2, Game.HEIGHT / 2);
				entity.x = ((float) Math.random() * Game.WIDTH) + ((Game.WIDTH / 2));
				entity.y = ((float) Math.random() * Game.HEIGHT) + ((Game.HEIGHT / 2));
				entity.dx = ((float) Math.random() * 0.05f) - 0.02f;
				entity.dy = ((float) Math.random() * 0.05f) - 0.02f;
				entity.color = Color.RED;
				entity.layer = 3;
			} else {
				entity = new EatBall("eatable_" + i, Game.WIDTH / 2, Game.HEIGHT / 2);
				entity.x = ((float) Math.random() * Game.WIDTH) + ((Game.WIDTH / 2));
				entity.y = ((float) Math.random() * Game.HEIGHT) + ((Game.HEIGHT / 2));
				entity.dx = ((float) Math.random() * 0.05f) - 0.02f;
				entity.dy = ((float) Math.random() * 0.05f) - 0.02f;
				entity.color = Color.CYAN;
				entity.layer = 4;
			}
			entities.add(entity);
			addObject(entity);
		}

	}

	/**
	 * remove a nbObjectoRemove number of object clazz
	 * 
	 * @param clazz
	 * @param nbObjectToRemove
	 */
	private void removeAllObjectOfClass(Class<? extends AbstractGameObject> clazz, int nbObjectToRemove) {
		List<GameObject> toBeDeleted = new ArrayList<>();
		int idx = nbObjectToRemove;
		for (GameObject o : objects) {
			if (o.getClass().equals(clazz)) {
				toBeDeleted.add(o);
				idx--;
			}
			if (idx <= 0) {
				break;
			}
		}
		objects.removeAll(toBeDeleted);
	}

	/**
	 * Return the array of Layers for this state.
	 * 
	 * @return the layers
	 */
	public Layer[] getLayers() {
		return layers;
	}
}
