package model.data;

import model.being.AbstractEnemy;
import model.being.AbstractPlayer;
import model.being.Player;
import model.being.PlayerData;
import model.collectable.AbstractCollectable;

import java.util.Collections;
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
        return Collections.unmodifiableList(enemies);
    }

    public List<AbstractCollectable> loadCollectables() {
        return Collections.unmodifiableList(collectables);
    }
}
