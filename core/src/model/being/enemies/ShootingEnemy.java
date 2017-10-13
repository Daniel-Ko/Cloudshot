package model.being.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.enemystates.Death;
import model.being.enemystates.IdleMovement;
import model.being.enemystates.ShooterAttack;
import model.being.player.AbstractPlayer;
import model.projectile.BulletImpl;
import view.Assets;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;
import view.sprites.StaticSprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ShootingEnemy extends AbstractEnemy{

	public final AbstractEnemy.entity_type type = AbstractEnemy.entity_type.archer;

	//bullet and attacking fields
	private Queue<BulletImpl> bullets = new LinkedList<>();

	public ShootingEnemy(World world, AbstractPlayer player, Vector2 pos){
		super(world,player,pos, AbstractEnemy.entity_type.archer);
		IdleMovement movement =  new IdleMovement();
		movement.setIdleMovementSpeed(0);
		enemyState = movement;
		attackRadius = 5;
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
		fDef.filter.groupIndex = -1;

		//
		bodyDef.position.set(position.x / GameModel.PPM,position.y/GameModel.PPM);
		body = world.createBody(bodyDef);
		//adding main fixture
		body.createFixture(fDef).setUserData("mob2");
	}

	@Override
	public void update() {
		updateBullets();
		enemyState.update(this,player);
		if(enemyState instanceof Death)return;
		updateAndCheckFields();
		checkForStateChange();
		attack();

	}
	protected void movement(){
		//Depre to remove
	}

	private void updateBullets(){
		//updating bullets enemy has fired
		for(BulletImpl b : bullets)
			b.update(new ArrayList<AbstractEnemy>(),player);//FIXME
		//Cleans up bullets
		if(bullets.size() > 10){
			bullets.poll();//remove the oldest 1st
		}
	}
	private void updateAndCheckFields(){
		position = body.getPosition();
		boundingBox.set(position.x, position.y, boundingBox.getWidth(), boundingBox.getHeight());
	}

	/**
	 *	Method is used to update the state of the enemy based on it and the player.
	 * */
	private void checkForStateChange(){
		if(state == enemy_state.EDEAD)return;
		if(position.dst(player.getPos())<attackRadius){
			if(enemyState instanceof ShooterAttack)return;
			ShooterAttack shootState = new ShooterAttack();
			shootState.setSecondsBetweenShots(0);
			enemyState = shootState;
		}else {
			if(enemyState instanceof IdleMovement)return;
			IdleMovement movement =  new IdleMovement();
			movement.setIdleMovementSpeed(0);
			enemyState = movement;
		}
	}
	/**
	 * Fire a bullet towards from this enemy to the players position.
	 *
	 * @return false if the enemy cannot attack at this time o.w can attack therefore true.
	 * */
	@Override
	protected boolean attack() {
		if(player.getPlayerState() == AbstractPlayer.player_state.ALIVE
				&& enemyState instanceof ShooterAttack){
			enemyState.attack(this,player);
			return true;
		}
		return false;
	}


	@Override
	public CustomSprite getImage() {
		if(enemyState instanceof ShooterAttack){
			if(player.getX()<this.getX()){
				return Assets.shootingEnemyAttackingLeft;
			}
			return Assets.shootingEnemyAttackingRight;
		}
		return Assets.shootingEnemyIdle;
	}


	public Queue<BulletImpl> getBullets (){return this.bullets;}

	public CustomSprite getBulletSprite() {
		return Assets.shootingEnemyBulletSprite;
	}
}
