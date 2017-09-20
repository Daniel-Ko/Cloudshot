package model.collectable;

import com.badlogic.gdx.math.Vector2;

import view.CustomSprite;

public class slowPack extends AbstractBuff {

	public slowPack(Vector2 position, int width, int height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see model.collectable.AbstractCollectable#getImage()
	 */
	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void pickedUp() {
		Vector2 oldVelocity = this.player.getVelocity();
		this.player.setVelocity(oldVelocity.scl((float) 0.5));
	}

}
