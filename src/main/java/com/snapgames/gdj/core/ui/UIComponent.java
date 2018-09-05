/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.core.ui;

import com.snapgames.gdj.core.Game;

import java.awt.*;

/**
 * This is the main interface to manage User Interface Component.
 * 
 * @author Frédéric Delorme
 *
 */
public interface UIComponent {

	/**
	 * retrieve the unique id of this component.
	 * @return
	 */
	int getId();

	/**
	 * return the value for this component.
	 * @return
	 */
	String getValue();

	/**
	 * when the component get the focus.
	 */
	void onFocus();

	/**
	 * when the component lost focus.
	 */
	void onFocusLost();

	/**
	 * Add a parent to this UIComponent.
	 * @param parent
	 */
	void setParent(UIComponent parent);

	void draw(Game game, Graphics2D g);

	Rectangle getBoundingBox();

	float getWidth();
	float getHeight();
	void setPosition(float x, float y);
}
