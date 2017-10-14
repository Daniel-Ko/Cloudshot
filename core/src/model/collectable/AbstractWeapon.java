package model.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import model.being.player.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;

import java.util.ArrayList;

public abstract class AbstractWeapon extends AbstractCollectable {
	//fields which every bullet needs
	protected int ammo;
	private float damage;
	private transient Texture gunImage;

	public enum weapon_type {
		pistol, semiauto, sniper, shotgun;
	}


	public AbstractWeapon(Vector2 position, float width, float height) {
		super(position, width, height);
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
	
	public abstract CustomSprite getBulletImage();
	
	public abstract void setAmmo(int i);
	
	public abstract int getAmmo();
	
	public abstract int getMaxAmmo();
	

}
