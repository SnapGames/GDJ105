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
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.gfx.RenderHelper.TextPosition;

/**
 * Menu item element.
 * 
 * @author Frédéric Delorme
 *
 */
public class UIMenuItem extends UIGameObject implements UIi18nReload {

	/**
	 * Parent Menu which this item is attached to.
	 */
	private UIMenu menu;

	/**
	 * Displayed Label value
	 */
	private String label;

	/**
	 * Label key from translated message.
	 */
	private String labelKey;

	private String translatedKey;

	/**
	 * item value returned on item selection.
	 */
	private String value;

	private Rectangle rect;

	/**
	 * 
	 * @param labelKey
	 * @param value
	 */
	public UIMenuItem(UIMenu menu, int id, String labelKey, String value, String defaultText) {
		this(menu, id, labelKey, value, defaultText, (Object[]) null);
	}

	/**
	 * 
	 * @param labelKey
	 * @param value
	 * @param args
	 */
	public UIMenuItem(UIMenu menu, int id, String labelKey, String value, String defaultText, Object... args) {
		this.menu = menu;
		this.labelKey = labelKey;
		this.value = value;
		this.translatedKey = labelKey;
		String translatedLabel = Messages.getString(labelKey);
		if (translatedLabel.contains(labelKey) && defaultText != null && !defaultText.equals("")) {
			this.label = defaultText;
		} else {
			this.label = String.format(translatedLabel, args);
		}
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	public String getLabel(Object... args) {
		return String.format(Messages.getString(labelKey), args);
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
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#onFocus()
	 */
	@Override
	public void onFocus() {
		this.frontColor = Color.WHITE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#onFocusLost()
	 */
	@Override
	public void onFocusLost() {
		this.frontColor = Color.GRAY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.entity.AbstractGameObject#draw(com.snapgames.gdj.core.
	 * Game, java.awt.Graphics2D)
	 */
	@Override
	public void draw(Game game, Graphics2D g) {
		this.rect = RenderHelper.drawShadowString(g, getLabel(), (int) x, (int) y, frontColor, menu.getShadowColor(),
				(textPosition != null ? textPosition : TextPosition.LEFT), 2);
	}

	/**
	 * Return the current Bounding Box for this menu item.
	 * 
	 * @return
	 */
	public Rectangle getBoundingBox() {
		return rect;
	}

	/**
	 * Set text position for this menu item.
	 * 
	 * @param textPosition
	 */
	public void setTextPosition(TextPosition textPosition) {
		this.textPosition = textPosition;

	}

	@Override
	public void reload() {
		if (translatedKey != null && !translatedKey.equals("")) {
			label = Messages.getString(translatedKey);
		}

	}
}
