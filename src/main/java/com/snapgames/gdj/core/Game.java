/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ105
 * 
 * @year 2017
 */
package com.snapgames.gdj.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snapgames.gdj.core.gfx.ImageUtils;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.GameStateManager;
import com.snapgames.gdj.core.ui.Window;

/**
 * the basic Game container is a JPanel child.
 * 
 * @author Frédéric Delorme
 *
 */
public class Game extends JPanel {

	/**
	 * Internal logger.
	 */
	public static final Logger logger = LoggerFactory.getLogger(Game.class);

	/**
	 * Game screen width.
	 */
	public final static int WIDTH = 360;
	/**
	 * Game screen height.
	 */
	public final static int HEIGHT = 240;
	/**
	 * game screen scaling
	 */
	public final static float SCALE = 2.0f;

	public final static float SCREEN_FONT_RATIO = HEIGHT / 22;

	/**
	 * Number of frame per seconds
	 */
	public long FPS = 60;
	/**
	 * duration of a frame.
	 */
	public long fpsTargetTime = 1000 / 60;

	/**
	 * Number of frames in a second.
	 */
	public long framesPerSecond = 0;

	/**
	 * The rectangle containing the Game screen.
	 */
	public final static Rectangle bbox = new Rectangle(0, 0, WIDTH, HEIGHT);

	/**
	 * The title for the game instance.
	 */
	private String title = "game";

	/**
	 * Game display space dimension
	 */
	private Dimension dimension = null;

	/**
	 * Active window for this Game.
	 */
	private Window window;

	/**
	 * internal rendering buffer
	 */
	private BufferedImage image;

	/**
	 * Flag to activate debug information display.
	 */
	private int debug = 3;

	/**
	 * flag representing the exit request status. true => exit
	 */
	private boolean exit = false;

	/**
	 * Flag to track pause request.
	 */
	private boolean isPause = false;

	/**
	 * Flag to activate screenshot recording.
	 */
	private boolean screenshot = false;

	/**
	 * Rendering interface.
	 */
	private Graphics2D g;

	/**
	 * Input manager
	 */
	private InputHandler inputHandler;

	private GameStateManager gsm;

	/**
	 * the default constructor for the {@link Game} panel with a game
	 * <code>title</code>.
	 * 
	 * @param title
	 *            the title for the game.
	 */
	private Game(String title) {
		this.title = title;
		this.dimension = new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE));
		exit = false;
		gsm = new GameStateManager(this);
		inputHandler = new InputHandler(gsm);
	}

	/**
	 * Initialize the Game object with <code>g</code>, the Graphics2D interface to
	 * render things.
	 */
	private void initialize() {

		// Internal display buffer
		image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ResourceManager.add("debugFont", getRender().getFont().deriveFont(8.5f));

		gsm.activateDefaultState();
	}

	/**
	 * The main Loop !
	 */
	private void loop() {
		long currentTime = System.currentTimeMillis();
		long lastTime = currentTime;
		long second = 0;
		long framesCounter = 0;
		while (!exit) {
			currentTime = System.currentTimeMillis();
			long dt = currentTime - lastTime;

			// manage input
			input();
			if (!isPause) {
				// update all game's objects
				update(dt);
			}
			// render all Game's objects
			render(g);
			// copy buffer
			drawToScreen();

			// manage wait time
			long laps = System.currentTimeMillis() - lastTime;
			second += laps;
			framesCounter += 1;
			if (second >= 1000) {
				second = 0;
				framesPerSecond = framesCounter;
				framesCounter = 0;
			}
			long wait = fpsTargetTime - laps;

			logger.debug("FPS: {} (laps:{}, wait:{})", framesPerSecond, laps, wait);

			if (wait > 0) {
				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					logger.error("unable to wait 1 ms");
				}
			}
			lastTime = currentTime;

		}
	}

	/**
	 * Copy buffer to window.
	 */
	private void drawToScreen() {

		// copy buffer to window.
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE), 0, 0, WIDTH, HEIGHT, Color.BLACK,
				null);
		g2.dispose();

		if (screenshot) {
			ImageUtils.screenshot(image);
			screenshot = false;
		}
	}

	/**
	 * Manage Game input.
	 */
	private void input() {
		gsm.input(inputHandler);
	}

	/**
	 * Update game internals
	 * 
	 * @param dt
	 */
	private void update(long dt) {
		gsm.update(dt);
	}

	/**
	 * render all the beautiful things.
	 * 
	 * @param g
	 */
	private void render(Graphics2D g) {
		// clear display
		clearBuffer(g);

		gsm.render(g);
	}

	/**
	 * @param g
	 */
	private void clearBuffer(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	/**
	 * free all resources used by the Game.
	 */
	private void release() {
		gsm.dispose();
		window.getFrame().dispose();
	}

	/**
	 * the only public method to start game.
	 */
	public void run() {
		initialize();
		loop();
		release();
		System.exit(0);
	}

	/**
	 * request for a screen shot.
	 */
	public void captureScreenshot() {
		screenshot = true;

	}

	/**
	 * return the title of the game.
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * return the dimension of the Game display.
	 * 
	 * @return
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * Set the active window for this game.
	 * 
	 * @param window
	 *            the window to set as active window for the game.
	 */
	public void setWindow(Window window) {
		this.window = window;
	}

	/**
	 * @return the exit
	 */
	public boolean isExit() {
		return exit;
	}

	/**
	 * @param exit
	 *            the exit to set
	 */
	public void setExit(boolean exit) {
		this.exit = exit;
	}

	/**
	 * @return the inputHandler
	 */
	public InputHandler getInputHandler() {
		return inputHandler;
	}

	/**
	 * return debug activation flag. true, visual debug is activated, false, normal
	 * rendering.
	 * 
	 * @return
	 */
	public boolean isDebug(int level) {
		return debug >= level;
	}

	/**
	 * @return the pause
	 */
	public boolean isPause() {
		return isPause;
	}

	public Graphics2D getRender() {
		return g;
	}

	public float getScale() {
		return SCALE;
	}

	public void requestPause() {
		isPause = !isPause;

	}

	/**
	 * Activate the debug mode.
	 * 
	 * @param b
	 */
	public void setDebug(int level) {
		debug = level;
	}

	/**
	 * @return the debug
	 */
	public int getDebug() {
		return debug;
	}

	/**
	 * return the GameStateManager for this game.
	 * 
	 * @return
	 */
	public GameStateManager getGSM() {
		return gsm;
	}

	/**
	 * The main entry point to start our GDJ104 game.
	 * 
	 * @param argv
	 *            list of arguments from command line.
	 */
	public static void main(String[] argv) {
		Game game = new Game("Kingdom of Asperion");
		new Window(game);
		game.run();
	}

}
