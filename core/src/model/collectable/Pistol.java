package model.collectable;

import com.badlogic.gdx.math.Vector2;

import view.CustomSprite;

public class Pistol extends AbstractWeapon{
	
	protected final int PISTOL_DAMAGE = 5;
	
	public Pistol(Vector2 position, int width, int height) {
		super(position, width, height);
		this.setDamage(PISTOL_DAMAGE);
	}

	/* (non-Javadoc)
	 * @see model.collectable.AbstractWeapon#getDamage()
	 */
	@Override
	public int getDamage() {
		return super.getDamage();
	}

	/* (non-Javadoc)
	 * @see model.collectable.AbstractCollectable#getImage()
	 */
	@Override
	public CustomSprite getImage() {
		return super.getImage();
	}

	

}
