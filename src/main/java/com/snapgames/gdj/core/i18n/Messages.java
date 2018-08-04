/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj104
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.i18n;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.snapgames.gdj.core.ui.UIi18nReload;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class Messages {
	/**
	 * Path to the resource messages file
	 */
	private static final String BUNDLE_NAME = "res.messages"; //$NON-NLS-1$

	private static final String[] availableLanguages = new String[] { "EN", "DE", "FR", "ES", "IT" };
	private static int index = 0;

	/**
	 * Load bundle from path.
	 */
	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * private Constructor to not instantiate this class outside itself
	 */
	private Messages() {
	}

	/**
	 * Get a message from bundle file.
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * <p>
	 * Change default language of the game to the 2 characters code:
	 * <ul>
	 * <li><code>EN</code> for English,</li>
	 * <li><code>FR</code> for French,</li>
	 * <li><code>DE</code> for Deutch,</li>
	 * <li><code>ES</code> for Spanish,</li>
	 * <li><code>IT</code> for Italian.</li>
	 * </ul>
	 * 
	 * @param lang the 2 characters code for the needed language.
	 */
	public static void setLanguage(String lang) {
		Locale l = new Locale(lang.toLowerCase(), lang.toUpperCase());
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, l);
	}

	/**
	 * Switch display language with rotating list.
	 */
	public static void switchLanguage() {
		index++;
		index = (index < availableLanguages.length ? index : 0);
		String lang = availableLanguages[index];
		Locale locale = new Locale(lang.toLowerCase(), lang.toUpperCase());
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	public static void reloadUIi18n(List<UIi18nReload> uis) {
		for (UIi18nReload ui : uis) {
			ui.reload();
		}
	}

}
