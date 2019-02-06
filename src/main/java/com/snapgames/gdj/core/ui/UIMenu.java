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
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;

/**
 * <p>
 * The UIMenu class intends to provide a selector UI component between multiple
 * items. Those items are described by the inner class UIMenuItem.
 * 
 * <p>
 * Its provides navigation operation (previous(), next()) and standard
 * update/draw methods.
 * 
 * <p>
 * Each item of the menu is a {@link UIMenuItem}.
 * 
 * <p>
 * All the menu can be translated into any language accrding to the i18n
 * properties files.
 * <p>
 * In case of languages switching, the UIi18nReload interface will be used to
 * update this particular component and re-rendered it to the new active
 * language.
 * 
 * <p>
 * A new introduced {@link UIMenu#cursor} attribute is an UIImage to highlight
 * the active {@link UIMenuItem}.
 * <p>
 * The current active item number is hosted by the {@link UIMenu#activeItem}.
 * <p>
 * The {@link UIMenu#items} contains all UIMenuItem for this UIMenu.
 * 
 * @author Frédéric Delorme
 * @see UIMenuItem
 * @see UIGameObject
 * @see UIi18nReload
 *
 */
public class UIMenu extends UIGameObject implements UIi18nReload {

	private int index = 0;

	private UIImage cursor;

	/**
	 * active menu item.
	 */
	private int activeItem = 0;
	/**
	 * List of Items for this menu.
	 */
	private List<UIMenuItem> items = new ArrayList<>();

	private Justification cursorJustification;

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
		super(name, x, y);
		this.activeItem = defaultActiveItem;
		this.color = foreColor;
		this.shadowColor = shadowColor;
		this.font = font;
	}

	/**
	 * <p>
	 * Create a new {@link UIMenu} with a <code>name</code>, at position
	 * (<code>x</code>,<code>y</code>) with the <code>defaultActiveItem</code> item
	 * number, with a <code>foreColor</code> to draw text of label and a
	 * <code>shadowColor</code> to draw border shadow.
	 * <p>
	 * And finally the <code>pos</code> attribute indicates the text Justification
	 * for the UIMenuItem's.
	 * 
	 * @param name              Name of this UIMenu
	 * @param x                 position on X axis
	 * @param y                 position on Y axis
	 * @param defaultActiveItem default active item.
	 * @param font              the Font to be used on render phase.
	 * @param color             foreground color to draw menu item label
	 * @param shadowColor       shadow color to draw as border of label.
	 */
	public UIMenu(String name, int x, int y, int defaultActiveItem, Font font, Color foreColor, Color shadowColor,
			Justification pos) {
		this(name, x, y, defaultActiveItem, font, foreColor, shadowColor);
		this.justification = pos;
	}

	/**
	 * <p>
	 * Add a new item to the menu.
	 * <p>
	 * This will create a new {@link UIMenuItem} with set parameters, and add it to
	 * the {@link UIMenu}.
	 * 
	 * @param value       value to return if this item is selected
	 * @param labelKey    label key in the translated text (see messages.properties)
	 * @param defaultText the default text to draw if the labelKey does not exists.
	 *                    if this text is empty, will display the labelKey;
	 * @param args        a list of arguments used to render text (see
	 *                    {@link String#format(String, Object...)} for details. )
	 */
	public void addItem(String value, String labelKey, String defaultText, Object... args) {
		UIMenuItem item = new UIMenuItem(this, index++, labelKey, value, defaultText, args);
		items.add(item);
	}

	/**
	 * Add a new {@link UIMenuItem} menu item to the {@link UIMenu}.
	 * 
	 * @param item the Menu item to be added.
	 * @see UIMenuItem
	 */
	public void addItem(UIMenuItem item) {
		items.add(item);
	}

	/**
	 * Add a simple item with e the basic parameters by creating the corresponding
	 * UIMenuItem and add it to the UIMenu.
	 * 
	 * @param value       the retuernd valuie when this item is selected
	 * @param labelKey    the translated label key for this item.
	 * @param defaultText the default text used if this labelkey does not exist in
	 *                    the transation file for the activated language.
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
				if (cursor != null) {
					cursor.setPosition(x + 10, y + 3 + (i - 1) * fm.getHeight());
					cursor.draw(game, g);
				}
			}
			item.setTextPosition(justification);
			item.setPosition(x, y + i * fm.getHeight());
			item.draw(game, g);
			i++;
			// update rectangle Bounding Box for this object.
			extendMenuBoundingBox(fm, i, item);
		}
	}

	/**
	 * Adapt the bounding box of the UIMenu with the newly added UIMenuItem.
	 * 
	 * @param fm   the fontmetric for the current active font text
	 * @param i    the line number for this item in the menu.
	 * @param item the item that has been added.
	 */
	private void extendMenuBoundingBox(FontMetrics fm, int i, UIMenuItem item) {
		Rectangle rect = item.getBoundingBox();
		rectangle.x = (int) (rect.x < rectangle.x ? rect.x : rectangle.x);
		rectangle.y = (int) (y - fm.getHeight());
		rectangle.width = (int) (rect.width > width ? rect.width : width);
		rectangle.height = i * fm.getHeight();
		rectangle.width = width = (fm.stringWidth(item.getLabel()) > width ? fm.stringWidth(item.getLabel()) : width);
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

	public void addDebugInfo(Game game) {
		super.addDebugInfo(game);
		debugInfo.add(String.format("class:%s", this.getClass().getSimpleName()));
		debugInfo.add(String.format("active:%d", this.activeItem));

	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getValue() {
		return "" + activeItem;
	}

	@Override
	public void onFocus() {
	}

	@Override
	public void onFocusLost() {

	}

	public Color getShadowColor() {
		return shadowColor;
	}

	@Override
	public void reload() {
		for (UIMenuItem i : items) {
			i.reload();
		}

	}

	/**
	 * Add a specific {@link UIImage} as a cursor to be displayed near the current
	 * selected {@link UIMenuItem}.
	 * 
	 * @param cursor
	 */
	public void addCursor(UIImage cursor) {
		this.cursor = cursor;
	}
}
