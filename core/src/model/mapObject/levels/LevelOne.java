package model.mapObject.levels;

import com.badlogic.gdx.maps.MapProperties;
import model.collectable.AbstractCollectable;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tomherdson on 19/09/17.
 * First level.
 */
public class LevelOne extends AbstractLevel {


    public LevelOne() {
        super(1);
    }

    public LevelOne(boolean b){
        super();
    }

    @Override
    public String getLevelName() {
        return "Welcome to Cloudshot";
    }

    @Override
    public AbstractLevel getNextLevel() {
        return new LevelTwo();
    }

    @Override
    public HashMap<Integer, String> getSpawnRates() {
        HashMap<Integer,String> spawnRates = new HashMap<>();
        spawnRates.put(0,"HealthPack");
        spawnRates.put(1,"HealthPack");
        spawnRates.put(2,"HealthPack");
        spawnRates.put(3,"Sniper");
        spawnRates.put(4,"Sniper");
        spawnRates.put(5,"Sniper");
        spawnRates.put(6,"Sniper");
        spawnRates.put(7,"HeavyAmmoPack");
        spawnRates.put(8,"Shotgun");
        spawnRates.put(9,"Shotgun");
        spawnRates.put(10,"Shotgun");



        return spawnRates;
    }

    @Override
    public List<AbstractCollectable> getCollectables() {
        return collectables;
    }




}