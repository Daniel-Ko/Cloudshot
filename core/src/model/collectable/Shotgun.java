package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.Player;
import model.projectile.BulletImpl;
import view.CustomSprite;

public class Shotgun extends AbstractWeapon {

	public Shotgun(Vector2 position, float width, float height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BulletImpl shoot(Player p) {
		
		Vector2 aim = p.getAimedAt();
		Vector2 aimAbove = aim.set(aim.x, aim.y + 5);
		Vector2 aimBelow = aim.set(aim.x, aim.y - 5);
		
//		new BulletImpl(p.getPos(),aim, getDamage(), getBulletImage());
//		new BulletImpl(p.getPos(), aimAbove, getDamage(), getBulletImage());
		return new BulletImpl(p.getPos(), aimBelow, getDamage(), getBulletImage());
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CustomSprite getBulletImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
