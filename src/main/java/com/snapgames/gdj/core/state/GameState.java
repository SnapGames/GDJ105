/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ104
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.state;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.io.InputHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * An interface to design all states
 */
public interface GameState {

	/**
	 * Initialize the state on the game.
	 *
	 * @param game
	 *            the game parent of this state.
	 *
	 */
	public void initialize(Game game, boolean forcedReload);

	/**
	 * manage input in this State.
	 * 
	 * @param game
	 *            the parent game of this state
	 * @param input
	 *            the input handler
	 */
	public void input(Game game, InputHandler input);

	/**
	 * update the GameState
	 * 
	 * @param game
	 *            the parent game of this state
	 * @param dt
	 *            the elapsed time since previous call.
	 */
	public void update(Game game, long dt);

	/**
	 * Rendering this state
	 * 
	 * @param game
	 * @param g
	 */
	public void render(Game game, Graphics2D g);

	/**
	 * Free some resources and close the state.
	 * 
	 * @param game
	 */
	public void dispose(Game game);

	/**
	 * Intercept Key typed Event
	 * 
	 * @param game
	 * @param e
	 */
	public void keyTyped(Game game, KeyEvent e);

	/**
	 * Intercept Key pressed Event
	 * 
	 * @param game
	 * @param e
	 */
	public void keyPressed(Game game, KeyEvent e);

	/**
	 * Intercept Key released Event
	 * 
	 * @param game
	 * @param e
	 */
	public void keyReleased(Game game, KeyEvent e);

	/**
	 * Offer the opportunity to add front debug information.
	 *
	 * @param game
	 * @param g
	 */
	void renderFrontDebugInfo(Game game, Graphics2D g);
}