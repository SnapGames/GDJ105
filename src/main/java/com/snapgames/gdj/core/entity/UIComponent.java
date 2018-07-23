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
	 * @return
	 */
	public int getID();

	/**
	 * return the value for this component.
	 * @return
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
