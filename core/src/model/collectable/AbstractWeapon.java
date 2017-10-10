package model.collectable;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import model.being.AbstractPlayer;
import model.being.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;

public abstract class AbstractWeapon extends AbstractCollectable {
	
	protected int ammo;
	private float damage;
	private transient Texture gunImage;

	public AbstractWeapon(Vector2 position, float width, float height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	@Override
	public void pickedUp(AbstractPlayer p) {
		//adds the weapon to the players inventory.
		p.getInventory().add(this);
		Player player = (Player)p;
		player.setCurWeapon(this);

	}
	
	public abstract CustomSprite getBulletImage();
	
	public abstract ArrayList<BulletImpl> shoot(Player p);
	
	public abstract void setAmmo(int i);
	
	public abstract int getAmmo();
	
	public abstract int getMaxAmmo();
	

}
