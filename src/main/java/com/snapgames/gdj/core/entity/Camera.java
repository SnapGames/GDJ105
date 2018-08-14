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

import java.awt.Graphics2D;

import com.snapgames.gdj.core.Game;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class Camera extends AbstractGameObject {

	private float tweenFactor = 0.22f;
	private AbstractGameObject target;

	public Camera(String name, AbstractGameObject target) {
		super(name,0,0);
		this.target = target;
	}

	@Override
	public void drawSpecialDebugInfo(Game game, Graphics2D g) {
		super.drawSpecialDebugInfo(game, g);
	}

	public void beforeRender(Graphics2D g) {
		g.translate(-x, -y);
	}

	public void afterRender(Graphics2D g) {
		g.translate(x, y);
	}

	@Override
	public void update(Game game, long dt) {
		x = target.x + tweenFactor * dt;
		y = target.y + tweenFactor * dt;
	}

}
