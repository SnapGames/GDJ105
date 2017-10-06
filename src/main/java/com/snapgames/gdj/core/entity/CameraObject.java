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
import com.snapgames.gdj.core.state.AbstractGameState;

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
		super(name, (int) target.getX(), (int) target.getY(), Game.WIDTH, Game.HEIGHT, -1, -1, Color.ORANGE);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.entity.AbstractGameObject#update(com.snapgames.gdj.
	 * core.Game, long)
	 */
	@Override
	public void update(Game game, long dt) {
		if (target != null) {
			this.x += ((target.getX()) - (Game.WIDTH / 2) - x) * tween * dt;
			this.y += ((target.getY()) - (Game.HEIGHT / 2) - y) * tween * dt;
			rectangle.x = (int) x;
			rectangle.y = (int) y;
		}
	}

	/**
	 * Camera debug info are drawn at the point with coordinates (0,0), because it's
	 * all other objects that are moved according to camera position (see the method
	 * from the render of AbstractGameState object.
	 * 
	 * @see AbstractGameState#render(Game,Graphics2D)
	 * @see com.snapgames.gdj.core.entity.AbstractGameObject#draw(com.snapgames.gdj.core.Game,
	 *      java.awt.Graphics2D)
	 */
	@Override
	public void draw(Game game, Graphics2D g) {

		if (game.isDebug(1)) {
			g.setColor(color);
			int cWidth = (int)(width*0.96f); 
			int cHeight = (int)(height*0.96f); 
			g.drawRect((int)(width*0.02f), (int)(height*0.02f), cWidth, cHeight);
			g.drawString(String.format("%s:[%01.2f,%01.2f], tgt:[%01.2f,%01.2f]", name, this.x, this.y,
					(target != null ? target.getX() : 0.0f), (target != null ? target.getY() : 0.0f)), 4, 12);
		}
	}

}
