/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj106
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.gfx;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public enum DebugLevel {
	/**
	 * No debugging information
	 */
	DEBUG_NONE(0),
	/**
	 * Display FPS information
	 */
	DEBUG_FPS(1),
	/**
	 * Display FPS and BoundingBox object.
	 */
	DEBUG_FPS_BOX(2),
	/**
	 * Display FPS, BoundingBox and Direction of GameObject. See
	 */
	DEBUG_FPS_BOX_DIRECTION(3),
	/**
	 * Display FPS, BoundingBox, Direction and all gameObject Debug attributes. See
	 * {@link AbstractGameObject#addDebugInfo(Game)}
	 */
	DEBUG_FPS_BOX_DIRECTION_ATTRS(4);

	int level = 0;

	private DebugLevel(int i) {
		this.level = i;
	}

	public boolean isDebugLevel(DebugLevel dl) {
		return this.level >= dl.level;
	}

	public int getValue() {
		return level;
	}

	public DebugLevel setValue(int debugLevel) {
		this.level = (debugLevel);
		return this;
	}
	
}
