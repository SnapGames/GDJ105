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
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;

/**
 * The UIGameObject is the basic object from every UIcomponent inherent from to
 * implements mandatory attributes and methods.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 * @see UIComponent
 * @see AbstractGameObject
 *
 */
public class UIGameObject extends AbstractGameObject implements UIComponent {

	/**
	 * Font for text rendering
	 */
	protected Font font;
	/**
	 * Border color for this component
	 */
	protected Color borderColor;
	/**
	 * front color for this component (text color)
	 */
	protected Color frontColor;
	/**
	 * background color (filling color)
	 */
	protected Color backgroundColor;
	/**
	 * color to render the text shadow.
	 */
	protected Color shadowColor;
	/**
	 * bolder size of the text shadow (in pixels).
	 */
	protected int shadowBold;
	/**
	 * Text jusitifcation rendering into the default box.
	 * 
	 * @see Justification
	 */
	protected Justification justification;

	/**
	 * Default constructor of this UIComponent implementation.
	 */
	public UIGameObject() {
		super();
	}

	/**
	 * Create a brand new UIGameObject with a <code>name</code>, a positrion
	 * <code>(x,y)</code>, a size <code>(width,height)</code>, a rendering
	 * <code>layer</code> and a <code>priority</code> in the rendering pipeline of
	 * this layer.
	 * 
	 * @param name     name for this UI component
	 * @param x        X horizontal position of the component
	 * @param y        Y vertical position of the component
	 * @param width    width (in pixel) of this component
	 * @param height   height(in pixel) of this component
	 * @param layer    layer to render this object to
	 * @param priority priority of thios onbject in the rendering pipeline in the
	 *                 layer
	 * @param color    default color for this object.
	 */
	public UIGameObject(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		super(name, x, y, width, height, layer, priority, color);
	}

	/**
	 * A smart constructor with less paramaters. Olny provide a <code>name</code>
	 * and a position <code>(x,y)</code>.
	 * 
	 * @param name name for this UI component
	 * @param x    X horizontal position of the component
	 * @param y    Y vertical position of the component
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

}
