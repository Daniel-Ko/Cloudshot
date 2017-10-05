package model.mapObject.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import model.GameModel;
import model.GameObjectInterface;
import model.collectable.*;
import model.mapObject.terrain.AbstractTerrain;
import model.mapObject.terrain.Ground;
import model.mapObject.terrain.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tomherdson on 19/09/17.
 * An interface for describing a level.
 * This includes the various ground heights and platforms, as well as potentially including enemy spawns in the future.
 */
public abstract class AbstractLevel {

    protected ArrayList<AbstractTerrain> terrain;
    protected TiledMap tiledMap;
    protected TiledMapRenderer tiledMapRenderer;
    protected Array<Rectangle> tiles = new Array<>();
    protected List<AbstractCollectable> collectables;


    public AbstractLevel() {
        terrain = new ArrayList<>();
        generateLevel();
        loadCollectibles();
    }

    /**
     * Initialises level.
     */
    public void generateLevel(){

        tiledMap = new TmxMapLoader().load("levels/level"+getLevelNumber()+".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/ GameModel.PPM);
        MapLayer layer = tiledMap.getLayers().get("Object Layer 1");
        MapObjects objects = layer.getObjects();
        //add terrain to map.
        for(MapObject o : objects){
            if(!(o instanceof RectangleMapObject)) continue;
            RectangleMapObject r = (RectangleMapObject) o;
            tiles.add(r.getRectangle());
        }

    }

    public void loadCollectibles(){
        MapLayer collectibles = tiledMap.getLayers().get("Collectibles");
        MapObjects collectibleObjs = collectibles.getObjects();
        collectables = new ArrayList<>();
        for(MapObject o : collectibleObjs){
            RectangleMapObject r = (RectangleMapObject) o;
            AbstractCollectable collectable;
            int rand = (int)(Math.random()*10);
            collectable = getCollectableFromRand(rand, r);
            collectables.add(collectable);
        }
    }

    private AbstractCollectable getCollectableFromRand(int rand,RectangleMapObject r){
        AbstractCollectable collectable;
        switch (getSpawnRates().get(rand)) {
            case "DeathPack": {
                collectable = new DeathPack(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                break;
            }
            case "HealthPack": {
                collectable = new HealthPack(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                break;
            }
            case "HeavyAmmoPack": {
                collectable = new HeavyAmmoPack(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);

                break;
            }
            case "LightAmmoPack": {
                collectable = new LightAmmoPack(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                break;
            }
            case "Pistol": {
                collectable = new Pistol(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                break;
            }
            case "Shotgun": {
                collectable = new Shotgun(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                break;
            }
            case "SlowPack": {
                collectable = new SlowPack(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                break;
            }
            default: {
                collectable = new HealthPack(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
            }
        }
        return collectable;
    }

    public abstract String getLevelName();

    public abstract int getLevelNumber();

    public abstract HashMap<Integer, String> getSpawnRates();

    /**
     * A list of all the collectible objects on the map.
     * @return
     */
    public abstract List<AbstractCollectable> getCollectables();




    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public Array<Rectangle> getTiles() {
        return tiles;
    }
}
