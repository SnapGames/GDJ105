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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.Actions;
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

        if (bgi != null) {
            g.drawImage(bgi, 0, 0, null);
        }

        for (int y = 0; y < mapHeight; y++) {

            for (int x = 0; x < mapWidth; x++) {

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
            float tlX = o.x;
            float trX = o.x + o.width;
            float blX = o.x;
            float brY = o.y + o.height;

            // Y coordinates
            float tlY = o.y;
            float blY = o.y;

            // Test on horizontal movement
            if (o.dx < 0) {
                int i = getTile(tlX, tlY).blocker;
                if (i != 0) {
                    o.x = (((int) (tlX / tileWidth) + 1) * tileWidth);
                    o.ax = -o.ax * o.elasticity;
                }
            }
            if (o.dx > 0) {
                int i = getTile(trX, tlY).blocker;
                if (i != 0) {
                    o.x = (((int) ((trX / tileWidth)) * tileWidth) - o.width);
                    o.ax = -o.ax * o.elasticity;
                }

            }
            // Test on vertical movement
            if (o.dy < 0) {
                int i = getTile(blX, blY).blocker;
                o.action = Actions.JUMP;
                if (i != 0) {
                    o.y = (((int) (blY / tileHeight) + 1) * tileHeight);
                    o.ay = -o.ay * o.elasticity;
                    o.action = Actions.FALL;
                }
            }
            if (o.dy > 0) {
                int i = getTile(blX, brY).blocker;
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class MapTileDescription {
        @JsonProperty("id")
        public String id;
        @JsonProperty("type")
        public String type;
        @JsonProperty("value")
        public int value;
        @JsonProperty("blocker")
        public int blocker;
        @JsonProperty("frames")
        public int[] frames;
        @JsonProperty("attributes")
        public Map<String, String> attributes;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class MapDescription {
        @JsonProperty("name")
        public String name;
        @JsonProperty("comment")
        public String comment;
        @JsonProperty("background")
        public String background;
        @JsonProperty("spritesheet")
        public String spriteSheet;
        @JsonProperty("mapWidth")
        public int mapWidth;
        @JsonProperty("mapHeight")
        public int mapHeight;
        @JsonProperty("tileWidth")
        public int tileWidth;
        @JsonProperty("tileHeight")
        public int tileHeight;
        @JsonProperty("tileSprites")
        public MapTileDescription[] tiles;
        @JsonProperty("tileMap")
        public String[] tileMap;
    }

}
