package model.mapObject.levels;

import model.mapObject.terrain.AbstractTerrain;
import model.mapObject.terrain.Ground;
import model.mapObject.terrain.Platform;

import java.util.ArrayList;

/**
 * Created by tomherdson on 19/09/17.
 * First level.
 */
public class LevelOne implements LevelInterface {

    @Override
    public String getLevelName() {
        return "Level One";
    }

    @Override
    public int getLevelNumber() {
        return 1;
    }

    @Override
    public ArrayList<AbstractTerrain> getTerrain() {
        ArrayList<AbstractTerrain> terrain = new ArrayList<>();
        terrain.add(new Ground());
        terrain.add(new Platform());
        return terrain;
    }
}
