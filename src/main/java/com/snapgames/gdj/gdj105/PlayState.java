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
import com.snapgames.gdj.core.ui.JaugeObject;
import com.snapgames.gdj.core.ui.TextObject;

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
	private AbstractGameObject player = null;
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

	private GameStateManager gsm = null;

	/**
	 * Flag to display Help.
	 */
	private boolean isHelp = false;

	public PlayState() {
	}

	public PlayState(GameStateManager gsm) {
		this.gsm = gsm;
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
		layers = new boolean[5];

		font = game.getGraphics().getFont();
		scoreFont = game.getGraphics().getFont().deriveFont(14.0f);
		// prepare Game objects
		player = new AbstractGameObject("player", game.getWidth() / (2 * game.getScale()),
				game.getHeight() / (2 * game.getScale()), 16, 16, 1, 1, Color.BLUE);
		player.hSpeed = 0.05f;
		player.vSpeed = 0.05f;
		player.priority = 1;
		player.layer = 2;
		addObject(player);

		for (int i = 0; i < 4; i++) {
			layers[i] = true;
		}

		for (int i = 0; i < 10; i++) {

			AbstractGameObject entity = new AbstractGameObject("entity_" + i, game.getWidth() / (2 * game.getScale()),
					game.getHeight() / (2 * game.getScale()), 16, 16, 2, 1, Color.RED);
			entity.dx = ((float) Math.random() * 0.05f) - 0.02f;
			entity.dy = ((float) Math.random() * 0.05f) - 0.02f;
			entity.hSpeed = 0.042f;
			entity.vSpeed = 0.042f;

			if (i < 5) {
				entity.layer = 3;
				entity.color = Color.MAGENTA;
			} else {
				entity.layer = 4;
				entity.color = Color.CYAN;
			}
			entities.add(entity);
			addObject(entity);
		}

		scoreTextObject = new TextObject("score", 4, -4, String.format("%06d", score), scoreFont, 1, 1, Color.WHITE);
		addObject(scoreTextObject);

		energy = new JaugeObject("energy", game.WIDTH - 50, 4, 42, 4, 1, 1, new Color(1.0f,0.0f,0.0f,0.7f));
		energy.minValue=0;
		energy.maxValue=100;
		energy.value=90;
		addObject(energy);
		mana = new JaugeObject("energy", game.WIDTH - 50, 12, 42, 4, 1, 1,new Color(0.0f,0.0f,1.0f,0.9f));
		mana.minValue=0;
		mana.maxValue=100;
		mana.value=20;
		addObject(mana);
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
			displayHelp(game, g, 10, 20);
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
		Font f = font.deriveFont(28.0f).deriveFont(Font.ITALIC);

		g.setFont(f);
		RenderHelper.drawShadowString(g, lblPause, game.WIDTH / 2, game.HEIGHT / 2, Color.WHITE, Color.BLACK,
				RenderHelper.TextPosition.CENTER, 3);
		g.setFont(bck);

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
	public void displayHelp(Game game, Graphics2D g, int x, int y) {
		g.setColor(new Color(.5f, .5f, .5f, .3f));
		g.fillRect(x - 10, y - 16, 248, 132);
		g.setColor(Color.WHITE);
		String[] text = { "[" + RenderHelper.showBoolean(layers[0]) + "] 1: show/hide layer 1",
				"[" + RenderHelper.showBoolean(layers[1]) + "] 2: show/hide layer 2",
				"[" + RenderHelper.showBoolean(layers[2]) + "] 3: show/hide layer 3",
				"[" + RenderHelper.showBoolean(game.isDebug()) + "] D: display debug info",
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
		case KeyEvent.VK_H:
			isHelp = !isHelp;
			break;
		}
	}
}
