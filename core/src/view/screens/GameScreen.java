package view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import model.GameModel;
import model.being.MeleeEnemy;
import model.being.Player;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.terrain.AbstractTerrain;
import view.CustomSprite;
import view.MovingSprite;
import view.StaticSprite;

import java.util.ArrayList;
import java.util.List;

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

        this.gameModel = new GameModel(new LevelOne());

        batch = new SpriteBatch();
        sprites = new ArrayList<>();


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(viewWidth,viewWidth * (h / w));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        gameModel.getTiledMapRenderer().setView(cam);
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
