package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.GameModelInterface;
import model.being.enemies.*;
import model.being.player.Player;
import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.mapObject.levels.LevelOne;
import model.mapObject.levels.LevelThree;
import model.projectile.BulletImpl;
import view.Assets;
import view.factories.ButtonFactory;
import view.factories.LabelFactory;
import view.utils.EnemyCountActor;
import view.utils.InventoryActor;
import view.utils.PlayerHealthBar;

import java.util.List;

/**
 * GameScreen displays the screen of the game, drawing out the sprites for each component of the game
 * such as the player, player inventory, collectables in the game, enemies and etc.
 * @author Yi Sian Lim
 */
public class GameScreen extends ScreenAdapter {

    /**
     * Different states of the game:
     *      - GAME_PAUSED is when the game is paused on screen.
     *      - GAME_RUNNING is when the game is still running.
     *      - GAME_OVER is when the game is over
     *      - GAME_PAUSED_MENU is when the game is paused by pressing "ESC"
     *        and goes to the menu screen.
     */
    public enum State{
        GAME_PAUSED, GAME_RUNNING, GAME_OVER, GAME_PAUSED_MENU, GAME_WIN
    }

    /**
     * Current state.
     */
    public static State state;

    /**
     * Constants.
     */
    private static final float PADDING = 10;
    public static final float FRAME_RATE = 0.09f;


    /**
     * inputMultiplexer is the controller which listens for user input.
     */
    public static InputMultiplexer inputMultiplexer;

    /**
     * To handle the save and load of the current game model.
     */
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
    private PlayerHealthBar healthBar;
    private Button mute;
    private Button pause;
    private Label levelText;
    private EnemyCountActor enemyCountActor;
    private InventoryActor inventoryActor;

    public GameScreen(GameModelInterface gameModel) {
        this.stage = new Stage(new ScreenViewport());
        this.gameModel = gameModel;
        this.batch = new SpriteBatch();
        this.state = State.GAME_RUNNING;

        initGameModel();

        // Set up the controllers (buttons and player)
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gameModel.getPlayer());

