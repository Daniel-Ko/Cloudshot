package model.being;

import com.badlogic.gdx.math.Vector2;
import com.brashmonkey.spriter.Rectangle;

import model.GameObjectInterface;

/**
 * Provideds basic character structure, location, size etc.
 * 
 * @author Jeremy Southon
 */
public abstract class AbstractPlayer implements GameObjectInterface, EntityInterface {

	/**
	 * Used to represent the different states of the player
	 */
	public static enum player_state {
		ALIVE, DEAD;
	}

	protected player_state playerState = player_state.ALIVE;

	/* variables used in player physics */
	protected Vector2 pos;
	protected Vector2 velocity;
	protected Vector2 gravity;

	protected int health;
	protected int damage;
	protected Rectangle boundingBox;

	// players of player actions
	protected boolean canJump = false;
	protected boolean falling = true;

	/** Players inventory */
	// List<Collectables> inventory
	public AbstractPlayer(Vector2 position, int width, int height, int hp, Vector2 vel) {
		health = hp;
		pos = position;
		velocity = vel;
		boundingBox = new Rectangle((int) pos.x, (int) pos.y, width, height);

		// init player constants
		gravity = new Vector2(0, 3);
	}

	/**
	 * Updates forces acting on player, therefore updating his pos over time
	 */
	public void update() {
		applyGrav();
	}

	protected void applyGrav() {
		// TODO replace this as a y == 10 will not always represent ground
		// if player is not on ground apply gravity
		if (pos.y >= 10) {
			pos.add(velocity);
			velocity.add(gravity);
		} else {
			// on the ground
			falling = false;
			canJump = true;
		}
	}

	public void jump() {
		if (canJump) {

		}
	}

	/**
	 * Updateds the players pos by velovity
	 * 
	 */
	public void moveRight() {
		pos.x += velocity.x;
	}

	/**
	 * Updateds the players pos by velovity
	 * 
	 */
	public void moveLeft() {
		pos.x -= velocity.x;
	}

	public player_state getPlayerState() {
		return playerState;
	}

	public void setPlayerState(player_state playerState) {
		this.playerState = playerState;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

}
