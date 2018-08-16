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

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.gfx.RenderHelper;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AbstractGameObject.
 * 
 * This is the Main implementation for the GameObject interface. It provides
 * basic behavior for such object.
 * 
 * @author Frédéric Delorme
 *
 */
public class AbstractGameObject implements GameObject {
	/**
	 * internal indexCounter to generate the default object name.
	 */
	private static int indexCounter = 0;
	public int id;

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
	 * Name of this object.
	 */
	public String name;
	/**
	 * position of this object onto the display space.
	 */
	public float x = 0, y = 0;

	/**
	 * Velocity of this object.
	 */
	public float dx = 0, dy = 0;

	/**
	 * Size of this object.
	 */
	public int width = 32, height = 32;

	public float scale = 1.0f;

	public Rectangle rectangle;

	public Map<String, Object> attributes = new ConcurrentHashMap<>();

	public Point2D offsetInfo;

	public boolean showDebuginfo;

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
		id = indexCounter;
	}

	public AbstractGameObject(String name, float x, float y) {
		this();
		// if name is null, generate a default name.
		this.name = (name == null || name.equals("") ? "noname_" + indexCounter : name);
		this.x = x;
		this.y = y;
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		this.rectangle = new Rectangle((int) x, (int) y, width, height);
	}

	/**
	 * Create a AbstractGameObject with <code>name</code>, position
	 * (<code>x</code>,<code>y</code>) size (<code>width</code>,<code>height</code>)
	 * on a <code>layer</code> width a rendering <code>priority</code> and a
	 * <code>color</code>.
	 * 
	 * @param name     the name for this object.
	 * @param x        x position in the (x,y) for this object
	 * @param y        y position in the (x,y) for this object
	 * @param width    width of the object
	 * 
	 * @param height   height of the object
	 * @param layer    layer where to render this object
	 * @param priority priority to sort the rendering position of this object.
	 */
	public AbstractGameObject(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		this(name, x, y);
		this.width = width;
		this.height = height;
		this.layer = layer;
		this.priority = priority;
		this.color = color;
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
	 * @see
	 * com.snapgames.gdj.core.entity.GameObject#drawSpecialDebugInfo(com.snapgames.
	 * gdj.core.Game, java.awt.Graphics2D)
	 */
	public void drawSpecialDebugInfo(Game game, Graphics2D g) {
		RenderHelper.drawDebugInfoObject(game, g, this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String builder = "AbstractGameObject [name=" + name + ", x=" + x + ", y=" + y +
				", width=" + width + ", height=" + height + ", layer=" + layer +
				", priority=" + priority + ", color=" + color + "]";
		return builder;
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
	public void addDebugInfo(Game game) {
		debugInfo.clear();
		debugInfo.add(String.format("name:(%s)", name));
		debugInfo.add(String.format("pos :(%4.0f,%4.0f)", x, y));
		debugInfo.add(String.format("layr:(%d)", layer));
		debugInfo.add(String.format("prio:(%d)", priority));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.GameObject#getScale()
	 */
	@Override
	public float getScale() {
		return scale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.GameObject#isDebugInfoDisplayed()
	 */
	@Override
	public boolean isDebugInfoDisplayed() {
		return showDebuginfo;
	}

	/**
	 * Set position for this object.
	 *
	 * @param x horizontal position for this object
	 * @param y vertical position for this object
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		if (rectangle != null) {
			rectangle.setBounds((int) x, (int) y, width, height);
		}
	}

}
