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

/**
 * <p>The {@link DynamicGameObject} will be used by any next moving game object, like
 * enemies, player, bullets, animated parts, etc...
 * 
 * <p>It provides speed attributes with some default values for speed, some
 * specific attributes for animation purpose like {@link Actions},
 * {@link Direction}. It also proposes some default position computation based
 * on the Object's speed and elapsed time between each
 * {@link DynamicGameObject#update(Game, long)} method's call.
 * 
 * <p>The main attributes added by this kind of object are :
 * 
 * <ul>
 * <li>vSpeed</li> the default vertital speed for this object,
 * <li>hSpeed</li> the default horizontal speed for this object.
 * </ul>
 * 
 * @author Frédéric Delorme
 *
 */
public class DynamicGameObject extends AbstractGameObject {

	/**
	 * default horizontal speed for any object
	 */
	public final static float DEFAULT_HSPEED = 0.6f;
	/**
	 * default vertical speed for any object
	 */
	public final static float DEFAULT_VSPEED = 0.2f;

	/**
	 * default speed for this object.
	 */
	public float vSpeed, hSpeed;

	public Actions action = Actions.IDLE;

	public Direction direction = Direction.NONE;

	/**
	 * Create a AbstractGameObject with <code>name</code>, position
	 * (<code>x</code>,<code>y</code>) with a velocity of
	 * (<code>dx</code>,<code>dy</code>).
	 * 
	 * @param name the name for this object.
	 * @param x    x position in the (x,y) for this object
	 * @param y    y position in the (x,y) for this object
	 * @param dx   velocity on x direction
	 * @param dy   velocity on y direction.
	 */
	public DynamicGameObject(String name, int x, int y, int dx, int dy) {
		super(name, x, y);
		this.dx = dx;
		this.dy = dy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.AbstractGameObject#addDebugInfo()
	 */
	@Override
	public void addDebugInfo(Game game) {
		super.addDebugInfo(game);
		debugInfo.add(String.format("spd:(%4.2f,%4.2f)", dx, dy));
		debugInfo.add(String.format("action:(%s)", action));
		debugInfo.add(String.format("dir:(%s)", direction));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.GameObject#update(com.snapgames.gdj.core .Game,
	 * long)
	 */
	@Override
	public void update(Game game, long dt) {
		// compute basic physic mechanic
		x += dx * dt;
		y += dy * dt;

		// limit speed
		if (Math.abs(dx) < 0.005) {
			dx = 0.0f;
		}
		if (Math.abs(dy) < 0.005) {
			dy = 0.0f;
		}
		rectangle.setBounds((int) x, (int) y, width, height);

	}

	/**
	 * Set Velocity (speed) for this object.
	 * 
	 * @param dx
	 * @param dy
	 */
	public void setVelocity(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DynamicGameObject [dx=").append(dx).append(", dy=").append(dy).append(", vSpeed=")
				.append(vSpeed).append(", hSpeed=").append(hSpeed).append(", action=").append(action)
				.append(", direction=").append(direction).append(", id=").append(id).append(", name=").append(name)
				.append(", x=").append(x).append(", y=").append(y).append(", width=").append(width).append(", height=")
				.append(height).append(", scale=").append(scale).append(", layer=").append(layer).append(", priority=")
				.append(priority).append("]");
		return builder.toString();
	}

}
