package model.collectable;

import com.badlogic.gdx.math.Vector2;

import view.CustomSprite;

public class Pistol extends AbstractWeapon{
	
	protected final int PISTOL_DAMAGE = 5;
	
	public Pistol(Vector2 position, int width, int height) {
		super(position, width, height);
		this.setDamage(PISTOL_DAMAGE);
	}

	@Override
	public int getDamage() {
		return super.getDamage();
	}

	@Override
	public CustomSprite getImage() {
		return super.getImage();
	}

	

}
