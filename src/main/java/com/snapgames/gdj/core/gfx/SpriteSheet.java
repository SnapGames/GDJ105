/**
 * SnapGames
 * <p>
 * Game Development Java
 * <p>
 * gdj105
 *
 * @year 2018
 */
package com.snapgames.gdj.core.gfx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

/**
 * @author Frédéric Delorme
 */
public class SpriteSheet {

    private static final Logger logger = LoggerFactory.getLogger(SpriteSheet.class);

    private String name = "";
    private String path = "";

    private BufferedImage pixels;

    private Sprite[] sprites;
    private int spWidth = 16;
    private int spHeight = 16;
    private int bufferWidth = 0, bufferHeight = 0;

    /**
     * Create a new Sprite sheet based on the pixeSource BufferedImage, each Sprite
     * will be sized (spriteWidth x spriteHeight).
     *
     * @param pixelSource  the BufferedImage to create Sprites from.
     * @param spriteWidth  the Width of a sprite
     * @param spriteHeight the height of a sprite.
     * @see Sprite;
     */
    public SpriteSheet(String name, BufferedImage pixelSource, int spriteWidth, int spriteHeight) {
        this.name = name;
        this.pixels = pixelSource;
        this.spWidth = spriteWidth;
        this.spHeight = spriteHeight;
    }

    public void generate() {
        int ix = 0, iy = 0;

        /**
         * Compute size of the sprites buffer
         */
        bufferHeight = Math.floorDiv(pixels.getHeight(), spHeight);
        bufferWidth = Math.floorDiv(pixels.getWidth(), spWidth);
        // Size he Sprite buffer.
        sprites = new Sprite[bufferHeight * bufferWidth];

        /**
         * Extract Pixels from base image to create All {@link Sprites}.
         */
        for (int y = 0; y < bufferHeight; y++) {
            for (int x = 0; x < bufferWidth; x++) {

                Sprite sp = new Sprite(pixels.getSubimage(x * spWidth, y * spHeight, spWidth, spHeight),
                        "SP_" + ix + "_" + iy);
                logger.info("Store sprite from ({},{},{},{}) to (iy={},ix={}) where max size is (width={},height={})",
                        x, y, spWidth, spHeight, iy, ix, bufferHeight, bufferWidth);
                sprites[(iy * bufferWidth) + ix++] = sp;
            }
            ix = 0;
            iy++;
        }
    }

    /**
     * retrieve a sprite from its SpriteSheet coordinates (ix,iy).
     *
     * @param ix horizontal position in the matrix,
     * @param iy vertical position in the matrix
     * @return the (ix,iy) matrix corresponding Sprite object.
     */
    public Sprite getSprite(int ix, int iy) {
        return sprites[iy * bufferHeight + ix];
    }

    /**
     * retrieve a sprite on its global index in the sprites buffer.
     *
     * @param index index of the request sprite in the preloaded SpriteSheet buffer.
     * @return the index corresponding Sprite object.
     */
    public Sprite getSprite(int index) {
        if (sprites != null && index < sprites.length) {
            return sprites[index];
        } else {
            return null;
        }
    }

    /**
     * Return size of the sprite sheet in term of number of sprites defined in the internal SpriteSheet buffer.
     *
     * @return the size of the internal buffer
     */
    public int getSize() {
        return sprites.length;
    }
}
