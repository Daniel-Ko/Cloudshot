package model.projectile;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import model.GameModel;
import model.GameObjectInterface;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import view.sprites.CustomSprite;

import java.io.Serializable;
import java.util.List;
import java.util.Arrays;

/**
 * Implements ProjectileInterface and provides functionality specific to a bullet.
 * @author Jacob Robson
 *
 */
public class BulletImpl implements ProjectileInterface, GameObjectInterface, Serializable {
	/**
	 * fields which define a bullet.
     */
	private static final long serialVersionUID = 120954733508781847L;
	protected Vector2 startingPos;
	protected Vector2 endPos;
	protected Vector2 pos;

	protected float damage;
	protected float speed = 3;
	private float xVel;
	private float yVel;
	private transient CustomSprite image;

	private boolean toRemove = false;
	private boolean playerBullet;

	/**
	 * This represents a bullet object which is created by a gun
	 * @param start starting position
	 * @param end end Position (aimedAt field in player)
	 * @param damage damaged of this bullet(declared in bullet);
	 * @param t bullet image
     * @param owner true if owned by player.
     */

	public BulletImpl(Vector2 start, Vector2 end, float damage, CustomSprite t, boolean owner){
		this.pos = new Vector2(start.x,start.y + 0.3f);
		this.startingPos = new Vector2(start.x,start.y + 0.3f);
		this.endPos = new Vector2(end.x, end.y);
		this.damage = damage;
		this.image = t;
		this.playerBullet = owner;
		float tX = startingPos.x - endPos.x;
		float tY = startingPos.y - endPos.y;
		float mag = (float) java.lang.Math.hypot(tX, tY);
		tX/=mag;
	    tY/=mag;
		//scaling speed
	    tX*=speed/GameModel.PPM;
		tY*=speed/GameModel.PPM;
		xVel = tX;
	    yVel = tY;
	}

	/**
	 * Update increments the bullets position, And removes the bullets that
	 * have collided.
	 * @param enemies
	 * @param player
     */
	public void update(List<AbstractEnemy> enemies, AbstractPlayer player, Array<Rectangle> terrain){
		if(pos.dst2(this.getStartingPos()) > 1000){
			this.setToRemove();
		}
		doCollide(enemies,player, terrain);
		pos.set(pos.x-xVel*speed,pos.y-yVel*speed);
	}

	/**
	 * Checks if the bullets have collided with player, or enemies.
	 * And does damage to them if true.
	 * @param enemies
	 * @param player
     */
	private void doCollide(List<AbstractEnemy> enemies, AbstractPlayer player, Array<Rectangle> terrain) {

		if (this.playerBullet){
			for (Rectangle r : terrain){
				if(r.contains(this.getX(),this.getY())){
					this.setToRemove();
				}
			}
			for (AbstractEnemy e: enemies){
				if (e.getBoundingBox().contains(this.getX(),this.getY())){
					e.hit((int)this.getDamage());
					this.setToRemove();
				}
			}
		}
		else{
			if (player.getBoundingBox().contains(this.getX(),this.getY())){
				player.hit(this.getDamage());
				this.setToRemove();
			}
		}


	}



	//--------------------------GETTERS AND SETTER---------------------------

	public void setSpeed(float newSpeed) {
		this.speed = newSpeed;
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
	 */
	public void setToRemove() {
		this.toRemove = true;
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
