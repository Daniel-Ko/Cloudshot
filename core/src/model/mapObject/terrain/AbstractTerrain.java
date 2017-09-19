package model.mapObject.terrain;

import com.badlogic.gdx.math.Rectangle;
import model.GameObjectInterface;
import model.being.AbstractPlayer;
import view.SpriteDrawer;

public abstract class AbstractTerrain implements GameObjectInterface {

    //delegate to subclasses.

    public abstract Rectangle getBoundingbox();


    public abstract SpriteDrawer getImage();

    public boolean isOnTerrain(AbstractPlayer p){
        throw new UnsupportedOperationException();
    }

    public boolean isAboveTerrain(AbstractPlayer p){
        throw new UnsupportedOperationException();

    }


}
