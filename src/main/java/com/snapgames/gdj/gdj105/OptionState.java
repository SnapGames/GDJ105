/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.gdj105;

import java.awt.Color;
import java.awt.Graphics2D;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.UIText;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class OptionState extends AbstractGameState {

	private UIText text;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.AbstractGameState#initialize(com.snapgames.gdj.
	 * core.Game)
	 */
	@Override
	public void initialize(Game game) {
		super.initialize(game);
		game.getWindow().setTitle("OptionState");
		UIText text = new UIText("optionsTitle", game.getWidth() / 2, game.getHeight() / 2, "Options", null, 1, 1,
				Color.WHITE);
		addObject(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.GameState#input(com.snapgames.gdj.core.Game,
	 * com.snapgames.gdj.core.io.InputHandler)
	 */
	@Override
	public void input(Game game, InputHandler input) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.GameState#update(com.snapgames.gdj.core.Game,
	 * long)
	 */
	@Override
	public void update(Game game, long dt) {
	}

	/*
	 * @Override(non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.AbstractGameState#render(com.snapgames.gdj.core.
	 * Game, java.awt.Graphics2D)
	 */
	public void render(Game game, Graphics2D g) {
		if (text != null) {
			text.draw(game, g);
		}
	}

}
