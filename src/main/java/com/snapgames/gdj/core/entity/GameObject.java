package com.snapgames.gdj.core.entity;

import com.snapgames.gdj.core.Game;

import java.awt.*;
import java.util.List;

/**
 * <p>
 * This interface aims at proposing the default signature for the Game object.
 * <p>
 * All the default operation for an object in the game engine.
 * <ul>
 * <li><code>update(float)</code> to update status of the object,</li>
 * <li><code>draw(Graphics2D)</code> to draw this object,</li>
 * <li><code>getPriority()</code> to get the rendering priority of the
 * object,</li>
 * <li><code>getLayer()</code> to find the layer where to render this object (to
 * manage rendering layers !)</li>
 * </ul>
 *
 * <p>
 * And there is more small method to help developers to do there job,
 * <code>addDebugInfo()</code>, <code>getDebuginfo()</code>,
 * <code>isInfoDebugDisplayed()</code>, <code>drawSpecialDebugInfo()</code>. :)
 *
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 */
public interface GameObject {

    /**
     * Update object position.
     *
     * @param game
     * @param dt
     */
    public void update(Game game, long dt);

    /**
     * Draw object
     *
     * @param game the parent Game
     * @param g a Graphics API
     */
    public void draw(Game game, Graphics2D g);

    /**
     * return the name of this object.
     *
     * @return the name of this component
     */
    public String getName();

    /**
     * return the layer this object belongs to.
     *
     * @return the layer for this object
     */
    public int getLayer();

    /**
     * return the rendering priority for the layer this object belongs to.
     *
     * @return the rendering piority of this object
     */
    public int getPriority();

    /**
     * Add some debug information to display if needed.
     * thes debug information are added at update time.
     *
     * @return add object specific debug information.
     */
    public void addDebugInfo(Game game);

    /**
     * return all the debug information for this object.
     * @return retur nthe list of interpreted debug information for this object.
     */
    public List<String> getDebugInfo();

    /**
     * Return the object scale factor.
     *
     * @return the scale of this object.
     */
    public float getScale();

    /**
     * Does the debug info must be displayed for this object ?
     *
     * @return a flag activated if debug info display is actvated.
     */
    public boolean isDebugInfoDisplayed();

    /**
     * If an object need to add specific debug information at display time, this
     * method can be overridden.
     *
     * @param game the parent game.
     * @param g    the Graphics interface to render things.
     */
    public void drawSpecialDebugInfo(Game game, Graphics2D g);

    /**
     * return true if this object is active
     *
     * @return true if this object is active.
     */
    public boolean isActive();
}