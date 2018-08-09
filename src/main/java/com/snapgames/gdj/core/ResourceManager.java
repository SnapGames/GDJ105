/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj104
 * 
 * @year 2017
 */
package com.snapgames.gdj.core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The resourceManager is an internal cache to manage resources like images,
 * fonts, sounds, musics used by the game.
 * 
 * It's an easy way to abstract resource access from any part of the game. It
 * also abstract access to {@link Font}, {@link BufferedImage}, and any object
 * the ResourceManager will be able to support.
 * 
 * @author Frédéric Delorme
 *
 */
public class ResourceManager {

	private static ResourceManager instance = null;

	private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

	/**
	 * Internal Cache to store resources.
	 */
	private Map<String, Object> resources = new ConcurrentHashMap<>();

	/**
	 * the private constructor to instantiate the {@link ResourceManager} only from
	 * the getInstance(when needed). private ResourceManager() {
	 * 
	 * }
	 * 
	 * /** This main method is able to read some type of data (now Images and Font)
	 * and store them in the resource cache.
	 * 
	 * @param name the name (file name) of the resource to be loaded.
	 * @return
	 */
	private Object addResource(String name) {
		assert (resources != null);
		assert (name != null);
		if (!resources.containsKey(name)) {
			String extension = name.substring(name.lastIndexOf("."), name.length());
			switch (extension) {
			// load an image resource?
			case ".jpg":
			case ".png":
				try {
					BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
					resources.put(name, image);
				} catch (IOException e) {
					logger.error("unable to find resource for {}", name);
				}
				break;
			// load a Font resource
			case ".ttf":
				try {
					InputStream stream = this.getClass().getResourceAsStream(name);
					Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
					resources.put(name, font);
				} catch (FontFormatException | IOException e) {
					logger.error("Unable to read font from {}", name);
				}
			}
		}
		return resources.get(name);
	}

	private void addResource(String name, Object value) {
		assert (resources != null);
		assert (name != null);
		assert (value != null);
		if (!resources.containsKey(name)) {
			resources.put(name, value);
		}
	}

	/**
	 * retrieve a resource for this name
	 * 
	 * @param name
	 * @return
	 */
	public static Object get(String name) {
		return getInstance().addResource(name);
	}

	/**
	 * Add an external (<code>name</code>,<code>value</code>) resource to the
	 * manager.
	 * 
	 * @param name
	 * @param value
	 */
	public static void add(String name, Object value) {
		getInstance().addResource(name, value);
	}

	/**
	 * Retrieve an image <code>name</code> from the resources.
	 * 
	 * @param name
	 * @return
	 */
	public static BufferedImage getImage(String name) {
		return (BufferedImage) getInstance().addResource(name);
	}

	/**
	 * Retrieve a Font <code>name</code> from the resources
	 * 
	 * @param name
	 * @return
	 */
	public static Font getFont(String name) {
		return (Font) getInstance().addResource(name);
	}

	/**
	 * return the instance of this Resource Manager.
	 * 
	 * @return
	 */
	public static ResourceManager getInstance() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
}
