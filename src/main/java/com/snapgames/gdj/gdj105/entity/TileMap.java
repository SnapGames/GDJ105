/**
 * SnapGames
 * <p>
 * Game Development Java
 * <p>
 * gdj104
 *
 * @year 2017
 */
package com.snapgames.gdj.gdj105.entity;

import com.google.gson.Gson;
import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.Actions;
import com.snapgames.gdj.core.entity.Camera;
import com.snapgames.gdj.core.entity.DynamicGameObject;
import com.snapgames.gdj.core.gfx.Sprite;
import com.snapgames.gdj.core.gfx.SpriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * The TileMap object.
 *
 * @author Frédéric Delorme
 */
public class TileMap extends AbstractGameObject {


    public final Logger logger = LoggerFactory.getLogger(TileMap.class);
    private String comment;
    private SpriteSheet tileSprites;
    private int mapWidth = 0;
    private int mapHeight = 0;
    private int tileWidth = 0;
    private int tileHeight = 0;
    private MapTileDescription[] tileJsonMap;
    private Map<String, Integer> mappedTiles = new HashMap<>();
    private Map<String, MapTileDescription> mapTiles = new HashMap<>();
    private Map<String, Sprite> sprites = new HashMap<>();
    private BufferedImage bgi;
    private float playerX;
    private float playerY;
    private Map<String, Enemy> enemies = new HashMap<>();

    int minX = 0, minY = 0;
    int maxX = 0, maxY = 0;

    private Camera camera;

    /**
     * TileMap constructor.
     *
     * @param name
     */
    public TileMap(String name) {
        super(name, 0, 0);
    }

    /**
     * Load tileSprites into the map fril the file !
     *
     * @param path
     */
    public void loadTileFile(String path) {
        if (path.endsWith(".json")) {
            String mapStr = ResourceManager.getText(path);
            parseFromJSON(mapStr);
        }
        // Compute BoundingBox.
        this.width = tileWidth * mapWidth;
        this.height = tileHeight * mapHeight;
        minX = 0;
        minY = 0;
        maxX = mapWidth;
        maxY = mapHeight;
    }

    private void parseFromJSON(String jsonFile) {
        MapDescription map = new Gson().fromJson(jsonFile, MapDescription.class);
        this.name = map.name;
        this.comment = map.comment;
        this.tileWidth = map.tileWidth;
        this.tileHeight = map.tileHeight;
        this.mapWidth = map.mapWidth;
        this.mapHeight = map.mapHeight;
        extractSpriteSheet(map.spriteSheet, this.tileWidth, this.tileHeight);
        extractBackground(map.background);
        extractTiles(map);
        parseJsonTileMap(map);
    }

    private void extractTiles(MapDescription map) {
        for (MapTileDescription tile : map.tiles) {
            mapTiles.put(tile.id, tile);
            logger.info("map has been parsed : tile={}", tile);
        }
    }

    private void extractBackground(String attribute) {
        bgi = ResourceManager.getImage(attribute);
    }

    private void extractSpriteSheet(String name, int tileWidth, int tileHeight) {
        BufferedImage spritesImg = ResourceManager.getImage(name);
        tileSprites = new SpriteSheet("tileSprites", spritesImg, tileWidth, tileHeight);
        tileSprites.generate();
    }

