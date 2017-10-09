package model.collectable;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import model.being.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;
import view.sprites.StaticSprite;

public class Shotgun extends AbstractWeapon {

	public static final int MAX_AMMO = 25;
	protected final int SHOTGUN_DAMAGE = 6;
	private MovingSprite bulImage;
	private StaticSprite image;
	

	public Shotgun(Vector2 position, float width, float height) {
		super(position, width, height);
		this.ammo = MAX_AMMO;
		this.setDamage(SHOTGUN_DAMAGE);
		bulImage = new MovingSprite("bullet.png", 3, 2);
		image = new StaticSprite("shotgun.png");
	}

	@Override
	public ArrayList<BulletImpl> shoot(Player p) {
		//System.out.println("gets here");
		if(this.ammo <= 0){return null;}
		ArrayList<BulletImpl> bullets = new ArrayList<>();		
		this.ammo --;
		
		Vector2 aim = p.getAimedAt();
		Vector2 aimAbove = new Vector2(aim.x,  (float) (aim.y + 0.5));
		Vector2 aimBelow =  new Vector2(aim.x, (float) (aim.y - 0.5));
		
		bullets.add(new BulletImpl(p.getPos(),aim, getDamage(), getBulletImage()));
		bullets.add(new BulletImpl(p.getPos(), aimAbove, getDamage(), getBulletImage()));
		bullets.add(new BulletImpl(p.getPos(), aimBelow, getDamage(), getBulletImage()));
		System.out.println(bullets.size());
		return bullets;
		
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return this.image;
	}

	
	public CustomSprite getBulletImage() {
		// TODO Auto-generated method stub
		return this.bulImage;
	}
	
	@Override
	public void setAmmo(int i) {
		this.ammo  = i;
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
