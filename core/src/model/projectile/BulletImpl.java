package model.projectile;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import model.GameModel;
import model.GameObjectInterface;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import view.sprites.CustomSprite;

/**
 * Implements ProjectileInterface and provides functionality specific to a bullet.
 * @author tomherdson
 *
 */
public class BulletImpl implements ProjectileInterface, GameObjectInterface {
	protected Vector2 startingPos;
	protected Vector2 endPos;
	protected Vector2 pos;
	
	

	protected float damage;

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	protected float speed = 3;
	private float xVel;
	private float yVel;
	private CustomSprite image;


	private double angle;
	private boolean toRemove = false;
	private boolean playerBullet;


	public BulletImpl(Vector2 startingPos) {
		this.startingPos = startingPos;
	}

	public BulletImpl(Vector2 start, Vector2 end, float damage, CustomSprite t, boolean owner){
		this.pos = new Vector2(start.x,start.y + 0.3f);
		this.startingPos = new Vector2(start.x,start.y + 0.3f);
		this.endPos = new Vector2(end.x, end.y);
		this.damage = damage;
		this.image = t;
		this.playerBullet = owner;


		
	/*	float tX = startingPos.x/GameModel.PPM - endPos.x/GameModel.PPM;
		float tY = startingPos.y/GameModel.PPM - endPos.y/GameModel.PPM;*/
		float tX = startingPos.x - endPos.x;
		float tY = startingPos.y - endPos.y;
		float mag = (float) java.lang.Math.hypot(tX, tY);
		tX/=mag;
	    tY/=mag;
//		this.angle = java.lang.Math.atan2(endPos.y, endPos.x) -java.lang.Math.atan2(startingPos.y, startingPos.x);
//		if(startingPos.x*endPos.y - startingPos.y*endPos.x < 0) {
//			angle = -angle;
//		}
//
//
//		this.angle *= 180/Math.PI;




		System.out.println("andgle = " + this.getAngle());
		//scaling speed
	    tX*=speed/GameModel.PPM;;
	    tY*=speed/GameModel.PPM;;
	    xVel = tX;
	    yVel = tY;
	}
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}


	/* (non-Javadoc)
	 * @see model.GameObjectInterface#getX()
	 */
	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return this.pos.x;
	}

	/* (non-Javadoc)
	 * @see model.GameObjectInterface#getY()
	 */
	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return this.pos.y;
	}

	/* (non-Javadoc)
	 * @see model.GameObjectInterface#getImage()
	 */
	@Override
	public CustomSprite getImage() {
		return this.image;
	}

	public void update(List<AbstractEnemy> enemies, AbstractPlayer player){
		if(pos.dst2(this.getStartingPos()) > 1000){
			this.setToRemove(true);
		}
		doCollide(enemies,player);
		pos.set(pos.x-xVel*speed,pos.y-yVel*speed);
	}

	private void doCollide(List<AbstractEnemy> enemies, AbstractPlayer player) {
		if (this.playerBullet){
			for (AbstractEnemy e: enemies){
				if (e.getBoundingBox().contains(this.getX(),this.getY())){
					e.hit((int)this.getDamage());
					this.setToRemove(true);
				}
			}
		}
		else{
			if (player.getBoundingBox().contains(this.getX(),this.getY())){
				player.hit(this.getDamage());
			}
		}

	}

	/**
	 * @return the startingPos
	 */
	public Vector2 getStartingPos() {
		return startingPos;
	}

	/**
	 * @param startingPos the startingPos to set
	 */
	public void setStartingPos(Vector2 startingPos) {
		this.startingPos = startingPos;
	}

	/**
	 * @return the endPos
	 */
	public Vector2 getEndPos() {
		return endPos;
	}

	/**
	 * @param endPos the endPos to set
	 */
	public void setEndPos(Vector2 endPos) {
		this.endPos = endPos;
	}

	/**
	 * @return the damage
	 */
	public float getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	/**
	 * @return the toRemove
	 */
	public boolean isToRemove() {
		return toRemove;
	}

	/**
	 * @param toRemove the toRemove to set
	 */
	public void setToRemove(boolean toRemove) {
		this.toRemove = toRemove;
	}

	/**
	 * @return the playerBullet
	 */

	public Vector2 getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	
}
