package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StaticSprite extends CustomSprite {

    public StaticSprite(String imageName){
        super(imageName);
    }

    @Override
    public void createSprite(float frameRate) {
        spriteImage = new Texture(Gdx.files.internal(imageName));
    }

    @Override
    public TextureRegion getFrameFromTime(float elapsedTime) {
        return new TextureRegion(spriteImage);
    }

}
