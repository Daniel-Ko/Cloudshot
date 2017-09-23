package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import model.being.AbstractEnemy;

import model.being.Player;
import model.mapObject.levels.AbstractLevel;

import java.util.ArrayList;
import java.util.List;


public class GameModel {

    Player player;
    List<AbstractEnemy> enemies;
    AbstractLevel level;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private Array<Rectangle> tiles = new Array<>();

    private float elapsedTime = 0f;

    //Box2D
    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    //End

    public GameModel(AbstractLevel level, OrthographicCamera cam) {
        //Box2D
        this.cam = cam;
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        //End

        this.level = level;
        player = new Player(new Vector2(50,200), 50, 50, 100, 3);
        enemies = new ArrayList<>();
        Gdx.input.setInputProcessor(player);
        generateLevel();

    }

    public void updateState(float elapsedTime){
        this.elapsedTime = elapsedTime;
        updatePlayerModel();
        for(AbstractEnemy ae : enemies){
            ae.update();
        }

        //Box2D
        debugRenderer.render(world, cam.combined);
        world.step(1/60f, 6, 2);
    }

    private void generateLevel(){
        int pixelWidth = 16;
        tiledMap = new TmxMapLoader().load("levels/levelOne.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get("Tile Layer 1");
        for (int i = 0; i < layer.getWidth(); i++) {
            for (int j = 0; j < layer.getHeight(); j++) {
                TiledMapTileLayer.Cell cell = layer.getCell(i,j);
                if(cell == null) continue;
                tiles.add(new Rectangle(i*pixelWidth,j*pixelWidth,pixelWidth,pixelWidth));
            }
        }
    }

    public void addEnemy(AbstractEnemy enemy){
        enemies.add(enemy);
    }

    public void draw(SpriteBatch sb){
        sb.draw(player.getImage().getFrameFromTime(elapsedTime),player.getX(),player.getY());
        for(AbstractEnemy ae : enemies){
            sb.draw(ae.getImage().getFrameFromTime(elapsedTime),ae.getX(),ae.getY());
        }
    }

    private void updatePlayerModel(){
        player.update(tiles);
        for(AbstractEnemy e : enemies){
            player.attack(e);
        }
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public Player getPlayer() {
        return player;
    }

    public List<AbstractEnemy> getEnemies() {
        return enemies;
    }
}
