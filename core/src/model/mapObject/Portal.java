package model.mapObject;

import model.GameObjectInterface;
import view.SpriteDrawer;

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
    public SpriteDrawer getImage() {
        return null;
    }
}
