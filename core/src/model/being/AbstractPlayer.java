package model.being;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import model.GameObjectInterface;

/**
 * Provideds basic character structure, location, size etc.
 * 
 * @author Jeremy Southon
 */
public abstract class AbstractPlayer implements GameObjectInterface {
	protected Texture playerImage;

	public static enum player_state {
		ALIVE, DEAD;
	}

	protected int health;
	protected int damage;
	protected Vector2 pos;
	protected Vector2 velocity;
	protected Vector2 accel = new Vector2(0,-1);//FIXME
	int maxD = 10;
	protected Rectangle boundingBox;/// collision detection

	//players of player actions
	protected boolean canJump = false;
	protected boolean falling = true;
	
	public AbstractPlayer(Vector2 position, int width, int height, int hp, Vector2 vel, Texture image) {
		health = hp;
		pos = position;
		velocity = vel;
		boundingBox = new Rectangle((int) pos.x, (int) pos.y, width, height);
		playerImage = image;
	}
	
	/**
	 * Updates forces acting on player, therefore updating his pos over time
	 * */
	public void update(){
		falling();
	}
	
	protected void falling(){
		//if player is not on ground apply gravity
		if(pos.y>= 10){
			pos.add(velocity);
			velocity.add(accel);
		}else {
			//on the ground
			falling = false;
			canJump = true;
		}
	}
	
	public void jump() {
		if(canJump){
			
		}
	}

	/* Public methods for moving player by players velocity */
	public void moveRight() {
		pos.x += velocity.x;
	}

	public void moveLeft() {
		pos.x -= velocity.x;
	}

	public Texture getPlayerImage() {
		return playerImage;
	}

	public void setPlayerImage(Texture playerImage) {
		this.playerImage = playerImage;
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

	public Vector2 getAccel() {
		return accel;
	}

	public void setAccel(Vector2 accel) {
		this.accel = accel;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

}
