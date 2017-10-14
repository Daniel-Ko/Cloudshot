package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import view.Assets;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

public class DeathPack extends AbstractBuff {


	public DeathPack(Vector2 position, float width, float height) {
		super(position, width, height, buff_type.death);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * @return the deathpack image
     */
	@Override
	public CustomSprite getImage() {
		return Assets.deathPack;
	}

	/**
	 * this damages the player by 50.
	 * @param AbstractPlayer
     */
	@Override
	public void pickedUp(AbstractPlayer p) {
		int oldHealth = p.getHealth();
		p.setHealth(oldHealth - 50);

	}

}
