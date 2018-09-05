/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.gdj105.i18n;

/**
 * Here are defined all key values for translated labels.
 * 
 * @author Frédéric Delorme
 *
 */
public enum Labels {
	// title of the game
	GAME_TITLE("TitleState.label.title", "GDJ"),
	// copyright message
	COPYRIGHT("TitleState.label.copyright", "copyright 2018"),
	// TitleState Menu Start item
	TITLE_MENU_START("TitleState.label.start", "Start"),
    // ScoreState Menu Score item
    TITLE_MENU_SCORE("TitleState.label.score", "Scores"),
	// TitleState Menu options item
	TITLE_MENU_OPTIONS("TitleState.label.options", "Options"),
	// TitleState Menu options quit
	TITLE_MENU_QUIT("TitleState.label.quit", "Quit"),
	// Language for the game.
    TITLE_LANGUAGE("language", "Language"),
    OPT_LANG_LABEL("options.language.label", "Language"),
	// Language for the game.
	OPT_LANG_ENGLISH("options.language.item.english", "English"),
	// Lang for the game.
	OPT_LANG_FRENCH("options.language.item.french", "Français"),
	// Lang for the game.
	OPT_LANG_GERMAN("options.language.item.german", "German"),
	// Lang for the game.
	OPT_LANG_ITALIAN("options.language.item.italian", "Italian"),
	// Language for the game.
	OPT_LANG_SPANISH("options.language.item.spanish", "Español"),
	// Option State title
	OPTION_TITLE("OptionState.title", "Options"),
    // Option State title
    PLAY_TITLE("PlayState.title", "Play !"),
    // Option State title
    PLAY_PAUSE("PlayState.label.pause", "PAUSE"),
    // Score State title
    SCORE_TITLE("ScoreState.title", "Scores");


    private Labels(String keyLabel, String defaultText) {
		this.keyLabel = keyLabel;
		this.defaultText = defaultText;
	}

	public String getKey() {
		return keyLabel;
	}

	public String getDefaultValue() {
		return defaultText;
	}

	private String keyLabel;
	private String defaultText;
}
