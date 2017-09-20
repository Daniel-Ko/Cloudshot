package model.mapObject.terrain;

import com.badlogic.gdx.math.Rectangle;
import view.CustomSprite;
import view.MovingSprite;

/**
 * AbstractTerrain implementation.
 * The difference between Ground and Platform is that ground extends up from the very bottom of a window.
 */
public class Ground extends AbstractTerrain {

    private Rectangle groundPiece;

    public Ground(int startingX, int width, int height){
        groundPiece = new Rectangle(startingX,0,width,height);
    }

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
    public CustomSprite getImage() {
        return null;
    }
}
