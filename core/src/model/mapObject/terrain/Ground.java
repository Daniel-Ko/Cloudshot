package model.mapObject.terrain;

import view.SpriteDrawer;

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
    public SpriteDrawer getImage() {
        return null;
    }
}
