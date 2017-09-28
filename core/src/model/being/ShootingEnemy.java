package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import view.CustomSprite;

public class ShootingEnemy extends AbstractEnemy{

	public ShootingEnemy(int hp,AbstractPlayer player,Vector2 pos,World world){
		super(hp,player,pos,world);
	}

	protected void defineBody(){}
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
