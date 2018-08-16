/**
 * SnapGames
 * <p>
 * Game Development Java
 * <p>
 * gdj105
 *
 * @year 2018
 */
package com.snapgames.gdj.gdj105.entity;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.Direction;
import com.snapgames.gdj.core.entity.DynamicGameObject;
import com.snapgames.gdj.core.gfx.Sprite;

import java.awt.*;

/**
 * This is the Player Object. The main character for this sample game.
 *
 * @author Frédéric Delorme
 */
public class Player extends DynamicGameObject {

    /**
     * the rendering sprite for this player.
     */
    Sprite sprite;

    public Player(String name, float x, float y) {
        super(name, (int) x, (int) y, 0, 0);
        // define the Player attributes.
        attributes.put("energy", 1000);
        attributes.put("mana", 1000);
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
     * Set the sprite for the Player Object.
     *
     * @param s
     */
    public void setSprite(Sprite s) {
        this.sprite = s;
        this.width = sprite.getImage().getWidth();
        this.height = sprite.getImage().getHeight();
    }

    /*
     * Add specific Player information for debug purpose.
     * @param game
     */
    @Override
    public void addDebugInfo(Game game) {
        super.addDebugInfo(game);
        debugInfo.add(String.format("nrj:%d", attributes.get("energy")));
        debugInfo.add(String.format("mana:%d", attributes.get("mana")));
    }

}
