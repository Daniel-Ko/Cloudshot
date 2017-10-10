package model.collectable;

import com.badlogic.gdx.math.Vector2;

import model.being.AbstractPlayer;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

public class HealthPack extends AbstractBuff {

	private transient CustomSprite image;

	public HealthPack(Vector2 position, float width, float height) {
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
		p.setHealth(oldHealth + 50);
		System.out.println(p.getHealth());
		//Make sure health cant go above max health
		if(p.getHealth() > 150){p.setHealth(150);}
	}
	

	

}
