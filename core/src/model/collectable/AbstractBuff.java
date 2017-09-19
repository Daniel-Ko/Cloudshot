package model.collectable;

import com.badlogic.gdx.math.Vector2;

import view.MovingSprite;

public abstract class AbstractBuff extends AbstractCollectable {

    public AbstractBuff(Vector2 position, int width, int height) {
		super(position, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public MovingSprite getImage() {
        return null;
    }
}
