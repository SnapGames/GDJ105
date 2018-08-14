/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.gdj105;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.Camera;
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;
import com.snapgames.gdj.core.gfx.Sprite;
import com.snapgames.gdj.core.i18n.Messages;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.UIText;
import com.snapgames.gdj.gdj105.entity.Player;
import com.snapgames.gdj.gdj105.entity.TileMap;
import com.snapgames.gdj.gdj105.i18n.Labels;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class PlayState extends AbstractGameState {

	private Map<String, Camera> cameras = new ConcurrentHashMap<>();
	private Camera activeCamera;

	private TileMap tilemap;
	private Player player;
	private Camera camera;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.AbstractGameState#initialize(com.snapgames.gdj.
	 * core.Game)
	 */
	@Override
	public void initialize(Game game) {
		// TODO Auto-generated method stub
		super.initialize(game);

		// read i18n labels
		String titleLabel = Messages.getString(Labels.PLAY_TITLE.getKey());

		// Prepare fonts.
		Font titleFont = ResourceManager.getFont("/res/fonts/Prince Valiant.ttf")
				.deriveFont(2.5f * Game.SCREEN_FONT_RATIO);

		game.getWindow().setTitle("OptionState");

		// Define the main Game title object
		UIText titleText = new UIText("title", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.05f), titleLabel,
				titleFont, 1, 1, Color.WHITE, Justification.CENTER);
		titleText.setLabel(Labels.PLAY_TITLE.getKey());
		titleText.layer = 1;
		titleText.priority = 2;
		addObject(titleText);

		tilemap = new TileMap("tilemap");
		tilemap.loadTileFile("/res/maps/level-001.map");
		tilemap.layer = 2;
		addObject(tilemap);

		Sprite sps = new Sprite(
				ResourceManager
					.getImage("/res/images/Sprite-0001.png")
						.getSubimage(32, 48, 32, 32),
				"player");
		player = new Player("player", game.getWidth() / 2, game.getHeight() / 2);
		player.setSprite(sps);
		player.layer = 1;
		player.priority = 1;
		addObject(player);

		Camera camera = new Camera("cam0", player);
		addCamera(camera);
	}

	/**
	 * Add a camera to the system to track an object and move viewport.
	 * 
	 * @param camera
	 */
	private void addCamera(Camera camera) {
		if (activeCamera == null) {
			activeCamera = camera;
		}
		cameras.put(camera.name, camera);
	}

	/**
	 * Define the current active camera.
	 * 
	 * @param name the name of the camera to be added.
	 */
	public void setActiveCamera(String name) {
		assert (cameras.containsKey(name));
		this.activeCamera = cameras.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.GameState#update(com.snapgames.gdj.core.Game,
	 * long)
	 * 
	 */
	@Override
	public void update(Game game, long dt) {
	}

	@Override
	public void keyPressed(Game game, KeyEvent e) {
		super.keyPressed(game, e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			player.dy -= 2;
			break;
		case KeyEvent.VK_DOWN:
			player.dy += 2;
			break;
		case KeyEvent.VK_LEFT:
			player.dx -= 2;
			break;
		case KeyEvent.VK_RIGHT:
			player.dx -= 2;
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.AbstractGameState#keyReleased(com.snapgames.gdj.
	 * core.Game, java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(Game game, KeyEvent e) {
		super.keyReleased(game, e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			game.getGSM().activate("title");
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.GameState#input(com.snapgames.gdj.core.Game,
	 * com.snapgames.gdj.core.io.InputHandler)
	 */
	@Override
	public void input(Game game, InputHandler input) {

	}

	@Override
	public void render(Game game, Graphics2D g) {
		if (activeCamera != null) {
			activeCamera.beforeRender(g);
		}
		super.render(game, g);
		if (activeCamera != null) {
			activeCamera.afterRender(g);
		}
	}
}
