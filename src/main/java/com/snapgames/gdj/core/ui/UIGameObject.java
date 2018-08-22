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

import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;

import java.awt.*;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class UIGameObject extends AbstractGameObject implements UIComponent {

	protected Font font;
    public Color frontColor;
    public Color shadowColor;
    public int shadowBold;
    public Color backgroundColor;
    public Justification justification;

	/**
	 * 
	 */
	public UIGameObject() {
		super();
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
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 */
	public UIGameObject(String name, int x, int y) {
		super(name, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#getID()
	 */
	@Override
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#getValue()
	 */
	@Override
	public String getValue() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#onFocus()
	 */
	@Override
	public void onFocus() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#onFocusLost()
	 */
	@Override
	public void onFocusLost() {

	}

    public void setFrontColor(int red, int green, int blue) {
        frontColor = new Color(red, green, blue);
    }



}
