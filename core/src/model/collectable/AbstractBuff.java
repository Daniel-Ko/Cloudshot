package model.collectable;

import com.badlogic.gdx.math.Vector2;

import view.CustomSprite;

public abstract class AbstractBuff extends AbstractCollectable {

    public AbstractBuff(Vector2 position, float width, float height) {
		super(position, width, height);
	}

	@Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }


   
}
