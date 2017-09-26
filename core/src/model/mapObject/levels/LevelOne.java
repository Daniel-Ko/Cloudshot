package model.mapObject.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import model.collectable.AbstractCollectable;
import model.collectable.DeathPack;
import model.collectable.HealthPack;
import model.mapObject.terrain.AbstractTerrain;
import model.mapObject.terrain.Ground;
import model.mapObject.terrain.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomherdson on 19/09/17.
 * First level.
 */
public class LevelOne extends AbstractLevel {

    public LevelOne() {
        super();
    }

    List<AbstractCollectable> collectables;
    @Override
    public String getLevelName() {
        return "Welcome to Cloudshot";
    }

    @Override
    public int getLevelNumber() {
        return 1;
    }

    @Override
    public List<AbstractCollectable> getCollectables() {
        return collectables;
    }

    public void generateLevel(){
        /*int pixelWidth = 16;
        tiledMap = new TmxMapLoader().load("levels/levelOne.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get("Tile Layer 1");
        for (int i = 0; i < layer.getWidth(); i++) {
            for (int j = 0; j < layer.getHeight(); j++) {
                TiledMapTileLayer.Cell cell = layer.getCell(i,j);
                if(cell == null) continue;
                tiles.add(new Rectangle(i*pixelWidth,j*pixelWidth,pixelWidth,pixelWidth));
            }
        }*/
        tiledMap = new TmxMapLoader().load("levels/levelOne.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        MapLayer layer = tiledMap.getLayers().get("Object Layer 1");
        MapObjects objects = layer.getObjects();
        for(MapObject o : objects){
            if(!(o instanceof RectangleMapObject)) continue;
            RectangleMapObject r = (RectangleMapObject) o;
            tiles.add(r.getRectangle());
        }
        MapLayer collectibles = tiledMap.getLayers().get("Collectibles");
        MapObjects collectibleObjs = collectibles.getObjects();
        collectables = new ArrayList<>();
        for(MapObject o : collectibleObjs){
            RectangleMapObject r = (RectangleMapObject) o;

            AbstractCollectable collectable;
            if(Math.random() > 0.3){
                collectable = new HealthPack(new Vector2(r.getRectangle().x,r.getRectangle().y),(int)r.getRectangle().width,(int)r.getRectangle().height);
            }
            else{
                collectable = new DeathPack(new Vector2(r.getRectangle().x,r.getRectangle().y),(int)r.getRectangle().width,(int)r.getRectangle().height);
            }

            collectables.add(collectable);

        }
    }
}
