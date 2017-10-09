package model.collectable;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import model.being.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;
import view.sprites.StaticSprite;

public class Pistol extends AbstractWeapon{
	
	public final int MAX_AMMO = 50;

	protected final float PISTOL_DAMAGE = 4;

	private CustomSprite image;

	private CustomSprite bulImage;

	
	
	
	public Pistol(Vector2 position, float width, float height) {
		super(position, width, height);
		this.setDamage(PISTOL_DAMAGE);
		bulImage = new MovingSprite("bullet.png", 3, 2);
		this.image = new StaticSprite("pistol.png");
		this.ammo = MAX_AMMO;
	}

	@Override
	public CustomSprite getImage() {
		return this.image;
	}
	
	public CustomSprite getBulletImage() {
		return this.bulImage;
	}
	

	@Override
	public ArrayList<BulletImpl> shoot(Player p) {
		//shoots single pistol bullet.
		//dont shoot if theres no ammo
		System.out.println("gets here");

		if(this.ammo <= 0){return null;}
		ArrayList<BulletImpl> bullets = new ArrayList<>();
		this.ammo --;
		bullets.add(new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage()));
		return bullets;
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
