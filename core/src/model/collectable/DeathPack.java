package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.AbstractPlayer;
import view.CustomSprite;
import view.StaticSprite;

public class DeathPack extends AbstractBuff {

	CustomSprite image;

	public DeathPack(Vector2 position, float width, float height) {
		super(position, width, height);
		image = new StaticSprite("deathpack.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	public CustomSprite getImage() {
		return image;
	}

	@Override
	public void pickedUp(AbstractPlayer p) {
		int oldHealth = p.getHealth();
		p.setHealth(oldHealth - 5);

	}

}
