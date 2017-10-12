package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.player.AbstractPlayer;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

public class HeavyAmmoPack extends AbstractBuff {
	private transient CustomSprite image;

	public HeavyAmmoPack(Vector2 position, float width, float height) {
		super(position, width, height);
		image = new StaticSprite("ammo.png");
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return this.image;
	}

	/**
	 * this applies max ammo to everything in the
	 * players inventory.
	 * @param Abstract Player
     */
	@Override
	public void pickedUp(AbstractPlayer p) {
		//Maximises all weapons ammo
		for (AbstractWeapon wep : p.getInventory()) {
			wep.setAmmo(wep.getMaxAmmo());
		}
	}

}
