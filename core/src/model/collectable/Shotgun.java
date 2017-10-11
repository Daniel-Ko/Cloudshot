package model.collectable;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;
import view.sprites.StaticSprite;

public class Shotgun extends AbstractWeapon {

	public static final int MAX_AMMO = 25;
	protected final int SHOTGUN_DAMAGE = 15;
	private transient StaticSprite bulImage;
	private transient StaticSprite image;
	

	public Shotgun(Vector2 position, float width, float height) {
		super(position, width, height);
		this.ammo = MAX_AMMO;
		this.setDamage(SHOTGUN_DAMAGE);
		bulImage = new StaticSprite("bullet.png");
		image = new StaticSprite("shotgun.png");
	}

	@Override
	public ArrayList<BulletImpl> shoot(Player p) {
		if(this.ammo <= 0){return null;}
		ArrayList<BulletImpl> bullets = new ArrayList<>();		
		this.ammo --;
		
		Vector2 aim = p.getAimedAt();
		Vector2 aimAbove = new Vector2(aim.x,  (float) (aim.y + 0.5));
		Vector2 aimBelow =  new Vector2(aim.x, (float) (aim.y - 0.5));
		
		bullets.add(new BulletImpl(p.getPos(),aim, getDamage(), getBulletImage()));
		bullets.add(new BulletImpl(p.getPos(), aimAbove, getDamage(), getBulletImage()));
		bullets.add(new BulletImpl(p.getPos(), aimBelow, getDamage(), getBulletImage()));
		return bullets;
		
	}
	@Override
	public void pickedUp(AbstractPlayer p) {
		for (AbstractWeapon w: p.getInventory()) {
			if(w.getClass().equals(this.getClass())){
				w.setAmmo(getMaxAmmo());
				return;
			}
		}
		//adds the weapon to the players inventory.

		p.getInventory().add(this);
		Player player = (Player)p;
		player.setCurWeapon(this);

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
