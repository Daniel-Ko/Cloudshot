package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.GameModel;
import model.being.player.AbstractPlayer;
import model.data.GameStateTransactionHandler;
import view.CloudShotGame;
import view.HealthBar;
import view.InventoryActor;
import view.buttons.LoadButton;
import view.buttons.MenuButton;
import view.buttons.MuteButton;
import view.buttons.SaveButton;
import view.labels.InventoryLabel;
import view.labels.LevelLabel;

public class GameScreen extends ScreenAdapter{

    //These values may get changed on a per level basis.
    public final int WORLD_HEIGHT = 2000;
    public final int WORLD_WIDTH = 3000;

    private static final float PADDING = 10;
    public static final float FRAME_RATE = 0.09f;

    public static InputMultiplexer inputMultiplexer;

    public static final int VIEW_WIDTH = 1000;

    private SpriteBatch batch;

    private OrthographicCamera camera;

    private float elapsedTime;
    private HealthBar healthBar;
    private GameModel gameModel;
    private Stage stage;

    /**
     * UI elements;
     */
    private TextButton save;
    private TextButton mute;
    private TextButton menu;
    private TextButton load;

    private Label levelText;

    private InventoryActor inventoryActor;


    public GameScreen(){
        this.stage = new Stage(new ScreenViewport());

        levelText = new LevelLabel().createLabel();
        stage.addActor(levelText);

        healthBar = new HealthBar(100, 10);
        healthBar.setPosition(10, Gdx.graphics.getHeight() - 20);
        stage.addActor(healthBar);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);

        batch = new SpriteBatch();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(VIEW_WIDTH/GameModel.PPM,((VIEW_WIDTH * (h / w))/GameModel.PPM));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        gameModel = new GameModel(camera);
        gameModel.getTiledMapRenderer().setView(camera);

        inventoryActor = new InventoryActor(gameModel.getPlayer());
        inventoryActor.setY(30);
        inventoryActor.setX(120);
        stage.addActor(inventoryActor);

        save = new SaveButton(
                Gdx.graphics.getWidth() - PADDING,
                PADDING,
                gameModel).createButton();

        mute = new MuteButton(
                Gdx.graphics.getWidth() - PADDING*2,
                PADDING,
                gameModel).createButton();

        menu = new MenuButton(
                Gdx.graphics.getWidth() - PADDING*3,
                PADDING
        ).createButton();

        load = new LoadButton(
                Gdx.graphics.getWidth()- PADDING*4,
                PADDING,
                gameModel
        ).createButton();

        stage.addActor(mute);
        stage.addActor(save);
        stage.addActor(menu);
        stage.addActor(load);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        elapsedTime += delta;

        gameModel.getTiledMapRenderer().setView(camera); // Game map.
        gameModel.getTiledMapRenderer().render();

        // Update the camera.
        updateCamera(gameModel.getPlayer());

        // Update the game state.
        gameModel.updateState(elapsedTime);


        batch.begin();

        levelText.setText(gameModel.getLevel().getLevelName());
        gameModel.draw(batch);
        batch.end();

        healthBar.setValue(gameModel.getPlayer().getHealth()/150.0f);

        stage.act();
        stage.draw();
    }

    private void updateCamera(AbstractPlayer player) {
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.set(player.getX(), player.getY(),0);//lock camera to player's position

        camera.position.x = MathUtils.clamp(camera.position.x,
                effectiveViewportWidth / 2f,
                WORLD_WIDTH - effectiveViewportWidth
        );

        camera.position.y = MathUtils.clamp(camera.position.y,
                effectiveViewportHeight / 2f,
                WORLD_HEIGHT - effectiveViewportHeight / 2f
        );

        camera.update();
    }


    @Override
    public void dispose () {
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public static void displayGameOverScreen(){
        MenuScreen.game.setScreen(new GameOverScreen());
    }

}
