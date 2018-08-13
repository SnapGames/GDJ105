/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.gdj105.entity;

import java.awt.Graphics2D;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.gfx.Sprite;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class Player extends AbstractGameObject {

	Sprite sprite;

	public Player(String name, float x, float y) {
		super(name, (int) x, (int) y);
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
		sprite.draw(g, (int) x, (int) y);
	}

	/**
	 * Set the sprite for the Player Object.
	 * 
	 * @param s
	 */
	public void setSprite(Sprite s) {
		this.sprite = sprite;
	}

}
