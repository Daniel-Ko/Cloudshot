package model.projectile;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import model.GameModel;
import model.GameObjectInterface;
import model.being.AbstractEnemy;
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
	protected float speed = 3;
	private float xVel;
	private float yVel;
	private CustomSprite image;
	private boolean toRemove = false;
	private boolean playerBullet;
	


	public BulletImpl(Vector2 start, Vector2 end, float damage, CustomSprite t){
		this.pos = new Vector2(start.x,start.y);
		this.startingPos = new Vector2(start.x,start.y);;
		this.endPos = new Vector2(end.x, end.y);

		this.damage = damage;
		this.image = t;
		///this.playerBullet = owner;
		
	/*	float tX = startingPos.x/GameModel.PPM - endPos.x/GameModel.PPM;
		float tY = startingPos.y/GameModel.PPM - endPos.y/GameModel.PPM;*/
		float tX = startingPos.x - endPos.x;
		float tY = startingPos.y - endPos.y;
		float mag = (float) java.lang.Math.hypot(tX, tY);
		tX/=mag;
	    tY/=mag;
	    //scaling speed
	    tX*=speed/GameModel.PPM;;
	    tY*=speed/GameModel.PPM;;
	    xVel = tX;
	    yVel = tY;
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

	public void update(List<AbstractEnemy> enemies){
		if(pos.dst2(this.getStartingPos()) > 1000){
			this.setToRemove(true);
		}
		doCollide(enemies);
		pos.set(pos.x-xVel*speed,pos.y-yVel*speed);
	}

	private void doCollide(List<AbstractEnemy> enemies) {
		for (AbstractEnemy e: enemies){
			if (e.getBoundingBox().contains(this.getX(),this.getY())){
				e.hit((int)this.getDamage());
				this.setToRemove(true);
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
	public boolean isPlayerBullet() {
		return playerBullet;
	}
	/**
	 * @return the pos
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
