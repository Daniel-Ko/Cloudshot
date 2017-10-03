package model.projectile;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import model.GameModel;
import model.GameObjectInterface;
import model.being.AbstractEnemy;
import view.CustomSprite;

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
	protected float speed = 4;
	private float xVel;
	private float yVel;
	private CustomSprite image;
	
	
	public BulletImpl(Vector2 start, Vector2 end, float damage, CustomSprite t){
		this.pos = new Vector2(start.x,start.y);
		this.startingPos = new Vector2(start.x,start.y);;
		this.endPos = new Vector2(end.x, end.y);
		System.out.println("start pos = " + startingPos);
		System.out.println("end pos = " + endPos);
		this.damage = damage;
		this.image = t;
		float tX = startingPos.x/GameModel.PPM - endPos.x/GameModel.PPM;
		float tY = startingPos.y/GameModel.PPM - endPos.y/GameModel.PPM;
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

	public void update(ArrayList<AbstractEnemy> enemies){
		doCollide(enemies);
		pos.set(pos.x-xVel*speed,pos.y+yVel*speed);
	}

	private void doCollide(ArrayList<AbstractEnemy> enemies) {
//		for (AbstractEnemy e: enemies){
//			System.out.println("x = " + e.getX());
//			System.out.println("y = " + e.getY());
//			if (e.getBoundingBox().contains(this.getX(),this.getY())){
//				e.hit(2000);
//				System.out.println(222);
//			
//			}
//		}
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
	
	
}
