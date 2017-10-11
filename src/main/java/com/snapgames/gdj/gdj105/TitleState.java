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
import com.snapgames.gdj.core.entity.Layer;
import com.snapgames.gdj.core.gfx.RenderHelper.TextPosition;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.ImageObject;
import com.snapgames.gdj.core.ui.MenuObject;
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

	private MenuObject menu;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.state.AbstractGameState#initialize(com.
	 * snapgames.gdj.gdj105.core.Game)
	 */
	@Override
	public void initialize(Game game) {
		super.initialize(game);

		// activate needed layers
		for (int i = 0; i < layers.length; i++) {
			layers[i] = new Layer(true, false);
		}

		titleFont = game.getGraphics().getFont().deriveFont(24.0f);
		menuItemFont = game.getGraphics().getFont().deriveFont(16.0f);
		FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

		String titleLabel = Messages.getString("TitleState.label.title");
		String copyrightLabel = Messages.getString("TitleState.label.copyright");

		BufferedImage bgImg = ResourceManager.getImage("/res/images/background-large.jpg");
		bgi = new ImageObject("background", bgImg, 0, (Game.HEIGHT - bgImg.getHeight()) / 2, 2, 1);
		bgi.scale = 1.0f;
		bgi.dx = 0.029f;
		addObject(bgi);

		TextObject titleText = new TextObject("title", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.10f), titleLabel,
				titleFont, 1, 1, Color.WHITE, TextPosition.CENTER);
		addObject(titleText);

		menu = new MenuObject("menu", (int) (Game.WIDTH * 0.50f), (int) (Game.HEIGHT * 0.50f), 0, menuItemFont,
				Color.WHITE, Color.BLACK, TextPosition.CENTER);
		menu.layer = 2;
		menu.priority = 1;

		menu.addItem("start", "TitleState.label.start", "Start");
		menu.addItem("options", "TitleState.label.options", "Options");
		menu.addItem("quit", "TitleState.label.quit", "Quit");

		addObject(menu);

		TextObject cpyText = new TextObject("copyright", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.85f),
				copyrightLabel, debugFont, 2, 1, Color.WHITE, TextPosition.CENTER);
		addObject(cpyText);

		logger.info("State TitleState initialized");
	}

	@Override
	public void input(Game game, InputHandler input) {
	}

	@Override
	public void update(Game game, long dt) {
		bgi.x = bgi.x - bgi.dx * dt;
		if (bgi.x < -Game.WIDTH) {
			bgi.x = 0.0f;
		}
		menu.update(game, dt);
	}

	@Override
	public void keyReleased(Game game, KeyEvent e) {
		super.keyReleased(game, e);
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
		case KeyEvent.VK_KP_UP:
			menu.previous();
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_KP_DOWN:
			menu.next();
			break;
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			switch (menu.getActiveItem().getValue()) {
			case "start":
				game.getGSM().activate("play");
				break;
			case "options":
				break;
			case "quit":
				game.setExit(true);
				break;
			}
			break;
		}
	}

}
