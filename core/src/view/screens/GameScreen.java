package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.GameModel;
import model.GameModelInterface;
import model.being.enemies.*;
import model.being.player.Player;
import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.mapObject.levels.LevelOne;
import model.projectile.BulletImpl;
import view.utils.ButtonFactory;
import view.utils.LabelFactory;
import view.utils.HealthBar;
import view.utils.InventoryActor;

import java.util.List;

public class GameScreen extends ScreenAdapter {

    public enum State{
        GAME_PAUSED, GAME_RUNNING, GAME_OVER
    }

    /**
     * Constants.
     */
    private static final float PADDING = 10;
    public static final float FRAME_RATE = 0.09f;

    public static State state;

    /**
     * inputMultiplexer is the controller which listens for user input.
     */
    public static InputMultiplexer inputMultiplexer;
    private GameStateTransactionHandler saveLoadHandler;

    /**
     * batch draws the elements of the game.
     */
    private SpriteBatch batch;

    /**
     * Time elapsed so far.
     */
    private float elapsedTime;

    /**
     * Model of Cloudshot.
     */
    private GameModelInterface gameModel;

    /**
     * Stage is the staging area for all the UI elements in the game.
     */
    private Stage stage;

    /**
     * UI elements;
     */
    private HealthBar healthBar;
    private TextButton save;
    private TextButton mute;
    private TextButton menu;
    private TextButton load;
    private TextButton pause;
    private Label levelText;
    private InventoryActor inventoryActor;

    public GameScreen(GameModelInterface gameModel) {
        this.stage = new Stage(new ScreenViewport());
        this.gameModel = gameModel;
        initGameModel();

        this.batch = new SpriteBatch();
        this.state = State.GAME_RUNNING;

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gameModel.getPlayer());