        initialiseLevelText();
        initialiseHealthBar();
        initialiseInventory();
        initialiseButtons();
        initialiseEnemyCount();
    }

    /**
     * Initialise the game model by setting up the respective components.
     */
    private void initGameModel() {
        // Finally, load in the first level.
        gameModel.setLevel(new LevelOne());

        gameModel.setupCamera();
        gameModel.setupGame();
        gameModel.loadTerrain();
        gameModel.loadMusic();

        // Set separate module to handle save/load.
        saveLoadHandler = new GameStateTransactionHandler();
        gameModel.setRepoScraper(saveLoadHandler);
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
        healthBar = new PlayerHealthBar(100, 10);
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

    private void initialiseEnemyCount(){
        enemyCountActor = new EnemyCountActor(gameModel);
        stage.addActor(enemyCountActor);
    }

    /**
     * Initialise all the buttons in the GameScreen.
     */
    private void initialiseButtons(){
        mute = ButtonFactory.muteButton(
                Gdx.graphics.getWidth() - PADDING,
                -30,
                gameModel);

        pause = ButtonFactory.pauseButton(
                Gdx.graphics.getWidth() - PADDING * 2,
                -30
        );

        // Add buttons into the staging area.
        stage.addActor(mute);
        stage.addActor(pause);
    }

    @Override
    public void render(float delta) {
        // Initialise the Gdx render settings.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(gameModel.getCamera().combined);

        // Draw the game map.
        gameModel.getTiledMapRenderer().setView(gameModel.getCamera());
        gameModel.getTiledMapRenderer().render();
        
        // Check if the "ESC" key is clicked.
        checkIfNeedToGoBackToMenu();

        // Display to the player based on the state of the game.
        switch(state){
            case GAME_RUNNING:
                presentRunningGame(delta);
                break;
            case GAME_PAUSED:
                presentPausedGame();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
            case GAME_PAUSED_MENU:
                presentMenu();
                break;
            case GAME_WIN:
                presentGameWin();
        }

    }

    private void presentGameWin() {
        MenuScreen.game.setScreen(new GameWinScreen(gameModel));
    }

    /**
     * Update the state of the game if the "esc" key is pressed.
     * The game will be redirected to the main menu with the option to resume the game.
     */
    private void checkIfNeedToGoBackToMenu() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            state = State.GAME_PAUSED_MENU;
        }
    }

    /**
     * Present the running game. Increases the elapsed time as well as update the game model.
     * @param delta
     */
    private void presentRunningGame(float delta){
        // Update the gameModel and elapsed time only if the game is running.
        elapsedTime += delta;
        gameModel.update();
        renderGame();
    }

    /**
     * Displays the game over screen.
     */
    public void presentGameOver() {
        MenuScreen.game.setScreen(new GameOverScreen(gameModel));
    }

    /**
     * Presents the paused game, which pretty much renders the game without updating the model.
     */
    private void presentPausedGame() {
        renderGame();
    }

    /**
     * Present the menu to the player, which effectively pauses the game as well.
     */
    private void presentMenu() {
        MenuScreen.game.setScreen(new MenuScreen(MenuScreen.game, this, gameModel));
        state = State.GAME_PAUSED;
    }

    /**
     * Logic to render the game lies here.
     */
    private void renderGame(){
        batch.begin();

        // Update levelText and healthBar.
        levelText.setText(gameModel.getLevelName());
        healthBar.setValue(gameModel.getPlayer().getHealth() / 150.0f);

        // Update and draw the game model.
        Player player = (Player) gameModel.getPlayer();

        // Update the actors for drawing.
        inventoryActor.setPlayer(player);
        enemyCountActor.setGameModel(gameModel);

        // Draw the components.
        drawPlayer(player);
        drawBullets(player);
        drawEnemies(gameModel.getEnemies(), player);
        drawCollectables(gameModel.getCollectables());

        batch.end();
        stage.act();
        stage.draw();
    }

    /**
     * Draw the player as well as its equipped weapon (if any) using batch.
     * @param player
     *          Player that we are going to draw.
     */
    private void drawPlayer(Player player){
        // Draw the player.
        float x = player.getX() - 0.9f;
        float y = player.getY() - 0.6f;
        float width = 1.80f;
        float height = 1.80f;
        batch.draw(
                player.getImage().getFrameFromTime(elapsedTime),
                player.flip() ? x + width : x, y,
                player.flip() ? -width : width, height
        );

        // Draw the current weapon.
        if(!player.getInventory().isEmpty()){
            if(player.flip()){
                // Should be facing left.
                batch.draw(player.getInventory().get(player.getCurWeapon()).getImage().getFrameFromTime(elapsedTime),
                        player.flip() ? x + width/2 : x, y+height/2,
                        player.flip() ? -0.5f : 0.5f,0.5f
                );
            }
            else{
                batch.draw(player.getInventory().get(player.getCurWeapon()).getImage().getFrameFromTime(elapsedTime),
                        x + width/2, y+height/2,
                        player.flip() ? -0.5f : 0.5f,0.5f
                );
            }
        }
    }

    /**
     * Draw the bullets that the player shoots out.
     * @param player
     *          Player that we are going to draw.
     *
     */
    private void drawBullets(Player player){
        for (BulletImpl b : player.getBullets()) {
            batch.draw(player.getInventory().get(player.getCurWeapon()).getBulletImage().getFrameFromTime(elapsedTime),
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

            // Draw the enemy health bar.
            float ratio = (float)ae.getHealth() / (float)ae.getMaxHealth();
            // Draw out the no health state first.
            batch.draw(Assets.no_health,
                    ae.getX() - ae.getDrawingWidth() / 2,
                    ae.getY() + ae.getDrawingHeight(),
                    0.7f,
                    0.1f);

            // Then draw out the full health bar with the width being based off the health.
            batch.draw(Assets.full_health,
                    ae.getX() - ae.getDrawingWidth() / 2,
                    ae.getY() + ae.getDrawingHeight(),
                    0.7f * ratio,
                    0.1f);

            // Draw the enemy.
            batch.draw(ae.getImage().getFrameFromTime(elapsedTime),
                    ae.getX() - ae.getDrawingWidth() / 2,
                    ae.getY() - ae.getDrawingHeight() / 4,
                    ae.getDrawingWidth(),
                    ae.getDrawingHeight()
            );

            // Draw the bullets that the enemy shoots.
            if (ae instanceof ShootingEnemy) {
                ShootingEnemy s = (ShootingEnemy) ae;
                for (BulletImpl b : s.getBulletsShot())
                    batch.draw(s.getBulletSprite().getFrameFromTime(elapsedTime),
                            b.getX() - 0.25f, b.getY() - 0.25f,
                            0.2f,
                            0.2f
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



    public void setPersistHandler(GameStateTransactionHandler persistHandler) {
        this.saveLoadHandler = persistHandler;
        this.gameModel.setRepoScraper(persistHandler);
    }
}
