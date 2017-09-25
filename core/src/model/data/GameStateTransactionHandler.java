
package model.data;

import com.badlogic.gdx.Gdx;
import model.GameModel;
import model.being.AbstractEnemy;
import model.being.AbstractPlayer;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by Dan Ko on 9/19/2017.
 */
public class GameStateTransactionHandler implements Observer{
    private GameStateRepository repository;

    public GameStateTransactionHandler() {
        repository = new GameStateRepository();
    }

    public boolean save(GameModel model) {
        GameState newState = new GameState(Gdx.app.getPreferences("yo")); //TODO: I have to find a way to gen a unique name
        if(writeQuery(model, newState)) {//TODO should this even be done
            commit(newState);
            return true;
        }
        return false;
    }

    private boolean writeQuery(GameModel model, GameState newState) {
        if(!updatePlayer(newState, model.getPlayer())) {
            return false;
        }
        if(!updateEnemies(newState, model.getEnemies())) {
            return false;
        }
        return true;
    }

    /** sends signal to repo to pull the latest state and wrap it in a StateQuery to the model
     *
     * @return
     */
    public StateQuery load() {
        GameState latest = repository.pullSoft(); //TODO I don't thin pullSoft is neessary
        if(!latest.containsPlayer() || !latest.containsEnemies()) {
            repository.pullHard(); //cleanse the repo of the bad state
            throw new InvalidTransactionException("Corrupted state");
        }
        repository.pullHard();
        return new StateQuery(latest);
    }


    private void commit(GameState newState) {
        repository.push(newState);
    }


    public boolean updatePlayer( GameState newState, AbstractPlayer newPlayer) {
        if(newPlayer == null || ! (newPlayer instanceof AbstractPlayer)) {
            return false;
        }
        newState.setPlayer(newPlayer);
        return true;
    }

    private boolean updateEnemies( GameState newState, List<AbstractEnemy> newFoes) {
        if(newFoes == null ) {
            return false;
        }
        newState.setEnemies(newFoes);
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
//        save();
    }



    /** Upon invalid or missing data, this exception will be thrown to rollback all changes
     * done upon the database
     */
    public class InvalidTransactionException extends RuntimeException {
        public InvalidTransactionException(String msg) {
            super(msg);
        }
    }
}
