package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * MovingSprite handles all the logic to create and return a TextureRegion based on the time elapsed and frame
 * rate of the animation. The TextureRegion is to be rendered by View.java
 * @author Yi Sian Lim
 */
public class MovingSprite extends CustomSprite {

    /**
     * Stores all the TextureRegion of the frames for animation.
     */
    private TextureRegion[] animationFrames;

    /**
     * Animation will store the frame rates and animation frames to animate.
     */
    private Animation animation;

    /**
     * Number of rows and columns in the sprite image.
     */
    private final int ROWS;
    private final int COLS;

    public MovingSprite(String imageName, int rows, int cols){
        super(imageName);
        this.ROWS = rows;
        this.COLS = cols;
    }

    /**
     * createSprite takes the single sprite image and cuts it all up so that each frame consist of one TextureRegion.
     * animation is initialised by storing all the TextureRegion frames and frame rate.
     * @param frameRate
     *          frameRate for the sprite animation.
     */
    public void createSprite(float frameRate){
        // Get the sprite image and split it into TextureRegions consisting of the image during that frame.
        spriteImage = new Texture(Gdx.files.internal(imageName));
        TextureRegion[][] tmpFrames = TextureRegion.split(spriteImage, spriteImage.getWidth() / COLS,
                spriteImage.getHeight() / ROWS);

        // Initialise animationFrames to create the animation.
        animationFrames = new TextureRegion[ROWS * COLS];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                animationFrames[index++] = tmpFrames[i][j];
            }
        }

        // Create the animation.
        animation = new Animation(frameRate, animationFrames);
    }

    /**
     * Return a TextureRegion of the image that should be displayed based on the elapsedTime.
     * @param elapsedTime
     *          Time elapsed so far.
     * @return
     */
    public TextureRegion getFrameFromTime(float elapsedTime){
        return (TextureRegion) animation.getKeyFrame(elapsedTime, true);
    }

}
