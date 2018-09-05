/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.gdj105.states;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.Layer;
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;
import com.snapgames.gdj.core.i18n.Messages;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.UIMenu;
import com.snapgames.gdj.core.ui.UIText;
import com.snapgames.gdj.gdj105.i18n.Labels;

import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * 
 * @author Frédéric Delorme
 *
 */
public class OptionState extends AbstractGameState {

	private UIText text;
    private UIMenu menuLang;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapgames.gdj.core.state.AbstractGameState#initialize(com.snapgames.gdj.
	 * core.Game)
	 */
	@Override
	public void initialize(Game game, boolean forcedReload) {
		super.initialize(game, forcedReload);
		// activate needed layers
		for (int i = 0; i < layers.length; i++) {
			layers[i] = new Layer(true, false);
		}

		// read i18n labels
		String titleLabel = Messages.getString(Labels.OPTION_TITLE.getKey());

		// Prepare fonts.
		Font titleFont = ResourceManager.getFont("/res/fonts/Prince Valiant.ttf")
				.deriveFont(2.5f * Game.SCREEN_FONT_RATIO);
        Font menuItemFont = game.getGraphics().getFont().deriveFont(1.2f * Game.SCREEN_FONT_RATIO);

		game.getWindow().setTitle("OptionState");

        // Define the Options title
        UIText titleText = new UIText("title", (int) (Game.WIDTH) / 2, (int) (Game.HEIGHT * 0.05f), titleLabel,
                titleFont, 1, 1, Color.WHITE, Justification.CENTER);
        titleText.setLabel(Labels.OPTION_TITLE.getKey());
        addObject(titleText);

        // Define the label for language option
        UIText menuLangTitle = new UIText("language", (int) (Game.WIDTH*0.33f), (int) (Game.HEIGHT * 0.25f), Messages.getString(Labels.TITLE_LANGUAGE.getKey()),
                menuItemFont, 1, 2, Color.WHITE, Justification.RIGHT);
        menuLangTitle.setLabel(Labels.OPT_LANG_LABEL.getKey());
        addObject(menuLangTitle);

        // Define the Language menu options
		menuLang = new UIMenu("language",(int) (Game.WIDTH*0.66f),(int) (Game.HEIGHT*0.25f),1 , menuItemFont, Color.WHITE,
                Color.BLACK, Justification.LEFT);
        menuLang.layer = 2;
        menuLang.priority = 1;
		menuLang.addItem("EN", Labels.OPT_LANG_ENGLISH.getKey(), "English");
		menuLang.addItem("FR", Labels.OPT_LANG_FRENCH.getKey(), "Français");
		menuLang.addItem("GE", Labels.OPT_LANG_GERMAN.getKey(), "German");
		menuLang.addItem("IT", Labels.OPT_LANG_ITALIAN.getKey(), "Italian");
		menuLang.addItem("ES", Labels.OPT_LANG_SPANISH.getKey(), "Español");
		addObject(menuLang);

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
        case KeyEvent.VK_UP:
            menuLang.previous();
            break;
        case KeyEvent.VK_DOWN:
            menuLang.next();
            break;
        case KeyEvent.VK_ENTER:
        case KeyEvent.VK_SPACE:
            manageMenu(game);
            break;
        default:
			break;
		}
	}
    private void manageMenu(Game game) {
        String value = menuLang.getActiveItem().getValue();
        Messages.setLanguage(value);
        Messages.reloadUIi18n(this.uis);
    }
}
