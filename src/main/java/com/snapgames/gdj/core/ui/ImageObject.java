/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ105
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;

/**
 * The Background image is a simple image to be displayed in background.
 * 
 * @author Frédéric Delorme
 *
 */
public class ImageObject extends AbstractGameObject {

	public BufferedImage image;

	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param layer
	 * @param priority
	 * @param color
	 */
	public ImageObject(String name, BufferedImage image, int x, int y, int layer, int priority) {
		super(name, x, y, image.getWidth(), image.getHeight(), layer, priority, null);
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
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
		g.drawImage(image, (int) (x), (int) (y), null);
	}
}
