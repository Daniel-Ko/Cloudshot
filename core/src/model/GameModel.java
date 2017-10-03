package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;
import model.being.*;

import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.data.StateQuery;
import model.mapObject.levels.AbstractLevel;
import model.projectile.BulletImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class GameModel {

    Player player;
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
        player = new Player(this,new Vector2(50,500));

        Array<Rectangle> terrain = level.getTiles();
        for(Rectangle r : terrain){
            BodyDef terrainPiece = new BodyDef();
            terrainPiece.type = BodyDef.BodyType.StaticBody;
            terrainPiece.position.set(new Vector2((r.x+r.width/2)/PPM,(r.y+r.height/2)/PPM));
            enemies.add(new MeleeEnemy(this, new Vector2(r.x,r.y)));
            Body groundBody = world.createBody(terrainPiece);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox((r.width/2)/GameModel.PPM,(r.height/2)/GameModel.PPM);
            //userdata to tell us which things are colliding
            groundBody.createFixture(groundBox,0.0f).setUserData("platform");
            groundBox.dispose();
        }
        //boss
        enemies.add(new BossTwo(this,new Vector2(300,500)));
        //enemies.add(new BossOne(this,new Vector2(300,500)));

        //enemies.add(new MeleeEnemy(this,new Vector2(300,500)));

        //ground.
        //End


        //enemies.add(new MeleeEnemy(20,player, new Vector2(70,500),world));
        Gdx.input.setInputProcessor(player);

        //generateLevel();

        repoScraper = new GameStateTransactionHandler();
    }

    public void updateState(float elapsedTime){
        this.elapsedTime = elapsedTime;
        updatePlayerModel();
        updateEnemies();
        updateCollectables();
        world.step(1/60f,6,2);
    }
    public void updateEnemies(){
        //First Clean up all dead enemies
        for(AbstractEnemy ae:enemiesToRemove)
            enemies.remove(ae);
        for(AbstractEnemy ae : enemies){
            ae.update();
            //added dead enemies to be removed
            if(ae.getState() == AbstractEnemy.enemy_state.EDEAD)enemiesToRemove.add(ae);
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
        sb.draw(player.getImage().getFrameFromTime(elapsedTime),player.getX()-0.9f,player.getY()-0.6f,1.80f,1.80f);
        //drawing player bullets
        for(BulletImpl b : player.getBullets()){
            sb.draw(player.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime),b.getX()-0.25f,b.getY()-0.25f,0.5f,0.5f);
        }
        for(AbstractEnemy ae : enemies){
            sb.draw(ae.getImage().getFrameFromTime(elapsedTime),ae.getX()-ae.getDrawingWidth()/2,ae.getY()-ae.getDrawingHeight()/4,ae.getDrawingWidth(),ae.getDrawingHeight());
            if(ae instanceof ShootingEnemy){
                ShootingEnemy s = (ShootingEnemy)ae;
                for(BulletImpl b : s.bullets)
                    sb.draw(player.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime),b.getX()-0.25f,b.getY()-0.25f,0.5f,0.5f);
            }
            if(ae instanceof BossOne){
                BossOne s = (BossOne)ae;
                for(BulletImpl b : s.bullets)
                    sb.draw(player.getCurWeapon().getBulletImage().getFrameFromTime(elapsedTime),b.getX()-0.25f,b.getY()-0.25f,0.5f,0.5f);
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
        player.update(level.getTiles());
        for(AbstractEnemy e : enemies){
            player.attack(e);
        }
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return level.getTiledMapRenderer();
    }


    public Player getPlayer() {
        return player;
    }

    public List<AbstractEnemy> getEnemies() {
        return enemies;
    }

    public List<AbstractCollectable> getCollectables() {
        return level.getCollectables();
    }

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
            AbstractPlayer loadedPlayer = loader.loadPlayer();
            List<AbstractEnemy> loadedEnemies = loader.loadEnemies();
            List<AbstractCollectable> loadedCollectables = loader.loadCollectables();
            
            
            this.player = loadedPlayer;
            this.enemies = loadedEnemies;
            this.

            //TODO: Jerem + jake, you can replace your data with my loaded data
        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            //TODO: msg dialog: load failed
        }
    }

    public AbstractLevel getLevel() {
        return level;
    }
}
