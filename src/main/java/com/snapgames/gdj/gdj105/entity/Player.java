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
 * The Player entity.
 * 
 * @author Frédéric Delorme
 *
 */
public class Player extends AbstractGameObject {

	/**
	 * 
	 */
	public Player() {
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
	public Player(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		super(name, x, y, width, height, layer, priority, color);
		this.hSpeed = 0.05f;
		this.vSpeed = 0.05f;
		this.priority = 1;
		this.layer = 2;
		attributes.put("energy", new Integer(100));
		attributes.put("mana", new Integer(100));
		attributes.put("level", new Integer(1));
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 */
	public Player(String name, int x, int y, int dx, int dy) {
		super(name, x, y, dx, dy);
	}

}
