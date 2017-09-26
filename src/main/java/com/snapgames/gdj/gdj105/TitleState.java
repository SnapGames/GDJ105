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
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.ImageObject;
import com.snapgames.gdj.core.ui.Messages;
import com.snapgames.gdj.core.ui.TextObject;

/**
 * This is the Title Screen for the game.
 * 
 * @author Frédéric Delorme
 *
 */
public class TitleState extends AbstractGameState {

	private static final Logger logger = LoggerFactory.getLogger(TitleState.class);

	private Font titleFont;
	private Font menuItemFont;
	private ImageObject bgi;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.AbstractGameState#initialize(com.
	 * snapgames.gdj.gdj105.core.Game)
	 */
	@Override
	public void initialize(Game game) {
		super.initialize(game);

		titleFont = game.getGraphics().getFont().deriveFont(24.0f);
		menuItemFont = game.getGraphics().getFont().deriveFont(10.0f);
		FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

		String titleLabel = Messages.getString("TitleState.label.title");
		String startLabel = Messages.getString("TitleState.label.start");
		String copyrightLabel = Messages.getString("TitleState.label.copyright");

		BufferedImage bgImg = ResourceManager.getImage("/res/images/background-large.jpg");
		bgi = new ImageObject("background", bgImg, 0, 0, 2, 1);
		bgi.dx = 0.015f;
		addObject(bgi);

		TextObject titleText = new TextObject("title",
				(int) (game.WIDTH - titleFont.getStringBounds(titleLabel, frc).getWidth()) / 2, 5, titleLabel,
				titleFont, 1, 1, Color.WHITE);
		addObject(titleText);

		TextObject msgText = new TextObject("start",
				(int) (game.WIDTH - menuItemFont.getStringBounds(startLabel, frc).getWidth()) / 2, 100, startLabel,
				menuItemFont, 1, 1, Color.WHITE);
		addObject(msgText);

		TextObject cpyText = new TextObject("copyright",
				(int) (game.WIDTH - menuItemFont.getStringBounds(copyrightLabel, frc).getWidth()) / 2, game.HEIGHT - 30,
				copyrightLabel, debugFont, 2, 1, Color.WHITE);
		addObject(cpyText);

		// activate needed layers
		layers[0] = true;
		layers[1] = true;
	}

	@Override
	public void input(Game game, InputHandler input) {
	}

	@Override
	public void update(Game game, long dt) {
		bgi.x = bgi.x - bgi.dx * dt;
		if (bgi.x < -game.WIDTH) {
			bgi.x = 0.0f;
		}
	}

	@Override
	public void keyReleased(Game game, KeyEvent e) {
		super.keyReleased(game, e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			game.getGSM().activate("play");
			break;
		}
	}

}
