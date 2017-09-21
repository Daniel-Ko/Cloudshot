package model.collectable;

import com.badlogic.gdx.math.Vector2;

import view.CustomSprite;

public class Pistol extends AbstractWeapon{
	
	protected final float PISTOL_DAMAGE = 5;
	
	public Pistol(Vector2 position, float width, float height) {
		super(position, width, height);
		this.setDamage(PISTOL_DAMAGE);
	}

	@Override
	public float getDamage() {
		return super.getDamage();
	}

	@Override
	public CustomSprite getImage() {
		return null;
	}

	

}
