package view.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * StaticSprite loads and draws the sprite which does not require any sort of animation.
 * It is a still image, used for drawing collectables etc.
 * @author Yi Sian Lim
 */
public class StaticSprite extends CustomSprite {
    private static final long serialVersionUID = -385280789516080722L;

    /**
     * Dimensions of the sprite.
     */
    private int width;
    private int height;

    public StaticSprite(String imageName){
        super(imageName);
        this.width = spriteImage.getWidth();
        this.height = spriteImage.getHeight();
    }

    @Override
    public TextureRegion getFrameFromTime(float elapsedTime) {
        spriteImage.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
        TextureRegion r = new TextureRegion(spriteImage);
        r.setRegion(0,0,spriteImage.getWidth()*(width/spriteImage.getWidth()),
                spriteImage.getHeight()*(height/spriteImage.getHeight()));
        r.flip(horizontal, vertical);
        return r;
    }

}
