package view.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;


public abstract class CustomSprite implements Serializable{
    private static final long serialVersionUID = 8723458913423623379L;
    /**
     * Image of the sprite sheet to use.
     */
    protected transient Texture spriteImage;

    /**
     * Flags to determine whether the sprites needs to be flipped or not.
     */
    protected boolean horizontal;
    protected boolean vertical;

    protected String name;

    public CustomSprite(String imageName){
        this.name = imageName;
        this.spriteImage = new Texture(Gdx.files.internal(imageName));
        this.horizontal = false;
        this.vertical = false;
    }

    /**
     * Set the horizontal flip to true.
     * The sprite will be drawn such that it is flipped horizontally.
     */
    public void flipHorizontal(){
        this.horizontal = true;
    }

    /**
     * Set the vertical flip to true.
     * The sprite will be drawn such that it is flipped vertically.
     */
    public void flipVertical(){
        this.vertical = true;
    }

    /**
     * Get the frame in the form of TextureRegion based on the elapsedTime of the GameScreen.
     * @param elapsedTime
     *          time elapsed from the GameScreen.
     * @return
     *          TextureRegion of the frame needed to render.
     */
    public abstract TextureRegion getFrameFromTime(float elapsedTime);



}
