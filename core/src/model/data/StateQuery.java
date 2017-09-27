package model.data;

import model.being.AbstractEnemy;
import model.being.AbstractPlayer;
import model.being.Player;
import model.collectable.AbstractCollectable;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dan Ko on 9/25/2017.
 */
public class StateQuery {
    private AbstractPlayer player;
    private List<AbstractEnemy> enemies;
    private List<AbstractCollectable> collectables;

    public StateQuery(AbstractPlayer p, List<AbstractEnemy> e, List<AbstractCollectable> c) {
        player = p;
        enemies = e;
        collectables = c;
    }

    public AbstractPlayer loadPlayer() {
        return player; //todo: player.clone()
    }

    public List<AbstractEnemy> loadEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    public List<AbstractCollectable> loadCollectables() {
        return Collections.unmodifiableList(collectables);
    }
}
