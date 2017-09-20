package model.mapObject.terrain;

import com.badlogic.gdx.math.Rectangle;
import model.GameObjectInterface;
import src.model.being.AbstractPlayer;
import view.CustomSprite;
import view.MovingSprite;

public abstract class AbstractTerrain implements GameObjectInterface {

    //delegate to subclasses.

    public abstract Rectangle getBoundingbox();

    public abstract CustomSprite getImage();

    public boolean isOnTerrain(AbstractPlayer p){
        throw new UnsupportedOperationException();
    }

    public boolean isAboveTerrain(AbstractPlayer p){
        throw new UnsupportedOperationException();

    }

}
