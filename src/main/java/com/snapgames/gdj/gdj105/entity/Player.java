package com.snapgames.gdj.gdj105.entity;

import java.awt.Color;

import com.snapgames.gdj.core.entity.AbstractGameObject;

public class Player extends AbstractGameObject {

	/**
	 * 
	 */
	public Player() {
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
	public Player(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		super(name, x, y, width, height, layer, priority, color);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated constructor stub
	}

}
