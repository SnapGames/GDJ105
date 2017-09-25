/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ105
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.entity;

/**
 * @author Frédéric Delorme
 *
 */
public enum Actions {
	IDLE("idle"), 
	WALK("walk"), 
	RUN("run"), 
	UP("up"),
	DOWN("down"), 
	FALL("fall"), 
	DEAD("dead"), 
	ACTION1("a1"), 
	ACTION2("a2"), 
	ACTION3("a3"), 
	ACTION4("a4");
	
	Actions(String value) {
		this.action = value;
	}

	String action = "";

	public String getValue() {
		return action;
	}
}
