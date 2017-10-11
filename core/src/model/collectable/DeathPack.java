package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.player.AbstractPlayer;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

public class DeathPack extends AbstractBuff {

	private transient CustomSprite image;

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
		p.setHealth(oldHealth - 50);
		System.out.println(p.getHealth());

	}

}
