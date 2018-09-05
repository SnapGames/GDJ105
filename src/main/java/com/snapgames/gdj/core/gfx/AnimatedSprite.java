package com.snapgames.gdj.core.gfx;

import com.snapgames.gdj.core.Game;

import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite {


    Sprite[] frames;
    int[] durations;
    int total = 0;
    int index = 0;

    public AnimatedSprite(String name, Sprite[] sprites, int duration) {
        super(null, name);
        this.durations = new int[0];
        for (int i = 0; i < sprites.length; i++) {
            this.durations[i] = duration;
        }
        this.frames = sprites;
    }

    public AnimatedSprite(String name, Sprite[] sprites, int[] durations) {
        super(null, name);
        this.durations = new int[0];
        this.durations = durations;
        this.frames = sprites;
    }

    public void update(Game game, float dt) {
        total += dt;
        if (total > durations[index]) {
            index++;
            if (index > frames.length - 1) {
                index = 0;
            }
            total = 0;
        }
    }

    @Override
    public BufferedImage getImage() {
        pixels = frames[index].getImage();
        return pixels;
    }

}
