package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.Player;
import model.projectile.BulletImpl;
import view.CustomSprite;
import view.MovingSprite;
import view.StaticSprite;

public class Pistol extends AbstractWeapon{
	
	protected final float PISTOL_DAMAGE = 5;

	private CustomSprite image;
	
	
	public Pistol(Vector2 position, float width, float height) {
		super(position, width, height);
		this.setDamage(PISTOL_DAMAGE);
		image = new MovingSprite("bullet.png", 3, 2);
	}

	@Override
	public CustomSprite getImage() {
		return this.image;
	}
	
	public CustomSprite getBulletImage() {
		return this.image;
	}
	

	@Override
	public BulletImpl shoot(Player p) {
		//shoots single pistol bullet.
		return new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage());
		
	}

	

}
