
package model.data;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by Dan Ko on 9/19/2017.
 */
public class StateQueryTransactionHandler implements Observer{
    private GameStateRepository repository;
    private GameStateDB database;

    public StateQueryTransactionHandler(GameStateDB db) {
        repository = new GameStateRepository();
        database = db;
    }

    public void save() {
        GameState latest = database.latestState();
        query(); //TODO should this even be done
        repository.save(latest);
    }

    private GameState query() {
        try{

        } catch(GameStateDB.InvalidTransactionException e) {

        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        save();
    }
}
