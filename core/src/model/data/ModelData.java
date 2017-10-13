package model.data;

import com.badlogic.gdx.math.Rectangle;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractCollectable;
import model.mapObject.levels.Spawn;

import java.util.List;

/**
 * Created by kodani on 13/10/17.
 */
public class ModelData {
    private AbstractPlayer player;
    private List<AbstractEnemy> enemies;
    private List<AbstractCollectable> collectables;
    private List<Rectangle> spawnTriggers;
    private List<Spawn> spawns;


    public ModelData(AbstractPlayer pl, List<AbstractEnemy> enems, List<AbstractCollectable> collects, List<Rectangle> spwTrig, List<Spawn> spwns) {
        player = pl;
        enemies = enems;
        collectables = collects;
        spawnTriggers = spwTrig;
        spawns = spwns;
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    public List<AbstractEnemy> getEnemies() {
        return enemies;
    }

    public List<AbstractCollectable> getCollectables() {
        return collectables;
    }

    public List<Rectangle> getSpawnTriggers() {
        return spawnTriggers;
    }

    public List<Spawn> getSpawns() {
        return spawns;
    }
}
