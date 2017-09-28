package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.AbstractPlayer;
import view.CustomSprite;

public class HeavyAmmoPack extends AbstractBuff {

	public HeavyAmmoPack(Vector2 position, float width, float height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pickedUp(AbstractPlayer p) {
		//Maximises all weapons ammo
		for (AbstractWeapon wep : p.getInventory()) {
			wep.setAmmo(wep.getMaxAmmo());
		}
	}

}
