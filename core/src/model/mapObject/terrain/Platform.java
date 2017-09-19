package model.mapObject.terrain;

import com.badlogic.gdx.math.Rectangle;
import view.SpriteDrawer;

public class Platform extends AbstractTerrain {


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
