package model.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import model.GameObjectInterface;
import view.SpriteDrawer;

public abstract class AbstractCollectable implements GameObjectInterface {
	protected Vector2 pos;
	protected Rectangle boundingBox;
	protected Texture image;
	
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
	public SpriteDrawer getImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
