package model.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import model.GameObjectInterface;
<<<<<<< HEAD
import model.being.AbstractPlayer;
import view.SpriteDrawer;
=======
import view.CustomSprite;
import view.MovingSprite;
>>>>>>> branch 'v1' of https://gitlab.ecs.vuw.ac.nz/swen222-2017-p1-t14/Cloudshot.git

public abstract class AbstractCollectable implements GameObjectInterface {
	protected Vector2 pos;
	protected Rectangle boundingBox;
	protected Texture image;
	protected boolean pickedUp;
	protected AbstractPlayer player;
	
	public AbstractCollectable(Vector2 position, int width, int height){
		this.boundingBox = new Rectangle(pos.x, pos.y, width, height);
		this.pos = position;
		
	}
	
	/* (non-Javadoc)
	 * @see model.GameObjectInterface#getX()
	 */
	@Override
	public int getX() {
		return (int) pos.x;
	}

	/* (non-Javadoc)
	 * @see model.GameObjectInterface#getY()
	 */
	@Override
	public int getY() {
		return (int) pos.y;
	}

	/* (non-Javadoc)
	 * @see model.GameObjectInterface#getImage()
	 */
	@Override
<<<<<<< HEAD
	public SpriteDrawer getImage() {
		
=======
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
>>>>>>> branch 'v1' of https://gitlab.ecs.vuw.ac.nz/swen222-2017-p1-t14/Cloudshot.git
		return null;
	}
	/* (non-Javadoc)
	 * abstract method that manipulates player when picked up
	 * eg increase help if the collectable is a health pack
	 */
	public void pickedUp(){
		throw new UnsupportedOperationException();
	}

}
