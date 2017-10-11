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
import java.util.ArrayList;
import java.util.List;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.gfx.RenderHelper.TextPosition;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class MenuObject extends AbstractGameObject {

	private int index = 0;

	/**
	 * Menu item element.
	 * 
	 * @author Frédéric Delorme
	 *
	 */
	public class MenuItem {
		/*
		 * this element item id.
		 */
		private int id;

		/**
		 * Displayed Label value
		 */
		private String label;

		/**
		 * Label key from translated message.
		 */
		private String labelKey;

		/**
		 * item value returned on item selection.
		 */
		private String value;

		/**
		 * 
		 * @param labelKey
		 * @param value
		 */
		public MenuItem(int id, String labelKey, String value, String defaultText) {
			this(id, labelKey, value, defaultText, (Object[]) null);
		}

		/**
		 * 
		 * @param labelKey
		 * @param value
		 * @param args
		 */
		public MenuItem(int id, String labelKey, String value, String defaultText, Object... args) {
			this.labelKey = labelKey;
			this.value = value;
			String translatedLabel = Messages.getString(labelKey);
			if (translatedLabel.contains(labelKey) && defaultText != null && !defaultText.equals("")) {
				this.label = defaultText;
			} else {
				this.label = String.format(translatedLabel, args);
			}
			this.id = index++;
		}

		/**
		 * 
		 * @return
		 */
		public String getValue() {
			return value;
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

	}

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
	private List<MenuItem> items = new ArrayList<>();
	private Font font;
	private TextPosition textPosition;

	/**
	 * Create a new {@link MenuObject} with a <code>name</code>, at position
	 * (<code>x</code>,<code>y</code>) with the <code>defaultActiveItem</code> item
	 * number, with a <code>foreColor</code> to draw text of label and a
	 * <code>shadowColor</code> to draw border shadow..
	 * 
	 * @param name
	 *            Name of this MenuObject
	 * @param x
	 *            position on X axis
	 * @param y
	 *            position on Y axis
	 * @param defaultActiveItem
	 *            default active item.
	 * @param font
	 *            the Font to be used on render phase.
	 * @param color
	 *            foreground color to draw menu item label
	 * @param shadowColor
	 *            shadow color to draw as border of label.
	 */
	public MenuObject(String name, int x, int y, int defaultActiveItem, Font font, Color foreColor, Color shadowColor) {
		super(name, x, y, 0, 0);
		this.activeItem = defaultActiveItem;
		this.color = foreColor;
		this.shadowColor = shadowColor;
		this.font = font;
	}

	
	public MenuObject(String name, int x, int y, int defaultActiveItem, Font font, Color foreColor, Color shadowColor, TextPosition pos) {
		this(name,x,y,defaultActiveItem,font,foreColor,shadowColor);
		this.textPosition = pos;
	}
	
	/**
	 * Add a new Item to the menu.
	 * 
	 * @param value
	 *            value to return if this item is selected
	 * @param labelKey
	 *            label key in the translated text (see messages.properties)
	 * @param text
	 *            the default text to draw if the labelKey does not exists. if this
	 *            text is empty, will display the labelKey;
	 */
	public void addItem(String value, String labelKey, String defaultText, Object... args) {
		MenuItem item = new MenuItem(index++, labelKey, value, defaultText, args);
		items.add(item);
	}

	/**
	 * 
	 * @param value
	 * @param labelKey
	 * @param defaultText
	 */
	public void addItem(String value, String labelKey, String defaultText) {
		MenuItem item = new MenuItem(index++, labelKey, value, defaultText);
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
		height = 0;
		int i = 0;
		Color drawColor = color;
		for (MenuItem item : items) {
			if (i != activeItem) {
				drawColor = Color.GRAY;
			} else {
				drawColor = color;
			}
			RenderHelper.drawShadowString(g, item.getLabel(), (int) x, (int) y + (i * fm.getHeight()), drawColor,
					shadowColor, (textPosition!=null?textPosition:TextPosition.LEFT), 2);
			i++;
			height = i * fm.getHeight();
			width = (fm.stringWidth(item.getLabel()) > width ? fm.stringWidth(item.getLabel()) : width);
		}
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
	public MenuItem getActiveItem() {
		return items.get(activeItem);
	}

}
