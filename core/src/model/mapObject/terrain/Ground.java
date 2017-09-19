package model.mapObject.terrain;

import com.badlogic.gdx.math.Rectangle;
import view.SpriteDrawer;

/**
 * AbstractTerrain implementation.
 * The difference between Ground and Platform is that ground extends up from the very bottom of a window.
 */
public class Ground extends AbstractTerrain {

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public Rectangle getBoundingbox() {
        return null;
    }

    @Override
    public SpriteDrawer getImage() {
        return null;
    }
}
