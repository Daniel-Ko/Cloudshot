package view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StaticSprite extends CustomSprite {

    protected int width;
    protected int height;

    public StaticSprite(String imageName, int width, int height){
        super(imageName);
        this.width = width;
        this.height = height;
    }

    public StaticSprite(String imageName){
        super(imageName);

        this.width = spriteImage.getWidth();
        this.height = spriteImage.getHeight();

    }

    @Override
    public void createSprite(float frameRate) {
        spriteImage.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
    }

    @Override
    public TextureRegion getFrameFromTime(float elapsedTime) {
        TextureRegion r = new TextureRegion(spriteImage);
        r.setRegion(0,0,spriteImage.getWidth()*(width/spriteImage.getWidth()),
                spriteImage.getHeight()*(height/spriteImage.getHeight()));
        return r;
    }

}
