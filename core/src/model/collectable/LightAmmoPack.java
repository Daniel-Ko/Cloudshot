package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import view.Assets;
import view.sprites.CustomSprite;

public class LightAmmoPack extends AbstractBuff {

    private static final long serialVersionUID = 366645573229085796L;

    public LightAmmoPack(Vector2 position, float width, float height) {
		super(position, width, height, buff_type.lightammo);
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return Assets.lightAmmoPack;
		
	}

	/**
	 * this applies max ammo only to the
	 *	weapon which is equipped.
	 * @param p
     */
	@Override
	public void pickedUp(AbstractPlayer p) {
		//need to change this to equipped weapon not the first element in inventory
		Player player = (Player)p;
		AbstractWeapon wep = player.getCurWeapon();
		wep.setAmmo(wep.getMaxAmmo());

	}

}
