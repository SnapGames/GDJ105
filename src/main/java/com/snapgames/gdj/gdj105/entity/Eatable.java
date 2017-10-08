/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj105.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class Eatable extends AbstractGameObject {

	public Eatable() {
		super();
	}

	public Eatable(String name, int x, int y) {
		super(name, x, y, 8, 8, 1, 2, Color.CYAN);
		attributes.put("power", 20);
	}

	public Eatable(String name, int x, int y, int dx, int dy) {
		super(name, x, y, dx, dy);
	}

	@Override
	public void draw(Game game, Graphics2D g) {
		g.setColor(color);
		g.fillArc((int) x, (int) y, 8, 8, 0, 360);
	}

}
