/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * gdj105
 * 
 * @year 2018
 */
package com.snapgames.gdj.core.gfx;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.GameObject;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class DebugInfo {
	int fontHeight = 0;

	int pane_padding = 4;
	int pane_width = 60;
	int pane_height = 40;
	int pane_x = 0, pane_y = 0;
	int link = 2;

	
	GameObject o;
	Game g;

	List<String> lines = new ArrayList<>();

	public DebugInfo(Game g, Graphics2D g2D, GameObject o) {
		this.g = g;
		this.o = o;
		fontHeight = g2D.getFontMetrics().getHeight();

	}

}
