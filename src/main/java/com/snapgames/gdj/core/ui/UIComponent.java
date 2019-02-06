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

/**
 * This is the main interface to manage User Interface Component. Each UI
 * component will have to provide implementation for :
 * <ul>
 * <li>{@link UIComponent#getId()} the id of the component,</li>
 * <li>{@link UIComponent#getValue()} the value returned by the component, when
 * selected,</li>
 * <li>{@link UIComponent#onFocus()} the needed processing when the
 * mouse/selector focus this component,</li>
 * <li>{@link UIComponent#onLostFocus()} the processing when the component lost
 * the focus.</li>
 * </ul>
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public interface UIComponent {

	/**
	 * retrieve the unique id of this component.
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * return the value for this component.
	 * 
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
