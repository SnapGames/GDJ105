package com.snapgames.gdj.core.entity;

import java.awt.Graphics2D;
import java.util.List;

import com.snapgames.gdj.core.Game;

public interface GameObject {

	/**
	 * Update object position.
	 * 
	 * @param game
	 * @param dt
	 */
	public void update(Game game, long dt);

	/**
	 * Draw object
	 * 
	 * @param game
	 * @param g
	 */
	public void draw(Game game, Graphics2D g);

	/**
	 * return the name of this object.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * return the layer this object belongs to.
	 * 
	 * @return
	 */
	public int getLayer();

	/**
	 * return the rendering priority for the layer this object belongs to.
	 * 
	 * @return
	 */
	public int getPriority();

	/**
	 * Add some debug information to display if needed.
	 * 
	 * @return
	 */
	public void addDebugInfo();

	public List<String> getDebugInfo();

	/**
	 * Return the object scale factor.
	 * 
	 * @return
	 */
	public float getScale();

	/**
	 * Does the debug info must be displayed for this object ?
	 * 
	 * @return
	 */
	public boolean isDebugInfoDisplayed();

	/**
	 * If an object need to add specific debug information at display time, this
	 * method can be overridden.
	 * 
	 * @param game
	 *            the parent game.
	 * @param g
	 *            the Graphics interface to render things.
	 */
	public void drawSpecialDebugInfo(Game game, Graphics2D g);
}