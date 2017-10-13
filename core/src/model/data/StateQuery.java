package model.data;

import com.badlogic.gdx.math.Rectangle;
import model.being.enemies.AbstractEnemy;
import model.being.player.PlayerData;
import model.collectable.AbstractCollectable;
import model.mapObject.levels.Spawn;

import java.util.List;

/**
 * Created by Dan Ko on 9/25/2017.
 */
public class StateQuery {
    private PlayerData playerData;
    private List<AbstractEnemy> enemies;
    private List<AbstractCollectable> collectables;
    private List<Rectangle> spawnTriggers;
    private List<Spawn> spawns;
    

    public StateQuery(PlayerData pdata, List<AbstractEnemy> e, List<AbstractCollectable> c, List<Rectangle> t, List<Spawn> s) {
        playerData = pdata;
        enemies = e;
        collectables = c;
        spawnTriggers = t;
        spawns = s;
    }

    public PlayerData loadPlayerData() {
        return playerData;
    }

    public List<AbstractEnemy> loadEnemies() {
        return enemies;
        //return Collections.unmodifiableList(enemies);
    }

    public List<AbstractCollectable> loadCollectables() {
        return collectables;
        //return Collections.unmodifiableList(collectables);
    }

    public List<Rectangle> loadSpawnTriggers() {
        return spawnTriggers;
    }

    public List<Spawn> loadSpawns() {
        return spawns;
    }
}
