package model.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


/**
 * Created by Dan Ko on 9/19/2017.
 */
public class StateQueryTransactionHandler {
    private GameStateRepository repository;
    private GameStateDB database;

    public StateQueryTransactionHandler(GameStateDB db) {
        repository = new GameStateRepository();
        database = db;
    }

    public void save() {
        Preferences unitOfWork = Gdx.app.getPreferences("yo");
        query();
        //repository.save(unitOfWork);
    }

    private GameState query() {
        try{

        } catch(GameStateDB.InvalidTransactionException e) {

        }
    }

    /** example of game data to query */
    public int getLivesLeft() {
        //repository.pref.livesLeft();
        return 0;
    }

}
