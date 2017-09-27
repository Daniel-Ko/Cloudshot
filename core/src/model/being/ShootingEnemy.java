package model.being;

import com.badlogic.gdx.math.Vector2;
import view.CustomSprite;

public class ShootingEnemy extends AbstractEnemy{

	public ShootingEnemy(int hp,AbstractPlayer player,Vector2 pos){
		super(hp,player,pos);
	}

	@Override
	protected boolean attack() {
		return false;
	}

	@Override
	public void update() {

	}

	protected void movement(){


	}

	@Override
	public CustomSprite getImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
