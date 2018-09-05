/**
 * SnapGames
 * <p>
 * Game Development Java
 * <p>
 * GDJ105
 *
 * @year 2017
 */
package com.snapgames.gdj.core;

import com.snapgames.gdj.core.gfx.DebugLevel;
import com.snapgames.gdj.core.gfx.ImageUtils;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.GameStateManager;
import com.snapgames.gdj.core.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
    public final static float SCREEN_FONT_RATIO = HEIGHT / 22;
    /**
     * The boundingBox containing the Game screen.
     */
    public final static Rectangle bbox = new Rectangle(0, 0, WIDTH, HEIGHT);
    /**
     * game screen scaling
     */
    private final static float SCALE = 2.0f;
    /**
     * Number of update per seconds
     */
    public long UPS = 60;
    public long updatePerSecond = 0;
    /**
     * Number of frame per seconds
     */
    public long FPS = 30;
    public long framesPerSecond = 0;
    private long upsTargetTime = 1000 / UPS;
    private long fpsTargetTime = 1000 / FPS;
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
    private DebugLevel debug = DebugLevel.DEBUG_NONE;

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
     * @param title the title for the game.
     */
    private Game(String title) {
        this.title = title;
        this.dimension = new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE));
        exit = false;
        gsm = new GameStateManager(this);
        inputHandler = new InputHandler(gsm);
    }

    /**
     * The main entry point to start our GDJ104 game.
     *
     * @param argv list of arguments from command line.
     */
    public static void main(String[] argv) {
        Game game = new Game("Kingdom of Asperion");
        new Window(game);
        game.run();
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
        long ulastTime = lastTime;
        long second = 0;
        long framesCounter = 0, updateCounter = 0;
        int i = 0;
        while (!exit) {
            currentTime = System.currentTimeMillis();
            long dt = currentTime - lastTime;

            // manage input
            input();
            if (!isPause) {
                while (i <= 5) {
                    dt = currentTime - ulastTime;
                    // update all game's objects
                    update(dt);
                    updateCounter += 1;
                    i++;
                    ulastTime = currentTime;
                }
                i = 0;
            }
            ulastTime = currentTime;

            // copy buffer
            drawToScreen();

            // manage wait time
            long laps = System.currentTimeMillis() - lastTime;
            second += laps;
            // render all Game's objects
            render(g);
            // compute number of frame per seconds
            framesCounter += 1;
            if (second >= 1000) {
                second = 0;
                framesPerSecond = framesCounter;
                updatePerSecond = updateCounter;
                framesCounter = 0;
                updateCounter = 0;
            }
            long wait = fpsTargetTime - laps;

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
        g.translate(0, 0);
        gsm.render(g);
    }

    /**
     * @param g
     */
    private void clearBuffer(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
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
     * @return the title for this game.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set Game title.
     *
     * @param title the tatle to be assigned.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * return the dimension of the Game display.
     *
     * @return the dimension of the game.
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * @return the exit
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * @param exit the exit to set
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
    public boolean isDebug(DebugLevel level) {
        return debug.isDebugLevel(level);
    }

    /**
     * @return the pause
     */
    public boolean isPause() {
        return isPause;
    }

    /**
     * The Graphics API
     * @return a Graphics2D instance.
     */
    public Graphics2D getRender() {
        return g;
    }

    /**
     * return the scaling of this game.
     * @return a float value for this game scale factor.
     */
    public float getScale() {
        return SCALE;
    }

    /**
     * Switch Pause status.
     */
    public void requestPause() {
        isPause = !isPause;

    }

    /**
     * @return the debug
     */
    public DebugLevel getDebug() {
        return debug;
    }

    /**
     * Activate the debug mode.
     *
     * @param level the debug level to be activated
     * @see DebugLevel
     */
    public void setDebug(DebugLevel level) {
        debug = level;
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
     * return the current window object f the game.
     *
     * @return a Window object.
     * @see Window
     */
    public Window getWindow() {

        return window;
    }

    /**
     * Set the active window for this game.
     *
     * @param window the window to set as active window for the game.
     */
    public void setWindow(Window window) {
        this.window = window;
    }
}
