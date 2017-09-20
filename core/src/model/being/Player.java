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
	public int getX() {
		return (int) getPos().x;
	}

	@Override
	public int getY() {
		return (int) getPos().y;
	}

	@Override
	public MovingSprite getImage() {
		return new MovingSprite("anticon.png", 1, 1);
	}

}
