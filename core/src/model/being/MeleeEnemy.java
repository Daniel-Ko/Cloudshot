package model.being;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import view.CustomSprite;
import view.MovingSprite;

import java.util.Random;

/**
 * Class has the basic structure of a basic melee attacking enemy to get a more
 * unqiue melee enemy think about extending from this class
 * 
 * @author Jeremy Southon
 * 
 */
public class MeleeEnemy extends AbstractEnemy {
	int detectionRadius = 3;
	int attackRadius = 1;
	private int splitID = 0;//0 = original smile,1 = second gen..

	//DIFFERENT IMAGES FOR DIFFERENT STATES
	private String walking = "Skeleton Walk.png";
	private String attacking = "Skeleton Walk.png";

	private CustomSprite attack;
	private CustomSprite dead;
	private CustomSprite idle;
	private CustomSprite walk;
	private CustomSprite walk_l;

	public MeleeEnemy(GameModel gameModel,Vector2 pos){
		super(gameModel,pos);
		health = 20;
		attack =  new MovingSprite("slime_attack.png",1,7);
		dead = new MovingSprite("Skeleton Dead.png",1,1);
		idle = new MovingSprite("slime_walk.png",1, 9);
		walk = new MovingSprite("slime_walk.png",1, 9);
		walk_l = new MovingSprite("slime_walk.png",1, 9);
		walk_l.flipHorizontal();
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
		body.createFixture(fDef).setUserData("mob1");

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
		if(state == enemy_state.EDEAD || player.getPlayerState() == AbstractPlayer.player_state.DEAD)return;
		if(health <= 0 ){
			state = enemy_state.EDEAD;
			if(splitID < 1 ){
				//split slime into 2 but half that stats
				MeleeEnemy e1 = new MeleeEnemy(game,new Vector2((body.getPosition().x* GameModel.PPM)-10,body.getPosition().y * GameModel.PPM));
				MeleeEnemy e2 = new MeleeEnemy(game,new Vector2((body.getPosition().x* GameModel.PPM)+10,body.getPosition().y * GameModel.PPM));
				e1.drawingWidth = drawingWidth/2;
				e1.drawingHeight = drawingHeight/2;
				e2.drawingWidth = drawingWidth/2;
				e2.drawingHeight = drawingHeight/2;
				e1.splitID=splitID+1;
				e2.splitID=splitID+1;
				e1.damage = damage/2;
				e2.damage = damage/2;
				game.addEnemy(e1);
				game.addEnemy(e2);
			}
			world.destroyBody(body);
		}
		if(state == enemy_state.EDEAD)return;

		position.set(body.getPosition());
		boundingBox.set(position.x,position.y,boundingBox.getWidth(),boundingBox.getHeight());
		state = enemy_state.EALIVE;
		movement();
	}

	@Override
	public void movement(){
		if(position.dst(player.getPos())>detectionRadius)state = enemy_state.EIDLE;
		if(position.dst(player.pos)<attackRadius){
			if(player.getPlayerState() == AbstractPlayer.player_state.ALIVE)attack();
		}
		//MOVEMENT
		if(state == enemy_state.EIDLE){
			idleMovement();
		}else {
			foundPlayerMovement();
		}
		//if not within attacking range
		if(position.dst(player.pos)>attackRadius){ state = enemy_state.EIDLE;}
	}

	private void foundPlayerMovement(){
		if(state == enemy_state.EDEAD)return;
		if(position.dst(player.getPos())< detectionRadius){
			if(getX()<player.getX())
				body.setLinearVelocity(1f,body.getLinearVelocity().y);
			if(getX()>player.getX())
				body.setLinearVelocity(-1f,body.getLinearVelocity().y);
		}
	}
	private void idleMovement() {
		Random r = new Random();
		int i = r.nextInt(10 - 1 + 1) + 1;
		if(i<8){
			body.setLinearVelocity(1f,body.getLinearVelocity().y);

		}else {
			body.setLinearVelocity(-1f,body.getLinearVelocity().y);

		}
	}
	@Override
	public CustomSprite getImage() {
		if(state == enemy_state.EDEAD){
			return dead;
		}

		if(state == enemy_state.EATTACKING){
			if(body.getLinearVelocity().x<0){
				MovingSprite m = new MovingSprite("slime_attack.png",1,7);
				m.flipHorizontal();
				return m;
			}
			return attack;
		}
		//IDLE STATE
		if(state == enemy_state.EIDLE){
			return idle;
		}
		if(body.getLinearVelocity().x<0){
			walk_l = new MovingSprite("slime_walk.png",1, 9);
			walk_l.flipHorizontal();
			return walk_l;
		}
		return walk;
	}

}