    /**
     * Lines parsed to build the real map.
     *
     * @param map the map where lines must be parsed.
     */
    private void parseJsonTileMap(MapDescription map) {

        int index = 0;
        String line = "";
        tileJsonMap = new MapTileDescription[map.mapWidth * map.mapHeight];
        int enemyIndex = 0;

        int y = 0;
        while (index < map.tileMap.length) {
            line = map.tileMap[index];
            for (int x = 0; x < mapWidth; x++) {
                String value = "" + line.charAt(x);
                tileJsonMap[(y * mapWidth) + x] = mapTiles.get(value);

                if (tileJsonMap[(y * mapWidth) + x].id.equals("P")) {
                    this.playerX = x * tileWidth;
                    this.playerY = y * tileHeight;
                }
                if (tileJsonMap[(y * mapWidth) + x].id.equals("E")) {
                    Enemy enemy = new Enemy("enemy_" + (enemyIndex++), x * tileWidth, y * tileHeight);
                    this.enemies.put("e" + (enemyIndex++), enemy);
                }
            }
            y++;
            index++;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.snapgames.gdj.core.entity.AbstractGameObject#draw(com.snapgames.gdj.core.
     * Game, java.awt.Graphics2D)
     */
    @Override
    public void draw(Game game, Graphics2D g) {
        int minX = 0, minY = 0;
        int maxX = mapWidth, maxY = mapHeight;
        int midXScreen = game.getWidth() / 2;
        int midYScreen = game.getHeight() / 2;

        if (bgi != null) {
            g.drawImage(bgi, 0, 0, null);
        }
        // compute zone to be rendered according to game current viewport.

        if (camera != null) {
            minX = (int) Math.min(Math.abs((this.camera.x - 32 - midXScreen) / tileWidth), 0);
            maxX = (int) Math.min((this.camera.x + camera.width + 64 + midXScreen) / tileWidth, mapWidth);
            minY = (int) Math.min(Math.abs((this.camera.y - 32 - midYScreen) / tileHeight), 0);
            maxY = (int) Math.min((this.camera.y + camera.height + 64 + midYScreen) / tileHeight, mapHeight);
        }
        for (int y = minY; y < maxY; y++) {

            for (int x = minX; x < maxX; x++) {

                int index = tileJsonMap[x + (y * mapWidth)].value;

                if (index > 0 && index < 240) {
                    Sprite tile = tileSprites.getSprite(index);
                    if (tile != null) {
                        tile.draw(g, x * tileWidth, y * tileHeight);
                    }
                }

            }
        }

    }

    /**
     * Capture player position ('P' character) from the map and initialize the AbstractGameObject with.
     *
     * @param player the AbstractGameObject which is the Game player character.
     */
    public void setPlayerPosition(AbstractGameObject player) {
        player.setPosition(this.playerX, this.playerY);
        logger.info("Move player {} to position ({},{})", player.getName(), playerX, playerY);
    }

    /**
     * retrieve Tile id unde the <code>(gx,gy)</code> position.
     *
     * @param gx the horizontal graphic position
     * @param gy the vertical graphic position
     * @return
     */
    public MapTileDescription getTile(float gx, float gy) {
        if ((gx > 0 && gx < mapWidth * tileWidth) &&
                (gy > 0 && gy < mapHeight * tileHeight)) {
            int x = (int) gx / tileWidth;
            int y = (int) gy / tileHeight;
            return this.tileJsonMap[x + (y * mapWidth)];
        } else {
            return null;
        }
    }

    /**
     * resolve DynamicGameObject o position accordingto the list of Colliding points.
     *
     * @param o the DynamicGameObject to solve new position according to detected colliding points
     * @return
     */
    public DynamicGameObject resolve(DynamicGameObject o) {
        if (o.collisionBox != null) {
            // X coordinates
            float tlX = o.boundingBox.x;
            float trX = o.boundingBox.x + o.boundingBox.width;
            float blX = o.boundingBox.x;
            float brY = o.boundingBox.y + o.boundingBox.height;

            // Y coordinates
            float tlY = o.boundingBox.y;
            float blY = o.boundingBox.y + o.boundingBox.height;

            MapTileDescription tile, tile2;

            // Test on horizontal movement
            if (o.dx < 0) {
                tile = getTile(tlX, tlY);
                if (tile != null) {
                    int i = tile.blocker;

                    if (i != 0) {
                        o.x = (((int) (tlX / tileWidth) + 1) * tileWidth);
                        o.ax = -o.ax * o.elasticity;
                    }
                }
            }
            if (o.dx > 0) {
                tile = getTile(trX, tlY);
                if (tile != null) {
                    int i = tile.blocker;

                    if (i != 0) {
                        o.x = (((int) ((trX / tileWidth)) * tileWidth) - o.width);
                        o.ax = -o.ax * o.elasticity;
                    }
                }
            }
            // Test on vertical movement
            if (o.dy < 0) {
                tile = getTile(blX, blY);
                if (tile != null) {
                    int i = tile.blocker;
                    o.action = Actions.JUMP;
                    if (i != 0) {
                        o.y = (((int) (blY / tileHeight) + 1) * tileHeight);
                        o.ay = -o.ay * o.elasticity;
                        o.action = Actions.FALL;
                    }
                }
            }
            if (o.dy > 0) {
                tile = getTile(blX, brY);
                if (tile != null) {
                    int i = tile.blocker;
                    if (i != 0) {
                        o.y = (((int) ((brY / tileHeight)) * tileHeight) - o.height);
                        o.ay = -o.ay * o.elasticity;
                        if (!o.previousAction.equals(Actions.WALK)) {
                            o.action = Actions.IDLE;
                        }
                    } else {
                        o.action = Actions.FALL;
                    }
                }

            }
            constrains(o);
        }
        return o;
    }

    /**
     * Constrains the DynamicGameObject to the Game view (mainly the TileMap area size).
     *
     * @param o the object to be constrained to the game area.
     */
    private void constrains(DynamicGameObject o) {
        if (o.x < 0) o.x = 0;
        if (o.y < 0) o.y = 0;
        if (o.x > this.boundingBox.width) o.x = this.boundingBox.width;
        if (o.y > this.boundingBox.height) o.y = this.boundingBox.height;

    }

    /**
     * Camera to be track for TileMap rendering.
     *
     * @param cam camera targeting for TileMap rendering;
     */
    public void setCamera(Camera cam) {
        this.camera = cam;
    }

    public class MapTileDescription {
        public String id;
        public String type;
        public int value;
        public int blocker;
        public int[] frames;
        public Map<String, String> attributes;

    }

    public class MapDescription {
        public String name;
        public String comment;
        public String background;
        public String spriteSheet;
        public int mapWidth;
        public int mapHeight;
        public int tileWidth;
        public int tileHeight;
        public MapTileDescription[] tiles;
        public String[] tileMap;
    }

    @Override
    public void addDebugInfo(Game game) {
        super.addDebugInfo(game);
        debugInfo.add(String.format("rendArea:(%03d,%03d)-(%03d,%03d)", minX, minY, maxX, maxY));

    }
}
