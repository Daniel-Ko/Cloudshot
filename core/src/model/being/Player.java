package model.being;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import view.SpriteDrawer;

/**
 * Reperesents the player..
 * 
 * @author Jeremy Southon
 * */
public class Player extends AbstractPlayer{

	public Player(Vector2 position, int width, int height, int hp, Vector2 vel) {
		super(position, width, height, hp, vel);
		// TODO
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SpriteDrawer getImage() {
		return new SpriteDrawer("anticon.png", 1, 1);
	}

}
