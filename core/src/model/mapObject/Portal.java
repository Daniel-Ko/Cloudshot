package model.mapObject;

import model.GameObjectInterface;
import view.CustomSprite;
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
    public CustomSprite getImage() {
        return null;
    }
}
