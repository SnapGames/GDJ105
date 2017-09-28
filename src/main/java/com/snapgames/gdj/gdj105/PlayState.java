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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.Actions;
import com.snapgames.gdj.core.entity.Direction;
import com.snapgames.gdj.core.entity.GameObject;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.state.GameState;
import com.snapgames.gdj.core.state.GameStateManager;
import com.snapgames.gdj.core.ui.TextObject;
import com.snapgames.gdj.gdj105.entity.Enemy;
import com.snapgames.gdj.gdj105.entity.ItemContainerObject;
import com.snapgames.gdj.gdj105.entity.JaugeObject;
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

	/**
	 * objects to be animated on the game display.
	 */
	// Object moved by player
	private Player player = null;
	// list of other entities to demonstrate AbstractGameObject usage.
	private List<AbstractGameObject> entities = new CopyOnWriteArrayList<>();

	// Object moved by player
	private TextObject scoreTextObject = null;

	int dEnergy = 1;
	private JaugeObject energy;
	int dMana = 1;
	private JaugeObject mana;

	// score
	private int score = 0;

	private Font scoreFont;

	/**
	 * internal Font to draw any text on the screen !
	 */
	private Font font;
	private Font helpFont;


	/**
	 * Flag to display Help.
	 */
	private boolean isHelp = false;
	private ItemContainerObject[] itemContainers;

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
		scoreFont = game.getGraphics().getFont().deriveFont(14.0f);
		helpFont = font.deriveFont(8f);

		// Activate Layers
		layers = new boolean[5];
		for (int i = 0; i < 4; i++) {
			layers[i] = true;
		}
		// prepare Game objects

		// player (layer 1)
		player = new Player("player", Game.WIDTH / 2, Game.HEIGHT / 2, 16, 16, 1, 1, Color.BLUE);
		player.hSpeed = 0.05f;
		player.vSpeed = 0.05f;
		player.priority = 1;
		player.layer = 2;
		addObject(player);

		// NPC
		generateEnemies(10);

		// HUD Definition (layer 1)
		scoreTextObject = new TextObject("score", 4, -4, String.format("%06d", score), scoreFont, 1, 1, Color.WHITE);
		addObject(scoreTextObject);

		energy = new JaugeObject("energy", Game.WIDTH - 50, 4, 42, 4, 1, 1, new Color(1.0f, 0.0f, 0.0f, 0.7f));
		energy.minValue = 0;
		energy.maxValue = 100;
		energy.value = 90;
		addObject(energy);

		mana = new JaugeObject("mana", Game.WIDTH - 50, 12, 42, 4, 1, 1, new Color(0.0f, 0.0f, 1.0f, 0.9f));
		mana.minValue = 0;
		mana.maxValue = 100;
		mana.value = 20;
		addObject(mana);

		itemContainers = new ItemContainerObject[2];
		for (int i = 0; i < itemContainers.length; i++) {
			itemContainers[i] = new ItemContainerObject("itContainer_" + i, Game.WIDTH - (6 + (i + 1) * 22),
					Game.HEIGHT - 40, 22, 22);
			itemContainers[i].layer = 1;
			itemContainers[i].priority = 1;
			itemContainers[i].attributes.put("items", new Integer((int) Math.random() * 10));
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
		for (GameObject o : objects) {
			o.update(game, dt);
		}

		int winborder = 4;
		int wl = winborder;
		int wr = game.getWidth() / game.getScale() - player.width - winborder;
		int wt = winborder;
		int wb = game.getHeight() / game.getScale() - player.height - winborder;

		// player limit to border window
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

		for (AbstractGameObject o : entities) {
			if (o.x <= wl || o.x >= wr) {
				o.dx = -Math.signum(o.dx) * o.hSpeed;
			}
			if (o.y <= wt || o.y >= wb) {
				o.dy = -Math.signum(o.dy) * o.vSpeed;
			}
			computeEntityAction(o);
		}
		if (scoreTextObject != null) {
			scoreTextObject.text = String.format("%06d", score);
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

		// Display Pause state
		if (game.isPause()) {
			drawPause(game, g);
		}

	}

	/**
	 * draw the Pause label.
	 * 
	 * @param g
	 */
	private void drawPause(Game game, Graphics2D g) {
		String lblPause = "Pause";

		Font bck = g.getFont();
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
	 * @return the layers
	 */
	public boolean[] getLayers() {
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
		String[] text = { "[" + RenderHelper.showBoolean(layers[0]) + "] 1: show/hide layer 1",
				"[" + RenderHelper.showBoolean(layers[1]) + "] 2: show/hide layer 2",
				"[" + RenderHelper.showBoolean(layers[2]) + "] 3: show/hide layer 3",
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
			layers[0] = !layers[0];
			break;
		case KeyEvent.VK_NUMPAD2:
		case KeyEvent.VK_2:
			layers[1] = !layers[1];
			break;
		case KeyEvent.VK_NUMPAD3:
		case KeyEvent.VK_3:
			layers[2] = !layers[2];
			break;
		case KeyEvent.VK_NUMPAD4:
		case KeyEvent.VK_4:
			layers[3] = !layers[3];
			break;
		case KeyEvent.VK_PAGE_UP:
			generateEnemies(10);
			score += 10;
			break;
		case KeyEvent.VK_PAGE_DOWN:
			if (score - 10 >= 0) {
				score -= 10;
				removeAllObjectOfClass(Enemy.class, 10);
			}
			break;
		case KeyEvent.VK_H:
			isHelp = !isHelp;
			break;
		}
	}

	private void generateEnemies(int nb) {
		// NPC (layers 3 & 4)
		for (int i = 0; i < nb; i++) {

			Enemy entity = new Enemy("entity_" + i, Game.WIDTH / 2, Game.HEIGHT / 2, 16, 16, 2, 1, Color.RED);
			entity.x = ((float) Math.random() * Game.WIDTH) + ((Game.WIDTH / 2));
			entity.y = ((float) Math.random() * Game.HEIGHT) + ((Game.HEIGHT / 2));
			entity.dx = ((float) Math.random() * 0.05f) - 0.02f;
			entity.dy = ((float) Math.random() * 0.05f) - 0.02f;
			entity.hSpeed = 0.042f;
			entity.vSpeed = 0.042f;

			if (i < 3) {
				entity.layer = 3;
				entity.color = Color.MAGENTA;
			} else {
				entity.layer = 4;
				entity.color = Color.CYAN;
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
	private void removeAllObjectOfClass(Class<Enemy> clazz, int nbObjectToRemove) {
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

}
