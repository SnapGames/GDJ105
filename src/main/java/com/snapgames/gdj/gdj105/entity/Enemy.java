/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ105
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj105.entity;

import java.awt.Color;

import com.snapgames.gdj.core.entity.AbstractGameObject;

/**
 * A bad enemy !
 * 
 * @author Frédéric Delorme
 *
 */
public class Enemy extends AbstractGameObject {

	/**
	 * 
	 */
	public Enemy() {
		super();
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
	public Enemy(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		super(name, x, y, width, height, layer, priority, color);
		this.hSpeed = 0.042f;
		this.vSpeed = 0.042f;
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 */
	public Enemy(String name, int x, int y, int dx, int dy) {
		super(name, x, y, dx, dy);
	}

}
