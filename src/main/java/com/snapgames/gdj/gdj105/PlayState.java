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
import java.awt.Font;
import java.awt.event.KeyEvent;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;
import com.snapgames.gdj.core.i18n.Messages;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.UIText;
import com.snapgames.gdj.gdj105.entity.TileMap;
import com.snapgames.gdj.gdj105.i18n.Labels;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class PlayState extends AbstractGameState {

	@Override
	public void initialize(Game game) {
		// TODO Auto-generated method stub
		super.initialize(game);

		// read i18n labels
		String titleLabel = Messages.getString(Labels.PLAY_TITLE.getKey());

		// Prepare fonts.
		Font titleFont = ResourceManager.getFont("/res/fonts/Prince Valiant.ttf")
				.deriveFont(2.5f * Game.SCREEN_FONT_RATIO);

		game.getWindow().setTitle("OptionState");

		// Define the main Game title object
		UIText titleText = new UIText("title", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.05f), titleLabel,
				titleFont, 1, 1, Color.WHITE, Justification.CENTER);
		titleText.setLabel(Labels.PLAY_TITLE.getKey());
		titleText.layer = 1;
		addObject(titleText);

		TileMap tilemap = new TileMap("tilemap");
		tilemap.loadTileFile("/res/maps/level-001.map");
		tilemap.layer = 2;
		addObject(tilemap);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.GameState#update(com.snapgames.gdj.core.Game,
	 * long)
	 * 
	 */
	@Override
	public void update(Game game, long dt) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.AbstractGameState#keyReleased(com.snapgames.gdj.
	 * core.Game, java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(Game game, KeyEvent e) {
		super.keyReleased(game, e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			game.getGSM().activate("title");
			break;
		default:
			break;
		}
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
}
