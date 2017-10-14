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

    //
    //  GETTERS
    //

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

    //
    //  SETTERS
    //

    public void setPlayer(AbstractPlayer player) {
        this.player = player;
    }

    public void setEnemies(List<AbstractEnemy> enemies) {
        this.enemies = enemies;
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
}
