package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.GameModel;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.BossOne;
import model.being.enemies.ShootingEnemy;
import model.being.enemies.SpikeBlock;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.collectable.AbstractCollectable;
import model.projectile.BulletImpl;
import view.HealthBar;
import view.InventoryActor;
import view.buttons.LoadButton;
import view.buttons.MenuButton;
import view.buttons.MuteButton;
import view.buttons.SaveButton;
import view.labels.LevelLabel;

public class GameScreen extends ScreenAdapter {

    private static final float PADDING = 10;
    public static final float FRAME_RATE = 0.09f;

    public static InputMultiplexer inputMultiplexer;

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


    public GameScreen(GameModel gameModel) {
        this.stage = new Stage(new ScreenViewport());
        this.gameModel = gameModel;

        levelText = new LevelLabel().createLabel();
        stage.addActor(levelText);

        healthBar = new HealthBar(100, 10);
        healthBar.setPosition(10, Gdx.graphics.getHeight() - 20);
        stage.addActor(healthBar);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gameModel.getPlayer());

        batch = new SpriteBatch();

        inventoryActor = new InventoryActor(gameModel.getPlayer());
        inventoryActor.setY(30);
        inventoryActor.setX(120);
        stage.addActor(inventoryActor);

        save = new SaveButton(
                Gdx.graphics.getWidth() - PADDING,
                PADDING,
                gameModel).createButton();

        mute = new MuteButton(
                Gdx.graphics.getWidth() - PADDING * 2,
                PADDING,
                gameModel).createButton();

        menu = new MenuButton(
                Gdx.graphics.getWidth() - PADDING * 3,
                PADDING
        ).createButton();

        load = new LoadButton(
                Gdx.graphics.getWidth() - PADDING * 4,
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
        batch.setProjectionMatrix(gameModel.getCamera().combined);

        elapsedTime += delta;

        gameModel.update();
        levelText.setText(gameModel.getLevel().getLevelName());

        batch.begin();
        Player player = (Player) gameModel.getPlayer();
        float x = player.getX() - 0.9f;
        float y = player.getY() - 0.6f;
        float width = 1.80f;
        float height = 1.80f;
        batch.draw(
                player.getImage().getFrameFromTime(elapsedTime),
                player.flip() ? x + width : x, y,
                player.flip() ? -width : width, height
        );

        //drawing player bullets
        for (BulletImpl b : player.getBullets()) {
            //sb.draw(play.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime),b.getX()-0.25f,b.getY()-0.25f,0.5f,0.5f);
            batch.draw(player.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime), b.getX() - 0.25f, b.getY() - 0.25f,
                    0f, 0f,
                    0.1f, 0.1f,
                    1.0f, 1.0f,
                    60f, true);

        }
        for (AbstractEnemy ae : gameModel.getEnemies()) {
            if (ae.getImage() == null) continue;
            batch.draw(ae.getImage().getFrameFromTime(elapsedTime), ae.getX() - ae.getDrawingWidth() / 2, ae.getY() - ae.getDrawingHeight() / 4, ae.getDrawingWidth(), ae.getDrawingHeight());
            if (ae instanceof ShootingEnemy) {
                ShootingEnemy s = (ShootingEnemy) ae;
                for (BulletImpl b : s.getBullets())
                    batch.draw(s.getBulletSprite().getFrameFromTime(elapsedTime), b.getX() - 0.25f, b.getY() - 0.25f, 0.5f, 0.5f);
            }
            if (ae instanceof SpikeBlock) {
                SpikeBlock s = (SpikeBlock) ae;
                batch.draw(s.getImage().getFrameFromTime(elapsedTime), s.getX() - s.width / 2, s.getY() - s.height / 2, s.width, s.height);
            }
            if (ae instanceof BossOne) {
                BossOne s = (BossOne) ae;
                for (BulletImpl b : s.bullets)
                    batch.draw(player.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime), b.getX() - 0.25f, b.getY() - 0.25f, 0.5f, 0.5f);
            }

        }

        for (AbstractCollectable ac : gameModel.getCollectables()) {
            batch.draw(ac.getImage().getFrameFromTime(elapsedTime), ac.getX(), ac.getY(), ac.getBoundingBox().getWidth(), ac.getBoundingBox().getHeight());
        }
        gameModel.postDraw();
        batch.end();

        healthBar.setValue(gameModel.getPlayer().getHealth() / 150.0f);

        stage.act();
        stage.draw();
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
