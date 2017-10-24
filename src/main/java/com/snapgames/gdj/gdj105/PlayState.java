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
import com.snapgames.gdj.core.entity.CameraObject;
import com.snapgames.gdj.core.entity.Direction;
import com.snapgames.gdj.core.entity.GameObject;
import com.snapgames.gdj.core.entity.Layer;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.state.GameState;
import com.snapgames.gdj.core.state.GameStateManager;
import com.snapgames.gdj.gdj105.entity.Eatable;
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

		CameraObject camera = new CameraObject("cam1", player, 0.1f);
		addCamera(camera);

		// NPC
		generateEnemies(10);

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

		// compute play zone borders.
		float winborder = 4;
		float wl = winborder;
		float wr = (float) playZone.getWidth() - player.width - winborder;
		float wt = winborder;
		float wb = (float) playZone.getHeight() - player.height - winborder;

		// player limit to playzone
		constrainPlayerTo(wl, wr, wt, wb);
		// entities moving limit to playzone.
		constrainObjectTo();

		// Update camera
		if (defaultCamera != null) {
			defaultCamera.update(game, dt);
		}
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
			computeEntityAction(o);
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

	private void computeEntityAction(AbstractGameObject o) {
		if (o.dx < 0 || o.dx > 0) {
			o.action = Actions.WALK;
		}
		if (o.dy < 0) {
			o.action = Actions.DOWN;
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

	}

	/**
	 * @return the layers
	 */
	public Layer[] getLayers() {
		return layers;
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
				entity = new Eatable("eatable_" + i, Game.WIDTH / 2, Game.HEIGHT / 2);
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

}
