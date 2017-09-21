package view;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import controller.Controller;
import controller.PlayerController;
import model.being.Player;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.terrain.AbstractTerrain;
import model.mapObject.terrain.Ground;

import java.util.ArrayList;
import java.util.List;

public class View extends ApplicationAdapter{

    //These values may get changed on a per level basis.
    private final int WORLD_HEIGHT = 1000;
    private final int WORLD_WIDTH = 2000;

    Controller controller;
    PlayerController playerController;//TODO move this into master controller class
    Player player;

    AbstractLevel level;

    public int x = 50;
    public int y = 50;

    private final int viewWidth = 1000;

    // CustomSprite animation.
    SpriteBatch batch;
    MovingSprite walkingMan;
    //MovingSprite player;
    StaticSprite backgroundImage;

    float elapsedTime;

    // Map
    private OrthographicCamera cam;
    private Viewport viewport;

    // Ground textures.
    private List<CustomSprite> groundSprites;

    private static final float FRAME_RATE = 0.02f;
    

    @Override
    public void create () {
        batch = new SpriteBatch();
        controller = new Controller(this);
        player = new Player(new Vector2(50,50), 50, 50, 10, new Vector2(5,2));
        playerController = new PlayerController(player);

        level = new LevelOne();

        Gdx.input.setInputProcessor(playerController);//set the controller to receive input when keys pressed
        Gdx.input.setInputProcessor(controller);//set the controller to receive input when keys pressed
        
        walkingMan = new MovingSprite("sprite-animation4.png", 5, 6);
        walkingMan.createSprite(FRAME_RATE);

        backgroundImage = new StaticSprite("background.png",WORLD_WIDTH,WORLD_HEIGHT);
        backgroundImage.createSprite(FRAME_RATE);


        groundSprites = new ArrayList<>();
        for(AbstractTerrain t : level.getTerrain()){
            CustomSprite customSprite = t.getImage();
            customSprite.createSprite(FRAME_RATE);
            groundSprites.add(customSprite);
        }

        float w = Gdx.graphics.getWidth();//* 0.5f;
        float h = Gdx.graphics.getHeight();// * 0.5f;




        cam = new OrthographicCamera(viewWidth,viewWidth * (h / w));
        //viewport = new FitViewport(300,300, cam);

        //cam = new OrthographicCamera(30, 30 * (h / w));

       cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        //cam.position.set(300f,300f,0f);
        //cam.zoom = 1000f;
        cam.update();
    }

    @Override
    public void render () {
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

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

        batch.draw(walkingMan.getFrameFromTime(elapsedTime), x, y);
        batch.end();




    }

    private void handleInput() {
        /*if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            cam.zoom += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.1;
        }*/
        int speed = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            //cam.translate(-10, 0, 0);
            x -=speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            //cam.translate(10, 0, 0);
            if(x >= WORLD_WIDTH-5) return;
            x+=speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            //cam.translate(0, -10, 0);
            y-=speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            //cam.translate(0, 10, 0);
            y+=speed;
        }


        //cam.zoom = MathUtils.clamp(cam.zoom, 1000f, 1000/cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;


        int width = walkingMan.getFrameFromTime(elapsedTime).getRegionWidth();
        if(x > WORLD_WIDTH - effectiveViewportWidth/2f-width){
            x =(int)(WORLD_WIDTH - effectiveViewportWidth/2f- width);
        }
        else if(x < 0){
            x = 0;
        }

        cam.position.set(x,cam.position.y,0);
        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 1000 - effectiveViewportHeight / 2f);

    }


    @Override
    public void dispose () {
        batch.dispose();
        //img.dispose();
    }
	}
