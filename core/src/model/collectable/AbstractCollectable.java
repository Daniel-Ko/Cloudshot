package model.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import model.GameObjectInterface;

import model.being.AbstractPlayer;


import view.CustomSprite;
import view.MovingSprite;


public abstract class AbstractCollectable implements GameObjectInterface {
	

	protected Vector2 pos;
	protected Rectangle boundingBox;
	protected boolean pickedUp;
	protected AbstractPlayer player;
	
	public final int COLLECTABLE_WIDTH = 10;
	public final int COLLECTABLE_HIEGHT = 10;

	
	public AbstractCollectable(Vector2 position, int width, int height){
		this.boundingBox = new Rectangle(pos.x, pos.y, width, height);
		this.pos = position;
		
	}
	
	@Override
	public int getX() {
		return (int) pos.x;
	}

	@Override
	public int getY() {
		return (int) pos.y;
	}
	
	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void pickedUp(){
		throw new UnsupportedOperationException();
	}

}
