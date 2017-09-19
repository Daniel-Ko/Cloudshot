package model.collectable;

import model.GameObjectInterface;
import view.SpriteDrawer;

public abstract class AbstractCollectable implements GameObjectInterface {

	/* (non-Javadoc)
	 * @see model.GameObjectInterface#getX()
	 */
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see model.GameObjectInterface#getY()
	 */
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
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
