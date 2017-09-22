package model.being;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import model.GameObjectInterface;
import model.collectable.AbstractWeapon;

import com.badlogic.gdx.math.Rectangle;
import java.util.List;

/**
 * Provides basic character structure, location, size etc.
 * 
 * @author Jeremy Southon
 */
public abstract class AbstractPlayer implements GameObjectInterface, EntityInterface, InputProcessor {

	/**
	 * Used to represent the different states of the player
	 */
	public static enum player_state {
		ALIVE, DEAD
	}

	protected player_state playerState = player_state.ALIVE;

	/* variables used in player physics */
	protected Vector2 pos;
	protected Vector2 velocity;
	protected Vector2 gravity;
	protected float speed;

	protected int health;
	protected int damage;
	protected Rectangle boundingBox;

	// Variables of player actions
	protected boolean canJump = true;
	protected boolean attacking = false;

	/** Players inventory */
	protected List<AbstractWeapon> inventory;
	/** Position of the mouse*/
	protected Vector2 aimedAt;

	public AbstractPlayer(Vector2 position, int width, int height, int hp, float speed) {
		health = hp;
		pos = position;
		this.speed=speed;
		velocity = new Vector2(0,0);
		boundingBox = new Rectangle(pos.x,pos.y, width, height);
		// init player constants
		gravity = new Vector2(0, -1);

	}

	/**
	 * Updates forces acting on player, therefore updating his pos over time
	 */
	public void update() {
		//updating player pos based on velocity
		getPos().add(getVelocity());
		collisionChecks();
		//checks if dead
		if(health<=0){
			playerState = player_state.DEAD;
		}
		//updating players bounding box position
		boundingBox = new Rectangle(pos.x,pos.y,boundingBox.width,boundingBox.height);
	}

	protected void collisionChecks() {
		// TODO replace this as a y == 10 will not always represent ground
		// if player is not on ground apply gravity
		if (pos.y <= 100) {
			//on ground
			canJump = true;
			velocity.y=0;
		}else if(pos.y>20){
			//in air
			velocity.add(gravity);
		}
	}

	public abstract boolean attack(AbstractEnemy enemy);

	public abstract void shoot();
	/**
	 * Updates the players pos by velocity
	 */
	public void moveRight() {
		pos.x += velocity.x;
	}
	public void moveLeft() {
		pos.x -= velocity.x;
	}
	public void moveDown() {
		pos.y -= velocity.y;
	}
	public void moveUp() {
		pos.y += velocity.y;
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

	/**
	 * Inflicts param damage onto players current health
	 *
	 * @param damage to inflict on player
	 * */
	public void hit(int damage){
		this.health-=damage;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return List of AbstractWeapons which the player has in inventory
	 * */
	public List<AbstractWeapon> getInventory() {
		return inventory;
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

	public Vector2 getAimedAt(){ return aimedAt; }

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
	public boolean getIsAttacking(){ return attacking; }

	@Override
	public boolean keyDown(int keycode) {
		//Player is dead cant move
		if(playerState == player_state.DEAD)return false;
		switch (keycode){
			case Input.Keys.A:
				velocity.x = -speed;
				break;
			case Input.Keys.D:
				velocity.x = speed;
				break;
			case Input.Keys.W:
				if(canJump)
					velocity.y = 50;
					canJump =false;
				break;
			case Input.Keys.SPACE:
				attacking = true;
				break;
			default:
				velocity = new Vector2(0,0);
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
			case Input.Keys.A:
				velocity.x = 0;
				break;
			case Input.Keys.D:
				velocity.x = 0;
				break;
			case Input.Keys.W:
				velocity.y = 0;
				break;
			case Input.Keys.SPACE:
				attacking = false;
				break;
			default:
				velocity = new Vector2(0,0);
				break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		aimedAt = new Vector2(screenX,screenY);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
