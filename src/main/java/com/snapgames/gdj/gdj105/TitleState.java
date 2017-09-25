/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj104
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj105;

import java.awt.Color;

import com.snapgames.gdj.gdj105.core.Game;
import com.snapgames.gdj.gdj105.core.InputHandler;
import com.snapgames.gdj.gdj105.core.state.AbstractGameState;
import com.snapgames.gdj.gdj105.core.ui.TextObject;

/**
 * @author Frédéric Delorme
 *
 */
public class TitleState extends AbstractGameState {

	@Override
	public void initialize(Game game) {
		super.initialize(game);
		TextObject titleText = new TextObject("title", 320, 200, "GDJ", debugFont, 1, 1, Color.WHITE);
		addObject(titleText);
	}

	@Override
	public void input(Game game, InputHandler input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Game game, long dt) {
		// TODO Auto-generated method stub

	}

}
