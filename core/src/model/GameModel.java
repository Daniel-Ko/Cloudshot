package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.SpikeBlock;
import model.being.enemystates.Death;
import model.being.player.AbstractPlayer;
import model.being.player.PlayerData;
import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.data.StateQuery;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import view.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Optional;

public class GameModel implements GameModelInterface {

    /**
     * Constants for the game.
     */
    public static final float PPM = 50;
    private static final int GRAVITY = -8;
    private static final int WORLD_HEIGHT = 2000;
    private static final int WORLD_WIDTH = 3000;
    public static final int VIEW_WIDTH = 1000;

    /**
     * Player of Cloudshot.
     */
    private AbstractPlayer player;

    /**
     * Manage the enemies in the game.
     */
    private List<AbstractEnemy> enemies;
    private Stack<AbstractEnemy> enemiesToAdd;
    private List<AbstractEnemy> enemiesToRemove;

    /**
     * Keeps track of what level we are currently at.
     */
    private AbstractLevel level;

    /**
     * Save or load game state happens here.
     */
    private GameStateTransactionHandler repoScraper;

    /**
     * Game world.
     */
    private World world;

    /**
     * Box2D handles the physics of the game.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The camera which would essentially be following the player.
     */
    private OrthographicCamera camera;

    /**
     * Soundtrack playing during the game.
     */
    private Music music;

    public GameModel() {
        setupCamera();
        setupGame();
        loadTerrain();
        loadMusic();
    }

    private void setupGame() {
        this.world = new World(new Vector2(0, GRAVITY), true);
        this.debugRenderer = new Box2DDebugRenderer();
        this.enemies = new ArrayList<>();
        this.enemiesToRemove = new ArrayList<>();
        this.enemiesToAdd = new Stack<>();
        this.player = EntityFactory.producePlayer(this, new Vector2(50, 500));
        this.level = new LevelOne();
        this.repoScraper = new GameStateTransactionHandler();
    }

