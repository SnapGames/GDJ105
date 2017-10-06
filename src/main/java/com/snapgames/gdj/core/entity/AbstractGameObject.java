/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.snapgames.gdj.core.Game;

/**
 * AbstractGameObject
 * 
 * @author Frédéric Delorme
 *
 */
public class AbstractGameObject implements GameObject {
	/**
	 * internal indexCounter to generate the default object name.
	 */
	private static int indexCounter = 0;
	public int index;

	/**
	 * default internal constants.
	 */
	/**
	 * default width for any object
	 */
	public final static int DEFAULT_WIDTH = 16;
	/**
	 * default height for any object
	 */
	public final static int DEFAULT_HEIGHT = 16;
	/**
	 * default horizontal speed for any object
	 */
	public final static float DEFAULT_HSPEED = 0.6f;
	/**
	 * default vertical speed for any object
	 */
	public final static float DEFAULT_VSPEED = 0.2f;
	/**
	 * Name of this object.
	 */
	public String name;
	/**
	 * position of this object onto the display space.
	 */
	public float x = 0, y = 0;

	/**
	 * default speed for this object.
	 */
	public float vSpeed, hSpeed;

	/**
	 * Velocity of this object.
	 */
	public float dx = 0, dy = 0;
	/**
	 * Size of this object.
	 */
	public int width = 32, height = 32;

	public Rectangle rectangle;

	public Actions action = Actions.IDLE;

	public Direction direction = Direction.NONE;

	public Map<String, Object> attributes = new ConcurrentHashMap<>();

	/**
	 * Rendering depth and priority.
	 */
	public int layer = 0, priority = 1;

	public Color color = Color.GREEN;

	/**
	 * Debug info if needed.
	 */
	protected List<String> debugInfo = new ArrayList<>();

	/**
	 * Default constructor for this AbstractGameObject.
	 */
	public AbstractGameObject() {
		super();
		indexCounter++;
		index = indexCounter;
	}

	/**
	 * Create a AbstractGameObject with <code>name</code>, position
	 * (<code>x</code>,<code>y</code>) size (<code>width</code>,<code>height</code>)
	 * on a <code>layer</code> width a rendering <code>priority</code> and a
	 * <code>color</code>.
	 * 
	 * @param name
	 *            the name for this object.
	 * @param x
	 *            x position in the (x,y) for this object
	 * @param y
	 *            y position in the (x,y) for this object
	 * @param width
	 *            width of the object
	 * 
	 * @param height
	 *            height of the object
	 * @param layer
	 *            layer where to render this object
	 * @param priority
	 *            priority to sort the rendering position of this object.
	 */
	public AbstractGameObject(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		this(name, x, y, 0, 0);
		this.width = width;
		this.height = height;
		this.layer = layer;
		this.priority = priority;
		this.color = color;

		this.rectangle = new Rectangle(x, y, width, height);
	}

	/**
	 * Create a AbstractGameObject with <code>name</code>, position
	 * (<code>x</code>,<code>y</code>) with a velocity of
	 * (<code>dx</code>,<code>dy</code>).
	 * 
	 * @param name
	 *            the name for this object.
	 * @param x
	 *            x position in the (x,y) for this object
	 * @param y
	 *            y position in the (x,y) for this object
	 * @param dx
	 *            velocity on x direction
	 * @param dy
	 *            velocity on y direction.
	 */
	public AbstractGameObject(String name, int x, int y, int dx, int dy) {
		this();
		// if name is null, generate a default name.
		this.name = (name == null || name.equals("") ? "noname_" + indexCounter : name);
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		this.hSpeed = DEFAULT_HSPEED;
		this.vSpeed = DEFAULT_VSPEED;
		this.rectangle = new Rectangle(x, y, width, height);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.GameObject#draw(com.snapgames.gdj.core. Game,
	 * java.awt.Graphics2D)
	 */
	@Override
	public void draw(Game game, Graphics2D g) {
		g.setColor(color);
		g.fillRect((int) x, (int) y, width, height);
		// Extended object will use their own draw process.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractGameObject [name=").append(name).append(", x=").append(x).append(", y=").append(y)
				.append(", vSpeed=").append(vSpeed).append(", hSpeed=").append(hSpeed).append(", dx=").append(dx)
				.append(", dy=").append(dy).append(", width=").append(width).append(", height=").append(height)
				.append(", layer=").append(layer).append(", priority=").append(priority).append(", color=")
				.append(color).append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.GameObject#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.GameObject#getLayer()
	 */
	@Override
	public int getLayer() {
		return layer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.GameObject#getPriority()
	 */
	@Override
	public int getPriority() {
		return priority;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.GameObject#addDebugInfo()
	 */
	@Override
	public void addDebugInfo() {
		debugInfo.clear();
		debugInfo.add(name);
		debugInfo.add(String.format("pos:(%4.0f,%4.0f)", x, y));
		debugInfo.add(String.format("spd:(%4.2f,%4.2f)", dx, dy));
		debugInfo.add(String.format("lyr,prio(:(%d,%d)", layer, priority));
		debugInfo.add(String.format("action:(%s)", action));
		debugInfo.add(String.format("dir:(%s)", direction));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.GameObject#getDebugInfo()
	 */
	@Override
	public List<String> getDebugInfo() {
		return debugInfo;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

}
