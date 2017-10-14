package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import view.Assets;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

public class HealthPack extends AbstractBuff {


	public HealthPack(Vector2 position, float width, float height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public CustomSprite getImage() {
		return Assets.healthPack;
	}

	/**
	 * this applies a health bonus of 50.
	 * Also ensures that player cant get more health
	 * than 150.
	 * @param p
     */
	@Override
	public void pickedUp(AbstractPlayer p) {
		int oldHealth = p.getHealth();
		p.setHealth(oldHealth + 50);

		//Make sure health cant go above max health
		if(p.getHealth() > 150){p.setHealth(150);}
	}
	

	

}