        initialiseLevelText();
        initialiseHealthBar();
        initialiseInventory();
        initialiseButtons();

    }

    private void initGameModel() {
        GameModel model = (GameModel) gameModel;

        // Finally, load in the first level.
        gameModel.setLevel(new LevelOne());

        model.setupCamera();
        model.setupGame();
        model.loadTerrain();
        model.loadMusic();

        // Set separate module to handle save/load.
        saveLoadHandler = new GameStateTransactionHandler();
        model.setRepoScraper(saveLoadHandler);
    }

    /**
     * Initialise the label to display the levels.
     */
    private void initialiseLevelText() {
        levelText = LabelFactory.levelLabel(gameModel);
        stage.addActor(levelText);
    }

    /**
     * Initialise healthBar which displays the player's health rate.
     */
    private void initialiseHealthBar(){
        healthBar = new HealthBar(100, 10);
        healthBar.setPosition(10, Gdx.graphics.getHeight() - 20);
        stage.addActor(healthBar);
    }

    /**
     * Initialise inventoryActor which displays what weapons does the player currently own as well as the
     * ammo counts for each weapon.
     */
    private void initialiseInventory(){
        inventoryActor = new InventoryActor(gameModel.getPlayer());
        inventoryActor.setY(30);
        inventoryActor.setX(120);
        stage.addActor(inventoryActor);
    }

    /**
     * Initialise all the buttons in the GameScreen.
     */
    private void initialiseButtons(){
        // Create the buttons first.
        save = ButtonFactory.saveButton(
                Gdx.graphics.getWidth() - PADDING,
                PADDING,
                gameModel);

        mute = ButtonFactory.muteButton(
                Gdx.graphics.getWidth() - PADDING * 2,
                PADDING,
                gameModel);

        menu = ButtonFactory.menuButton(
                Gdx.graphics.getWidth() - PADDING * 3,
                PADDING
        );

        load = ButtonFactory.loadButton(
                Gdx.graphics.getWidth() - PADDING * 4,
                PADDING,
                gameModel
        );

        pause = ButtonFactory.pauseButton(
                Gdx.graphics.getWidth() - PADDING *5,
                PADDING
        );

        // Add buttons into the staging area.
        stage.addActor(mute);
        stage.addActor(save);
        stage.addActor(menu);
        stage.addActor(load);
        stage.addActor(pause);
    }

    @Override
    public void render(float delta) {
        // Initialise the Gdx render settings.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(gameModel.getCamera().combined);

        // Update the gameModel and elapsed time only if the game is running.
        if(state.equals(State.GAME_RUNNING)) {
            elapsedTime += delta;
            gameModel.update();
        }

        // Draw the terrains.
        gameModel.getTiledMapRenderer().setView(gameModel.getCamera()); // Game map.
        gameModel.getTiledMapRenderer().render();

        // Drawing logic begins here.
        batch.begin();

        switch(state){
            case GAME_RUNNING:
                presentRunningGame();
                break;
            case GAME_PAUSED:
                presentPausedGame();
                break;
            case GAME_OVER:
                //TODO: Display game over screen.
                break;
        }


        batch.end();

    }

    private void presentRunningGame(){

        // Update levelText and healthBar.
        levelText.setText(gameModel.getLevelName());
        healthBar.setValue(gameModel.getPlayer().getHealth() / 150.0f);

        // Update and draw the game model.
        Player player = (Player) gameModel.getPlayer();
        drawPlayer(player);
        drawBullets(player);
        drawEnemies(gameModel.getEnemies(), player);
        drawCollectables(gameModel.getCollectables());

        stage.act();
        stage.draw();
    }

    private void presentPausedGame() {
        // Update levelText and healthBar.
        levelText.setText(gameModel.getLevelName());
        healthBar.setValue(gameModel.getPlayer().getHealth() / 150.0f);

        // Update and draw the game model.
        Player player = (Player) gameModel.getPlayer();
        drawPlayer(player);
        drawBullets(player);
        drawEnemies(gameModel.getEnemies(), player);
        drawCollectables(gameModel.getCollectables());

        stage.act();
        stage.draw();
    }

    /**
     * Draw the player using batch.
     * @param player
     *          Player that we are going to draw.
     */
    private void drawPlayer(Player player){
        float x = player.getX() - 0.9f;
        float y = player.getY() - 0.6f;
        float width = 1.80f;
        float height = 1.80f;
        batch.draw(
                player.getImage().getFrameFromTime(elapsedTime),
                player.flip() ? x + width : x, y,
                player.flip() ? -width : width, height
        );
    }

    /**
     * Draw the bullets that the player shoots out.
     * @param player
     *          Player that we are going to draw.
     *
     */
    private void drawBullets(Player player){
        //drawing player bullets
        for (BulletImpl b : player.getBullets()) {
            batch.draw(player.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime),
                    b.getX() - 0.25f,
                    b.getY() - 0.25f,
                    0f, 0f,
                    0.1f, 0.1f,
                    1.0f, 1.0f,
                    60f, true);
        }
    }

    /**
     * Draw the collection of AbstractEnemy enemies.
     * @param enemies
     *          AbstractEnemy(s) that we want to draw.
     * @param player
     *          Player that retrieve information in order to draw enemies.
     */
    private void drawEnemies(List<AbstractEnemy> enemies, Player player) {
        for (AbstractEnemy ae : enemies) {
            if (ae.getImage() == null) continue;//TODO this shouldnt be null look into this
            batch.draw(ae.getImage().getFrameFromTime(elapsedTime),
                    ae.getX() - ae.getDrawingWidth() / 2,
                    ae.getY() - ae.getDrawingHeight() / 4,
                    ae.getDrawingWidth(),
                    ae.getDrawingHeight()
            );
            if (ae instanceof ShootingEnemy) {
                ShootingEnemy s = (ShootingEnemy) ae;
                for (BulletImpl b : s.getBullets())
                    batch.draw(s.getBulletSprite().getFrameFromTime(elapsedTime),
                            b.getX() - 0.25f, b.getY() - 0.25f,
                            0.5f,
                            0.5f
                    );
            }
            if (ae instanceof Boss1V2) {
                Boss1V2 s = (Boss1V2) ae;
                for (BulletImpl b : s.getBulletsShot())
                    batch.draw(s.getBulletSprite().getFrameFromTime(elapsedTime),
                            b.getX() - 0.25f, b.getY() - 0.25f,
                            0.5f,
                            0.5f
                    );
            }
            if (ae instanceof SpikeBlock) {
                SpikeBlock s = (SpikeBlock) ae;
                batch.draw(s.getImage().getFrameFromTime(elapsedTime),
                        s.getX() - s.width / 2,
                        s.getY() - s.height / 2,
                        s.width,
                        s.height
                );
            }
            if (ae instanceof BossOne) {
                BossOne s = (BossOne) ae;
                for (BulletImpl b : s.bullets)
                    batch.draw(player.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime),
                            b.getX() - 0.25f,
                            b.getY() - 0.25f,
                            0.5f,
                            0.5f
                    );
            }
        }
    }

    /**
     * Draw the collection of Collectables
     * @param collectables
     *          AbstractCollectable(s) that we want to draw.
     */
    private void drawCollectables(List<AbstractCollectable> collectables) {
        for (AbstractCollectable ac : collectables) {
            batch.draw(ac.getImage().getFrameFromTime(elapsedTime),
                    ac.getX(),
                    ac.getY(),
                    ac.getBoundingBox().getWidth(),
                    ac.getBoundingBox().getHeight()
            );
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public static void displayGameOverScreen() {
        MenuScreen.game.setScreen(new GameOverScreen());
    }

}
