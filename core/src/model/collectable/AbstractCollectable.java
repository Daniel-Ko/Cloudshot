package model.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import model.GameObjectfloaterface;

import model.being.AbstractPlayer;


import view.CustomSprite;
import view.MovingSprite;


public abstract class AbstractCollectable implements GameObjectfloaterface {
	

	protected Vector2 pos;
	protected Rectangle boundingBox;
	protected boolean pickedUp;
	protected AbstractPlayer player;
	
	public final float COLLECTABLE_WIDTH = 10;
	public final float COLLECTABLE_HIEGHT = 10;

	
	public AbstractCollectable(Vector2 position, float width, float height){
		this.boundingBox = new Rectangle(pos.x, pos.y, width, height);
		this.pos = position;
		
	}
	
	@Override
	public float getX() {
		return (float) pos.x;
	}

	@Override
	public float getY() {
		return (float) pos.y;
	}
	
	@Override
	public abstract CustomSprite getImage();

	public abstract void pickedUp();

}
