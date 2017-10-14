package model.mapObject.levels;

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
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.collectable.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Library for creating levels by loading TMX files into java objects that can easily be tested for collisions.
 */
public abstract class AbstractLevel {


    protected TiledMap tiledMap;
    protected TiledMapRenderer tiledMapRenderer;
    protected List<AbstractCollectable> collectables;

    protected Array<Rectangle> tiles = new Array<>();
    protected Rectangle endZone;
    protected List<Rectangle> spawnTriggers;
    protected List<Spawn> spawns;
    protected Array<Portal> portals;
    protected Array<Rectangle> hurtyTiles;


    /**
     * Constructs the level by loading the tmx file, and filling the fields with various rectangles to test for collisions/intersections.
     */
    public AbstractLevel() {
        tiledMap = new TmxMapLoader().load("levels/level" + getLevelNumber() + ".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / GameModel.PPM);

        // load information from .tmx file.
        generateCollidablePolygons();
        loadCollectibles();
        loadEndPoint();
        loadSpawnTriggerPoints();
        loadSpawns();
        loadHurtyTiles();
        loadPortals();
    }

    /**
     * Loads the 'Collisions' layer of TMX file.
     * Collidable objects are stored in Rectangles.
     */
    public void generateCollidablePolygons() {
        MapLayer layer = tiledMap.getLayers().get("Collisions");
        MapObjects objects = layer.getObjects();
        //add terrain to map.
        for (MapObject o : objects) {
            if (!(o instanceof RectangleMapObject)) continue;
            RectangleMapObject r = (RectangleMapObject) o;
            tiles.add(r.getRectangle());
        }
    }

    /**
     * Loads the 'Collectibles' layer of the TMX file. This layer contains the spawn points of the various collectibles.
     * Collectibles are stored as 'AbstractCollectable' objects.
     */
    public void loadCollectibles() {
        MapLayer collectibles = tiledMap.getLayers().get("Collectibles");
        MapObjects collectibleObjs = collectibles.getObjects();
        collectables = new ArrayList<>();
        for (MapObject o : collectibleObjs) {
            RectangleMapObject r = (RectangleMapObject) o;
            AbstractCollectable collectable;
            if (r.getProperties().get("Type") != null) {//Check for specifically named Collectible objects in Collectibles layer.
                String s = r.getProperties().get("Type").toString();
                if(s.equals("Sniper")){
                    collectable = new Sniper(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                }
                else if(s.equals("SemiAuto")){
                    collectable = new SemiAuto(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                }
                else
                {
                    collectable = new SemiAuto(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
                }
            }
            else {//If the collectible is not specified, it is random.
                int rand = (int) (Math.random() * 10);
                collectable = getCollectableFromRand(rand, r);
            }
            collectables.add(collectable);
        }
    }

    /**
     * Creates an AbstractCollectable, based on a random value and a RectangleMapObject (to define x/y coordinates).
     * @param rand random calue between 0-10 inclusive.
     * @param r RectangleMapObject, defines the x/y coordinates and width and height of the AbstractCollectable.
     * @return appropriate AbstractCollectable decided by random value r. Uses HashMap in Level implementation to decide.
     */
    private AbstractCollectable getCollectableFromRand(int rand, RectangleMapObject r) {
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
        MapLayer spawns = tiledMap.getLayers().get("SpawnTrigger");
        for (MapObject r : spawns.getObjects()) {
            RectangleMapObject rmo = (RectangleMapObject) r;
            Rectangle rect = rmo.getRectangle();
            spawnTriggers.add(new Rectangle(rect.x / GameModel.PPM, rect.y / GameModel.PPM, rect.getWidth() / GameModel.PPM, rect.getHeight() / GameModel.PPM));
        }
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
        MapLayer layer = tiledMap.getLayers().get("Spawn Location");
        for (MapObject r : layer.getObjects()) {
            RectangleMapObject rmo = (RectangleMapObject) r;
            Rectangle rect = rmo.getRectangle();
            AbstractEnemy.entity_type enemyType;

            String type = (String) rmo.getProperties().get("Type");
            if (type.equals("Rogue")) {
                enemyType = AbstractEnemy.entity_type.rogue;
            } else if (type.equals("Shooter")) {
                enemyType = AbstractEnemy.entity_type.archer;
            } else {
                enemyType = AbstractEnemy.entity_type.slime;
            }
            spawns.add(new Spawn(enemyType, (int) rmo.getProperties().get("Number"), rect.x, rect.y));
        }
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
        portals = new Array<>();
        MapLayer ml = tiledMap.getLayers().get("Portals");
        for (int i = 0; i < ml.getObjects().getCount(); i += 2) {
            RectangleMapObject rmo1 = (RectangleMapObject) ml.getObjects().get(i);
            RectangleMapObject rmo2 = (RectangleMapObject) ml.getObjects().get(i + 1);
            portals.add(new Portal(new Rectangle(rmo1.getRectangle().x / GameModel.PPM, rmo1.getRectangle().y / GameModel.PPM, rmo1.getRectangle().getWidth() / GameModel.PPM, rmo1.getRectangle().getHeight() / GameModel.PPM),
                    new Rectangle(rmo2.getRectangle().x / GameModel.PPM, rmo2.getRectangle().y / GameModel.PPM, rmo2.getRectangle().getWidth() / GameModel.PPM, rmo2.getRectangle().getHeight() / GameModel.PPM)));
        }
    }

    /**
     * Performs all checks on the level. For example: checks to see if player has fallen of map, checks player has won, checks if
     * enemies should be spawned, checks if player should be injured by spike tiles, checks if player should be teleported by portal.
     * @param p the player
     * @param gm the game model
     */
    public void update(AbstractPlayer p, GameModel gm) {
        if (p.getPos().y < -40) {//falling off map kills, but with a bit of delay (can fall off screen for a few seconds)
            p.hit(p.getHealth());
        }

        if (hasPlayerWon(p)) {
            gm.setNewLevel(this.getNextLevel());
        }

        for (int i = 0; i < spawnTriggers.size(); i++) {//check if walking over spawn trigger.
            if (spawnTriggers.get(i).contains(p.getPos())) {
                spawns.get(i).spawn(gm.getEnemies(), gm);
                spawnTriggers.remove(i);
                spawns.remove(i);
            }
        }
        for (int i = 0; i < hurtyTiles.size; i++) {//spike tiles
            Rectangle rect = hurtyTiles.get(i);
            if (rect.contains(p.getPos())) {//affect player
                p.hit(10);
                p.applyKnockBack(AbstractPlayer.knock_back.NORTH);
            }
            for (AbstractEnemy ae : gm.getEnemies()) {//affect enemies
                if (rect.contains(ae.getPosition())) {
                    ae.hit(10);
                }
            }
        }
        for (int i = 0; i < portals.size; i++) {
            if (!portals.get(i).isActive()) continue;
            Rectangle rect = portals.get(i).getEntry();
            if (rect.contains(p.getPos())) {
                portals.get(i).setActive(false);
                p.setPos(new Vector2(portals.get(i).getExit().x, portals.get(i).getExit().y));
            }
        }
    }
    //Abstract methods that must be implemented by the Level.

    /**
     * Specifies the name of the level, for display purposes.
     * @return name of the level.
     */
    public abstract String getLevelName();

    /**
     * Specifies level number, used for picking which TMX file to load
     * @return level number.
     */
    public abstract int getLevelNumber();

    /**
     * The next level in the linked list.
     * Used to easily move to the next level.
     * @return next level in the game.
     */
    public abstract AbstractLevel getNextLevel();

    /**
     * HashMap containing the spawn rates of each collectible.
     * @return HashMap with spawnrates.
     */
    public abstract HashMap<Integer, String> getSpawnRates();

    /**
     * @return Collectibles on the map.
     */
    public abstract List<AbstractCollectable> getCollectibles();

    public  Dimension getLevelDimension(){

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

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public Array<Rectangle> getTiles() {
        return tiles;
    }
}