    private void setupCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(
                VIEW_WIDTH / GameModel.PPM,
                ((VIEW_WIDTH * (h / w)) / GameModel.PPM)
        );
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        this.camera.update();
    }

    private void loadTerrain() {
        Array<Rectangle> terrain = level.getTiles();
        for (Rectangle r : terrain) {
            BodyDef terrainPiece = new BodyDef();
            terrainPiece.type = BodyDef.BodyType.StaticBody;
            terrainPiece.position.set(new Vector2((r.x + r.width / 2) / PPM, (r.y + r.height / 2) / PPM));
            Body groundBody = world.createBody(terrainPiece);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox((r.width / 2) / GameModel.PPM, (r.height / 2) / GameModel.PPM);

            // User data to tell us what things are colliding.
            groundBody.createFixture(groundBox, 0.0f).setUserData("platform");
            groundBox.dispose();
        }

        enemies.add(new SpikeBlock(world, getPlayer(), new Vector2(800, 400)));
        enemies.add(new SpikeBlock(world, getPlayer(), new Vector2(1000, 700)));
        enemies.add(new SpikeBlock(world, getPlayer(), new Vector2(1400, 600)));
        enemies.add(new SpikeBlock(world, getPlayer(), new Vector2(2000, 450)));

    }

    private void loadMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("soundtrack.mp3"));
        music.setVolume(0.6f);
        music.setLooping(true);
    }

    @Override
    public void update() {
        updatePlayerModel();
        updateEnemies();
        updateCollectables();
        updateCamera();

        getTiledMapRenderer().setView(camera); // Game map.
        getTiledMapRenderer().render();

        level.spawnEnemies(player, this);
        world.step(1 / 60f, 6, 2);
        debugRenderer.render(world, camera.combined);
        world.step(1 / 60f, 6, 2);

        checkIfGameOver();
    }

    private void updateCamera() {
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.set(player.getX(), player.getY(), 0);//lock camera to player's position

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

    private void updatePlayerModel() {
        // Let the player knows about the enemies around it.
        player.update(enemies);

        // Attack the enemy if the player is attacking a valid enemy.
        for (AbstractEnemy e : enemies) {
            player.attack(e);
        }

    }

    public void updateEnemies() {
        // Clean up all dead enemies.
        for (AbstractEnemy ae : enemiesToRemove)
            enemies.remove(ae);

        for (AbstractEnemy ae : enemies) {
            ae.update();

            // Dead enemies to be removed.
            if (ae.enemyState instanceof Death)
                enemiesToRemove.add(ae);
        }

        // Add enemies into the game.
        for (int i = 0; i < enemiesToAdd.size(); i++) {
            enemies.add(enemiesToAdd.pop());
        }
    }


    public void updateCollectables() {
        AbstractCollectable remove = null;

        // Iterate through all of the collectables in the scene.
        for (AbstractCollectable ac : level.getCollectables()) {
            // Check if the player have collected it.
            if (ac.checkCollide(getPlayer()) == true) {
                remove = ac;
                break;
            }
        }

        // Remove the collectable in the game.
        if (remove != null) {
            getCollectables().remove(remove);
        }
    }

    @Override
    public AbstractPlayer getPlayer() {
        return player;
    }

    @Override
    public List<AbstractEnemy> getEnemies() {
        return enemies;
    }

    @Override
    public List<AbstractCollectable> getCollectables() {
        return level.getCollectables();
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public TiledMapRenderer getTiledMapRenderer() {
        return level.getTiledMapRenderer();
    }

    public World getWorld() {
        return this.world;
    }

    public AbstractLevel getLevel() {
        return level;
    }

    @Override
    public String getLevelName() {
        return level.getLevelName();
    }

    /**
     * Mutes or plays the soundtrack in the background.
     */
    public void setMuted() {
        if (music.isPlaying()) {
            music.pause();
        } else {
            music.play();
        }
    }

    /**
     * Return true if the music is playing. False otherwise.
     *
     * @return true if music is playing.
     */
    public boolean musicIsPlaying() {
        return music.isPlaying();
    }

    public void setLevel(AbstractLevel level) {
        // Reload all the fields.
        enemies = new ArrayList<>();
        enemiesToRemove = new ArrayList<>();
        enemiesToAdd = new Stack<>();

        world = new World(new Vector2(0, GRAVITY), true);

        this.level = level;
        loadTerrain();

        GameScreen.inputMultiplexer.removeProcessor(player);
        player = EntityFactory.producePlayer(this, new Vector2(50, 500));
        GameScreen.inputMultiplexer.addProcessor(player);
    }

    private void checkIfGameOver() {
        //TODO: Change this once the game over condition is more or less confirmed.
        if (player.getHealth() <= 0) {
            GameScreen.displayGameOverScreen();
            music.dispose();
        }
    }

    /**
     * Used to add to enemies at runtime, to avoid concurrentModification
     */
    public void addEnemy(AbstractEnemy enemy) {
        enemiesToAdd.push(enemy);
    }

    public void save() {
        if (!repoScraper.save(this.player, this.enemies, this.getCollectables())) {
            //TODO: msg dialog: save failed
        }
    }

    public void load() {
        try {
            StateQuery loader = repoScraper.load();
            if (loader == null)
                return; //todo say nothing to load?

            //beautiful waterfall design of method calls into assignments
            PlayerData loadedPlayerData = loader.loadPlayerData();
            List<AbstractEnemy> loadedEnemies = loader.loadEnemies();
            List<AbstractCollectable> loadedCollectables = loader.loadCollectables();


            loadPlayer(loadedPlayerData);
            loadEnemies(loadedEnemies);
            //this.

            //TODO: Jerem + jake, you can replace your data with my loaded data
        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            //TODO: msg dialog: load failed
        }
    }

    private void loadPlayer(PlayerData pdata) {
        if (pdata.isLiving())
            this.player.setPlayerState(AbstractPlayer.player_state.ALIVE);
        else
            this.player.setPlayerState(AbstractPlayer.player_state.DEAD);

        player.setPos(pdata.getPos());
        player.setHealth(pdata.getHealth());
        player.setDamage(pdata.getDamage());
        player.setBoundingBox(pdata.getBoundingBox());

        //TODO set inventory  player.setInventory(pdata.getInventory());

        player.setInAir(pdata.isInAir());
        player.setAttacking(pdata.isAttacking());
        player.setGrounded(pdata.isGrounded());
        player.setMovingLeft(pdata.isMovingLeft());
        player.setMovingRight(pdata.isMovingRight());


        //TODO REPLACE BODY newPlayer.getBody().setTransform();
        //TODO REPLACE FIXTURE
    }

    private void loadEnemies(List<AbstractEnemy> enemiesToLoad) {
        this.enemies.clear();
        enemies.addAll(enemiesToRemove);
        for (AbstractEnemy e : enemiesToLoad) {
            AbstractEnemy newEnemy = EntityFactory.produceEnemy(this,
                    new Vector2(
                            e.getPosition().x * PPM,
                            e.getPosition().y * PPM
                    ),
                    e.type);

            newEnemy.setSpeed(e.getSpeed());
            newEnemy.setDamage(e.getDamage());
            newEnemy.setHealth(e.getHealth());
            newEnemy.setEnemyState(e.enemyState);

            newEnemy.setDrawingWidth(e.getDrawingWidth());
            newEnemy.setDrawingHeight(e.getDrawingHeight());

            //enemies.add(newEnemy);
            enemiesToAdd.push(newEnemy);
        }
    }
}
