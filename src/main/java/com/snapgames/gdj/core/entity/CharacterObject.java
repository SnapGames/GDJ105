/**
 * SnapGames
 * <p>
 * Game Development Java
 * <p>
 * gdj105
 *
 * @year 2018
 */
package com.snapgames.gdj.core.entity;


import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.gfx.Sprite;

import java.awt.*;

/**
 * This is the CharacterObject Object where all PO and NPO will inherit from.
 *
 * @author Frédéric Delorme
 */
public class CharacterObject extends DynamicGameObject {

    /**
     * the rendering sprite for this player.
     */
    protected Sprite sprite;

    public CharacterObject(String name, float x, float y) {
        super(name, (int) x, (int) y, 0, 0);
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
        if (direction.equals(Direction.LEFT)) {
            sprite.draw(g, (int) x, (int) y, -1);
        } else {
            sprite.draw(g, (int) x, (int) y, 1);
        }
    }

    public void computeCollisionBox() {
        if (collisionBox == null) {
            collisionBox = new Rectangle();
        }
        collisionBox.setBounds((int) x + 4, (int) y + 8, 24, 24);
    }

    /**
     * Set the sprite for the CharacterObject Object.
     *
     * @param s
     */
    public void setSprite(Sprite s) {
        this.sprite = s;
        this.width = sprite.getImage().getWidth();
        this.height = sprite.getImage().getHeight();
    }
}

