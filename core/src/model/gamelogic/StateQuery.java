package model.gamelogic;

/**
 * Created by Dan Ko on 9/19/2017.
 */
public class StateQuery {
    private GameStateHandler statehandler;

    public StateQuery() {
        statehandler = new GameStateHandler();
    }

    public void save() {
        statehandler.save();
    }

    /** example of game data to query */
    public int getLivesLeft() {
        //statehandler.pref.livesLeft();
        return 0;
    }
}
