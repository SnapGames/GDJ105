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
		g.setColor(new Color(0.9f, 0.9f, 0.9f));
		g.drawRect((int) x - 1, (int) y - 1, (int) width + 2, (int) height + 2);
		g.setColor(Color.WHITE);
		g.drawRect((int) x - 1, (int) y - 1, 1, 1);
		int eWidth = (int) (width * (float) ((float) (value + 1) / (float) (maxValue - minValue)));
		if (value > 0) {
			g.setColor(color);
			g.fillRect((int) x, (int) y, eWidth + 1, (int) height + 1);
		}
		g.setColor(new Color(0.0f,0.0f,0.0f,0.6f));
		g.fillRect((int) x+eWidth, (int) y, width-eWidth, (int) height + 1);
		g.setColor(Color.BLACK);
		g.drawRect((int) x, (int) y, (int) width, (int) height);
		g.drawLine((int) x + eWidth, (int) y, (int) x + eWidth, (int) y + height);

	}
}
