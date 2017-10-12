package model.collectable;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractBuff extends AbstractCollectable {
    /**
     * This class represents abstract buff which effects player's fields.
     * @param position
     * @param width
     * @param height
     */
	public AbstractBuff(Vector2 position, float width, float height) {
		super(position, width, height);
	}

    /**
     * get X pos on map.
     * @return Y
     */
	@Override
    public float getX() {
        return super.getX();
    }
    /**
     * get Y pos on map.
     * @return Y
     */
    @Override
    public float getY() {
        return super.getY();
    }


   
}
