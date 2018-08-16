package com.snapgames.gdj.gdj105.entity;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.gfx.Sprite;

import java.awt.*;

public class Eatable extends AbstractGameObject {

    private Sprite sprite;

    public Eatable(String name, float x, float y) {
        super(name, x, y);
        attributes.put("nrj", 10);
        sprite = new Sprite(ResourceManager.getImage("/res/images/Sprite-0001.png").getSubimage(3 * 16, 0, 16, 16), "eatable");
    }

    public void draw(Game game, Graphics2D g) {
        sprite.draw(g, (int) x, (int) y);
    }
}
