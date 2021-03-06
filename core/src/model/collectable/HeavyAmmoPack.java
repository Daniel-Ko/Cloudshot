package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import view.Assets;
import view.sprites.CustomSprite;

public class HeavyAmmoPack extends AbstractBuff {

    private static final long serialVersionUID = -4710054287634053891L;

    public HeavyAmmoPack(Vector2 position, float width, float height) {
		super(position, width, height, buff_type.heavyammo);
	}

	@Override
	public CustomSprite getImage() {
		return Assets.heavyAmmoPack;
	}

	/**
	 * this applies max ammo to everything in the
	 * players inventory.
	 * @param p
     */
	@Override
	public void pickedUp(AbstractPlayer p) {
		//Maximises all weapons ammo
		for (AbstractWeapon wep : p.getInventory()) {
			wep.setAmmo(wep.getMaxAmmo());
		}
	}

}
