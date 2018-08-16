package com.snapgames.gdj.gdj105.entity;

/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj104
 * 
 * @year 2017
 */

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.AbstractGameObject;
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
 *
 */
public class TileMap extends AbstractGameObject {

	public final Logger logger = LoggerFactory.getLogger(TileMap.class);

	private SpriteSheet tiles;
	private int mapWidth = 0;
	private int mapHeight = 0;
	private int tileWidth = 0;
	private int tileHeight = 0;
	private int[] tileMap;
	private Map<String, Integer> mappedTiles = new HashMap<>();
	private BufferedImage bgi;
	private float playerX;
	private float playerY;

	/**
	 * TileMap constructor.
	 * 
	 * @param name
	 */
	public TileMap(String name) {
		super(name, 0, 0);
	}

	/**
	 * Load tiles into the map fril the file !
	 * 
	 * @param path
	 */
	public void loadTileFile(String path) {
		String mapStr = ResourceManager.getText("/res/maps/level-001.map");
		parseMapFromText(mapStr);

	}

	/**
	 * Parse all the map file to generate the real map.
	 * 
	 * @param mapStr
	 */
	private void parseMapFromText(String mapStr) {

		String[] lines = mapStr.split("\n");

		for (int index = 0; index < lines.length; index++) {
			String line = lines[index];
			String[] attribute = line.split(":");
			switch (attribute[0]) {
			case "name":
				this.name = attribute[1];
				break;

			case "spriteSheet":
				BufferedImage spritesImg = ResourceManager.getImage(attribute[1]);
				tiles = new SpriteSheet("tiles", spritesImg, 16, 16);
				tiles.generate();
				break;
			case "background":
				bgi = ResourceManager.getImage(attribute[1]);
				break;
				
			case "mapSize":
				String[] tileMapValue = attribute[1].split("x");
				mapWidth = Integer.parseInt(tileMapValue[0]);
				mapHeight = Integer.parseInt(tileMapValue[1]);
				tileMap = new int[mapWidth * mapHeight];
				break;

			case "tileSize":
				String[] tileSizeValue = attribute[1].split("x");
				tileWidth = Integer.parseInt(tileSizeValue[0]);
				tileHeight = Integer.parseInt(tileSizeValue[1]);
				break;

			case "tile":
				String[] mappedTiled = attribute[1].split("=");
				if (!mappedTiles.containsKey(mappedTiled[0])) {
					mappedTiles.put(mappedTiled[0], Integer.parseInt(mappedTiled[1]));
				}
				break;

			case "tileMap":
				index++;
				index = parseTileMap(lines, index);
				break;
			default:
				logger.error("TileMap: parserMap: unable to read {}, tag unknown.", attribute[0]);
				break;
			}
		}

	}

	/**
	 * Lines parsed to build the real map.
	 * 
	 * @param lines the map lines to be parsed.
	 * @param index line index in the map.
	 */
	private int parseTileMap(String[] lines, int index) {

		String line = lines[index];

		int y = 0;

		while (!line.equals("end")) {
			for (int x = 0; x < mapWidth; x++) {
				tileMap[(y * mapWidth) + x] = mappedTiles.get("" + line.charAt(x));
				if (tileMap[(y * mapWidth) + x] == 999) {
					this.playerX = x * tileWidth;
					this.playerY = y * tileHeight;
				}
			}
			y++;
			index++;
			line = lines[index];
		}
		// Compute BoundingBox.
		this.rectangle = new Rectangle((int) (tileWidth * x), (tileHeight * y));
		// update debug info
		debugInfo.add((String.format("tile:%dx%d", tileWidth, tileHeight)));
		debugInfo.add((String.format("map:%dx%d", mapWidth, mapHeight)));

		return index;
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

		if(bgi!=null) {
			g.drawImage(bgi, 0, 0, null);
		}
		for (int y = 0; y < mapHeight; y++) {

			for (int x = 0; x < mapWidth; x++) {
				int index = tileMap[x + (y * mapWidth)];
				if (index != 999) {
					Sprite tile = tiles.getSprite(index);
					tile.draw(g, x * tileWidth, y * tileHeight);
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
}
