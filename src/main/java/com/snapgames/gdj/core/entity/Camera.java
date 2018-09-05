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

import java.awt.*;

/**
 *
 * @author Frédéric Delorme
 *
 */
public class Camera extends AbstractGameObject {

    /**
     * deay factor for camera position computation according to target position.
     */
    private float tweenFactor = 0.050f;
    /**
     * Object followed by the camera
     */
    private AbstractGameObject target;
    /**
     * Flag to trackthe camera before move operation
     */
    private boolean moved;

    public Camera(String name, AbstractGameObject target) {
        super(name, target.y, target.x);
        this.target = target;
        this.boundingBox.setBounds(16, 16, Game.WIDTH - 32, Game.HEIGHT - 32);
        this.layer = 1;
        this.priority = 1;
    }

    @Override
    public void drawSpecialDebugInfo(Game game, Graphics2D g) {
        super.drawSpecialDebugInfo(game, g);
    }

    public void beforeRender(Graphics2D g) {
        // move renderer to camera position
        moved = true;
        g.translate(-x, -y);
    }

    public void afterRender(Graphics2D g) {
        // move back the renderer
        if (moved) {
            g.translate(x, y);
            moved = false;
        }
    }

    @Override
    public void update(Game game, long dt) {
        // if target exists, copute camera position.
        if (target != null) {
            this.x += ((target.x) - (Game.WIDTH / 2) - x) * tweenFactor * dt;
            this.y += ((target.y) - (Game.HEIGHT / 2) - y) * tweenFactor * dt;
        }
    }

    @Override
    public void draw(Game game, Graphics2D g) {
    }

    /**
     * Set hte tween factor for the camera delay to follow its target
     *
     * @param tweenFactor
     */
    public void setTweenFactor(float tweenFactor) {
        this.tweenFactor = tweenFactor;
    }

    @Override
    public void addDebugInfo(Game game) {
        super.addDebugInfo(game);
        debugInfo.add(String.format("twn:%03f", tweenFactor));
        debugInfo.add(String.format("tgt:%s", target.getName()));
    }


    public void setTarget(AbstractGameObject ago) {
        this.target = target;
        this.x = this.target.x;
        this.x = this.target.y;
    }
}
