package model.being;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import model.collectable.AbstractWeapon;
import model.collectable.Pistol;
import view.CustomSprite;
import view.MovingSprite;


/**
 * Represents the main playable character that the user controls
 * 
 * @author Jeremy Southon
 * */
public class Player extends AbstractPlayer {
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	private int meleeRange = 30;
	protected AbstractWeapon curWeapon;

	private CustomSprite current;

	private CustomSprite idle_right;
	private CustomSprite attack_right;
	private CustomSprite jump_right;
	private CustomSprite walk_right;
	private CustomSprite death;


	private CustomSprite idle_left;
	private CustomSprite attack_left;
	private CustomSprite jump_left;
	private CustomSprite walk_left;

	Pistol pistol;

	//Box2D
	int numFootContact = 0;
	public Player(Vector2 position, int width, int height, int hp, float speed, World world) {
		super(position, width, height, hp, speed,world);
		damage = 1;

		//load sprites
		current = new MovingSprite("player_idle.png",2,2);
		idle_right = new MovingSprite("player_idle.png",2,2);
		attack_right = new MovingSprite("player_attack.png",2,3);
		jump_right = new MovingSprite("player_jump.png",2,3);
		walk_right = new MovingSprite("player_walk.png",3,3);
		death = new MovingSprite("player_death.png",1,1);

		idle_left = new MovingSprite("player_idle.png",2,2);
		attack_left = new MovingSprite("player_attack.png",2,3);
		jump_left = new MovingSprite("player_jump.png",2,3);
		walk_left = new MovingSprite("player_walk.png",3,3);
		idle_left.flipHorizontal();
		attack_left.flipHorizontal();
		jump_left.flipHorizontal();
		walk_left.flipHorizontal();
		// TODO

		pistol = new Pistol(pos,10,10);

		//Box2D
		world.setContactListener(new MyContactListener());
	}

	protected void definePlayer(Vector2 pos){
		//body def
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true;

		//shape def for main fixture
		PolygonShape shape = new PolygonShape();
		//shape.setRadius;
		shape.setAsBox(1,2);

		//fixture def
		playerProperties = new FixtureDef();
		playerProperties.shape = shape;
		playerProperties.density = 1;
		playerProperties.friction = 18;
		//
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		//adding main fixture
		body.createFixture(playerProperties);

		//add foot sensor fixture
		shape.setAsBox(0.3f, 0.3f, new Vector2(0,-2), 0);
		playerProperties.isSensor = true;
		Fixture footSensorFixture = body.createFixture(playerProperties);
		footSensorFixture.setUserData("user_feet");
	}

	@Override
	public void update(Array<Rectangle> tiles){
		super.update(null);

		if(numFootContact< 1)inAir = true;
		if(numFootContact >= 1)inAir = false;
	}

	/**
	 * Expected to loop through 'enemies' and if the player is attacking
	 * and there is a enemy within melee or attack_range then we can hurt it..

	 * @param enemy Enemy which we are checking against
	 *
	 * @return true if the player attacked a enemy, o.w false
	 * */
	public boolean attack(AbstractEnemy enemy){
		//Enemy is within range of melee
		if(getPos().dst(enemy.getPosition())<meleeRange){
			if(getIsAttacking()){
				enemy.hit(damage);
			}
		}
		return false;
	}

	@Override
	public void shoot() {
		pistol.shoot(this);
	}

	/**
	 * Defined what happens when moving right
	 * */
	public void moveRight() {
		if(inAir){
			body.applyForce(new Vector2(1000,0),body.getWorldCenter(),true);
		}
		else
			body.applyLinearImpulse(new Vector2(500,0),body.getWorldCenter(),true);
	}

	/**
	 * Defined what happens when moving left
	 * */
	public void moveLeft()
	{
		if(inAir)
			body.applyForce(new Vector2(-1000,0),body.getWorldCenter(),true);

		else
			body.applyLinearImpulse(new Vector2(-200,0),body.getWorldCenter(),true);
	}

	/**
	 * applies players jump speed onto Box2D body
	 * */
	public void jump(){
		//players feet is not in contact with ground therefore cant jump
		if(numFootContact<1){
			inAir =true;
			return;
		}
		body.applyLinearImpulse(new Vector2(0,500f),body.getWorldCenter(),true);
		this.grounded = false;
		inAir = true;
	}

	@Override
		public float getX() {
			return getPos().x;
		}

		@Override
		public float getY() {
			return getPos().y;
		}



	@Override
	public CustomSprite getImage() {
		if(playerState == player_state.DEAD){
			return new MovingSprite("player_death.png", 1, 1);
		}

		if(getIsAttacking()){
			MovingSprite attacking =  new MovingSprite("player_attack.png", 2, 3);
			if(movingLeft)
				attacking.flipHorizontal();
			return  attacking;
		}
		//FIXME temp effect for inAir
		//JUMPING ANIMATION
		if(this.inAir){
			MovingSprite jump = new MovingSprite("player_jump.png", 2, 3);
			if(movingLeft)
				jump.flipHorizontal();
			return jump;
		}
		//IDLE ANIMATION
		if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0){
			MovingSprite idle = new MovingSprite("player_idle.png", 2, 2);
			//idle
			if(movingLeft) {
				idle.flipHorizontal();
				return idle;
			}
			return idle;
		}
		MovingSprite walking = new MovingSprite("player_walk.png", 3, 3);
		if(movingLeft)
			walking.flipHorizontal();
		return walking;


}

	class MyContactListener implements ContactListener{
		//http://www.iforce2d.net/b2dtut/jumpability

		@Override
		public void beginContact(Contact contact) {

			String id = (String) contact.getFixtureA().getUserData();
			if(id.equals("user_feet")){
				numFootContact++;
			}
			String id2 = (String) contact.getFixtureB().getUserData();
			if(id2 == null)return;
			if(id2.equals("user_feet")){
				numFootContact++;
			}
		}

		@Override
		public void endContact(Contact contact) {
			String id = (String) contact.getFixtureA().getUserData();
			if(id.equals("user_feet")){
				numFootContact--;
			}
			String id2 = (String) contact.getFixtureB().getUserData();
			if(id2 == null)return;
			if(id2.equals("user_feet")){
				numFootContact--;
			}
		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {

		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {

		}
	}

}
