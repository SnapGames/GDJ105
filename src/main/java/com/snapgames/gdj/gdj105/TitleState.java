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
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.Layer;
import com.snapgames.gdj.core.gfx.RenderHelper.TextPosition;
import com.snapgames.gdj.core.i18n.Messages;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.Repeat;
import com.snapgames.gdj.core.ui.UIImage;
import com.snapgames.gdj.core.ui.UIMenu;
import com.snapgames.gdj.core.ui.UIText;
import com.snapgames.gdj.gdj105.i18n.Labels;

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
	private Font copyFont;
	private UIImage bgi, logo;

	private UIMenu menu;

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

		titleFont = game.getGraphics().getFont().deriveFont(3.0f * Game.SCREEN_FONT_RATIO);
		menuItemFont = game.getGraphics().getFont().deriveFont(1.2f * Game.SCREEN_FONT_RATIO);
		copyFont = game.getGraphics().getFont().deriveFont(1.0f * Game.SCREEN_FONT_RATIO);

		String titleLabel = Messages.getString(Labels.GAME_TITLE.getKey());
		String copyrightLabel = Messages.getString(Labels.COPYRIGHT.getKey());

		BufferedImage bgImg = ResourceManager.getImage("/res/images/background-image.jpg");
		bgi = new UIImage("background", bgImg, 0, (Game.HEIGHT - bgImg.getHeight()) / 2, 2, 1);
		bgi.scale = 1.0f;
		bgi.dx = 0.029f;
		bgi.x = 1;
		bgi.repeat = Repeat.HORIZONTAL_INFINITY;
		addObject(bgi);

		UIText titleText = new UIText("title", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.10f), titleLabel,
				titleFont, 1, 1, Color.WHITE, TextPosition.CENTER);
		titleText.setLabel(Labels.GAME_TITLE.getKey());
		addObject(titleText);

		menu = new UIMenu("menu", (int) (Game.WIDTH * 0.65f), (int) (Game.HEIGHT * 0.50f), 0, menuItemFont, Color.WHITE,
				Color.BLACK, TextPosition.RIGHT);
		menu.layer = 2;
		menu.priority = 1;

		menu.addItem("start", Labels.TITLE_MENU_START.getKey(), "Start");
		menu.addItem("options", Labels.TITLE_MENU_OPTIONS.getKey(), "Options");
		menu.addItem("quit", Labels.TITLE_MENU_QUIT.getKey(), "Quit");

		addObject(menu);

		UIText cpyText = new UIText("copyright", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.85f), copyrightLabel,
				copyFont, 2, 1, Color.WHITE, TextPosition.CENTER);
		cpyText.setLabel(Labels.COPYRIGHT.getKey());
		addObject(cpyText);

		UIText lngText = new UIText("language", (int) (Game.WIDTH) - 10, (int) (Game.HEIGHT * 0.85f), copyrightLabel,
				copyFont, 2, 1, Color.WHITE, TextPosition.RIGHT);
		lngText.setLabel(Labels.TITLE_LANGUAGE.getKey());
		addObject(lngText);

		BufferedImage imgLogo = ResourceManager.getImage("/res/icons/gdj-app-16x16.png");
		logo = new UIImage("logo", imgLogo, 4, Game.HEIGHT - (24 + imgLogo.getHeight()), 1, 1);
		logo.repeat = Repeat.NONE;

		addObject(logo);

		logger.info("State TitleState initialized");
	}

	@Override
	public void input(Game game, InputHandler input) {
	}

	@Override
	public void update(Game game, long dt) {
		bgi.x = bgi.x - bgi.dx * dt;

		float ax = Math.abs(bgi.x);
		if (ax > bgi.width || ax < 1) {
			bgi.dx = -bgi.dx;
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
			manageMenu(game);
			break;
		}
	}

	/**
	 * <p>
	 * On Menu selection (ENTER or SPACE release key event) the method start the
	 * corresponding option.
	 * <ul>
	 * <li><code>start</code> start the game (PlayState),</li>
	 * <li><code>options</code> go to the option screen (OptionsState)</li>
	 * <li><code>quit</code> exit from the program.</li>
	 * </ul>
	 * 
	 * @param game all the things of the game can be managed from the menu !
	 */
	private void manageMenu(Game game) {
		switch (menu.getActiveItem().getValue()) {
		case "start":
			game.getGSM().activate("play");
			break;
		case "options":
			game.getGSM().activate("options");
			break;
		case "quit":
			game.setExit(true);
			break;
		}
	}

}
