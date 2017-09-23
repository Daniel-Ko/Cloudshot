package model.gamelogic;

/**
 * Created by Dan Ko on 9/19/2017.
 */
public class StateQueryTransactionHandler {
    private GameStateRepository statehandler;

    public StateQueryTransactionHandler() {
        statehandler = new GameStateRepository();
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
