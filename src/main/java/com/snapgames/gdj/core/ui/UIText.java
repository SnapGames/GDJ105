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

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;
import com.snapgames.gdj.core.i18n.Messages;

import java.awt.*;

/**
 * A textObject is a UI element to display Text.
 * 
 * @author Frédéric Delorme
 *
 */
public class UIText extends UIGameObject implements UIi18nReload {

	public String text;
	public String label;

	public UIText() {
		super();
		text = "notext";
		frontColor = Color.WHITE;
		backgroundColor = Color.BLUE;// new Color(0.5f, 0.5f, 0.5f, 0.2f);
		shadowColor = Color.BLACK;
	}

	/**
	 * Initialize a new UIText with a <code>name</code>, for <code>text</code> drawn
	 * with <code>font</code> at <code>x,y</code>, on <code>layer</code> with a
	 * <code>priority</code>.
	 * 
	 * @param name     name of this object.
	 * @param x        horizontal position for this object
	 * @param y        vertical position for this object
	 * @param text     text to be drawn
	 * @param font     font to be used (null will draw text with default font.
	 * @param layer    a default layer to draw this object.
	 * @param priority a priority for object in the layer
	 * @param color    the color to be used if not default foreground Color.
	 */
	public UIText(String name, int x, int y, String text, Font font, int layer, int priority, Color color) {
		super(name, x, y, 1, 1, layer, priority, color);
		this.text = text;
		this.shadowBold = 1;
		this.font = font;
		this.shadowColor = Color.BLACK;
		this.frontColor = color;
		this.shadowBold = 2;

	}

	public UIText(String name, int x, int y, String text, Font font, int layer, int priority, Color color,
			Justification pos) {
		super(name, x, y, 1, 1, layer, priority, color);
		this.text = text;
		this.shadowBold = 1;
		this.font = font;
		this.shadowColor = Color.BLACK;
		this.frontColor = color;
		this.shadowBold = 2;
		this.justification = pos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.AbstractGameObject#draw(com.snapgames.gdj.
	 * gdj105.core. Game, java.awt.Graphics2D)
	 */
	@Override
	public void draw(Game game, Graphics2D g) {
        super.draw(game, g);
		if (font != null) {
			g.setFont(font);
		}
		FontMetrics fm = g.getFontMetrics();
		this.width = fm.stringWidth(this.text);
		this.height = fm.getHeight();

		if (backgroundColor != null) {
			g.setColor(backgroundColor);
			g.fillRect((int) x - 2, (int) y + 2, width + 4, height + 4);
		}
		Rectangle rect = RenderHelper.drawShadowString(g, text, (int) x, (int) y + height - 2, frontColor, shadowColor,
				(justification != null ? justification : Justification.LEFT), shadowBold);
		boundingBox.x = (int) (rect.x < boundingBox.x ? rect.x : boundingBox.x);
		boundingBox.y = (int) (y);
		boundingBox.width = (int) (rect.width > width ? rect.width : width);
		boundingBox.height = fm.getHeight();
		boundingBox.width = width = (fm.stringWidth(text) > width ? fm.stringWidth(text) : width);
	}

	public void addDebugInfo(Game game) {
		super.addDebugInfo(game);
		debugInfo.add(String.format("text:(%s)", text));
		debugInfo.add(String.format("class:%s", this.getClass().getSimpleName()));
	}

	public void setLabel(String label) {
		assert (label != null);
		assert (!label.equals(""));
		this.label = label;
		this.text = Messages.getString(label);
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
		return text;
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

	/**
	 * If i18n sensitive, add he reload behavior.
	 */
	@Override
	public void reload() {
		if (label != null && !label.equals("")) {
			text = Messages.getString(label);
		}

	}

}
