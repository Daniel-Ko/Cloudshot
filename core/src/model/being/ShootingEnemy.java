package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.collectable.Pistol;
import model.projectile.BulletImpl;
import view.CustomSprite;
import view.MovingSprite;
import view.StaticSprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShootingEnemy extends AbstractEnemy{
	private float detectionRadius = 4;
	private float attackRadius =3;

	//bullet and attacking fields
	public Queue<BulletImpl> bullets = new LinkedList<>();
	private long lastBulletFired;
	public ShootingEnemy(GameModel gameModel,Vector2 pos){
		super(gameModel,pos);
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
		body.createFixture(fDef).setUserData("mob2");
	}


	@Override
	public void update() {
		updateBullets();
		if(state == enemy_state.EDEAD )return;
		updateAndCheckFields();
		if(player.getPlayerState() == AbstractPlayer.player_state.DEAD)return;

		movement();

		//ATTACK
		attackIfPossible();

	}
	protected void movement(){
		//MOVEMENT
		if(state == enemy_state.EIDLE){
			idleMovement();
		}else {
			foundPlayerMovement();
		}
	}

	private void updateBullets(){
		//updating bullets enemy has fired
		for(BulletImpl b : bullets)
			b.update(new ArrayList<AbstractEnemy>());//FIXME
		//Cleans up bullets
		if(bullets.size() > 10){
			bullets.poll();//remove the oldest 1st
		}
	}
	private void updateAndCheckFields(){
		if(health <= 0 ){
			state = enemy_state.EDEAD;
			world.destroyBody(body);
		}
		position = body.getPosition();
		if(position.dst(player.getPos())>attackRadius)state = enemy_state.EIDLE;
	}

	private void attackIfPossible(){
		if(state == enemy_state.EDEAD)return;
		if(position.dst(player.pos)<attackRadius){
			state = enemy_state.EATTACKING;
			if(player.getPlayerState() == AbstractPlayer.player_state.ALIVE)attack();
		}
	}
	/**
	 * Fire a bullet towards from this enemy to the players position.
	 *
	 * @return false if the enemy cannot attack at this time o.w can attack therefore true.
	 * */
	@Override
	protected boolean attack() {
		int secondsBetweenShots = 1;
		if(lastBulletFired+secondsBetweenShots<System.currentTimeMillis()/1000){
			lastBulletFired = System.currentTimeMillis()/1000;
			bullets.add(new BulletImpl(position,player.getPos(),2,new StaticSprite("player_jump.png")));
			return true;
		}
		return false;
	}
	/**
	 *
	 * //# precondition
	 * */
	private void foundPlayerMovement(){
		if(state == enemy_state.EDEAD)return;
		if(position.dst(player.getPos())< detectionRadius){
			if(getX()<player.getX())
				body.setLinearVelocity(1f,body.getLinearVelocity().y);
			if(getX()>player.getX())
				body.setLinearVelocity(-1f,body.getLinearVelocity().y);
		}
	}
	private void idleMovement(){
		//TODO
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		if(state == enemy_state.EATTACKING){
			if(body.getLinearVelocity().x>0){
				MovingSprite m = new MovingSprite("slime_attack.png",1,7);
				m.flipHorizontal();
				return m;
			}
			return new MovingSprite("slime_attack.png",1,7);
		}
		if(body.getLinearVelocity().x>0){
			MovingSprite m = new MovingSprite("slime_walk.png",1, 9);
			m.flipHorizontal();
			return m;
		}
		return new MovingSprite("slime_walk.png",1, 9);
	}

}
