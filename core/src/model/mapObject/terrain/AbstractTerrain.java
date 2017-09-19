package model.mapObject.terrain;

import model.GameObjectInterface;
import view.SpriteDrawer;

public abstract class AbstractTerrain implements GameObjectInterface {

    //delegate to subclasses.
    public abstract int getX();

    public abstract int getY();

    public abstract SpriteDrawer getImage();
}
