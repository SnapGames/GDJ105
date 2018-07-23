/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.UIComponent;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.gfx.RenderHelper.TextPosition;

/**
 * The UIMenu class intends to provide a selector UI component between multiple
 * items. Those items are described by the inner class UIMenuItem.
 * 
 * Its provides navigation operation (previous(), next()) and standard
 * update/draw methods.
 * 
 * @author Frédéric Delorme
 *
 */
public class UIMenu extends AbstractGameObject implements UIComponent {

	private int index = 0;

	/**
	 * active menu item.
	 */
	private int activeItem = 0;
	/**
	 * Shadow color for those items.
	 */
	private Color shadowColor = Color.BLACK;
	/**
	 * List of Items for this menu.
	 */
	private List<UIMenuItem> items = new ArrayList<>();
	private Font font;
	private TextPosition textPosition;

	/**
	 * Create a new {@link UIMenu} with a <code>name</code>, at position
	 * (<code>x</code>,<code>y</code>) with the <code>defaultActiveItem</code> item
	 * number, with a <code>foreColor</code> to draw text of label and a
	 * <code>shadowColor</code> to draw border shadow..
	 * 
	 * @param name              Name of this UIMenu
	 * @param x                 position on X axis
	 * @param y                 position on Y axis
	 * @param defaultActiveItem default active item.
	 * @param font              the Font to be used on render phase.
	 * @param color             foreground color to draw menu item label
	 * @param shadowColor       shadow color to draw as border of label.
	 */
	public UIMenu(String name, int x, int y, int defaultActiveItem, Font font, Color foreColor, Color shadowColor) {
		super(name, x, y, 0, 0);
		this.activeItem = defaultActiveItem;
		this.color = foreColor;
		this.shadowColor = shadowColor;
		this.font = font;
	}

	public UIMenu(String name, int x, int y, int defaultActiveItem, Font font, Color foreColor, Color shadowColor,
			TextPosition pos) {
		this(name, x, y, defaultActiveItem, font, foreColor, shadowColor);
		this.textPosition = pos;
	}

	/**
	 * Add a new Item to the menu.
	 * 
	 * @param value    value to return if this item is selected
	 * @param labelKey label key in the translated text (see messages.properties)
	 * @param text     the default text to draw if the labelKey does not exists. if
	 *                 this text is empty, will display the labelKey;
	 */
	public void addItem(String value, String labelKey, String defaultText, Object... args) {
		UIMenuItem item = new UIMenuItem(this, index++, labelKey, value, defaultText, args);
		items.add(item);
	}

	/**
	 * 
	 * @param value
	 * @param labelKey
	 * @param defaultText
	 */
	public void addItem(String value, String labelKey, String defaultText) {
		UIMenuItem item = new UIMenuItem(this, index++, labelKey, value, defaultText);
		items.add(item);
		computeSize();
	}

	/**
	 * Compute new size after any geometric change
	 */
	private void computeSize() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.entity.AbstractGameObject#update(com.snapgames.gdj.
	 * core.Game, long)
	 */
	@Override
	public void update(Game game, long dt) {
		// TODO Auto-generated method stub
		super.update(game, dt);
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
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		int i = 0;
		for (UIMenuItem item : items) {
			if (i != activeItem) {
				item.onFocusLost();
			} else {
				item.onFocus();
			}
			item.setTextPosition(textPosition);
			item.x = this.x;
			item.y = this.y+i*fm.getHeight();
			item.draw(game,g);
			i++;
			// update rectangle Bounding Box for this object.
			extendMenuBoundingBox(fm, i, item);
		}
	}
	
	private void extendMenuBoundingBox(FontMetrics fm, int i, UIMenuItem item) {
		Rectangle rect = item.getBoundingBox();
		rectangle.x = (int) (rect.x < rectangle.x ? rect.x : rectangle.x);
		rectangle.y = (int) (y - fm.getHeight());
		rectangle.width = (int) (rect.width > width ? rect.width : width);
		rectangle.height = i * fm.getHeight();
		rectangle.width = width = (fm.stringWidth(item.getLabel()) > width ? fm.stringWidth(item.getLabel())
				: width);
	}

	/**
	 * Activate the itemID menu item.
	 * 
	 * @param itemId
	 */
	public void activate(int itemId) {
		assert (activeItem < 0);
		assert (activeItem >= items.size());

		this.activeItem = itemId;
	}

	/**
	 * switch to previous item of the menu.
	 */
	public void previous() {
		activeItem--;
		if (activeItem < 0) {
			activeItem = items.size() - 1;
		}
	}

	/**
	 * switch to next item in the menu.
	 */
	public void next() {
		activeItem++;
		if (activeItem >= items.size()) {
			activeItem = 0;
		}
	}

	/**
	 * return the current active menu item.
	 * 
	 * @return
	 */
	public UIMenuItem getActiveItem() {
		return items.get(activeItem);
	}

	public void addDebugInfo() {
		super.addDebugInfo();
		debugInfo.add(String.format("class:%s", this.getClass().getSimpleName()));
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getValue() {
		return ""+activeItem;
	}

	@Override
	public void onFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFocusLost() {

	}

	public Color getShadowColor() {
		return shadowColor;
	}

}
