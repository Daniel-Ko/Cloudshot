package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.player.AbstractPlayer;
import model.being.player.Player;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

public class LightAmmoPack extends AbstractBuff {

	private transient CustomSprite image;

	public LightAmmoPack(Vector2 position, float width, float height) {
		super(position, width, height);
		image = new StaticSprite("ammo.png");
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return this.image;
		
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
