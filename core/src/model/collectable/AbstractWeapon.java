package model.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractWeapon extends AbstractCollectable {
	

	private float damage;
	private Texture gunImage;

	public AbstractWeapon(Vector2 position, float width, float height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	@Override
	public void pickedUp() {
		//this.player.getInventory().add(this);
	}
	
	

}
