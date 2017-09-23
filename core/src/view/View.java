package view;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.viewport.Viewport;
import controller.Controller;
import controller.PlayerController;
import model.being.MeleeEnemy;
import model.being.Player;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.terrain.AbstractTerrain;

import java.util.ArrayList;
import java.util.List;

public class View extends ApplicationAdapter{

    //These values may get changed on a per level basis.
    private final int WORLD_HEIGHT = 1000;
    private final int WORLD_WIDTH = 2000;

    public static final float FRAME_RATE = 0.09f;

    /**
     * Controllers.
     */
    Controller controller;
    PlayerController playerController;//TODO move this into master controller class

    /**
     * Model of the games.
     */
    Player player;
    MeleeEnemy e1;
    AbstractLevel level;

    public int x = 50;
    public int y = 50;

    private final int viewWidth = 1000;

    /**
     * batch describes many rectangles for the same texture and send to the GPU all at once.
     */
    private SpriteBatch batch;

    /**
     * UI elements for the game.
     */
    private MovingSprite playerSprite;
    private StaticSprite backgroundImage;
    private MovingSprite enemy1Sprite;
    private List<CustomSprite> groundSprites;

    /**
     * Time elapsed so far. This is used for animation.
     */
    private float elapsedTime;

    /**
     * Camera in the view. At the moment, the camera follows the player.
     */
    private OrthographicCamera cam;

    @Override
    public void create () {
        batch = new SpriteBatch();
        controller = new Controller(this);
        player = new Player(new Vector2(50,200), 50, 50, 100, 3);
        e1 = new MeleeEnemy(10,player,new Vector2(600,100));
        playerController = new PlayerController(player);

        level = new LevelOne();

        // Set the controller to receive input when keys pressed.
        Gdx.input.setInputProcessor(player);

        // Initialise the sprites.
        enemy1Sprite = e1.getImage();
        playerSprite = player.getImage();
        backgroundImage = new StaticSprite("background.png",WORLD_WIDTH,WORLD_HEIGHT);
        groundSprites = new ArrayList<>();
        for(AbstractTerrain t : level.getTerrain()){
            CustomSprite customSprite = t.getImage();
            groundSprites.add(customSprite);
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(viewWidth,viewWidth * (h / w));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
    }

    @Override
    public void render () {
        updateCamera();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        //Updating player model
        playerController.applyMovement();
        player.update();

        //for all enemies check if player is hitting them
        player.attack(e1);

        //re-updating players image based on state
        playerSprite = player.getImage();
        enemy1Sprite = e1.getImage();

        e1.update();
        elapsedTime += Gdx.graphics.getDeltaTime();

        //Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        batch.begin();
        batch.draw(backgroundImage.getFrameFromTime(elapsedTime),0,0);

        for(int i = 0; i < groundSprites.size(); i++){
            AbstractTerrain currentTerrain = level.getTerrain().get(i);
            batch.draw(groundSprites.get(i).getFrameFromTime(elapsedTime),
                    currentTerrain.getBoundingbox().getX(),
                    currentTerrain.getBoundingbox().getY(),
                    currentTerrain.getBoundingbox().getWidth(),
                    currentTerrain.getBoundingbox().getHeight());
        }

        batch.draw(playerSprite.getFrameFromTime(elapsedTime),player.getX(),player.getY());
        batch.draw(enemy1Sprite.getFrameFromTime(elapsedTime),e1.getX(),e1.getY());
        BitmapFont text = new BitmapFont();

        text.draw(batch, "Level: "+ level.getLevelNumber() + " - "+ level.getLevelName(),cam.position.x + 10 - cam.viewportWidth/2,cam.viewportHeight-10);

        batch.end();
    }

    private void updateCamera() {
        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        int width = playerSprite.getFrameFromTime(elapsedTime).getRegionWidth();
        if(x > WORLD_WIDTH - effectiveViewportWidth/2f-width){
            x =(int)(WORLD_WIDTH - effectiveViewportWidth/2f- width);
        }
        else if(x < 0){
            x = 0;
        }

        cam.position.set(player.getX(),cam.position.y,0);
        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);

    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
