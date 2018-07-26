/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.core.ui;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public enum Repeat {
	/*
	 * No repeat of the object in any way.
	 */
	NONE,
	/*
	 * Repeat object horizontally on time. 
	 */
	HORIZONTAL_ONE, 
	/*
	 * Repeat object horizontally an infinity times (regarding screen display).
	 */
	HORIZONTAL_INFINITY, 
	/*
	 * Repeat object veritally one time.
	 */
	VERTICAL_ONE,
	/*
	 * Repeat object vertically an infinity times (regarding screen display).
	 */
	VERTICAL_INFINITY;
}
