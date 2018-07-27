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
	// TitleState Menu options item
	TITLE_MENU_OPTIONS("TitleState.label.options", "Options"),
	// TitleState Menu options quit
	TITLE_MENU_QUIT("TitleState.label.quit", "Quit"),
	// Language for the game.
	TITLE_LANGUAGE("language", "Language");

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
