package model.data;

import com.badlogic.gdx.Preferences;
import model.being.AbstractEnemy;
import model.being.AbstractPlayer;

import java.util.List;

/**
 * Created by Dan Ko on 9/19/2017.
 */
public class GameState{
    private Preferences state;

    private AbstractPlayer player;
    private List<AbstractEnemy> enemies;

    public GameState(Preferences pref) {
        state = pref;
    }

    public void setState(Preferences state) {
        this.state = state;
    }

    public Preferences getState() {
        return state;
    }

    public void setPlayer(AbstractPlayer player) {
        //TODO: serialise player and add as string to pref
    }
    public void setEnemies(List<AbstractEnemy> enemies) {
        //TODO: serialise enemies and ass as string to pref
    }
}
