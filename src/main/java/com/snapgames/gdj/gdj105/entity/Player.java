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
import com.snapgames.gdj.core.entity.CharacterObject;
import com.snapgames.gdj.core.gfx.Sprite;

/**
 * This is the Player Object. The main character for this sample game.
 *
 * @author Frédéric Delorme
 */
public class Player extends CharacterObject {

    /**
     * the rendering sprite for this player.
     */
    Sprite sprite;

    public Player(String name, float x, float y) {
        super(name, (int) x, (int) y);
        // define the Player attributes.
        attributes.put("energy", 1000);
        attributes.put("mana", 1000);
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
