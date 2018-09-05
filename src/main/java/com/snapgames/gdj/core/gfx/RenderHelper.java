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

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.entity.DynamicGameObject;
import com.snapgames.gdj.core.entity.GameObject;

import java.awt.*;

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
	public enum Justification {
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
			drawShadowString(g, helps[i], x + 5, y + i * fm.getHeight(), Color.WHITE, Color.BLACK, Justification.LEFT,
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
		drawShadowString(g, text, x, y, front, back, Justification.LEFT);
	}

	/**
	 * Display String with front and back color at x,y positioning the text
	 * according to Justification.
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
			Justification txtPos) {
		drawShadowString(g, text, x, y, front, back, txtPos, 1);
	}

	/**
	 * Display String with front and back color at x,y positioning the text
	 * according to Justification. The back color will be drawn with a border.
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
			Justification txtPos, int border) {
		int textWidth = g.getFontMetrics().stringWidth(text);
		int textHeight = g.getFontMetrics().getHeight();
		int dx = 0;
		switch (txtPos) {
		case LEFT:
			break;
		case RIGHT:
			dx = -textWidth;
			break;
		case CENTER:
			dx = -(textWidth / 2);
			break;
		}
		g.setColor(back);
		for (int i = 0; i < border; i++) {
			for (int ix = -i; ix < i + 1; ix++) {
				for (int iy = -i; iy < i + 1; iy++) {
					g.drawString(text, x + dx - ix, y + iy);
				}
			}
		}
		g.setColor(front);
		g.drawString(text, x + dx, y);
		return new Rectangle(x + dx, y, textWidth, textHeight);
	}

	/**
	 * Display debug information for the game Object.
	 * 
	 * @param g the graphic interface to use to draw things
	 * @param o the object to be debugged.
	 */
	public static void drawDebugInfoObject(Game game, Graphics2D g, GameObject o) {

		AbstractGameObject ago = (AbstractGameObject) o;
		Font f = ResourceManager.getFont("debugFont");
		g.setFont(f);
		DebugInfo dbi = new DebugInfo(game, g, o);
		int fontHeight = g.getFontMetrics().getHeight();

		for (int i = 0; i < dbi.lines.size(); i++) {
			dbi.pane_width = (g.getFontMetrics().stringWidth(dbi.lines.get(i) + dbi.pane_padding) > dbi.pane_width
					? g.getFontMetrics().stringWidth(dbi.lines.get(i)) + (dbi.pane_padding * 2)
					: dbi.pane_width);
		}
		dbi.pane_height = dbi.lines.size() * fontHeight + fontHeight / 2;

		if (ago.offsetInfo != null) {
			dbi.pane_x = (int) ago.offsetInfo.getX();
			dbi.pane_y = (int) ago.offsetInfo.getY();
		} else {
			// pane_x = (int) (ago.boundingBox.x + ago.width + pane_padding);
			// pane_y = (int) (ago.boundingBox.y + ago.height + pane_padding);
			dbi.pane_x = (int) (ago.boundingBox.x);
			dbi.pane_y = (int) (ago.boundingBox.y);
		}
		if (o.getScale() != 1.0f) {
			g.scale(o.getScale(), o.getScale());
		}
		if (game.isDebug(DebugLevel.DEBUG_FPS_BOX)) {
			if (ago.boundingBox != null) {
				drawBoundingBox(g, ago);
			}
			if (ago.collisionBox != null) {
				drawCollisionBox(g, ago);
			}
		}
		if (ago instanceof DynamicGameObject) {
			DynamicGameObject dgo = (DynamicGameObject) ago;
			drawDirection(game, g, dgo);
		}
		if (game.isDebug(DebugLevel.DEBUG_FPS_BOX_DIRECTION_ATTRS)) {
			drawDebugAttributes(g, dbi);
		}
		if (o.getScale() != 1.0f) {
			g.scale(1 / o.getScale(), 1 / o.getScale());
		}
	}

	/**
	 * @param g
	 * @param ago
	 */
	private static void drawBoundingBox(Graphics2D g, AbstractGameObject ago) {
		g.setColor(Color.YELLOW);
		g.drawRect((int) ago.boundingBox.x, (int) ago.boundingBox.y, ago.boundingBox.width, ago.boundingBox.height);
		g.drawString("id:" + ago.id, (int) ago.boundingBox.x, (int) ago.boundingBox.y);
	}

	/**
	 * @param g
	 * @param ago
	 */
	private static void drawCollisionBox(Graphics2D g, AbstractGameObject ago) {
		g.setColor(Color.CYAN);
		g.drawRect((int) ago.collisionBox.x, (int) ago.collisionBox.y, ago.collisionBox.width, ago.collisionBox.height);
	}

	/**
	 * Draw all debug info attributes contained in <code>lines</code> and their
	 * values .
	 * 
	 * @param g
	 * @param fontHeight
	 * @param pane_padding
	 * @param pane_width
	 * @param pane_height
	 * @param lines
	 * @param pane_x
	 * @param pane_y
	 * @param link
	 */
	private static void drawDebugAttributes(Graphics2D g, DebugInfo dbi) {
		g.setColor(new Color(0.5f, .5f, .5f, .6f));
		g.fillRect(dbi.pane_x + dbi.link, dbi.pane_y + dbi.link, dbi.pane_width, dbi.pane_height);

		g.setColor(new Color(0.8f, .8f, .8f, .8f));
		g.drawRect(dbi.pane_x + dbi.link, dbi.pane_y + dbi.link, dbi.pane_width, dbi.pane_height);

		g.setColor(Color.GREEN);
		/*
		 * g.drawLine( (int) ago.boundingBox.x + ago.boundingBox.width, (int)
		 * ago.boundingBox.y + ago.boundingBox.height, (int) dbi.pane_x + dbi.link,
		 * dbi.pane_y + dbi.link);
		 */
		for (int i = 0; i < dbi.lines.size(); i++) {
			g.drawString(dbi.lines.get(i), dbi.pane_x + dbi.link + dbi.pane_padding,
					dbi.pane_y + dbi.link + (i + 1) * dbi.fontHeight);
		}
	}

	/**
	 * Draw the Moving direction for the <code>dgo</code> GameObject.
	 * 
	 * @param game
	 * @param g
	 * @param dgo
	 */
	private static void drawDirection(Game game, Graphics2D g, DynamicGameObject dgo) {
		if (game.isDebug(DebugLevel.DEBUG_FPS_BOX_DIRECTION)) {
			g.setColor(Color.GREEN);
			switch (dgo.direction) {
				case UP:
					g.drawLine((int) dgo.boundingBox.x, (int) dgo.boundingBox.y, (int) dgo.boundingBox.x + dgo.boundingBox.width,
							(int) dgo.boundingBox.y);
				break;
			case LEFT:
				g.drawLine((int) dgo.boundingBox.x, (int) dgo.boundingBox.y + (int) dgo.boundingBox.height,
						(int) dgo.boundingBox.x, (int) dgo.boundingBox.y);
				break;
			case RIGHT:
				g.drawLine((int) dgo.boundingBox.x + (int) dgo.boundingBox.width,
						(int) dgo.boundingBox.y + (int) dgo.boundingBox.height,
						(int) dgo.boundingBox.x + (int) dgo.boundingBox.width, (int) dgo.boundingBox.y);
				break;
			case DOWN:
				g.drawLine((int) dgo.boundingBox.x, (int) dgo.boundingBox.y + (int) dgo.boundingBox.height,
						(int) dgo.boundingBox.x + dgo.boundingBox.width,
						(int) dgo.boundingBox.y + (int) dgo.boundingBox.height);
				break;
			case NONE:
				break;
			}
		}
	}
}
