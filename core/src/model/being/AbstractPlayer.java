package model.being;

import java.awt.Rectangle;

import model.GameObjectInterface;

/**
 * Provideds basic character structure, location, size etc.
 * 
 * @author Jeremy Southon
 */
public abstract class AbstractPlayer implements GameObjectInterface {

	private int health;
	private int x;
	private int y;
	private int damage;
	private double velocity;

	private Rectangle boundingBox;/// collision detection
	
	public AbstractPlayer( int x, int y, int width, int height,int hp, double vel) {
		health = hp;
		this.x = y;
		this.y =y;
		velocity = vel;
		boundingBox = new Rectangle(x, y, width, height);
	}

}
