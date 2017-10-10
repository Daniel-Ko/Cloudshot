package view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.GameModel;
import model.mapObject.levels.LevelOne;
import view.CloudShotGame;
import view.HealthBar;
import view.InventoryActor;

import java.util.concurrent.TimeUnit;

public class GameScreen extends ScreenAdapter{

    //These values may get changed on a per level basis.
    public final int WORLD_HEIGHT = 2000;
    public final int WORLD_WIDTH = 3000;

    private static final float PADDING = 10;
    public static final float FRAME_RATE = 0.09f;

    public static InputMultiplexer inputMultiplexer;

    public static final int VIEW_WIDTH = 1000;

    private SpriteBatch batch;
    private static Game game;

    private OrthographicCamera camera;

    private float elapsedTime;
    private HealthBar healthBar;
    private GameModel gameModel;
    private Stage stage;

    /**
     * UI elements;
     */
    private TextButton startButton;
    private TextButton mute;
    private TextButton menu;
    private TextButton load;
    private Label levelText;
    private Label inventoryText;
    private InventoryActor inventoryActor;


    public GameScreen(Game game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        startButton = createSaveButton();
        mute = createMuteButton();
        menu = createMenuButton();
        load = createLoadButton();

        stage.addActor(mute);
        stage.addActor(startButton);
        stage.addActor(menu);
        stage.addActor(load);

        levelText = new Label("",CloudShotGame.gameSkin, "big");
        levelText.setAlignment(Align.center);
        levelText.setY(Gdx.graphics.getHeight() - 20);
        levelText.setWidth(Gdx.graphics.getWidth());
        levelText.setFontScale(1);
        stage.addActor(levelText);

        inventoryText = new Label("Inventory",CloudShotGame.gameSkin, "default");
        inventoryText.setY(10);
        inventoryText.setX(10);
        inventoryText.setWidth(Gdx.graphics.getWidth());
        inventoryText.setFontScale(1.5f);
        stage.addActor(inventoryText);


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
        inventoryActor.setY(10);
        inventoryActor.setX(120);
        stage.addActor(inventoryActor);
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
        updateCamera();
        camera.update();

        // Update the game state.
        gameModel.updateState(elapsedTime);


        batch.begin();

        drawLevelText();
        gameModel.draw(batch);
        batch.end();

        healthBar.setValue(gameModel.getPlayer().getHealth()/150.0f);

        stage.act();
        stage.draw();
    }

    private void updateCamera() {
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.set(gameModel.getPlayer().getX(), /*camera.position.y*/gameModel.getPlayer().getY(),0);//lock camera to player's position

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
        levelText.setText(gameModel.getLevel().getLevelName());

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
        game.setScreen(new GameOverScreen(game));
    }

    private TextButton createSaveButton() {
        TextButton saveButton = new TextButton("Save", CloudShotGame.gameSkin);
        saveButton.setWidth(Gdx.graphics.getWidth()/8);
        saveButton.setPosition(
                Gdx.graphics.getWidth() - saveButton.getWidth() - PADDING,
                PADDING

        );
        saveButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameModel.save();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return saveButton;
    }

    private TextButton createMuteButton() {
        TextButton muteButton = new TextButton("Unmute", CloudShotGame.gameSkin);
        muteButton.setWidth(Gdx.graphics.getWidth()/8);
        muteButton.setPosition(
                Gdx.graphics.getWidth() - muteButton.getWidth()*2 - PADDING*2,
                PADDING

        );
        muteButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameModel.setMuted();
                muteButton.setText(gameModel.musicIsPlaying() ? "Mute" : "Unmute");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return muteButton;
    }

    private TextButton createMenuButton() {
        TextButton menu = new TextButton("Menu", CloudShotGame.gameSkin);
        menu.setWidth(Gdx.graphics.getWidth()/8);
        menu.setPosition(
                Gdx.graphics.getWidth() - menu.getWidth()*3 - PADDING*3,
                PADDING

        );
        menu.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return menu;
    }

    private TextButton createLoadButton() {
        TextButton loadButton = new TextButton("Load", CloudShotGame.gameSkin);
        loadButton.setWidth(Gdx.graphics.getWidth()/8);
        loadButton.setPosition(
                Gdx.graphics.getWidth() - loadButton.getWidth()*4 - PADDING*4,
                PADDING

        );
        loadButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Load game here.
                System.out.println("LOAD GAME");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return loadButton;
    }
}
