package model.collectable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.GameObjectInterface;
import model.being.player.AbstractPlayer;
import view.sprites.CustomSprite;


public abstract class AbstractCollectable implements GameObjectInterface, java.io.Serializable{
	
	//fields for defining A collectable.
	protected Vector2 pos;
	protected Rectangle boundingBox;
	protected boolean pickedUp = false;
	
	public final float COLLECTABLE_WIDTH = 10;
	public final float COLLECTABLE_HIEGHT = 10;

	/**
	 * This class represents anything on the map which can be collected.
	 * @param position
	 * @param width
	 * @param height
     */
	public AbstractCollectable(Vector2 position, float width, float height){
		this.pos = position;
		pos.x = pos.x/GameModel.PPM;
		pos.y = pos.y/GameModel.PPM;
		this.boundingBox = new Rectangle(pos.x, pos.y, width/GameModel.PPM, height/GameModel.PPM);
	}

	/**
	 * Checks whether the Player's bounding box is colliding with the collectible's
	 * bounding box. Then calls Abstract method pickedUp(p)
	 * @param p
	 * @return true if the collision is detected.
     */
	public boolean checkCollide(AbstractPlayer p){
		if(this.isPickedUp()){return false;}
		if(p.getBoundingBox().overlaps(this.getBoundingBox())){
			this.pickedUp = true;
			this.pickedUp(p);
			return true;
		}
		return false;
	}

	/**
	 * Declares that all subclasses have to perform an action when picked up
	 * @param p
     */
	public abstract void pickedUp(AbstractPlayer p);




	/**
	 * -----------------GETTERS AND SETTERS-------------------
     *
	 * /
	/**
	 * @return the pickedUp
	 */
	public boolean isPickedUp() {
		return pickedUp;
	}

	/**
	 * @param pickedUp the pickedUp to set
	 */
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	/**
	 * gets bounding box
	 * @return bounding box of the object
     */
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
		return pos.y;
	}
	
	@Override
	public abstract CustomSprite getImage();



	

}
