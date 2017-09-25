package model.data;

import model.being.AbstractEnemy;
import model.being.Player;

import java.util.List;

/**
 * Created by Dan Ko on 9/25/2017.
 */
public class StateQuery {
    private GameState state;

    public StateQuery(GameState s) {
        state = s;
    }

    public Player loadPlayer() { //should this be AbstractPlayer?
        String player = state.getPref().getString("Player");
        //todo unserialise
        return null;
    }

    public List<AbstractEnemy> loadEnemies() {
        String enemies = state.getPref().getString("Enemies");
        //todo unserialise
        return null;
    }
}
