package model.mapObject.levels;

import model.mapObject.terrain.AbstractTerrain;

import java.util.ArrayList;

/**
 * Created by tomherdson on 19/09/17.
 * An interface for describing a level.
 * This includes the various ground heights and platforms, as well as potentially including enemy spawns in the future.
 */
public interface LevelInterface {

    public String getLevelName();

    public int getLevelNumber();

    public ArrayList<AbstractTerrain> getTerrain();


    //maybe also include an arraylist of enemies and their spawn locations?


}
