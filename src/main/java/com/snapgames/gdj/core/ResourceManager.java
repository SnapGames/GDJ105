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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frédéric Delorme
 *
 */
public class ResourceManager {

	private static ResourceManager instance = null;

	private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

	private Map<String, Object> resources = new ConcurrentHashMap<>();

	private ResourceManager() {

	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private Object getResource(String name) {
		if (!resources.containsKey(name)) {
			String extension = name.substring(name.lastIndexOf("."), name.length());
			switch (extension) {
			case ".jpg":
			case ".png":
				try {
					BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
					resources.put(name, image);
				} catch (IOException e) {
					logger.error("unable to find resource for {}", name);
				}
				break;
			}
		}
		return resources.get(name);
	}

	/**
	 * retrieve a resource for this name
	 * 
	 * @param name
	 * @return
	 */
	public static Object get(String name) {
		return getInstance().getResource(name);
	}

	/**
	 * Retrieve an image from the resources.
	 * 
	 * @param name
	 * @return
	 */
	public static BufferedImage getImage(String name) {
		return (BufferedImage) getInstance().getResource(name);
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
