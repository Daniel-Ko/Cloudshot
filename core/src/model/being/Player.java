package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import model.GameModel;
import model.collectable.AbstractWeapon;
import model.collectable.Shotgun;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Represents the main playable character that the user controls
 * 
 * @author Jeremy Southon
 * */
public class Player extends AbstractPlayer {
	public static final float WIDTH = 32;
	public static final float HEIGHT = 32;
	private float maxSpeed = 2.5f;
	private float maxSpeedInAir = 0.5f;
	private int doubleJump = 0;
	private float meleeRange = 1;
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

	Shotgun pistol;
	List<BulletImpl> bullets = new ArrayList<>();
	//Box2D
	int numFootContact = 0;

	public Player() {
		super();
		damage = 1;
		health = 150;
		//LOAD SPRITES
		//..TODO
		curWeapon = null;
	}

	@Override
	public void initBox2D(World world,Vector2 position){
		super.initBox2D(world,position);
		//Used for ground detection, therefore used for jump mechanics
		world.setContactListener(new MyContactListener());
	}


	/**
	 * @param curWeapon the curWeapon to set
	 */
	public void setCurWeapon(AbstractWeapon curWeapon) {
		this.curWeapon = curWeapon;
	}
	public AbstractWeapon getCurWeapon(AbstractWeapon curWeapon) {
		return this.curWeapon;
	}


	protected void definePlayer(Vector2 pos){
		if(!world.isPresent())throw new Error("Expect there to be a body present, please call InitBox2D()");
		//body def
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true;

		//shape def for main fixture
		//PolygonShape shape = new PolygonShape();
		CircleShape shape = new CircleShape();
		shape.setRadius(5f/ GameModel.PPM);
		//shape.setAsBox(1,2);

		//fixture def
		playerProperties = new FixtureDef();
		playerProperties.shape = shape;
		playerProperties.density = 0.7f;
		playerProperties.friction = 15;

		//
		bodyDef.position.set(pos.x / GameModel.PPM,pos.y/GameModel.PPM);
		body = Optional.of(world.get().createBody(bodyDef));

		//adding main fixture
		body.get().createFixture(playerProperties);
		//add foot sensor fixture

		//shape.setAsBox(0.3f, 0.3f, new Vector2(0,-2), 0);
		playerProperties.isSensor = true;
		Fixture footSensorFixture = body.get().createFixture(playerProperties);
		footSensorFixture.setUserData("user_feet");
	}

	@Override
	public void update(List<AbstractEnemy> enemies){
		super.update(enemies);

		//Things to update if we have a body and world to move/move in
		if(body.isPresent()){
			if(numFootContact< 1)inAir = true;
			if(numFootContact >= 1){
				inAir = false;
				doubleJump = 0;
			}
		}

		ArrayList<BulletImpl> toRemove = new ArrayList<>();
		//updating players bullets
		for(BulletImpl b: bullets ){
			b.update(enemies);
			if (b.isToRemove()) {
				toRemove.add(b);
			}
		}
		for(BulletImpl b: toRemove){
			bullets.remove(b);
		}

	}

	/**
	 * Expected to loop through 'enemies' and if the player is attacking
	 * and there is a enemy within melee or attack_range then we can hurt it..

	 * @param enemy Enemy which we are checking against
	 *
	 * @return true if the player attacked a enemy, o.w false
	 * */
	public boolean attack(AbstractEnemy enemy){
		if(enemy == null)throw new Error("Enemy should not be null");

		//Enemy is within range of melee
		if(getPos().dst(enemy.getPosition())<meleeRange){
			if(getIsAttacking()){
				//enemy.hit(damage);
				enemy.enemyState.damage(enemy,damage);
			}
		}
		return false;
	}

	@Override
	public void shoot() {
		if(this.getCurWeapon()== null){return;}
		ArrayList<BulletImpl> bul = this.getCurWeapon().shoot(this);
		if(bul == null){return;}
		for(BulletImpl b: bul){
			if(bul != null){
				this.bullets.add(b);
			}
		}
	}

	/**
	 * Defined what happens when moving right
	 * */
	public void moveRight() {
		if(!body.isPresent())throw new Error("Should only be called if a body and world has been init");

		if(inAir &&  body.get().getLinearVelocity().x < maxSpeedInAir){
			body.get().applyLinearImpulse(new Vector2(0.07f,0),body.get().getWorldCenter(),true);
		}
		else if(!inAir){
			body.get().setLinearVelocity(maxSpeed,body.get().getLinearVelocity().y);
		}
	}

	/**
	 * Defined what happens when moving left
	 * */
	public void moveLeft() {
		if(!body.isPresent())throw new Error("Should only be called if a body and world has been init");

		//restrict movement speed in air
		if(inAir && body.get().getLinearVelocity().x>-maxSpeedInAir) {
			body.get().applyLinearImpulse(new Vector2(-0.07f, 0), body.get().getWorldCenter(), true);
		}
		//On ground and not yet at max speed
		else if(!inAir)
			body.get().setLinearVelocity(-maxSpeed,body.get().getLinearVelocity().y);
	}

	/**
	 * applies players jump speed onto Box2D body
	 * */
	public void jump(){
		if(!body.isPresent())throw new Error("Should only be called if a body and world has been init");

		//players feet is not in contact with ground therefore cant jump
		if(numFootContact<1){
			inAir =true;
		}
		//limiting jump speed
		if(body.get().getLinearVelocity().y<maxSpeed && !inAir || doubleJump <= 1){
			body.get().applyLinearImpulse(new Vector2(0,0.3f),body.get().getWorldCenter(),true);
			this.grounded = false;
			doubleJump++;
			inAir = true;
		}
	}

	@Override
	public float getX() {
			return getPos().x;
		}

	@Override
	public float getY(){ return getPos().y; }

	public List<BulletImpl> getBullets(){ return this.bullets; }

	public float getMeleeRange() {
		return meleeRange;
	}

	public void setMeleeRange(float meleeRange) {
		this.meleeRange = meleeRange;
	}

	public AbstractWeapon getCurWeapon(){ return this.curWeapon; }

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
		if(body.isPresent()){
			if(body.get().getLinearVelocity().x == 0 && body.get().getLinearVelocity().y == 0){
				MovingSprite idle = new MovingSprite("player_idle.png", 2, 2);
				//idle
				if(movingLeft) {
					idle.flipHorizontal();
					return idle;
				}
				return idle;
			}
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
			if(id == null)return;
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
			if(id == null)return;
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
