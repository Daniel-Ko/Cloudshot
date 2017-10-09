package model.mapObject.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import model.GameModel;
import model.being.AbstractEnemy;
import model.being.AbstractPlayer;
import model.being.Slime2;
import model.collectable.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tomherdson on 19/09/17.
 * An interface for describing a level.
 * This includes the various ground heights and platforms, as well as potentially including enemy spawns in the future.
 */
public abstract class AbstractLevel {


    protected TiledMap tiledMap;
    protected TiledMapRenderer tiledMapRenderer;
    protected Array<Rectangle> tiles = new Array<>();
    protected List<AbstractCollectable> collectables;

    protected Rectangle endZone;
    protected  Array<Rectangle> spawnTriggers;
    protected Array<Spawn> spawns;
    protected Array<Portal> portals;
    protected Array<Rectangle> hurtyTiles;



    public AbstractLevel() {

        //load information from .tmx file.
        generateCollidablePolygons();
        loadCollectibles();
        loadEndPoint();
        loadSpawnTriggerPoints();
        loadSpawns();
        loadHurtyTiles();
        loadPortals();
    }

    /**
     * Initialises level.
     */
    public void generateCollidablePolygons(){

        tiledMap = new TmxMapLoader().load("levels/level"+getLevelNumber()+".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/ GameModel.PPM);
        MapLayer layer = tiledMap.getLayers().get("Collisions");
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
            if(r.getProperties().get("Type") != null){
                collectable = new Pistol(new Vector2(r.getRectangle().x, r.getRectangle().y), r.getRectangle().width, r.getRectangle().height);
            }
            else{
                int rand = (int)(Math.random()*10);
                collectable = getCollectableFromRand(rand, r);
            }

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

    public void loadEndPoint(){
        MapLayer endLayer = tiledMap.getLayers().get("End Zone");
        RectangleMapObject r = (RectangleMapObject)endLayer.getObjects().get(0);//assuming only one endzone
        endZone = new Rectangle(r.getRectangle().x/GameModel.PPM, r.getRectangle().y/GameModel.PPM, r.getRectangle().width/GameModel.PPM,r.getRectangle().height/GameModel.PPM);
    }

    public boolean hasPlayerWon(AbstractPlayer p){
        if(endZone.contains(p.getPos())){
            return true;
        }
        return false;
    }

    public void loadSpawnTriggerPoints(){
        spawnTriggers = new Array<>();
        MapLayer spawns = tiledMap.getLayers().get("SpawnTrigger");
        for(MapObject r : spawns.getObjects()){
            RectangleMapObject rmo = (RectangleMapObject) r;
            Rectangle rect = rmo.getRectangle();
            spawnTriggers.add(new Rectangle(rect.x/GameModel.PPM, rect.y/GameModel.PPM, rect.getWidth()/GameModel.PPM, rect.getHeight()/GameModel.PPM));
        }
    }

    public void loadSpawns(){
       // spawns = new Array<>();
        spawns = new Array<>();
        MapLayer layer = tiledMap.getLayers().get("Spawn Location");
        for(MapObject r : layer.getObjects()){
            RectangleMapObject rmo = (RectangleMapObject) r;
            Rectangle rect = rmo.getRectangle();
           //spawns.add(new Rectangle(rect.x/GameModel.PPM, rect.y/GameModel.PPM, rect.getWidth()/GameModel.PPM, rect.getHeight()/GameModel.PPM));
            Spawn.EnemyType enemyType;

            String type = (String)rmo.getProperties().get("Type");
            if(type.equals("Rogue")){
                enemyType = Spawn.EnemyType.ROGUE;
            }
            else if(type.equals("Shooter")){
                enemyType = Spawn.EnemyType.SHOOTER;
            }
            else
            {
                enemyType = Spawn.EnemyType.SLIME;
            }
            spawns.add(new Spawn(enemyType, (int)rmo.getProperties().get("Number"), rect.x, rect.y));
        }
    }

    public void spawnEnemies(AbstractPlayer p, GameModel gm){
        if(p.getPos().y < 0){//falling off map kills
            p.hit(p.getHealth());
        }
        if(endZone.contains(p.getPos())){
            gm.setLevel(new LevelTwo());
            System.out.println("next level");
        }
        for(int i = 0; i < spawnTriggers.size; i++){
            if(spawnTriggers.get(i).contains(p.getPos())){
                //currently just spawn slime but this will be changed.

                //gm.getEnemies().add(new Slime2(gm,new Vector2(spawns.get(i).getX(), spawns.get(i).getY())));
                spawns.get(i).spawn(gm.getEnemies(),gm);
                spawnTriggers.removeIndex(i);
                spawns.removeIndex(i);
            }
        }
        for(int i = 0; i < hurtyTiles.size; i++){//spike tiles
            Rectangle rect = hurtyTiles.get(i);
            if(rect.contains(p.getPos())){//affect player
                p.hit(10);
                p.applyKnockBack(AbstractPlayer.knock_back.NORTH);
            }
            for(AbstractEnemy ae : gm.getEnemies()){//affect enemies
                if(rect.contains(ae.getPosition())){
                    ae.hit(10);
                }
            }
        }

        for(int i = 0; i < portals.size; i++){
            if(portals.get(i).isActive() == false) continue;
            Rectangle rect = portals.get(i).getEntry();
            if(rect.contains(p.getPos())){
                portals.get(i).setActive(false);
                p.setPos(new Vector2(portals.get(i).getExit().x,portals.get(i).getExit().y));
                //p.setPos(new Vector2(1.0f,4.59999f));
            }

        }
    }

    public void loadHurtyTiles(){
        hurtyTiles = new Array<>();
        MapLayer mp = tiledMap.getLayers().get("Hurty Tiles");
        for(MapObject mo : mp.getObjects()){
            RectangleMapObject rmo = (RectangleMapObject) mo;
            Rectangle rect = rmo.getRectangle();
            hurtyTiles.add(new Rectangle(rect.x/GameModel.PPM, rect.y/GameModel.PPM, rect.getWidth()/GameModel.PPM, rect.getHeight()/GameModel.PPM));
        }
    }

    public void loadPortals(){
        portals = new Array<>();
        MapLayer ml = tiledMap.getLayers().get("Portals");
        for(int i = 0; i < ml.getObjects().getCount(); i+=2){
            RectangleMapObject rmo1 = (RectangleMapObject) ml.getObjects().get(i);
            RectangleMapObject rmo2 = (RectangleMapObject) ml.getObjects().get(i+1);
            portals.add(new Portal(new Rectangle(rmo1.getRectangle().x/GameModel.PPM,rmo1.getRectangle().y/GameModel.PPM,rmo1.getRectangle().getWidth()/GameModel.PPM,rmo1.getRectangle().getHeight()/GameModel.PPM),
                    new Rectangle(rmo2.getRectangle().x/GameModel.PPM,rmo2.getRectangle().y/GameModel.PPM,rmo2.getRectangle().getWidth()/GameModel.PPM,rmo2.getRectangle().getHeight()/GameModel.PPM)));
        }
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
