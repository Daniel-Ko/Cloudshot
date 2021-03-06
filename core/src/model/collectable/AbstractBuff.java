package model.collectable;

import com.badlogic.gdx.math.Vector2;

/**
 * AbstractBuff contains the logic for the buff collectables in the game.
 * @author Jake Robson
 */
public abstract class AbstractBuff extends AbstractCollectable {

    private static final long serialVersionUID = 1144730386837305193L;
    public final buff_type type;

    public enum buff_type {
        death, health, heavyammo, lightammo, slow
    }

    /**
     * This class represents abstract buff which effects player's fields.
     * @param position
     * @param width
     * @param height
     */
	public AbstractBuff(Vector2 position, float width, float height, buff_type bufftype) {
		super(position, width, height);
		type = bufftype;
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
