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
	public CameraObject(String name, GameObject target, float tween) {
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
		if (target != null) {
			this.x += (((target.getX()) - (Game.WIDTH / 2) - x) * tween * dt);
			this.y += (((target.getY()) - (Game.HEIGHT / 2) - y) * tween * dt);
			rectangle.x = (int) x;
			rectangle.y = (int) y;
		}
	}

	@Override
	public void draw(Game game, Graphics2D g) {
	}

}
