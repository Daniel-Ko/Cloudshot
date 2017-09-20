package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.GameObjectInterface;
import view.CustomSprite;

public abstract class AbstractBuff extends AbstractCollectable {

    public AbstractBuff(Vector2 position, int width, int height) {
		super(position, width, height);
	}

	@Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }

	/* (non-Javadoc)
	 * @see model.collectable.AbstractCollectable#getImage()
	 */
	
	

	/* (non-Javadoc)
	 * @see model.collectable.AbstractCollectable#pickedUp()
	 */
	

   
}
