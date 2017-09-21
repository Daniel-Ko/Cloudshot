package model.being;

import com.badlogic.gdx.math.Vector2;
import view.MovingSprite;


/**
 * Reperesents the main playable character that the user controls
 * 
 * @author Jeremy Southon
 * */
public class Player extends AbstractPlayer{

	public Player(Vector2 position, int width, int height, int hp, Vector2 vel) {
		super(position, width, height, hp, vel);
		// TODO
	}

	@Override
	public float getX() {
		return getPos().x;
	}

	@Override
	public float getY() {
		return getPos().y;
	}

	@Override
	public MovingSprite getImage() {
		return new MovingSprite("sheet_hero_walk.png", 1, 3);
	}

}
