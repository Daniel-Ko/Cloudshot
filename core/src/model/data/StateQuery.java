package model.data;

import model.being.enemies.AbstractEnemy;
import model.being.player.PlayerData;
import model.collectable.AbstractCollectable;

import java.util.List;

/**
 * Created by Dan Ko on 9/25/2017.
 */
public class StateQuery {
    private PlayerData playerData;
    private List<AbstractEnemy> enemies;
    private List<AbstractCollectable> collectables;

    public StateQuery(PlayerData pdata, List<AbstractEnemy> e, List<AbstractCollectable> c) {
        playerData = pdata;
        enemies = e;
        collectables = c;
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
}
