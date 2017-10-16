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
import model.being.enemystates.Death;
import model.being.player.AbstractPlayer;
import model.being.player.PlayerData;
import model.collectable.AbstractBuff;
import model.collectable.AbstractCollectable;
import model.collectable.AbstractWeapon;
import model.collectable.CollectableFactory;
import model.data.GameStateTransactionHandler;
import model.data.ModelData;
import model.mapObject.levels.*;
import view.Assets;
import view.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * GameModel encapsulates all the attributes and components for the model of the game.
 * It stores the level, camera, world, player, collectables etc of the game.
 * Also have the logic to updates the respective components as well as load and save the game.
 * @author Yi Sian Lim & Daniel Ko
 */
public class GameModel implements GameModelInterface {
    
    /**
     * Constants for the game.
     */
    public static final float PPM = 50;
    private static final int GRAVITY = -8;
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



    private Array<Rectangle> terrain;
    private Array<Rectangle> scaledTerrain = new Array<>();

    
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

    /**
     * Set up the model of the game.
     */
    public void setupGame() {
        this.world = new World(new Vector2(0, GRAVITY), true);
        this.debugRenderer = new Box2DDebugRenderer();
        this.enemies = new ArrayList<>();
        this.enemiesToRemove = new ArrayList<>();
        this.enemiesToAdd = new Stack<>();
        this.player = EntityFactory.producePlayer(this, new Vector2(level.getPlayerSpawnPoint()));
    }

    /**
     * Setup the data for the game model for the purpose of load and save.
     * @param repoScraper
     */
    public void setRepoScraper(GameStateTransactionHandler repoScraper) {
        this.repoScraper = repoScraper;
    }

    /**
     * Reinitializes the game, used when the game reloads.
     * @param level
     *          Level to reinitialize based on the load.
     */
    private void reinitGame(AbstractLevel level) {
        this.enemies = new ArrayList<>();
        this.enemiesToRemove = new ArrayList<>();
        this.enemiesToAdd = new Stack<>();
        this.world = new World(new Vector2(0, GRAVITY), true);
        this.level = level;
        loadTerrain();
    }

