package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.AbstractPlayer;
import view.CustomSprite;
import view.StaticSprite;

public class HeavyAmmoPack extends AbstractBuff {
	CustomSprite image;
	public HeavyAmmoPack(Vector2 position, float width, float height) {
		super(position, width, height);
		image = new StaticSprite("ammo.png");
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return this.image;
	}

	@Override
	public void pickedUp(AbstractPlayer p) {
		//Maximises all weapons ammo
		for (AbstractWeapon wep : p.getInventory()) {
			wep.setAmmo(wep.getMaxAmmo());
		}
	}

}
