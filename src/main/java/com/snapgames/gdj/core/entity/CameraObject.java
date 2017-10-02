/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ105
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.snapgames.gdj.core.Game;

/**
 * compute and move the point of view to the Camera coordinates.
 * 
 * @author Frédéric Delorme.
 *
 */
public class CameraObject extends AbstractGameObject {

	private GameObject target;
	private float tween = 1.0f;

	/**
	 * 
	 */
	public CameraObject() {
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
	public CameraObject(String name, GameObject target, int tween) {
		super(name, (int) target.getX(), (int) target.getY(), Game.WIDTH, Game.HEIGHT, -1, -1, Color.BLUE);
		this.target = target;
		this.tween = tween;
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 */
	public CameraObject(String name, int x, int y, int dx, int dy) {
		super(name, x, y, dx, dy);
	}

	@Override
	public void update(Game game, long dt) {
		this.x = target.getX() * tween;
		this.y = target.getY() * tween;

	}

	public void beforeDraw(Game game, Graphics2D g) {
		g.translate(-this.x / 2, -this.y / 2);
	}

	@Override
	public void draw(Game game, Graphics2D g) {
	}

	public void afterDraw(Game game, Graphics2D g) {
		g.translate(this.x / 2, this.y / 2);
	}

}
