package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.AbstractPlayer;
import view.CustomSprite;

public class LightAmmoPack extends AbstractBuff {

	public LightAmmoPack(Vector2 position, float width, float height) {
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
		//need to change this to equipped weapon not the first element in inventory
		AbstractWeapon wep = p.getInventory().get(1);
		wep.setAmmo(wep.getMaxAmmo());
	}

}
