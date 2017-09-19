package model.gamelogic;

import com.badlogic.gdx.Preferences;

/**
 * Created by Dan Ko on 9/19/2017.
 */
public class GameState {
    private Preferences state;

    public GameState(Preferences pref) {
        state = pref;
    }

    public void setState(Preferences state) {
        this.state = state;
    }

    public Preferences getState() {
        return state;
    }
}
