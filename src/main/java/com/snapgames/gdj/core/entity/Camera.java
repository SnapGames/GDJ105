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

import com.snapgames.gdj.core.Game;

import java.awt.*;

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
        if (target != null) {
            this.x += ((target.x) - (Game.WIDTH / 2) - x) * tweenFactor * dt;
            this.y += ((target.y) - (Game.HEIGHT / 2) - y) * tweenFactor * dt;
        }
	}

}
