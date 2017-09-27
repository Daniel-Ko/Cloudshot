package view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.GameModel;

public class StaticSprite extends CustomSprite {

    private int width;
    private int height;

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
    public TextureRegion getFrameFromTime(float elapsedTime) {
        spriteImage.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
        TextureRegion r = new TextureRegion(spriteImage);

        if(resize) {
            r.setRegionHeight((int) ((float)spriteImage.getHeight() / GameModel.PPM));
            r.setRegionWidth((int) ((float)spriteImage.getWidth() / GameModel.PPM));
        }

        r.setRegion(0,0,spriteImage.getWidth()*(width/spriteImage.getWidth()),
                spriteImage.getHeight()*(height/spriteImage.getHeight()));
        r.flip(horizontal, vertical);
        return r;
    }

}
