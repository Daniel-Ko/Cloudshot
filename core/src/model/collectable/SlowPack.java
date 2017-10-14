package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import view.sprites.CustomSprite;

public class SlowPack extends AbstractBuff {

	public SlowPack(Vector2 position, float width, float height) {
		super(position, width, height, buff_type.slow);
	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pickedUp(AbstractPlayer p) {
//		Vector2 oldVelocity = p.getVelocity();
//		p.setVelocity(oldVelocity.scl((float) 0.5)); //TODO FIXME hmu on what variables to use.
	}

}
