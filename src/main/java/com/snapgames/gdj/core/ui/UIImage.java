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
public class UIImage extends AbstractGameObject {

	public BufferedImage image;
	public Repeat repeat;
	int xmax = 0, ymax = 0;

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
		switch (repeat) {
		case NONE:
			g.drawImage(image, (int) (x), (int) (y), null);
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
