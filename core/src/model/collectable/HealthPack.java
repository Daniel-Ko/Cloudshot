package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.AbstractPlayer;
import view.CustomSprite;
import view.StaticSprite;

public class HealthPack extends AbstractBuff {

	public HealthPack(Vector2 position, int width, int height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public CustomSprite getImage() {
		return new StaticSprite("healthpack.png");
	}

	@Override
	public void pickedUp(AbstractPlayer p) {
		int oldHealth = p.getHealth();
		p.setHealth(oldHealth + 5);
	}

	

}
