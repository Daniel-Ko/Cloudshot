package view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import model.GameModel;
import model.mapObject.levels.LevelOne;
import view.CustomSprite;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter{

    //These values may get changed on a per level basis.
    private final int WORLD_HEIGHT = 1000;
    private final int WORLD_WIDTH = 2000;

    public static final float FRAME_RATE = 0.09f;


    private final int viewWidth = 1000;

    private SpriteBatch batch;
    private Game game;

    ArrayList<CustomSprite> sprites;

    private OrthographicCamera cam;

    float elapsedTime;

    private GameModel gameModel;

    public GameScreen(Game game){
        this.game = game;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(viewWidth,viewWidth * (h / w));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        this.gameModel = new GameModel(new LevelOne(),cam);
        gameModel.getTiledMapRenderer().setView(cam);

        batch = new SpriteBatch();
        sprites = new ArrayList<>();
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        cam.update();
        updateCamera();
        batch.setProjectionMatrix(cam.combined);

        gameModel.updateState(elapsedTime); //update game state

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameModel.getTiledMapRenderer().render();//draw game map

        //do drawing
        batch.begin();
        gameModel.draw(batch);
        batch.end();
    }

    private void updateCamera() {
        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.set(gameModel.getPlayer().getX(),cam.position.y,0);//lock camera to player's position
        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);

    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
