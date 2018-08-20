/**
 * SnapGames
 * <p>
 * Game Development Java
 * <p>
 * gdj105
 *
 * @year 2018
 */
package com.snapgames.gdj.gdj105.states;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.Actions;
import com.snapgames.gdj.core.entity.Camera;
import com.snapgames.gdj.core.entity.Direction;
import com.snapgames.gdj.core.entity.GameObject;
import com.snapgames.gdj.core.gfx.DebugLevel;
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;
import com.snapgames.gdj.core.gfx.Sprite;
import com.snapgames.gdj.core.i18n.Messages;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.UIText;
import com.snapgames.gdj.gdj105.entity.Player;
import com.snapgames.gdj.gdj105.entity.TileMap;
import com.snapgames.gdj.gdj105.i18n.Labels;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This state is an attempt to build a playable level for an hypothetic game.
 *
 * @author Frédéric Delorme
 */
public class PlayState extends AbstractGameState {

    private Map<String, Camera> cameras = new ConcurrentHashMap<>();
    private Camera activeCamera;

    private TileMap tilemap;
    private Player player;
    private Camera camera;

    private Point2D playerInitialPos;

    /*
     * (non-Javadoc)
     *
     * @see
     * com.snapgames.gdj.core.state.AbstractGameState#initialize(com.snapgames.gdj.
     * core.Game)
     */
    @Override
    public void initialize(Game game, boolean forcedReload) {
        super.initialize(game, forcedReload);
        cameras.clear();

        // read i18n labels
        String titleLabel = Messages.getString(Labels.PLAY_TITLE.getKey());

        // Prepare fonts.
        Font titleFont = ResourceManager.getFont("/res/fonts/Prince Valiant.ttf")
                .deriveFont(2.5f * Game.SCREEN_FONT_RATIO);

        game.getWindow().setTitle("playState");

        // Add the TileMap for this test level
        tilemap = new TileMap("tilemap");
        tilemap.loadTileFile("/res/maps/level-001.map");
        tilemap.layer = 2;
        addObject(tilemap);

        // Define the main Game title object
        UIText titleText = new UIText("title", (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.05f), titleLabel,
                titleFont, 1, 1, Color.WHITE, Justification.CENTER);
        titleText.setLabel(Labels.PLAY_TITLE.getKey());
        titleText.layer = 1;
        titleText.priority = 2;
        addObject(titleText);

        // Add the Player object with its display sprite.
        Sprite sps = new Sprite(
                ResourceManager
                        .getImage("/res/images/sprite-0001.png")
                        .getSubimage(32, 48, 32, 32),
                "player");
        player = new Player("player", 32, 32);
        tilemap.setPlayerPosition(player);
        player.setSprite(sps);
        player.dx = 0;
        player.dy = 0;
        player.layer = 1;
        player.priority = 1;
        player.showDebuginfo = true;
        addObject(player);
        playerInitialPos = new Point2D.Float(player.x, player.y);

        Camera camera = new Camera("cam0", player);
        camera.setTweenFactor(0.004f);
        addCamera(camera);
    }

    /**
     * Add a camera to the system to track an object and move viewport.
     * If there is no already active camera, this new camera is defined as the active one.
     *
     * @param camera the Camera object to be added to the Camera list
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
        for (GameObject o : objects) {
            o.update(game, dt);
        }
        if (player != null) {
            player = (Player) tilemap.resolve(player);
            player.computeCollisionBox();
        }
        if (activeCamera != null) {
            activeCamera.update(game, dt);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.snapgames.gdj.core.state.AbstractGameState#keyPressed(com.snapgames.gdj.
     * core.Game, java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(Game game, KeyEvent e) {
        super.keyPressed(game, e);
        switch (e.getKeyCode()) {
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
            // Escape reload the Title Screen.
            case KeyEvent.VK_ESCAPE:
                game.getGSM().activate("title");
                break;
            // Add the T interaction to move the Player object at its first position
            // only activated if debug mode on.
            case KeyEvent.VK_T:
                if (game.isDebug(DebugLevel.DEBUG_FPS)) {
                    player.x = (float) playerInitialPos.getX();
                    player.y = (float) playerInitialPos.getY();
                    activeCamera.setTarget(player);
                }
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
        if (player != null) {
            player.dx = 0f;
            player.dy = 0f;
            player.action = Actions.IDLE;

            if (input.getKeyPressed(KeyEvent.VK_UP)
                    && !player.previousAction.equals(Actions.JUMP)
                    && !player.previousAction.equals(Actions.FALL)) {
                player.ay = -0.024f;
                player.action = Actions.JUMP;
            }

            if (input.getKeyPressed(KeyEvent.VK_DOWN)
                    && !player.action.equals(Actions.JUMP)) {
                player.ay = 0.005f;
                player.action = Actions.IDLE;
            }

            if (input.getKeyPressed(KeyEvent.VK_LEFT)) {
                player.ax = -0.005f;
                player.direction = Direction.LEFT;
                player.action = Actions.WALK;
            }

            if (input.getKeyPressed(KeyEvent.VK_RIGHT)) {
                player.ax = 0.005f;
                player.direction = Direction.RIGHT;
                player.action = Actions.WALK;
            }
            player.previousAction = player.action;
        }
    }

    /**
     * Render the PlayState's objects
     *
     * @param game
     * @param g
     */
    @Override
    public void render(Game game, Graphics2D g) {
        // Process the before Camera rendering
        if (activeCamera != null) {
            activeCamera.beforeRender(g);
        }
        // Render all objects
        super.render(game, g);

        // Process the after Camera rendering
        if (activeCamera != null) {
            activeCamera.afterRender(g);
            // add some camera info to the debug rendering display.
            activeCamera.addDebugInfo(game);
            activeCamera.drawSpecialDebugInfo(game, g);
            activeCamera.getDebugInfo().clear();
        }
    }

}
