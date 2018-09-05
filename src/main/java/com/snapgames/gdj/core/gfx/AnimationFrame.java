package com.snapgames.gdj.core.gfx;

import java.awt.image.BufferedImage;

/**
 * This class is a tool to create AnimationFrame.
 */
public class AnimationFrame {

    /**
     * the coordinate in the source image to extract frame.
     */
    int x, y, width, height, duration;

    /**
     * Image for this frame.
     */
    BufferedImage image;

    /**
     * Create a new frame definition.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param duration
     */
    public AnimationFrame(BufferedImage image, int duration) {
        this.duration = duration;
        this.image = image;
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
