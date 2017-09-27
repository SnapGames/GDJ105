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

import java.awt.Color;
import java.awt.Graphics2D;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;

/**
 * a Jauge display (for energy, life, mana)
 * 
 * @author Frédéric Delorme
 *
 */
public class JaugeObject extends AbstractGameObject {
	public int minValue = 0, maxValue = 100;
	public int value = maxValue;

	/**
	 * 
	 */
	public JaugeObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param layer
	 * @param priority
	 * @param color
	 */
	public JaugeObject(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		super(name, x, y, width, height, layer, priority, color);
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 */
	public JaugeObject(String name, int x, int y, int dx, int dy) {
		super(name, x, y, dx, dy);
	}

	@Override
	public void draw(Game game, Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect((int) x - 2, (int) y - 2, (int) width + 4, (int) height + 4);
		g.setColor(Color.WHITE);
		g.drawRect((int) x - 1, (int) y - 1, (int) width + 2, (int) height + 2);
		int eWidth = (int) width * (maxValue / (value - minValue + 1));
		if (value > 0) {
			g.setColor(color);
			g.fillRect((int) x, (int) y, eWidth + 1, (int) height + 1);

		}
	}
}
