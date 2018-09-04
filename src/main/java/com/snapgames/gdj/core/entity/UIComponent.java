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

/**
 * This is the main interface to manage User Interface Component.
 * 
 * @author Frédéric Delorme
 *
 */
public interface UIComponent {

	/**
	 * retrieve the unique id of this component.
	 * @return return the unique ID
	 */
	public int getID();

	/**
	 * return the value for this component.
	 * @return return the value for this component
	 */
	public String getValue();

	/**
	 * when the component get the focus.
	 */
	public void onFocus();

	/**
	 * when the component lost focus.
	 */
	public void onFocusLost();

}
