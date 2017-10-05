package model.being;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import model.GameModel;
import model.GameObjectInterface;
import model.collectable.AbstractWeapon;

import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.List;

/**
 * Provides basic character structure, location, size etc.
 * 
 * @author Jeremy Southon
 */
public abstract class AbstractPlayer implements GameObjectInterface, EntityInterface, InputProcessor, java.io.Serializable{

	private static final long serialVersionUID = 1313414442696252302L;

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

	protected int health;
	protected int damage;
	protected Rectangle boundingBox;
	
	// Variables of player actions
	protected  boolean inAir = false;
	protected boolean attacking = false;
	protected boolean grounded = false;
	protected boolean movingLeft;
	protected boolean movingRight;
	
	// Players inventory
	protected List<AbstractWeapon> inventory;
	
	// Position of the mouse
	protected Vector2 aimedAt = new Vector2(50,50);


	protected EnemyState state;
	//Box2D
	World world;
	public Body body;
	FixtureDef playerProperties;

	public AbstractPlayer(GameModel gameModel,Vector2 pos) {
		this.world = gameModel.getWorld();
		health = 10;
		this.pos = pos;
		velocity = new Vector2(0,0);
		boundingBox = new Rectangle(pos.x,pos.y, 8/GameModel.PPM, 8/GameModel.PPM);
		definePlayer(pos);
		this.inventory = new ArrayList<AbstractWeapon>();
}
	protected abstract void definePlayer(Vector2 pos);

	/**
	 * Applies player movement if they are moving
	 *
	 * Update the players action fields & check for collisions with platforms...
	 */
	public void update(List<AbstractEnemy> enemies) {
		handleInput();
		pos.set(body.getPosition());
		updateActionsPlayerDoing();
		//Updating Player Position
		//updating players bounding box position
		boundingBox = new Rectangle(getPos().x,getPos().y,boundingBox.width,boundingBox.height);
	}

	protected void handleInput(){
		if(movingLeft){
			//only want to move left
			moveLeft();
		}
		else if(movingRight){
			moveRight();
		}
	}
	/**
	 * Method constantly updates the fields which indicate the actions the player is
	 * preforming such as moving left,right..
	 * */
	private void updateActionsPlayerDoing(){
		//checks if dead
		if(health<=0){
			playerState = player_state.DEAD;
		}
	}

	/**
	 * Updates moving left and right fields appropriately
	 * and updates the velocity by speed;
	 * */
	public abstract void moveRight();
	/**
	 * Updates moving left and right fields appropriately
	 * and updates the velocity by speed;
	 * */
	public abstract void moveLeft();

	/**
	 * applies players jump height onto Box2D body
	 * */
	public abstract void jump();

	/**
	 * Inflicts param damage onto players current health
	 *
	 * @param damage to inflict on player
	 * */
	public void hit(float damage){
		this.health-=damage;
	}

	/**
	 * @return List of AbstractWeapons which the player has in inventory
	 * */
	public List<AbstractWeapon> getInventory() {
		return inventory;
	}
	

	public abstract boolean attack(AbstractEnemy enemy);

	public abstract void shoot();

	@Override
	public boolean keyDown(int keycode) {
		//Player is dead cant move
		if(playerState == player_state.DEAD)return false;
		switch (keycode){
			case Input.Keys.A:
				movingLeft = true;
				movingRight = false;
				break;
			case Input.Keys.D:
				movingRight = true;
				movingLeft = false;
				break;
			case Input.Keys.F:
				attacking = true;
				break;

			case Input.Keys.SPACE:
				jump();
				break;

			default:
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
			case Input.Keys.A:
				movingLeft = false;
				break;
			case Input.Keys.D:
				movingRight = false;
				break;
			case Input.Keys.W:
				break;
			case Input.Keys.F:
				attacking = false;
				break;
			default:
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
		shoot();
		return true;
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
		aimedAt = new Vector2(screenX/GameModel.PPM,screenY/GameModel.PPM);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	//
	/* GETTERS + SETTERS */
	//
	
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
	
	public player_state getPlayerState() {
		return playerState;
	}
	
	public void setPlayerState(player_state playerState) {
		this.playerState = playerState;
	}
	
	public Vector2 getPos() {
		return pos;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public Vector2 getAimedAt(){ return aimedAt; }
	
	public Rectangle getBoundingBox()
	{
		return boundingBox;
	}
	
	public boolean getIsAttacking(){ return attacking; }
	
	public boolean isInAir() {
		return inAir;
	}
	
	public boolean isAttacking() {
		return attacking;
	}
	
	public boolean isGrounded() {
		return grounded;
	}
	
	public boolean isMovingLeft() {
		return movingLeft;
	}
	
	public boolean isMovingRight() {
		return movingRight;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Body getBody() {
		return body;
	}
	
	public FixtureDef getPlayerProperties() {
		return playerProperties;
	}
	
	//==========================================
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public void setInAir(boolean inAir) {
		this.inAir = inAir;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}
	
	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
	
	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}
	
	public void setInventory(List<AbstractWeapon> inventory) {
		this.inventory = inventory;
	}
	
	public void setAimedAt(Vector2 aimedAt) {
		this.aimedAt = aimedAt;
	}
}
