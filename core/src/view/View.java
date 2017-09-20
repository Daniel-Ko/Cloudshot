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
import com.badlogic.gdx.math.Vector2;

import controller.Controller;
import controller.PlayerController;
import model.being.Player;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.terrain.AbstractTerrain;
import model.mapObject.terrain.Ground;

import java.util.ArrayList;

public class View extends ApplicationAdapter{

    Controller controller;
    PlayerController playerController;//TODO move this into master controller class
    Player player;

    AbstractLevel level;

    public int x = 50;
    public int y = 50;

    // CustomSprite animation.
    SpriteBatch batch;
    MovingSprite walkingMan;

    float elapsedTime;

    // Map
    private OrthographicCamera cam;

    private StaticSprite mapSprite;

    private static final float FRAME_RATE = 0.25f;
    

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

        mapSprite = new StaticSprite("game_map.jpg");
        mapSprite.createSprite(FRAME_RATE);

        for(AbstractTerrain t : level.getTerrain()){
            t.getImage().createSprite(FRAME_RATE);
        }

        float w = Gdx.graphics.getWidth()* 0.5f;
        float h = Gdx.graphics.getHeight() * 0.5f;

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        cam = new OrthographicCamera(30, 30 * (h / w));
        cam.position.set(cam.viewportWidth, cam.viewportHeight, 0);
        cam.update();
    }

    @Override
    public void render () {
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        elapsedTime += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(controller.keyPressed) {
            switch(controller.keycode) {
                case Input.Keys.A: {
                    this.x-=10;
                    break;
                }
                case Input.Keys.D: {
                    this.x+=10;
                    break;
                }
                case Input.Keys.W: {
                    this.y+=10;
                    break;
                }
                case Input.Keys.S: {
                    this.y-=10;
                    break;
                }
            }
        }

        for(AbstractTerrain t : level.getTerrain()){
            batch.draw(t.getImage().getFrameFromTime(elapsedTime),t.getBoundingbox().getX(),t.getBoundingbox().getY());
        }

        batch.draw(mapSprite.getFrameFromTime(elapsedTime), 0,0);
        batch.draw(walkingMan.getFrameFromTime(elapsedTime), 0, 0);
        batch.end();

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            cam.zoom += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-10, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(10, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }

        //cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        //cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        //cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }


    @Override
    public void dispose () {
        batch.dispose();
        //img.dispose();
    }
	}
