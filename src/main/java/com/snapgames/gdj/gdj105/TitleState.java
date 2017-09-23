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

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.gfx.RenderHelper.TextPosition;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.TextObject;

/**
 * @author Frédéric Delorme
 *
 */
public class TitleState extends AbstractGameState {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.AbstractGameState#initialize(com.
	 * snapgames.gdj.gdj105.core.Game)
	 */
	@Override
	public void initialize(Game game) {
		super.initialize(game);
		TextObject titleText = new TextObject("title", 150, 20, "GDJ105", debugFont, 1, 1,
				Color.WHITE, TextPosition.LEFT);
		addObject(titleText);
		TextObject msgText = new TextObject("start", 120, 100, "Press Start to play", debugFont, 1, 1,
				Color.WHITE, TextPosition.LEFT);
				
		addObject(msgText);
		layers[0] = true;
	}

	@Override
	public void input(Game game, InputHandler input) {
		// TODO nothing special here

	}

	@Override
	public void update(Game game, long dt) {
		// TODO either nothing special here.

	}

}
