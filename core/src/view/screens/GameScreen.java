package view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import model.GameModel;
import model.mapObject.levels.LevelOne;

public class GameScreen extends ScreenAdapter{

    //These values may get changed on a per level basis.
    private final int WORLD_HEIGHT = 1000;
    private final int WORLD_WIDTH = 2000;

    public static final float FRAME_RATE = 0.09f;

    private final int VIEW_WIDTH = 1000;

    private SpriteBatch batch;
    private Game game;

    private OrthographicCamera camera;

    float elapsedTime;

    private GameModel gameModel;

    public GameScreen(Game game){
        this.game = game;
        this.gameModel = new GameModel(new LevelOne());

        batch = new SpriteBatch();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(VIEW_WIDTH, VIEW_WIDTH * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        gameModel.getTiledMapRenderer().setView(camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        elapsedTime += delta;

        // Update the camera.
        camera.update();
        updateCamera();

        // Update the game state.
        gameModel.updateState(elapsedTime);

        // Render the game elements.
        gameModel.getTiledMapRenderer().render(); // Game map.
        batch.begin();

        drawLevelText();
        gameModel.draw(batch);
        batch.end();
    }

    private void updateCamera() {
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.set(gameModel.getPlayer().getX(), camera.position.y,0);//lock camera to player's position
        camera.position.x = MathUtils.clamp(camera.position.x,
                effectiveViewportWidth / 2f,
                WORLD_WIDTH - effectiveViewportWidth
        );

        camera.position.y = MathUtils.clamp(camera.position.y,
                effectiveViewportHeight / 2f,
                WORLD_HEIGHT - effectiveViewportHeight / 2f
        );

    }

    public void drawLevelText(){
        BitmapFont text = new BitmapFont();
        text.draw(batch, "Level: "+ gameModel.getLevel().getLevelNumber() + " - "+ gameModel.getLevel().getLevelName(),camera.position.x + 10 - camera.viewportWidth/2,camera.viewportHeight-10);
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
