package com.snapgames.gdj.gdj105.entity;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.CharacterObject;

/**
 * Class to model a first enemy.
 */
public class Enemy extends CharacterObject {


    /**
     * Crate a new enemy.
     *
     * @param name
     * @param x
     * @param y
     */
    public Enemy(String name, float x, float y) {
        super(name, x, y);
        // define the Player attributes.
        attributes.put("energy", 300);
        attributes.put("attack", 10);
    }

    /*
     * Add specific Enemy information for debug purpose.
     * @param game
     */
    @Override
    public void addDebugInfo(Game game) {
        super.addDebugInfo(game);
        debugInfo.add(String.format("nrj:%d", attributes.get("energy")));
        debugInfo.add(String.format("atk:%d", attributes.get("attack")));
    }
}
