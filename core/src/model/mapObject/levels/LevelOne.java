package model.mapObject.levels;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import model.mapObject.terrain.AbstractTerrain;
import model.mapObject.terrain.Ground;
import model.mapObject.terrain.Platform;

import java.util.ArrayList;

/**
 * Created by tomherdson on 19/09/17.
 * First level.
 */
public class LevelOne extends AbstractLevel {

    public LevelOne() {
        super();
    }

    @Override
    public String getLevelName() {
        return "Welcome to Cloudshot";
    }

    @Override
    public int getLevelNumber() {
        return 1;
    }

    public void generateLevel(){
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
}
