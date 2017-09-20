package model.collectable;

import com.badlogic.gdx.math.Vector2;

public class slowPack extends AbstractBuff {

	public slowPack(Vector2 position, int width, int height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * Slows the player down by half
	 */
	@Override
	public void pickedUp() {
		Vector2 oldVelocity = this.player.getVelocity();
		this.player.setVelocity(oldVelocity.scl((float) 0.5));
	}

}
