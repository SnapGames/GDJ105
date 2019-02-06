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

/**
 * <p>
 * The <code>UIImage</code> component is a simple image rendering component to be
 * displayed as a background (as example) or as a simple image on screen.
 * 
 * <p>
 * It is inhering from the {@link UIGameObject} component
 * 
 * <p>
 * The image can be
 * <ul>
 * <li>simply rendered,</li>
 * <li>or repeated horizontaly,</li>
 * <li>or repeated verticaly,</li>
 * <li>or both ({@link Repeat}).</li>
 * </ul>
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 * @see UIGameObject
 * @see Repeat
 */
public class UIImage extends UIGameObject {

	public BufferedImage image;
	public Repeat repeat;
	int xmax = 0, ymax = 0;

	/**
	 * The default constructor for the UIImage component
	 * 
	 * @param name     name of this UIImage component
	 * @param x        the x position
	 * @param y        the y position
	 * @param width    the width of the UIImage
	 * @param height   the height of the UIImage
	 * @param layer    the layer where to render this object
	 * @param priority the priority of this object in the rendering pipeline for
	 *                 this layer
	 * @param color    the default color for this object (if no image is provided)
	 */
	public UIImage(String name, BufferedImage image, int x, int y, int layer, int priority) {
		super(name, x, y, image.getWidth(), image.getHeight(), layer, priority, null);
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.repeat = Repeat.NONE;
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
		/**
		 * @see Repeat
		 */
		switch (repeat) {
		case NONE:
			g.drawImage(image, (int) (x), (int) (y), null);
			break;
		case HORIZONTAL_ONE:
			g.drawImage(image, (int) (x) + width, (int) (y), null);
			break;
		case HORIZONTAL_INFINITY:
			xmax = ((int) (game.getWidth() / width) + 1);
			for (int rx = 0; rx < xmax; rx += 1) {
				g.drawImage(image, (int) (x) + (rx * width), (int) (y), null);
			}
			break;
		case VERTICAL_ONE:
			g.drawImage(image, (int) (x), (int) (y) + height, null);
			break;
		case VERTICAL_INFINITY:
			ymax = ((int) (game.getHeight() / height) + 1);
			for (int ry = 0; ry < ymax * height; ry += height) {
				g.drawImage(image, (int) (x), (int) (y) + ry, null);
			}
			break;
		case BOTH:
			xmax = ((int) (game.getWidth() / width) + 1);
			ymax = ((int) (game.getHeight() / height) + 1);
			for (int rx = 0; rx < xmax; rx += 1) {
				for (int ry = 0; ry < ymax * height; ry += height) {
					g.drawImage(image, (int) (x), (int) (y) + ry, null);
				}
			}
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.AbstractGameObject#addDebugInfo()
	 */
	@Override
	public void addDebugInfo(Game game) {
		super.addDebugInfo(game);
		debugInfo.add(String.format("class:%s", this.getClass().getSimpleName()));
		debugInfo.add(String.format("floor:(%03d,%03d)=>%03.02f", game.getWidth(), width, Math.abs(x)));
	}

}
