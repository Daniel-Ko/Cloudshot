package model.mapObject.levels;

import model.collectable.AbstractCollectable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tomherdson on 19/09/17.
 * First level.
 */
public class LevelThree extends AbstractLevel {

    public LevelThree() {
        super(3);
    }

    @Override
    public String getLevelName() {
        return "Jeremy's Level";
    }

    @Override
    public AbstractLevel getNextLevel() {
        return new LevelOne();
    }

    @Override
    public HashMap<Integer, String> getSpawnRates() {
        HashMap<Integer,String> spawnRates = new HashMap<>();
        spawnRates.put(0,"DeathPack");
        spawnRates.put(1,"HealthPack");
        spawnRates.put(2,"HealthPack");
        spawnRates.put(3,"DeathPack");
        spawnRates.put(4,"Pistol");
        spawnRates.put(5,"Pistol");
        spawnRates.put(6,"HeavyAmmoPack");
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
