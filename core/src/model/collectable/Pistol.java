package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.Player;
import model.projectile.BulletImpl;
import view.CustomSprite;
import view.StaticSprite;

public class Pistol extends AbstractWeapon{
	
	protected final float PISTOL_DAMAGE = 5;
	
	
	public Pistol(Vector2 position, float width, float height) {
		super(position, width, height);
		this.setDamage(PISTOL_DAMAGE);
	}

	@Override
	public CustomSprite getImage() {
		return new StaticSprite("bullet.png");
	}
	
	public CustomSprite getBulletImage() {
		return new StaticSprite("bullet.png");
	}
	

	@Override
	public BulletImpl shoot(Player p) {
		//shoots single pistol bullet.
		return new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage());
		
	}

	

}
