package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import view.Assets;
import view.sprites.CustomSprite;

public class HealthPack extends AbstractBuff {


    private static final long serialVersionUID = 6727531646815304787L;

    public HealthPack(Vector2 position, float width, float height) {
		super(position, width, height, buff_type.health);
	}
	
	@Override
	public CustomSprite getImage() {
		return Assets.healthPack;
	}

	/**
	 * this applies a health bonus of 50.
	 * Also ensures that player cant get more health
	 * than 150.
	 * @param p
     */
	@Override
	public void pickedUp(AbstractPlayer p) {
		int oldHealth = p.getHealth();
		p.setHealth(oldHealth + 50);

		//Make sure health cant go above max health
		if(p.getHealth() > 150){p.setHealth(150);}
	}
	

	

}
