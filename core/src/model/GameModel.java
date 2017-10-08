package model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import model.being.*;

import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.data.StateQuery;
import model.mapObject.levels.AbstractLevel;
import model.projectile.BulletImpl;
import view.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class GameModel {
    
    AbstractPlayer player;
    List<AbstractEnemy> enemies;

    Stack<AbstractEnemy> enemiesToAdd;
    List<AbstractEnemy> enemiesToRemove;
    AbstractLevel level;
    private GameStateTransactionHandler repoScraper;

    private float elapsedTime = 0f;

    //Box2D
    public static final float PPM = 50;
    private int GRAVITY = -8;
    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    //End

    public GameModel(AbstractLevel level, OrthographicCamera cam) {
        //Box2D
        this.cam = cam;
        world = new World(new Vector2(0, GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();

        enemies = new ArrayList<>();
        enemiesToRemove = new ArrayList<>();
        enemiesToAdd = new Stack<>();
        this.level = level;
        //Player setup
        player = new Player();
        player.initBox2D(world,new Vector2(50,500));
        //end

        Array<Rectangle> terrain = level.getTiles();
        for(Rectangle r : terrain){
            BodyDef terrainPiece = new BodyDef();
            terrainPiece.type = BodyDef.BodyType.StaticBody;
            terrainPiece.position.set(new Vector2((r.x+r.width/2)/PPM,(r.y+r.height/2)/PPM));
            //enemies.add(new Slime2(this, new Vector2(r.x,r.y)));
            Body groundBody = world.createBody(terrainPiece);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox((r.width/2)/GameModel.PPM,(r.height/2)/GameModel.PPM);
            //userdata to tell us which things are colliding
            groundBody.createFixture(groundBox,0.0f).setUserData("platform");
            groundBox.dispose();
        }
        //boss
       // enemies.add(new BossTwo(this,new Vector2(300,500)));
        enemies.add(new Rogue(this,new Vector2(300,800)));
        enemies.add(new Rogue(this,new Vector2(700,800)));
        enemies.add(new ShootingEnemy(this,new Vector2(400,900)));
        enemies.add(new ShootingEnemy(this,new Vector2(800,900)));



        //enemies.add(new Slime(this,new Vector2(300,500)));

        //ground.
        //End


        //enemies.add(new Slime(20,player, new Vector2(70,500),world));
        GameScreen.inputMultiplexer.addProcessor(player);

        //generateCollidablePolygons();

        repoScraper = new GameStateTransactionHandler();
    }

    public void updateState(float elapsedTime){
        this.elapsedTime = elapsedTime;
        updatePlayerModel();
        updateEnemies();
        updateCollectables();
        level.spawnEnemies(player, this);
        world.step(1/60f,6,2);

        checkIfGameOver();
    }

    private void checkIfGameOver() {
        //TODO: Change this once the game over condition is more or less confirmed.
        if(player.getHealth() <= 0){
            GameScreen.displayGameOverScreen();
        }
    }


    public void updateEnemies(){
        //First Clean up all dead enemies
        for(AbstractEnemy ae:enemiesToRemove)
            enemies.remove(ae);
        for(AbstractEnemy ae : enemies){
            ae.update();
            //added dead enemies to be removed
            if(ae.enemyState instanceof Death) enemiesToRemove.add(ae);
        }
        for(int i = 0;i< enemiesToAdd.size();i++){
            enemies.add(enemiesToAdd.pop());
        }
    }


    public void updateCollectables() {
    	AbstractCollectable remove = null;
        for(AbstractCollectable ac : level.getCollectables()){
           if(ac.checkCollide(getPlayer()) == true){
        	   remove = ac;
        	   break;
           } 
        }
        if(remove != null){level.getCollectables().remove(remove);} 
	}

    /**
     * Used to add to enemies at runtime, to avoid concurrentModification
     * */
	public void addEnemy(AbstractEnemy enemy){
        enemiesToAdd.push(enemy);
    }

    public void draw(SpriteBatch sb){
	    Player play = (Player) player;
	    
        sb.draw(player.getImage().getFrameFromTime(elapsedTime),player.getX()-0.9f,player.getY()-0.6f,1.80f,1.80f);
        //drawing player bullets
        for(BulletImpl b : play.getBullets()){
            sb.draw(play.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime),b.getX()-0.25f,b.getY()-0.25f,0.5f,0.5f);
        }
        for(AbstractEnemy ae : enemies){
            if(ae.getImage() == null)continue;
            sb.draw(ae.getImage().getFrameFromTime(elapsedTime),ae.getX()-ae.getDrawingWidth()/2,ae.getY()-ae.getDrawingHeight()/4,ae.getDrawingWidth(),ae.getDrawingHeight());
            if(ae instanceof ShootingEnemy){
                ShootingEnemy s = (ShootingEnemy)ae;
                for(BulletImpl b : s.bullets)
                    sb.draw(s.bulletSprite.getFrameFromTime(elapsedTime),b.getX()-0.25f,b.getY()-0.25f,0.5f,0.5f);
            }
            if(ae instanceof BossOne){
                BossOne s = (BossOne)ae;
                for(BulletImpl b : s.bullets)
                    sb.draw(play.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime),b.getX()-0.25f,b.getY()-0.25f,0.5f,0.5f);
            }
        }
        for(AbstractCollectable ac : level.getCollectables()){
            sb.draw(ac.getImage().getFrameFromTime(elapsedTime),ac.getX(),ac.getY(),ac.getBoundingBox().getWidth(),ac.getBoundingBox().getHeight());
        }

        //Box2D
        //debugRenderer.render(world, cam.combined);
        world.step(1/60f, 6, 2);

    }

    private void updatePlayerModel(){
        player.update(enemies);
        for(AbstractEnemy e : enemies){
            player.attack(e);
        }
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return level.getTiledMapRenderer();
    }


    public AbstractPlayer getPlayer() {
        return player;
    }

    public List<AbstractEnemy> getEnemies() {
        return enemies;
    }

    public List<AbstractCollectable> getCollectables() {
        return level.getCollectables();
    }

    public OrthographicCamera getCamera(){ return cam;}

    public World getWorld(){ return this.world; }

    public void save() {
        if(!repoScraper.save(this)) {
            //TODO: msg dialog: save failed
        }
    }

    public void load() {
        try {
            StateQuery loader = repoScraper.load();

            //beautiful waterfall design of method calls into assignments
            PlayerData loadedPlayerData = loader.loadPlayerData();
            List<AbstractEnemy> loadedEnemies = loader.loadEnemies();
            List<AbstractCollectable> loadedCollectables = loader.loadCollectables();
            
            
            loadPlayer(loadedPlayerData);
            this.enemies = loadedEnemies;
            //this.

            //TODO: Jerem + jake, you can replace your data with my loaded data
        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            //TODO: msg dialog: load failed
        }
    }

    public AbstractLevel getLevel() {
        return level;
    }
    
    private void loadPlayer(PlayerData pdata) {
        AbstractPlayer newPlayer = new Player();
        
        if(pdata.isLiving())
            newPlayer.setPlayerState(AbstractPlayer.player_state.ALIVE);
        else
            newPlayer.setPlayerState(AbstractPlayer.player_state.DEAD);
        
        newPlayer.setHealth(pdata.getHealth());
        newPlayer.setDamage(pdata.getDamage());
        newPlayer.setBoundingBox(pdata.getBoundingBox());
        
        //TODO set inventory  newPlayer.setInventory(pdata.getInventory());
    
        newPlayer.setInAir(pdata.isInAir());
        newPlayer.setAttacking(pdata.isAttacking());
        newPlayer.setGrounded(pdata.isGrounded());
        newPlayer.setMovingLeft(pdata.isMovingLeft());
        newPlayer.setMovingRight(pdata.isMovingRight());

        //TODO REPLACE BODY newPlayer.getBody().setTransform();
        //TODO REPLACE FIXTURE
        
        this.player = newPlayer;
    }
}
