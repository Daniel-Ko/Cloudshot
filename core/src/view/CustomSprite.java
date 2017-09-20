package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class CustomSprite{

    /**
     * Name of the sprite image to use.
     */
    protected String imageName;

    /**
     * Image of the sprite sheet to use.
     */
    protected Texture spriteImage;


    public CustomSprite(String imageName){
        this.imageName = imageName;
        this.spriteImage = new Texture(Gdx.files.internal(imageName));
    }

    /**
     * Create a sprite based on the frame rate of the view.
     * @param frameRate
     *          frame rate of the View.
     */
    public abstract void createSprite(float frameRate);

    /**
     * Get the frame in the form of TextureRegion based on the elapsedTime of the View.
     * @param elapsedTime
     *          time elapsed from the View.
     * @return
     *          TextureRegion of the frame needed to render.
     */
    public abstract TextureRegion getFrameFromTime(float elapsedTime);



}
