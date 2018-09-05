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

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.entity.AbstractGameObject;
import com.snapgames.gdj.core.gfx.RenderHelper.Justification;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class UIGameObject extends AbstractGameObject implements UIComponent {

	protected Font font;
    public Color frontColor;
    public Color shadowColor;
    public int shadowBold;
    public Color backgroundColor;
    public Justification justification;

    public List<UIComponent> child;
	public UIComponent parent;
	public UIComponent previous;
	public UIComponent next;
	public UILayout layout;
	public float offsetX = 10;
	public float offsetY = 4;

	/**
	 * 
	 */
	public UIGameObject() {
		super();
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param layer
	 * @param priority
	 * @param color
	 */
	public UIGameObject(String name, int x, int y, int width, int height, int layer, int priority, Color color) {
		super(name, x, y, width, height, layer, priority, color);
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 */
	public UIGameObject(String name, int x, int y) {
		super(name, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#getID()
	 */
	@Override
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#getValue()
	 */
	@Override
	public String getValue() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#onFocus()
	 */
	@Override
	public void onFocus() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapgames.gdj.core.entity.UIComponent#onFocusLost()
	 */
	@Override
	public void onFocusLost() {

	}

	/**
	 * Set the front color based on int values for RGB components.
	 * @param red red color int factor
	 * @param green green color int factor
	 * @param blue blue color int factor
	 */
    public void setFrontColor(int red, int green, int blue) {
        frontColor = new Color(red, green, blue);
    }

	/**
	 * Add a new child component to this one.
	 * @param component the UIComponent to add as child.
	 */
	public void add(UIComponent component){
		if(this.child==null){
			this.child=new ArrayList<>();
		}
		component.setParent(this);
		// if first
		if(child.size()==0){
			component.setPosition(this.x,this.y);
		}else{
			// not first !
			switch(layout){
				case HORIZONTAL:
					component.setPosition(this.x+child.get(child.size()-1).getWidth()+offsetX,y);
					break;
				case HORiZONTAL_DISTRIBUTED:
					distributeComponentsOnHorizontal();
					break;
				case VERTICAL:
					component.setPosition(this.x,this.y+child.get(child.size()-1).getHeight()+offsetY);
					break;
				case VERTICAL_DISTRIBUTED:
					distributeComponentsOnVertical();
					break;
			}
		}
		this.boundingBox.add(component.getBoundingBox());
		this.child.add(component);
	}

	private void distributeComponentsOnVertical() {

	}

	private void distributeComponentsOnHorizontal() {
	}

	/**
	 * set The parent UIComponent for this one.
	 * @param parent
	 */
	public void setParent(UIComponent parent){
		this.parent = parent;
	}

	@Override
	public Rectangle getBoundingBox() {
		return this.boundingBox;
	}


	@Override
	public void draw(Game game, Graphics2D g) {
		super.draw(game, g);
		this.boundingBox=new Rectangle();
		if(child!=null) {
			for (UIComponent c : child) {
				this.boundingBox.add(c.getBoundingBox());
			}
		}
	}

	@Override
	public float getHeight() {
		return this.height;
	}
	@Override
	public float getWidth() {
		return this.width;
	}

	@Override
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
		this.boundingBox=new Rectangle((int)x,(int)y,width,height);
	}
}
