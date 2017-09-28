package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import view.CustomSprite;
import view.MovingSprite;

/**
 * Class has the basic structure of a basic melee attacking enemy to get a more
 * unqiue melee enemy think about extending from this class
 * 
 * @author Jeremy Southon
 * 
 */
public class MeleeEnemy extends AbstractEnemy {
	int detectionRadius = 200;
	int attackRadius = 36;
	boolean canAttack;

	//DIFFERENT IMAGES FOR DIFFERENT STATES
	private String walking = "Skeleton Walk.png";
	private String attacking = "Skeleton Walk.png";

	private CustomSprite attack;
	private CustomSprite dead;
	private CustomSprite idle;
	private CustomSprite walk;

	public MeleeEnemy(int hp,AbstractPlayer player,Vector2 pos,World world){
		super(hp,player,pos,world);
		defineBody();
		attack = new MovingSprite("Skeleton Attack.png",1,18);
		dead = new MovingSprite("Skeleton Dead.png",1,1);
		idle = new MovingSprite("Skeleton Idle.png",1,11);
		walk = new MovingSprite("Skeleton Walk.png",1,13);
		damage = 1;
	}

	protected void defineBody(){
		//body def
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true;

		//shape def for main fixture
		//PolygonShape shape = new PolygonShape();
		CircleShape shape = new CircleShape();
		shape.setRadius(10f/ GameModel.PPM);
		//shape.setAsBox(1,2);

		//fixture def
		fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 0.7f;
		fDef.friction = 15;

		//
		bodyDef.position.set(position.x / GameModel.PPM,position.y/GameModel.PPM);
		body = world.createBody(bodyDef);
		//adding main fixture
		body.createFixture(fDef);

	}

	/**
	 * DESC
	 *
	 * @return true if landed and attack o.w false
	 * */
	@Override
	protected boolean attack() {
		state = enemy_state.EATTACKING;
		player.hit(damage);
		return true;
	}

	/**
	 * if the player is within this enemys detection radius then it follows the player
	 * if the player is also in hitting range it damages the player
	 *
	 * finally updates the players position by the velocity
	 * */
	@Override
	public void update() {
		if(state == enemy_state.EDEAD)return;
		if(health <= 0 )state = enemy_state.EDEAD;
		if(state == enemy_state.EDEAD)return;

		velocity.x=0;
		velocity.y=0;
		state = enemy_state.EALIVE;
		movement();
	}

	@Override
	public void movement(){
		if(position.dst(player.pos)<detectionRadius){
			if(position.dst(player.pos)<attackRadius){
				if(player.getPlayerState() == AbstractPlayer.player_state.ALIVE)attack();
			}
			if(getX()<player.getX())
				velocity.x = speed;
			if(getX()>player.getX())
				velocity.x = -speed;
			if(getY()<player.getY()+24)//FIXME replace 24 with image height
				velocity.y = speed;
			if(getY()>player.getY()+24)
				velocity.y = -speed;
		}
		//apply velocity onto position
		position.add(velocity);
	}

	@Override
	public CustomSprite getImage() {
		if(state == enemy_state.EDEAD){
			return dead;}

		if(state == enemy_state.EATTACKING){
			return attack;
		}
		//IDLE STATE
		if(velocity.x ==0 && velocity.y == 0){
			return idle;
		}
		return walk;
	}

}
