package model.mapObject.levels;

import model.mapObject.terrain.AbstractTerrain;
import model.mapObject.terrain.Ground;

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

    public abstract ArrayList<AbstractTerrain> getTerrain();

    public AbstractLevel() {
        terrain = new ArrayList<>();
    }

    private int xPos = 0;
    /**
     * Adds ground to the terrain.
     * xPos is constantly incremented, so the user only has to pass the width and height of the new piece of ground and the
     * xPos/yPos will be inferred.
     * @param width width of the piece of ground
     * @param height height of the new piece of ground
     */
    public void addGround(int width, int height){
        terrain.add(new Ground(xPos, width, height));
    }

    public void addPlatform(){

    }



    //maybe also include an arraylist of enemies and their spawn locations?


}
