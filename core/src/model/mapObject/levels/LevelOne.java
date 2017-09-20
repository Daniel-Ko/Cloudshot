package model.mapObject.levels;

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
        return "Level One";
    }

    @Override
    public int getLevelNumber() {
        return 1;
    }

    @Override
    public ArrayList<AbstractTerrain> getTerrain() {
       addGround(32,64);
       addGround(64,128);
       addGround(128,256);


        return super.terrain;
    }
}
