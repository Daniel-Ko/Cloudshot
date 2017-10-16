package model.being.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import model.GameModel;
import model.GameModelInterface;
import model.GameObjectInterface;
import model.being.EntityInterface;
import model.being.enemies.AbstractEnemy;
import model.collectable.AbstractWeapon;
import view.screens.GameScreen;
import view.screens.GameScreen.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractPlayer implements GameObjectInterface, EntityInterface, InputProcessor, java.io.Serializable{

	private static final long serialVersionUID = 1313414442696252302L;


	/**
	 * Used to represent the different states of the player
	 */

	public enum player_state {
		ALIVE, DEAD;
	}
	/**
	 * used in applyKnockBack() direction in which the knock back is being applied form
	 * */
	public enum knock_back {
		NORTH,EAST,WEST,SOUTH
    }

	protected int health;
	protected int damage;
	protected player_state playerState = player_state.ALIVE;

	/* variables used in player physics */
	protected Vector2 pos;
	protected Rectangle boundingBox;
	
	// Variables of player actions
	protected boolean inAir;
	protected boolean grounded;
	protected boolean attacking;
	protected boolean movingLeft;
	protected boolean movingRight;
	//used for drawing the player in the correct direction
	protected boolean wasLeft;
	
	// Players inventory
	protected List<AbstractWeapon> inventory;
	protected int curWeapon;
	
	// Position of the mouse
	protected Vector2 aimedAt = new Vector2(50,50);

	//Box2D
	protected transient Optional<World> world;
	protected transient Optional<Body> body;
	protected transient FixtureDef playerProperties;

	//Used for converting mouse pressed coords into world coords
	private transient OrthographicCamera cam;
	private transient GameModelInterface game;

	public AbstractPlayer() {
		world = Optional.empty();
		body = Optional.empty();
		this.health = 10;
		this.damage = 1;
		this.pos = new Vector2(0,0);
		this.boundingBox = new Rectangle(pos.x,pos.y, 1, 1);
		this.inventory = new ArrayList<>();
	}

	/**
	 * Used to init the variables involved in Box2D, creating and adding a box2D body and placing it in the provided world.
	 *
	 * @param world Box2D physics world to put our player body in.
	 * @param position Position in our world to place our newly created body, note: this takes
	 *                 normal pixel coords and will scale them my PPM.
	 *
	 * */
	public void initBox2D(World world,Vector2 position){
		this.world = Optional.of(world);
		//defining an creating Box2D body for world
		definePlayer(position);
	}

	protected abstract void definePlayer(Vector2 pos);



	/**
	 * Applies player movement if they are moving
	 *
	 * Update the players action fields & check for collisions with platforms...
	 */
	public void update(List<AbstractEnemy> enemies) {
		//if we have a body and world to move around in
        body.ifPresent(body -> {
            handleInput();
            pos.set(body.getPosition());
        });
		if(health<=0){
			playerState = player_state.DEAD;
		}
		//updating players bounding box position
		boundingBox = new Rectangle(getPos().x,getPos().y,boundingBox.width,boundingBox.height);
	}

	private void handleInput(){
		if(movingLeft){
			//only want to move left
			moveLeft();
		}
		else if(movingRight){
			moveRight();
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

	/**
	 * Method used to apply a simple bounce back on the players body, to simulate it being hurt.
	 *
	 * @param direction direction in which to knock the player in.
	 * */
	public void applyKnockBack(knock_back direction){
		if(!body.isPresent())return;
		if(direction == knock_back.EAST){
			body.get().applyLinearImpulse(new Vector2(0.1f,0),body.get().getWorldCenter(),true);
		}
		else if (direction == knock_back.NORTH){
			body.get().applyLinearImpulse(new Vector2(0,0.1f),body.get().getWorldCenter(),true);
		}
		else if (direction == knock_back.WEST){
			body.get().applyLinearImpulse(new Vector2(-0.1f,0),body.get().getWorldCenter(),true);
		}
		else if (direction == knock_back.SOUTH){
			body.get().applyLinearImpulse(new Vector2(0,-0.1f),body.get().getWorldCenter(),true);
		}
	}

	public abstract boolean attack(AbstractEnemy enemy);

	public abstract void shoot();

	@Override
	public boolean keyDown(int keycode) {
		//Player is dead cant move
		if(playerState == player_state.DEAD)
			return false;

		if(GameScreen.state.equals(State.GAME_PAUSED)){
			return false;
		}
		switch (keycode){
			case Input.Keys.A:
				movingLeft = true;
				movingRight = false;
				wasLeft = true;
				break;
			case Input.Keys.D:
				movingRight = true;
				movingLeft = false;
				wasLeft = false;
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
		switchingWeaponInput(keycode);
		return true;
	}

	/**
	 * Handles switching between weapons in players inventory.
	 * */
	private void switchingWeaponInput(int keycode){
		if(keycode == Input.Keys.NUM_1){
			if(inventory.size()>=1)curWeapon = 0;
		}else if (keycode == Input.Keys.NUM_2){
			if(inventory.size()>=2)curWeapon = 1;
		}else if (keycode == Input.Keys.NUM_3){
			if(inventory.size()>=3)curWeapon = 2;
		}else if (keycode == Input.Keys.NUM_4){
			if(inventory.size()>=4)curWeapon = 3;
		}else if (keycode == Input.Keys.NUM_5){
			if(inventory.size()>=5)curWeapon = 4;
		}
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
		if(cam==null)return false;
		//screenY = Gdx.graphics.getHeight()-screenY;
		Vector3 v3 = new Vector3(cam.unproject(new Vector3(screenX,screenY,0)));
		aimedAt = new Vector2(v3.x,v3.y);
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
		return false;
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
	public void setPos(Vector2 pos) {
		this.pos = pos;
        body.ifPresent(body -> body.setTransform(pos, 0));
	}

	public int getCurWeapon() {
		return curWeapon;
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

	/**
	 * @return World if present, is present if initBox2D has been called o.w return null
	 * */
	public World getWorld() {
        return world.orElse(null);
    }

	/**
	 * @return body if present, is present if initBox2D has been called o.w return null
	 * */
	public Body getBody() {
        return body.orElse(null);
    }

    public GameModelInterface getModel(){
		return game;
	}

	public FixtureDef getPlayerProperties() {
		return playerProperties;
	}

	/**Provides the player the game camera, to allow us to convert coordinates*/
	public void setCamera(OrthographicCamera gameCam){
		cam = gameCam;
	}
	/**
	 * Provided the list of terrain in the map.
	 * */
	public void provideGameModel(GameModelInterface game){
		this.game = game;
	}
	public Array<Rectangle> getTerrainScaled(){
		return game.getScaledTerrain();
	}

	public void setWorld(Optional<World> world) {
		this.world = world;
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

	public void setCurWeapon(int curWeapon) {
		this.curWeapon = curWeapon;
	}
	
	public void setAimedAt(Vector2 aimedAt) {
		this.aimedAt = aimedAt;
	}
	
	public void setLinearVelocity(Vector2 newVel) {
		this.body.get().setLinearVelocity(newVel);
	}

	public boolean flip(){
		return wasLeft;
	}
}
