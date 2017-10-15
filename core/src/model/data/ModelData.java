package model.data;

import com.badlogic.gdx.math.Rectangle;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.being.player.PlayerData;
import model.collectable.AbstractCollectable;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.Spawn;

import java.util.List;

/**
 * Created by kodani on 13/10/17.
 */
public class ModelData {
    protected AbstractPlayer player;
    protected List<AbstractEnemy> enemies;
    protected List<AbstractCollectable> collectables;
    protected List<Rectangle> spawnTriggers;
    protected List<Spawn> spawns;
    protected PlayerData pData;
    protected AbstractLevel level;

    //
    //  GETTERS
    //

    public AbstractPlayer loadPlayer() {
        return player;
    }
    
    public PlayerData loadPlayerData() {
        return pData;
    }

    public List<AbstractEnemy> loadEnemies() {
        return enemies;
    }

    public AbstractLevel loadLevel() {
        return level;
    }

    public List<AbstractCollectable> loadCollectables() {
        return collectables;
    }

    public List<Rectangle> loadSpawnTriggers() {
        return spawnTriggers;
    }

    public List<Spawn> loadSpawns() {
        return spawns;
    }

    //
    //  SETTERS
    //

    public void setPlayer(AbstractPlayer player) {
        this.player = player;
    }

    public void setEnemies(List<AbstractEnemy> enemies) {
        this.enemies = enemies;
    }

    public void setLevel(AbstractLevel level) {
        this.level = level;
    }

    public void setCollectables(List<AbstractCollectable> collectables) {
        this.collectables = collectables;
    }

    public void setSpawnTriggers(List<Rectangle> spawnTriggers) {
        this.spawnTriggers = spawnTriggers;
    }

    public void setSpawns(List<Spawn> spawns) {
        this.spawns = spawns;
    }
 
    public void setPlayerData(PlayerData playerData) {
        this.pData = playerData;
    }


}
