package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.AbstractPlayer;
import view.CustomSprite;
import view.StaticSprite;

public class HealthPack extends AbstractBuff {

	CustomSprite image;

	public HealthPack(Vector2 position, int width, int height) {
		super(position, width, height);
		image = new StaticSprite("healthpack.png");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public CustomSprite getImage() {
		/*if(this.pickedUp = true){
			return null;
		}*/
		return image;
	}

	@Override
	public void pickedUp(AbstractPlayer p) {
		int oldHealth = p.getHealth();
		p.setHealth(oldHealth + 5);
	}
	

	

}
