package model.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import model.being.player.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;

import java.util.ArrayList;

/**
 * AbstractBuff contains the logic for the weapon collectables in the game.
 * @author Jake Robson
 */
public abstract class AbstractWeapon extends AbstractCollectable {
	private static final long serialVersionUID = -9094601317027252528L;
	public final weapon_type type;
	public final CustomSprite BULLET_IMAGE;
	//fields which every bullet needs
	protected int ammo;
	private float damage;
	private transient Texture gunImage;

	public enum weapon_type {
		pistol, semiauto, sniper, shotgun
	}


	public AbstractWeapon(Vector2 position, float width, float height, weapon_type type, CustomSprite bulImg) {
		super(position, width, height);
		this.type = type;
		this.BULLET_IMAGE = bulImg;
	}

	/**
	 * Defines how each gun should shoot.
	 * @param p player
	 * @return the bullets which get shot by the gun
     */
	public abstract ArrayList<BulletImpl> shoot(Player p);




  //-----------------GETTERS AND SETTERS-------------------


	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	public CustomSprite getBulletImage() {
		return BULLET_IMAGE;
	}

	//
	// ABSTRACT
	//

	public abstract void setAmmo(int i);
	
	public abstract int getAmmo();
	
	public abstract int getMaxAmmo();
	

}
