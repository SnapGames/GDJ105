/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ104
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.GameObject;

/**
 * This class is a Render helper class to draw some shiny things.
 * 
 * @author Frédéric Delorme
 *
 */
public class RenderHelper {

	/**
	 * An internal Text POsition enumeration to provide a justification pattern.
	 * 
	 * @author Frédéric Delorme
	 *
	 */
	public enum TextPosition {
		LEFT, RIGHT, CENTER
	}

	public static void display(Graphics2D g, int x, int y, Font f, Object[] objects) {
		String[] helps = new String[objects.length];
		g.setFont(f);
		FontMetrics fm = g.getFontMetrics();
		int maxWidth = 0;
		for (Object o : objects) {
			int strWidth = g.getFontMetrics().stringWidth(o.toString());
			if (strWidth > maxWidth) {
				maxWidth = strWidth;
			}

		}
		g.setColor(new Color(.5f, .5f, .5f, .3f));
		g.fillRect(x - 10, y - fm.getHeight(), (maxWidth + 2 * 16), (objects.length + 1) * fm.getHeight());
		g.setColor(Color.WHITE);

		int i = 0;
		for (Object o : objects) {
			helps[i] = o.toString();
			drawShadowString(g, helps[i], x + 5, y + i * fm.getHeight(), Color.WHITE, Color.BLACK, TextPosition.LEFT,
					2);
			i++;
		}

	}

	public static String showBoolean(boolean b) {

		return showBoolean(b, "X", "_");
	}

	public static String showBoolean(boolean b, String on, String off) {

		return (b ? on : off);
	}

	/**
	 * Display String with front and back color.
	 * 
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param front
	 * @param back
	 */
	public static void drawShadowString(Graphics2D g, String text, int x, int y, Color front, Color back) {
		drawShadowString(g, text, x, y, front, back, TextPosition.LEFT);
	}

	/**
	 * Display String with front and back color at x,y positioning the text
	 * according to TextPosition.
	 * 
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param front
	 * @param back
	 * @param txtPos
	 */
	public static void drawShadowString(Graphics2D g, String text, int x, int y, Color front, Color back,
			TextPosition txtPos) {
		drawShadowString(g, text, x, y, front, back, txtPos, 1);
	}

	/**
	 * Display String with front and back color at x,y positioning the text
	 * according to TextPosition. The back color will be drawn with a border.
	 * 
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param front
	 * @param back
	 * @param border
	 */
	public static Rectangle drawShadowString(Graphics2D g, String text, int x, int y, Color front, Color back,
			TextPosition txtPos, int border) {
		int textWidth = g.getFontMetrics().stringWidth(text);
		int textHeight = g.getFontMetrics().getHeight();
		int dx = x;
		switch (txtPos) {
		case LEFT:
			break;
		case RIGHT:
			dx = x - textWidth;
			break;
		case CENTER:
			dx = x - (textWidth / 2);
			break;
		}
		g.setColor(back);
		for (int i = 0; i < border; i++) {
			g.drawString(text, dx - i, y + i);
			g.drawString(text, dx - i, y - i);
			g.drawString(text, dx + i, y + i);
			g.drawString(text, dx + i, y - i);
		}
		g.setColor(front);
		g.drawString(text, dx, y);
		return new Rectangle(dx, y, textWidth, textHeight);
	}

	/**
	 * Display debug information for the game Object.
	 * 
	 * @param g
	 *            the graphic interface to use to draw things
	 * @param o
	 *            the object to be debugged.
	 */
	public static void drawDebugInfoObject(Graphics2D g, GameObject o, Font f) {

		AbstractGameObject ago = (AbstractGameObject) o;
		g.setFont(f);
		int pane_padding = 4;
		int pane_x = (int) ago.x + (int) ago.width + pane_padding;
		int pane_y = (int) ago.y + (int) ago.height + pane_padding;
		int link = 2;
		int fontHeight = g.getFontMetrics().getHeight();
		int pane_width = 100;
		List<String> lines = new ArrayList<>();
		o.addDebugInfo();
		lines.addAll(o.getDebugInfo());
		for (int i = 0; i < lines.size(); i++) {
			pane_width = (g.getFontMetrics().stringWidth(lines.get(i) + pane_padding) > pane_width
					? g.getFontMetrics().stringWidth(lines.get(i)) + (pane_padding * 2)
					: pane_width);
		}
		g.setColor(new Color(0.5f, .5f, .5f, .6f));
		g.fillRect(pane_x + link, pane_y + link, pane_width, lines.size() * fontHeight + fontHeight / 2);

		g.setColor(Color.YELLOW);
		g.drawRect((int) ago.x, (int) ago.y, ago.width, ago.height);
		g.drawRect(pane_x + link, pane_y + link, pane_width, lines.size() * fontHeight + fontHeight / 2);
		g.drawLine((int) ago.x + ago.width, (int) ago.y + ago.height, (int) pane_x + link, pane_y + link);

		g.setColor(Color.GREEN);
		switch (ago.direction) {
		case UP:
			g.drawLine((int) ago.x, (int) ago.y, (int) ago.x + ago.width, (int) ago.y);
			break;
		case LEFT:
			g.drawLine((int) ago.x, (int) ago.y + (int) ago.height, (int) ago.x, (int) ago.y);
			break;
		case RIGHT:
			g.drawLine((int) ago.x + (int) ago.width, 
					(int) ago.y + (int) ago.height, 
					(int)ago.x+(int) ago.width, 
					(int) ago.y);
			break;
		case DOWN:
			g.drawLine((int) ago.x, (int) ago.y + (int) ago.height, (int) ago.x + ago.width,
					(int) ago.y + (int) ago.height);
			break;
		case NONE:
			break;
		}

		for (int i = 0; i < lines.size(); i++) {
			g.drawString(lines.get(i), pane_x + link + pane_padding, pane_y + link + (i + 1) * fontHeight);
		}
	}
}
