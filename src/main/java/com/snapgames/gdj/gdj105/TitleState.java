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
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;
import com.snapgames.gdj.core.gfx.SpriteSheet;
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
	private UIImage bgi, sword, logo;

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

		// Prepare fonts.
		titleFont = ResourceManager.getFont("/res/fonts/Prince Valiant.ttf").deriveFont(3.3f * Game.SCREEN_FONT_RATIO);
		menuItemFont = game.getGraphics().getFont().deriveFont(1.2f * Game.SCREEN_FONT_RATIO);
		copyFont = game.getGraphics().getFont().deriveFont(0.9f * Game.SCREEN_FONT_RATIO);

		// read i18n labels
		String titleLabel = Messages.getString(Labels.GAME_TITLE.getKey());
		String copyrightLabel = Messages.getString(Labels.COPYRIGHT.getKey());

		// Define the background image object
		SpriteSheet sheet = new SpriteSheet("sprite-0001",ResourceManager.getImage("/res/images/sprite-0001.png"), 16, 16);
		sheet.generate();

		/*
		 * AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		 * tx.translate(-swordImg.getWidth(null),0); AffineTransformOp op = new
		 * AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR); swordImg =
		 * op.filter(swordImg, null);
		 */

		// Define the UIMen cursor
		sword = new UIImage("sword", sheet.getSprite(5, 0).getImage(), 0, 0, 2, 1);
		sword.layer = 2;
		addObject(sword);

		// Define the background image object
		BufferedImage bgImg = ResourceManager.getImage("/res/images/background-image.jpg");

		bgi = new UIImage("background", bgImg, 0, (Game.HEIGHT - bgImg.getHeight()) / 2, 2, 1);
		bgi.scale = 1.0f;
		bgi.dx = 0.031f;
		bgi.x = 0;
		bgi.repeat = Repeat.HORIZONTAL_INFINITY;
		addObject(bgi);

		// Define the main Game title object
		UIText titleText = new UIText("title", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.10f), titleLabel,
				titleFont, 1, 1, Color.WHITE, Justification.CENTER);
		titleText.setLabel(Labels.GAME_TITLE.getKey());
		addObject(titleText);

		// Define the menu and all its options
		menu = new UIMenu("menu", (int) (Game.WIDTH * 0.65f), (int) (Game.HEIGHT * 0.50f), 0, menuItemFont, Color.WHITE,
				Color.BLACK, Justification.RIGHT);
		menu.layer = 2;
		menu.priority = 1;

		menu.addItem("start", Labels.TITLE_MENU_START.getKey(), "Start");
		menu.addItem("options", Labels.TITLE_MENU_OPTIONS.getKey(), "Options");
		menu.addItem("quit", Labels.TITLE_MENU_QUIT.getKey(), "Quit");
		menu.addCursor(sword);
		addObject(menu);

		// Add the copyroght text
		UIText cpyText = new UIText("copyright", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.85f), copyrightLabel,
				copyFont, 2, 1, Color.LIGHT_GRAY, Justification.CENTER);
		cpyText.setLabel(Labels.COPYRIGHT.getKey());
		addObject(cpyText);

		// Add the language label.
		UIText lngText = new UIText("language", (int) (Game.WIDTH) - 10, (int) (Game.HEIGHT * 0.85f), copyrightLabel,
				copyFont, 2, 1, Color.LIGHT_GRAY, Justification.RIGHT);
		lngText.setLabel(Labels.TITLE_LANGUAGE.getKey());
		addObject(lngText);

		BufferedImage imgLogo = ResourceManager.getImage("/res/icons/gdj-app-16x16.png");
		logo = new UIImage("logo", imgLogo, 4, Game.HEIGHT - (24 + imgLogo.getHeight()), 1, 1);
		logo.repeat = Repeat.NONE;

		addObject(logo);

		logger.info("State TitleState initialized");
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
		// Compute Background Image
		bgi.x -= bgi.dx * dt;

		float ax = Math.abs(bgi.x);
		if (ax > game.getWidth() - bgi.width) {
			ax = Math.floorMod((int) ax, bgi.width);
			bgi.x = -(ax + (bgi.dx * dt));
		}

		// update menu display
		menu.update(game, dt);
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
		// Move up the cursor on the menu
		case KeyEvent.VK_UP:
		case KeyEvent.VK_KP_UP:
			menu.previous();
			break;
		// Move down the cursor on the menu
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_KP_DOWN:
			menu.next();
			break;
		// select item in the menu where cursor is.
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			manageMenu(game);
			break;
		// Request to exit game.
		case KeyEvent.VK_ESCAPE:
			game.setExit(true);
			break;

		default:
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
