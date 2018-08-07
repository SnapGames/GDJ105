/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj104
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;

/**
 * the {@link Window} class to contains and display all the game.
 * 
 * @author Frédéric Delorme
 *
 */
public class Window {
	/**
	 * the internal JFrame containing the {@link Game} object.
	 */
	JFrame frame = null;

	Game game;

	/**
	 * The default unique constructor to initialize a {@link Window} on the
	 * <code>game</code>.
	 * 
	 * @param game the game to display in.
	 */
	public Window(Game game) {

		this.game = game;
		frame = new JFrame(game.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(game);
		frame.setLayout(new BorderLayout());

		// set window size
		frame.setSize(game.getDimension());
		frame.setPreferredSize(game.getDimension());
		frame.setMaximumSize(game.getDimension());
		frame.setResizable(false);

		// set default icon
		frame.setIconImage(ResourceManager.getImage("/res/icons/gdj-app.png"));

		// add the Game InputHandler as a KeyListener
		frame.addKeyListener(game.getInputHandler());

		// pack it and display it
		frame.pack();
		frame.setVisible(true);

		game.setWindow(this);
	}

	/**
	 * return the JFrame object for this wnidow.
	 * 
	 * @return
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Set the Window title.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		game.setTitle(title);
		frame.setTitle(title);
	}
}
