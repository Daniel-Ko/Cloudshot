package model.being;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import model.GameObjectInterface;
/**
 *  Class contains attributes which is common among all 'enemys'
 *  
 *  @author Jeremy Southon
 * 
 * */
public abstract class AbstractEnemy implements GameObjectInterface, EntityInterface {
	/** Used for collisions and getting X & Y coords */
	protected Rectangle boundingBox;
	protected Vector2 velocity;

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
