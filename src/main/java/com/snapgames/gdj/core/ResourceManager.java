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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
	 * retrieve from cache if exists or load the text file resource named <code>name</code>.
	 *
	 * @param name the name of the text resource to load.
	 * @return the text (as string) for this particular resource.
	 */
	public static String getText(String name) {
		return (String) getInstance().addResource(name);
	}

	/**
	 * retrieve a text file.
	 *
	 * @param name         the name of the resource to load.
	 * @param forcedReload flag to request a cache passthrough
	 * @return the text (as string) for this particular resource updated if requested.
	 */
	public static String getText(String name, boolean forcedReload) {
		return (String) getInstance().loadResource(name, forcedReload);
	}

	/**
	 * the private constructor to instantiate the {@link ResourceManager} only from
	 * the getInstance(when needed). 
	 */
	private ResourceManager() {

	}
	/** This main method is able to read some type of data (now Images and Font)
	 * and store them in the resource cache.
	 *
	 * @param name the name (file name) of the resource to be loaded.
	 * @return Object return as resource for the <code>name</code> file.
	 */
	private Object addResource(String name) {
		assert (resources != null);
		assert (name != null);
		if (!resources.containsKey(name)) {
			putResource(name);
		}
		return resources.get(name);
	}

	/**
	 * Add a specific value for a resource
	 * @param name name of this resource.
	 * @param value the value (Object) for this object.
	 */
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
	 * @param name name of this resource.
	 * @return the value (Object) for this object.
	 */
	public static Object get(String name) {
		return getInstance().addResource(name);
	}

	/**
	 * Add an external (<code>name</code>,<code>value</code>) resource to the
	 * manager.
	 * 
	 * @param name name of this resource.
	 * @param value the value (Object) for this object.
	 */
	public static void add(String name, Object value) {
		getInstance().addResource(name, value);
	}

	/**
	 * Retrieve an image <code>name</code> from the resources.
	 * 
	 * @param name name of this resource.
	 * @return a BuffuredImage as the name resource requested.
	 */
	public static BufferedImage getImage(String name) {
		logger.info("load resourve {}",name);
		return (BufferedImage) getInstance().addResource(name);
	}

	/**
	 * Retrieve a Font <code>name</code> from the resources
	 * 
	 * @param name name of this resource.
	 * @return Font requested.
	 */
	public static Font getFont(String name) {
		return (Font) getInstance().addResource(name);
	}

	/**
	 * return the instance of this Resource Manager.
	 * 
	 * @return instance of the ResourceManager.
	 */
	public static ResourceManager getInstance() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}

	/**
	 * Force cache to load resource again.
	 *
	 * @param name name of the resource to reload.
	 * @return Object correspoding to the name.
	 */
	private Object loadResource(String name, boolean forcedReload) {
		assert (resources != null);
		assert (name != null);
		if (forcedReload) {
			putResource(name);
		} else {
			addResource(name);
		}
		return resources.get(name);
	}

	/**
	 * Add a name resource to the cache. according to its file extension name,
	 * the cache is prepared as the corresponding Java Object.
	 * @param name name of the resource to be loaded (a File name).
	 */
	private void putResource(String name) {
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
				break;
            case ".txt":
			case ".map":
            case ".json":
				InputStream stream = this.getClass().getResourceAsStream(name);
				String value = new BufferedReader(
						new InputStreamReader(stream))
						.lines().parallel()
						.collect(Collectors.joining("\n"));
				resources.put(name, value);
			break;
		}
	}
}
