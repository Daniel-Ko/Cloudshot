package model.mapObject;

import model.GameObjectInterface;
import view.MovingSprite;

public class Portal implements GameObjectInterface {

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
