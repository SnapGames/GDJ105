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

import java.awt.Color;
import java.awt.Font;

import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.gfx.RenderHelper.TextPosition;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class UIGameObject extends AbstractGameObject implements UIComponent {

	protected Font font;
	protected Color frontColor;
	protected Color shadowColor;
	protected int shadowBold;
	protected Color backgroundColor;
	protected TextPosition textPosition;

	/**
	 * 
	 */
	public UIGameObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param layer
	 * @param priority
	 * @param color
	 */
	public UIGameObject(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		super(name, x, y, width, height, layer, priority, color);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 */
	public UIGameObject(String name, int x, int y, int dx, int dy) {
		super(name, x, y, dx, dy);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#getID()
	 */
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#getValue()
	 */
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#onFocus()
	 */
	@Override
	public void onFocus() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#onFocusLost()
	 */
	@Override
	public void onFocusLost() {
		// TODO Auto-generated method stub

	}

}
