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
import model.being.enemies.*;
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
import view.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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



    private Array<Rectangle> terrain;
    
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
    
    
    
    public void setupGame() {
        this.world = new World(new Vector2(0, GRAVITY), true);
        this.debugRenderer = new Box2DDebugRenderer();
        this.enemies = new ArrayList<>();
        this.enemiesToRemove = new ArrayList<>();
        this.enemiesToAdd = new Stack<>();
        this.player = EntityFactory.producePlayer(this, new Vector2(50, 500));
    }
    
    public void setRepoScraper(GameStateTransactionHandler repoScraper) {
        this.repoScraper = repoScraper;
    }
    
    private void reinitGame(AbstractLevel level) {
        enemies = new ArrayList<>();
        enemiesToRemove = new ArrayList<>();
        enemiesToAdd = new Stack<>();
        
        world = new World(new Vector2(0, GRAVITY), true);
        
        this.level = level;
        loadTerrain();
    }
    
    
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
    
    public void loadTerrain() {
        this.terrain = level.getTiles();
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
        enemies.add(EntityFactory.produceEnemy(this,new Vector2(2100,400),AbstractEnemy.entity_type.boss1));
        
    }
    
    public void loadMusic() {
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
        
        level.update(player, this);
        world.step(1 / 30f, 12, 4);
        debugRenderer.render(world, camera.combined);
        
        checkIfGameOver();
        
    }
    
    private void updateCamera() {
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        
        camera.position.set(player.getX(), player.getY(), 0);//lock camera to player's position
        
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
        enemies.removeAll(enemiesToRemove);
        
        for (AbstractEnemy ae : enemies) {
            ae.update();
            
            if(ae.getPosition().y < -40){// kill enemy if it falls off map.
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
    
    public void setLevel(AbstractLevel leve) {
        this.level = leve;
    }

    public Array<Rectangle> getTerrain() {
        return terrain;
    }

    public void setTerrain(Array<Rectangle> terrain) {
        this.terrain = terrain;
    }
    
    public void setNewLevel(AbstractLevel level) {
        // Reload all the fields.
        reinitGame(level);
        loadPlayer(new PlayerData(player)); //load PERSISTENT player data over levels!
        player.setPos(new Vector2(5, 5)); //set to the expected start of the level
    }
    
    private void checkIfGameOver() {
        //TODO: Change this once the game over condition is more or less confirmed.
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
    
    public void save() throws GameStateTransactionHandler.InvalidTransactionException {
        ModelData data = new ModelData();
        data.setPlayer(this.player);
        data.setEnemies(this.enemies);
        data.setLevel(this.level);
        
        //actually save
        try {
            repoScraper.save(data);
        }catch(GameStateTransactionHandler.InvalidTransactionException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }
    
    public void load() throws GameStateTransactionHandler.InvalidTransactionException{
        try {
            ModelData loader = repoScraper.load();
            if (loader == null)
                return; //todo say nothing to load?
            
            //beautiful waterfall design of method calls into assignments
            PlayerData loadedPlayerData = loader.loadPlayerData();
            List<AbstractEnemy> loadedEnemies = loader.loadEnemies();
            
            reinitGame(this.level);
            
            loadPlayer(loadedPlayerData);
            loadEnemies(loadedEnemies);
            loadLevel(loader.loadLevel());
            loadTerrain(); //reset terrain physics for this level
            
            
        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }
    
    private void loadPlayer(PlayerData pdata) {
        GameScreen.inputMultiplexer.removeProcessor(player); //remove the old player from input-handling
    
    
        AbstractPlayer newPlayer = EntityFactory.producePlayer(this,
                new Vector2(
                        //scale player pos back down to the normal world scale
                        pdata.getPos().x * PPM,
                        pdata.getPos().y * PPM
                ));
        
        //reconfirm that player has a new Box2D world (removes existing bodies)
        newPlayer.setWorld(java.util.Optional.of(this.world));
        
        //set all fields
        if (pdata.isLiving())
            newPlayer.setPlayerState(AbstractPlayer.player_state.ALIVE);
        else
            newPlayer.setPlayerState(AbstractPlayer.player_state.DEAD);
        
        newPlayer.setHealth(pdata.getHealth());
        newPlayer.setDamage(pdata.getDamage());
        newPlayer.setBoundingBox(pdata.getBoundingBox());
        
        //set inventory with "deep clone" gunes
        newPlayer.getInventory().clear();
        

        if(!pdata.getInventory().isEmpty()) {
            for (AbstractWeapon invWep : pdata.getInventory()) {
                AbstractWeapon loadedWeapon = CollectableFactory.produceAbstractWeapon(
                        invWep.type,
                        new Vector2(
                                invWep.getX() * PPM,
                                invWep.getY()* PPM
                        ));
                loadedWeapon.setAmmo(invWep.getAmmo());
                loadedWeapon.setPickedUp(invWep.isPickedUp());
        
                newPlayer.getInventory().add(loadedWeapon);
            }
            System.out.println(newPlayer);
        }

        if(pdata.getCurWeapon() != null) {
            // Now set cur weapon with another cloned weapon
            AbstractWeapon curWep = CollectableFactory.produceAbstractWeapon((
                            pdata.getCurWeapon()).type,
                    new Vector2(
                            pdata.getCurWeapon().getX() * PPM,
                            pdata.getCurWeapon().getY() * PPM
                    ));
            curWep.setAmmo(pdata.getCurWeapon().getAmmo());
            curWep.setPickedUp(pdata.getCurWeapon().isPickedUp());
            newPlayer.setCurWeapon(curWep);
        }
        
        newPlayer.setInAir(pdata.isInAir());
        newPlayer.setGrounded(pdata.isGrounded());
        
        newPlayer.setAttacking(pdata.isAttacking());
        
        newPlayer.setMovingLeft(pdata.isMovingLeft());
        newPlayer.setMovingRight(pdata.isMovingRight());
        newPlayer.setLinearVelocity(pdata.getBodyLinearVelocity()); // This sets physics and movement!
        
        this.player = newPlayer;
        
        GameScreen.inputMultiplexer.addProcessor(player); //finally, set the input to recognise this new player
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
    
    private void loadLevel(AbstractLevel levelToLoad) {
        AbstractLevel newLevel = null;
        
        if(levelToLoad.LEVEL_NUM == 1)
            newLevel = new LevelOne();
        else if(levelToLoad.LEVEL_NUM == 2)
            newLevel = new LevelTwo();
        else if(levelToLoad.LEVEL_NUM == 3)
            newLevel = new LevelThree();
        else if(levelToLoad.LEVEL_NUM == 4)
            newLevel = new LevelThree();
        
        
        loadCollectables(newLevel, levelToLoad.getCollectables()); //must load each collectable by itself
        
        newLevel.setPortals(levelToLoad.getPortals());
        newLevel.setSpawnTriggers(levelToLoad.getSpawnTriggers());
        newLevel.setSpawns(levelToLoad.getSpawns());
        
        this.level = newLevel;
    }
    
    private void loadCollectables(AbstractLevel newLevel, List<AbstractCollectable> collectsToLoad) {
        //clear the level's existing collectables
        newLevel.getCollectables().clear();
        
        for(AbstractCollectable c : collectsToLoad) {
            
            if(!c.isPickedUp()) { //check if this buff isn't supposed to be loaded in
                
                Vector2 pos = new Vector2(
                        c.getX()* PPM,
                        c.getY()* PPM
                );
                
                if (c instanceof AbstractBuff) {
                    //create new buff and set the loaded properties in
                    AbstractBuff loadedBuff = CollectableFactory.produceAbstractBuff((
                                    (AbstractBuff) c).type,
                                    pos);
                    loadedBuff.setPickedUp(c.isPickedUp());
    
                    newLevel.getCollectables().add(loadedBuff); //add to level
        
                } else { //else case is if c instanceof AbstractWeapon
        
                    //create new Weapon and set the loaded properties in
                    AbstractWeapon loadedWeapon = CollectableFactory.produceAbstractWeapon((
                                    (AbstractWeapon) c).type,
                                    pos);

                    
                    loadedWeapon.setAmmo(((AbstractWeapon) c).getAmmo());
                    loadedWeapon.setPickedUp(c.isPickedUp());
    
                    newLevel.getCollectables().add(loadedWeapon); //add to level
                }
            }
        }
    }
    
}
