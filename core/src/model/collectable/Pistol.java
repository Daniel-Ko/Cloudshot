package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

import java.util.ArrayList;

public class Pistol extends AbstractWeapon{

	public final weapon_type type = weapon_type.pistol;

	//concrete fields for pistol
	public final int MAX_AMMO = 50;

	protected final float PISTOL_DAMAGE = 8;

	private transient CustomSprite image;

	private transient CustomSprite bulImage;

	
	
	
	public Pistol(Vector2 position, float width, float height) {
		super(position, width, height);
		this.setDamage(PISTOL_DAMAGE);
		bulImage = new StaticSprite("bullet.png");
		this.image = new StaticSprite("pistol.png");
		this.ammo = MAX_AMMO;
	}

	/**
	 * Shoots one bullet.
	 * @param p
	 * @return bullet to be shot
     */
	@Override
	public ArrayList<BulletImpl> shoot(Player p) {
		if(this.ammo <= 0){return null;}
		ArrayList<BulletImpl> bullets = new ArrayList<>();
		this.ammo --;
		bullets.add(new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage(), true));
		return bullets;
	}
	/**
	 * Adds pistol to players inventory if not one there
	 * @param Abstract Player
	 *
	 */
	@Override
	public void pickedUp(AbstractPlayer p) {
		for (AbstractWeapon w: p.getInventory()) {
			if(w.getClass().equals(this.getClass())){
				w.setAmmo(getMaxAmmo());
				return;
			}
		}
		p.getInventory().add(this);
		Player player = (Player)p;
		player.setCurWeapon(this);

	}


	//---------------------GETTERS AND SETTERS------------------------

	@Override
	public CustomSprite getImage() {
		return this.image;
	}

	public CustomSprite getBulletImage() {
		return this.bulImage;
	}

	@Override
	public void setAmmo(int i) {
		this.ammo = i;
	}

	@Override
	public int getAmmo() {
		return this.ammo ;
	}

	@Override
	public int getMaxAmmo() {
		return this.MAX_AMMO;
	}

	

}
