package mock;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import model.GameModel;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.collectable.*;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.Portal;
import model.mapObject.levels.Spawn;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dan Ko on 10/5/2017.
 */
public class MockLevel implements Serializable{
    public static final float COLLECTABLE_SIZE = 31.4f;
    public final int LEVEL_NUM;

    protected transient TiledMap tiledMap;
    protected transient TiledMapRenderer tiledMapRenderer;
    protected transient Array<Rectangle> tiles = new Array<>();

    protected List<AbstractCollectable> collectables;
    protected Rectangle endZone;
    protected List<Rectangle> spawnTriggers;
    protected List<Spawn> spawns;
    protected List<Portal> portals;
    protected Array<Rectangle> hurtyTiles;
    protected Vector2 spawnPoint;




    public MockLevel() {
        LEVEL_NUM = 1;
    }


    public void loadPlayerSpawn(){
        MapLayer spawn = tiledMap.getLayers().get("Player Spawn");
        MapObject spawnObj = spawn.getObjects().get(0);
        RectangleMapObject rect = (RectangleMapObject) spawnObj;
        Vector2 loc = new Vector2(rect.getRectangle().x,rect.getRectangle().y);
        spawnPoint = loc;
    }

    /**
     * Loads 3 predefined weapons
     */
    public void loadCollectables() {
        collectables = new ArrayList<>();

        this.collectables.add(new Sniper(new Vector2(0, 0), COLLECTABLE_SIZE, COLLECTABLE_SIZE));
        this.collectables.add(new SemiAuto(new Vector2(10, 10), COLLECTABLE_SIZE, COLLECTABLE_SIZE));
        this.collectables.add(new HealthPack(new Vector2(10, 10), COLLECTABLE_SIZE, COLLECTABLE_SIZE));
    }


    /**
     * Gets the EndZone from the 'End Zone' layer of TMX file.
     * Stores it in a Rectangle, so it can be decided if player has won level.
     */
    public void loadEndPoint() {
        MapLayer endLayer = tiledMap.getLayers().get("End Zone");
        RectangleMapObject r = (RectangleMapObject) endLayer.getObjects().get(0);//assuming only one endzone
        endZone = new Rectangle(r.getRectangle().x / GameModel.PPM, r.getRectangle().y / GameModel.PPM, r.getRectangle().width / GameModel.PPM, r.getRectangle().height / GameModel.PPM);
    }

    /**
     * Checks if the endZone rectangle contains the player. If so player has won level.
     * @param p the player to be checked if they have won.
     * @return true if player has won level, false otherwise.
     */
    public boolean hasPlayerWon(AbstractPlayer p) {
        return endZone.contains(p.getPos());
    }

    /**
     * Loads spawn triggers from 'SpawnTrigger' layer in TMX file.
     * SpawnTriggers are stored in Rectangle objects. They can be used to see if enemies should be spawned if player is within rectangle.
     */
    public void loadSpawnTriggerPoints() {
        spawnTriggers = new ArrayList<>();
        spawnTriggers.add(new Rectangle(0, 0, 20, 20));
    }

    /**
     * Loads spawn points from TMX file.
     * Specific enemy type can be specified in TMX file.
     * Multiple enemies can be spawned at one location, specified by 'Number' property in file.
     * Spawns are stored in a 'Spawn' class.
     */
    public void loadSpawns() {
        // spawns = new Array<>();
        spawns = new ArrayList<>();
        spawns.add(new Spawn(AbstractEnemy.entity_type.archer, LEVEL_NUM, 0, 0));
    }

    /**
     * Hurty Tiles refer to spikes on the map, which injure the player when stepped upon.
     * This method loads the 'Hurty Tiles' layer of the TMX file, and turns it into rectangle objects.
     */
    public void loadHurtyTiles() {
        hurtyTiles = new Array<>();
        MapLayer mp = tiledMap.getLayers().get("Hurty Tiles");
        for (MapObject mo : mp.getObjects()) {
            RectangleMapObject rmo = (RectangleMapObject) mo;
            Rectangle rect = rmo.getRectangle();
            hurtyTiles.add(new Rectangle(rect.x / GameModel.PPM, rect.y / GameModel.PPM, rect.getWidth() / GameModel.PPM, rect.getHeight() / GameModel.PPM));
        }
    }

    /**
     * Loads the 'Portals' layer of the TMX file.
     * Portals are stored in Portal objects, which defined the start and end point of the portal, and if it has already been used.
     * Portals are stored in pairs in the TMX file, the object i and i+1 are paired as an entry and end point in the layer.
     */
    public void loadPortals() {
        portals = new ArrayList<>();
    }


    //Abstract methods that must be implemented by the Level.

    /**
     * Specifies the name of the level, for display purposes.
     * @return name of the level.
     */
    public String getLevelName() {
        return "Test";
    }

    /**
     * Specifies level number, used for picking which TMX file to load
     * @return level number.
     */
    public int getLevelNumber() {
        return this.LEVEL_NUM;
    }



    /**
     * @return Collectables on the map.
     */
    public List<AbstractCollectable> getCollectables() {
        return collectables;
    }

    public Dimension getLevelDimension(){

        MapProperties properties = tiledMap.getProperties();
        int mapWidth = properties.get("width", Integer.class);
        int mapHeight = properties.get("height", Integer.class);
        int tilePixelWidth = properties.get("tilewidth", Integer.class);
        int tilePixelHeight = properties.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        return new Dimension(mapPixelWidth, mapPixelHeight);

    }

    //Getters and setters
    public List<Rectangle> getSpawnTriggers() {
        return spawnTriggers;
    }

    public List<Spawn> getSpawns() {
        return spawns;
    }

    public void setSpawnTriggers(List<Rectangle> spawnTriggers) {
        this.spawnTriggers = spawnTriggers;
    }

    public void setSpawns(List<Spawn> spawns) {
        this.spawns = spawns;
    }


    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public Array<Rectangle> getTiles() {
        return tiles;
    }

    public void setCollectables(List<AbstractCollectable> collects) {
        collectables = collects;
    }

    public void setEndZone(Rectangle endZone) {
        this.endZone = endZone;
    }

    public void setPortals(List<Portal> portals) {
        this.portals = portals;
    }

    public List<Portal> getPortals() {
        return portals;
    }

    public Vector2 getPlayerSpawnPoint() { return spawnPoint; }
}
