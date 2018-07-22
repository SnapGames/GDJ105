/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.core.entity;

import java.awt.Color;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class DynamicObject extends AbstractGameObject {

	public Actions action = Actions.IDLE;

	public Direction direction = Direction.NONE;

	/**
	 * 
	 */
	public DynamicObject() {
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
	public DynamicObject(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		super(name, x, y, width, height, layer, priority, color);
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 */
	public DynamicObject(String name, int x, int y, int dx, int dy) {
		super(name, x, y, dx, dy);
	}

}
