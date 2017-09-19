package model.mapObject.terrain;

import com.badlogic.gdx.math.Rectangle;
import model.GameObjectInterface;
import view.SpriteDrawer;

public abstract class AbstractTerrain implements GameObjectInterface {

    //delegate to subclasses.

    public abstract Rectangle getBoundingbox();



    public abstract SpriteDrawer getImage();


}
