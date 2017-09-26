package model.being;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import model.GameObjectInterface;
import model.collectable.AbstractWeapon;

import com.badlogic.gdx.math.Rectangle;


import java.util.List;

/**
 * Provides basic character structure, location, size etc.
 * 
 * @author Jeremy Southon
 */
public abstract class AbstractPlayer implements GameObjectInterface, EntityInterface, InputProcessor, java.io.Serializable{

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
	protected float jumpSpeed = 100;
	final static float MAX_VELOCITY = 7f;

	protected int health;
	protected int damage;
	protected Rectangle boundingBox;

	// Variables of player actions
	protected boolean canJump = true;
	protected  boolean jumping = false;
	protected boolean attacking = false;
	protected boolean grounded = false;
	protected boolean movingLeft;
	protected boolean movingRight;
	/** Players inventory */
	protected List<AbstractWeapon> inventory;
	/** Position of the mouse*/
	protected Vector2 aimedAt;

	//Box2D
	World world;
	Body body;
	FixtureDef playerProperties;

	public AbstractPlayer(Vector2 position, int width, int height, int hp, float speed, World world) {
		this.world = world;
		health = hp;
		pos = position;
		this.speed=speed;
		velocity = new Vector2(0,0);
		boundingBox = new Rectangle(pos.x,pos.y, width, height);
		// init player constants
		gravity = new Vector2(0, -1);
		//Box2D
		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(pos);
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		body = world.createBody(bodyDef);

		playerProperties = new FixtureDef();
		//playerProperties.friction = 0f;
		CircleShape circle = new CircleShape();
		circle.setRadius(5);

		playerProperties.shape = circle;
		body.createFixture(playerProperties);
	}

	/**
	 * Updates forces acting on player, therefore updating his pos over time
	 *
	 * If the player has a velocity we add this to his position
	 * Update player y to that of the Box2D body (for the gravity effect).
	 */
	public void update(Array<Rectangle> tiles) {
		collisionChecks(null);
		updateActionsPlayerDoing();
		//Updating Player Position
		pos.add(velocity);
		pos.y = body.getPosition().y;//Box2D body y cause gravity

		//Moving(Translating) the x by players
		body.setTransform(pos,0);
		//updating players bounding box position
		boundingBox = new Rectangle(pos.x,pos.y+15,boundingBox.width,boundingBox.height);
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
		if(velocity.x<0) movingLeft = true;
		else if (velocity.x>0)movingRight=true;
	}

	/***
	 * Loops ..a
	 */
	protected void collisionChecks(Array<Rectangle> tiles) {
		Array<Contact> contactList = world.getContactList();
		for(int i = 0; i < contactList.size; i++) {
			Contact contact = contactList.get(i);
			if(contact.isTouching() && (contact.getFixtureA() == body.getFixtureList().first() ||
					contact.getFixtureB() == body.getFixtureList().first())) {
				//on ground
				grounded = true;
				jumping = false;
			}}
	}
	/**
	 * Updates moving left and right fields appropriately
	 * and updates the velocity by speed;
	 * */
	public void moveRight() {
		movingLeft = false;
		movingRight = true;
		velocity.x += speed;
	}
	/**
	 * Updates moving left and right fields appropriately
	 * and updates the velocity by speed;
	 * */
	public void moveLeft() {
		movingRight = false;
		movingLeft = true;
		velocity.x -= speed;
	}

	/**
	 * applies players jump speed onto Box2D body
	 * */
	public void jump(){
		body.applyLinearImpulse(new Vector2(0,jumpSpeed),body.getWorldCenter(),true);
		this.grounded = false;
		jumping = true;
	}

	/**
	 * Inflicts param damage onto players current health
	 *
	 * @param damage to inflict on player
	 * */
	public void hit(int damage){
		this.health-=damage;
	}

	/**
	 * @return List of AbstractWeapons which the player has in inventory
	 * */
	public List<AbstractWeapon> getInventory() {
		return inventory;
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

	public player_state getPlayerState() {
		return playerState;
	}

	public void setPlayerState(player_state playerState) {
		this.playerState = playerState;
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

	public abstract boolean attack(AbstractEnemy enemy);

	public abstract void shoot();

	@Override
	public boolean keyDown(int keycode) {
		//Player is dead cant move
		if(playerState == player_state.DEAD)return false;
		switch (keycode){
			case Input.Keys.A:
				moveLeft();
				break;
			case Input.Keys.D:
				moveRight();
				break;
			case Input.Keys.W:
				if(grounded)
					jump();
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