    /**
     * Set up camera fo the model of the game.
     */
    public void setupCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(
                VIEW_WIDTH / GameModel.PPM,
                ((VIEW_WIDTH * (h / w)) / GameModel.PPM)
        );
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        this.camera.update();
    }

    /**
     * Load the terrain for the map for the model of the game.
     */
    public void loadTerrain() {
        setTerrain(level.getTiles());
        scaledTerrain.clear();
        for (Rectangle r : terrain) {

            // Load the terrains.
            BodyDef terrainPiece = new BodyDef();
            terrainPiece.type = BodyDef.BodyType.StaticBody;
            terrainPiece.position.set(new Vector2((r.x + r.width / 2) / PPM, (r.y + r.height / 2) / PPM));

            // Load the ground body.
            Body groundBody = world.createBody(terrainPiece);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox((r.width / 2) / GameModel.PPM, (r.height / 2) / GameModel.PPM);

            //for getting scaled bounding boxes of terrain to do collisions
            scaledTerrain.add(new Rectangle(r.getX()/PPM,r.getY()/PPM,(r.width/PPM),(r.height/PPM)));

            // User data to tell us what things are colliding.
            groundBody.createFixture(groundBox, 0.0f).setUserData("platform");
            groundBox.dispose();
        }

    }

    /**
     * Load the music for the game.
     */
    public void loadMusic() {
        music = Assets.music;
        music.setVolume(0.6f);
        music.setLooping(true);
    }

    @Override
    public Array<Rectangle> getScaledTerrain() {
        return scaledTerrain;
    }

    @Override
    public void update() {
        // Update the various components of the game.
        updatePlayerModel();
        updateEnemies();
        updateCollectables();
        updateCamera();

        // Update the levels as well as the world.
        level.update(player, this);
        world.step(1 / 30f, 12, 4);
        //debugRenderer.render(world, camera.combined);

        // Check if the game is over.
        checkIfGameOver();
    }

    /**
     * Update the camera such that the camera moves in relative to the player movement.
     */
    private void updateCamera() {
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        // Lock camera to player's position.
        camera.position.set(player.getX(), player.getY(), 0);

        // Clamp the camera so that it does not show anything beyond the level map.
        camera.position.x = MathUtils.clamp(camera.position.x,
                effectiveViewportWidth / 2f,
                level.getLevelDimension().width/PPM - effectiveViewportWidth/2f
        );
        camera.position.y = MathUtils.clamp(camera.position.y,
                effectiveViewportHeight / 2f,
                level.getLevelDimension().height/PPM - effectiveViewportHeight / 2f
        );
        
        camera.update();
    }

    /**
     * Update the player to enable the player's action based on its surrounding and attributes.
     */
    private void updatePlayerModel() {
        // Let the player knows about the enemies around it.
        player.update(enemies);
        
        // Attack the enemy if the player is attacking a valid enemy.
        for (AbstractEnemy e : enemies) {
            player.attack(e);
        }
        
    }

    /**
     * Clean up and update the enemies in the map. This also triggers the AI for the enemies
     * in order for the enemies to target the player if the player is near.
     */
    public void updateEnemies() {
        // Clean up all dead enemies.
        enemies.removeAll(enemiesToRemove);
        
        for (AbstractEnemy ae : enemies) {
            ae.update();

            // Mill enemy if it falls off map.
            if(ae.getPosition().y < -40){
                ae.hit(ae.getHealth());
            }
            
            // Dead enemies to be removed.
            if (ae.enemyState instanceof Death)
                enemiesToRemove.add(ae);
        }
        
        // Add enemies into the game.
        for (int i = 0; i < enemiesToAdd.size(); i++) {
            enemies.add(enemiesToAdd.pop());
        }
    }

    /**
     * Update the collectable(s) in the game. Effectively remove the collectables if it
     * has been picked up.
     */
    public void updateCollectables() {
        AbstractCollectable remove = null;
        
        // Iterate through all of the collectables in the scene.
        for (AbstractCollectable ac : level.getCollectables()) {
            // Check if the player have collected it.
            if (ac.checkCollide(getPlayer())) {
                remove = ac;
                ac.setPickedUp(true);
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
    
    @Override
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

    public void setEnemies(List<AbstractEnemy> list) {
        enemies = list;
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

    /**
     * Set the level for the model of the game
     * @param level Level that we want the current model to be in.
     */
    public void setLevel(AbstractLevel level) {
        this.level = level;
    }

    public void setPlayer(AbstractPlayer player) { this.player = player; }

    public Array<Rectangle> getTerrain() {
        return terrain;
    }

    public void setWorld(World world) { this.world = world; }

    public void setTerrain(Array<Rectangle> terrain) {
        this.terrain = terrain;
    }

    /**
     * Set the new level for the game. Used when the game reloads.
     * @param level
     */
    public void setNewLevel(AbstractLevel level) {
        // Reload all the fields.
        reinitGame(level);
        loadPlayer(new PlayerData(player)); //load PERSISTENT player data over levels!
        player.setPos(new Vector2(5, 5)); //set to the expected start of the level
    }
    
    private void checkIfGameOver() {
        if (player.getHealth() <= 0) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GameScreen.state = GameScreen.State.GAME_OVER;
            music.dispose();
        }
    }
    
    /**
     * Used to add to enemies at runtime, to avoid concurrentModification
     */
    public void addEnemy(AbstractEnemy enemy) {
        enemiesToAdd.push(enemy);
    }



    /*
    //============================================================================
    //===========================LOAD AND SAVE====================================
    //============================================================================
    */

    /**
     * Saves the game by storing all of the data of the game into ModelData data.
     * Serialization logic begins here.
     * @throws GameStateTransactionHandler.InvalidTransactionException
     */
    public void save() throws GameStateTransactionHandler.InvalidTransactionException {

        // Create the data for the current model
        ModelData data = new ModelData();
        data.setPlayer(this.player);
        data.setEnemies(this.enemies);
        data.setLevel(this.level);
        
        // Create a save query
        try {
            repoScraper.save(data);
        }catch(GameStateTransactionHandler.InvalidTransactionException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }

    /**
     * Loads the saved game.
     * @throws GameStateTransactionHandler.InvalidTransactionException
     */
    public void load() throws GameStateTransactionHandler.InvalidTransactionException{
        try {
            ModelData loader = repoScraper.load();
            if (loader == null)
                throw new GameStateTransactionHandler.InvalidTransactionException("Egregious error : Data could not be loaded");

            // Calling to reset game physics (remove all Box2D bodies) and restart all entity Collections
            reinitGame(this.level);

            // Load the components of the game.
            loadPlayer(loader.loadPlayerData());
            loadEnemies(loader.loadEnemies());
            loadLevel(loader.loadLevel());

            // Reset terrain physics for the updated level
            loadTerrain();

        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }

    /**
     * Load the player into the loaded game.
     * @param pdata
     *          Saved player data.
     */
    private void loadPlayer(PlayerData pdata) {
        // Remove the old player from input-handling.
        GameScreen.inputMultiplexer.removeProcessor(player);

        AbstractPlayer newPlayer = EntityFactory.producePlayer(this,
                new Vector2(
                        //scale player pos back down to the normal world scale
                        pdata.getPos().x,
                        pdata.getPos().y
                ));
        
        // Reconfirm that player has a new Box2D world (removes existing bodies).
        newPlayer.setWorld(java.util.Optional.of(this.world));
        
        // Set all fields.
        if (pdata.isLiving())
            newPlayer.setPlayerState(AbstractPlayer.player_state.ALIVE);
        else
            newPlayer.setPlayerState(AbstractPlayer.player_state.DEAD);

        // Initialise the new player with the saved data.
        newPlayer.setHealth(pdata.getHealth());
        newPlayer.setDamage(pdata.getDamage());
        newPlayer.setBoundingBox(pdata.getBoundingBox());
        
        // Set inventory with "deep cloned" weapons.
        newPlayer.getInventory().clear();
        if(!pdata.getInventory().isEmpty()) {
            for (AbstractWeapon invWep : pdata.getInventory()) {
                AbstractWeapon loadedWeapon = CollectableFactory.produceAbstractWeapon(
                        invWep.type,
                        new Vector2(
                                invWep.getX(),
                                invWep.getY()
                        ));
                loadedWeapon.setAmmo(invWep.getAmmo());
                loadedWeapon.setPickedUp(invWep.isPickedUp());
        
                newPlayer.getInventory().add(loadedWeapon);
            }

            newPlayer.setCurWeapon(pdata.getCurWeapon());
        }

        // Initialise the loaded player with the saved data of the physics and movement.
        newPlayer.setInAir(pdata.isInAir());
        newPlayer.setGrounded(pdata.isGrounded());

        newPlayer.setAttacking(pdata.isAttacking());
        newPlayer.setMovingLeft(pdata.isMovingLeft());
        newPlayer.setMovingRight(pdata.isMovingRight());

        newPlayer.setLinearVelocity(pdata.getBodyLinearVelocity());

        // Set the model player with this loaded player
        this.player = newPlayer;

        // Finally, set the input to recognise this new player.
        GameScreen.inputMultiplexer.addProcessor(player);
    }

    /**
     * Load the saved enemies.
     * @param enemiesToLoad
     *           Saved enemies to be loaded into the game.
     */
    private void loadEnemies(List<AbstractEnemy> enemiesToLoad) {

        // Clear the current enemies of the game via the model's own idioms
        this.enemies.clear();
        enemies.addAll(enemiesToRemove);

        // Create a new enemy via factory for each deserialised enemy. Set the newly-generated
        // enemy to have the loaded properties
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

            enemiesToAdd.push(newEnemy); //add this "loaded" enemy to the model
        }
    }

    /**
     * Load the saved level into the game.
     * @param levelToLoad
     *          Level to load into the game.
     */
    private void loadLevel(AbstractLevel levelToLoad) {
        AbstractLevel newLevel = null;

        // In lieu of a level factory, we use the final identifier field for each level to replicate a new level instance
        if(levelToLoad.LEVEL_NUM == 1)
            newLevel = new LevelOne();
        else if(levelToLoad.LEVEL_NUM == 2)
            newLevel = new LevelTwo();
        else if(levelToLoad.LEVEL_NUM == 3)
            newLevel = new LevelThree();
        else if(levelToLoad.LEVEL_NUM == 4)
            newLevel = new LevelThree();
        
        // Set loaded properties into the level buffer
        loadCollectables(newLevel, levelToLoad.getCollectables()); // Each collectable must be loaded individually

        newLevel.setPortals(levelToLoad.getPortals()); // teleporting portals (in or between levels)
        newLevel.setSpawnTriggers(levelToLoad.getSpawnTriggers()); // spawning tiles with their trigger state
        newLevel.setSpawns(levelToLoad.getSpawns()); // Spawns with trigger state

        newLevel.setSpawnPoint(levelToLoad.getPlayerSpawnPoint());
//                new Vector2(
//                        levelToLoad.getPlayerSpawnPoint().x * PPM,
//                        levelToLoad.getPlayerSpawnPoint().y * PPM
//                ));

        newLevel.setEndZone(levelToLoad.getEndZone());
        // Update model's level
        this.level = newLevel;
    }

    /**
     * Load a List of deserialised Collectables into a Level
     * @param newLevel
     *          Load the new level.
     * @param collectsToLoad
     *          Collectables to load into the game.
     */
    private void loadCollectables(AbstractLevel newLevel, List<AbstractCollectable> collectsToLoad) {
        // Clear the level's existing collectables.
        newLevel.getCollectables().clear();
        
        for(AbstractCollectable c : collectsToLoad) {

            // Check if this buff isn't supposed to be loaded in.
            if(!c.isPickedUp()) {
                
                Vector2 pos = new Vector2(
                        c.getX() * PPM,
                        c.getY() * PPM
                );
                
                if (c instanceof AbstractBuff) {
                    // Create new buff and set the loaded properties in.
                    AbstractBuff loadedBuff = CollectableFactory.produceAbstractBuff((
                                    (AbstractBuff) c).type,
                                    pos);
                    loadedBuff.setPickedUp(c.isPickedUp());
    
                    newLevel.getCollectables().add(loadedBuff); //add to level
        
                } else { // Else case is if c instanceof AbstractWeapon.
        
                    // Create new Weapon and set the loaded properties in.
                    AbstractWeapon loadedWeapon = CollectableFactory.produceAbstractWeapon((
                                    (AbstractWeapon) c).type,
                                    pos);


                    loadedWeapon.setAmmo(((AbstractWeapon) c).getAmmo()); // weapons have ammo field to remember
                    loadedWeapon.setPickedUp(c.isPickedUp());
    
                    newLevel.getCollectables().add(loadedWeapon); // Add to level.
                }
            }
        }
    }
}
