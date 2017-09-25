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
	
	public final float COLLECTABLE_WIDTH = 10;
	public final float COLLECTABLE_HIEGHT = 10;

	
	public AbstractCollectable(Vector2 position, float width, float height){
		this.boundingBox = new Rectangle(pos.x, pos.y, width, height);
		this.pos = position;
		
	}
	
	public void checkCollide(AbstractPlayer p){
		if(p.getBoundingBox().overlaps(this.getBoundingBox())){
			this.pickedUp = true;
			this.pickedUp(p);
		}	
	}
			
	public Rectangle getBoundingBox() {
		return boundingBox;
	}


	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	
	@Override
	public float getX() {
		return pos.x;
	}

	@Override
	public float getY() {
		return (float) pos.y;
	}
	
	@Override
	public abstract CustomSprite getImage();

	public abstract void pickedUp(AbstractPlayer p);

	

}
