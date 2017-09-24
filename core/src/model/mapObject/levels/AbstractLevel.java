package model.mapObject.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import model.GameObjectInterface;
import model.mapObject.terrain.AbstractTerrain;
import model.mapObject.terrain.Ground;
import model.mapObject.terrain.Platform;

import java.util.ArrayList;

/**
 * Created by tomherdson on 19/09/17.
 * An interface for describing a level.
 * This includes the various ground heights and platforms, as well as potentially including enemy spawns in the future.
 */
public abstract class AbstractLevel {



    protected ArrayList<AbstractTerrain> terrain;

    public abstract String getLevelName();

    public abstract int getLevelNumber();



    protected TiledMap tiledMap;
    protected TiledMapRenderer tiledMapRenderer;
    protected Array<Rectangle> tiles = new Array<>();

    public abstract void generateLevel();

    public AbstractLevel() {
        terrain = new ArrayList<>();
        generateLevel();
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
}
