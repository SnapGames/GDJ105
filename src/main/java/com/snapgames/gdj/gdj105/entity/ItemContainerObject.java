/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj105.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.gfx.RenderHelper.TextPosition;

/**
 * @author Frédéric Delorme
 *
 */
public class ItemContainerObject extends AbstractGameObject {
	private static final Color borderColor = new Color(0.9f, 0.9f, 0.9f);
	private static final Color backgroundColor = new Color(0.0f, 0.0f, 0.0f, 0.6f);
	public BufferedImage image;
	public Map<String, Object> attributes = new HashMap<>();
	public Font font;

	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public ItemContainerObject(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		this.dx = 0;
		this.dy = 0;
		this.hSpeed = 0;
		this.vSpeed = 0;

	}

	/**
	 * 
	 */
	public void draw(Game game, Graphics2D g) {
		g.setColor(borderColor);
		g.drawRect((int) x - 2, (int) y - 2, width + 4, height + 4);
		g.setColor(Color.WHITE);
		g.drawRect((int) x - 1, (int) y - 1, 1, 1);
		g.setColor(Color.BLACK);
		g.drawRect((int) x - 1, (int) y - 1, width + 2, height + 2);
		g.setColor(backgroundColor);
		g.fillRect((int) x, (int) y, width + 1, height + 1);
		if (image != null) {
			g.drawImage(image, (int) x, (int) y, null);
		}
		if (!attributes.isEmpty()) {
			if (attributes.containsKey("items")) {
				g.setFont(font);
				// g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				// RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				RenderHelper.drawShadowString(g, attributes.get("items").toString(), (int) x + 17, (int) y + 20,
						Color.WHITE, backgroundColor, TextPosition.RIGHT, 2);
			}
		}
	};

}
