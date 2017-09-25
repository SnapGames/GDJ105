/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj104
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj105.core.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.snapgames.gdj.gdj105.core.Game;
import com.snapgames.gdj.gdj105.core.GameObject;

/**
 * A textObject is a UI element to display Text.
 * 
 * @author Frédéric Delorme
 *
 */
public class TextObject extends GameObject {

	private String text;
	private Font font;
	private Color frontColor;
	private Color shadowColor;
	private int shadowBold;
	private Color backgroundColor;

	public TextObject() {
		super();
		text = "notext";
		frontColor = Color.WHITE;
		backgroundColor = new Color(0.5f, 0.5f, 0.5f, 0.8f);
		shadowColor = Color.BLACK;
	}

	/**
	 * Initialize a new TextObject with a <code>name</code>, for <code>text</code>
	 * drawn with <code>font</code> at <code>x,y</code>, on <code>layer</code> with
	 * a <code>priority</code>.
	 * 
	 * @param name
	 *            name of this object.
	 * @param x
	 *            horizontal position for this object
	 * @param y
	 *            vertical position for this object
	 * @param text
	 *            text to be drawn
	 * @param font
	 *            font to be used (null will draw text with default font.
	 * @param layer
	 *            a default layer to draw this object.
	 * @param priority
	 *            a priority for object in the layer
	 * @param color
	 *            the color to be used if not default foreground Color.
	 */
	public TextObject(String name, int x, int y, String text, Font font, int layer, int priority, Color color) {
		super(name, x, y, 1, 1, layer, priority, color);
		this.text = text;
		this.shadowBold = 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.gdj105.core.GameObject#draw(com.snapgames.gdj.gdj105.core.
	 * Game, java.awt.Graphics2D)
	 */
	@Override
	public void draw(Game game, Graphics2D g) {
		if (font != null) {
			g.setFont(font);
		}
		FontMetrics fm = g.getFontMetrics();
		this.width = fm.stringWidth(this.text);
		this.height = fm.getHeight();
		if (backgroundColor != null) {
			g.setColor(backgroundColor);
			g.fillRect((int) x - 2, (int) y - 2, width + 4, height + 4);
		}
		if (shadowColor != null) {
			g.setColor(shadowColor);
			for (int i = 0; i < shadowBold; i++) {
				g.drawString(text, x + i, y + i);
			}
		}
		g.setColor((color != null ? color : frontColor));
		g.drawString(text, x, y);
	}

}
