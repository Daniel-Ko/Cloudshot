package model.data;

import com.badlogic.gdx.Gdx;
import model.GameModel;
import model.being.AbstractEnemy;
import model.being.AbstractPlayer;

import java.util.List;
import java.util.Observable;

/**
 * Created by Dan Ko on 9/23/2017.
 */
public class GameStateDB extends Observable {
    private StateQueryTransactionHandler repoScraper;
    private GameState state;

    public GameStateDB() {
        repoScraper = new StateQueryTransactionHandler(this);
    }

    public GameState latestState() {
        return state;
    }

    /** the unit of work for saving the game state
     *
     * @param model
     * @return
     */
    public boolean write(GameModel model) {
        GameState newState = new GameState(Gdx.app.getPreferences("yo")); //TODO: I have to find a way to gen a unique name


        if(!updatePlayer(newState, model.getPlayer())) {
            return false;
        }
        if(!updateEnemies(newState, model.getEnemies())) {
            return false;
        }
        commit(newState);
        return true;
    }

    public boolean read() {
        return false; //TODO FLESH THIS OUT
    }



    private void commit(GameState newState) {
        state = newState;
        notifyObservers();
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



    /** Upon invalid or missing data, this exception will be thrown to rollback all changes
     * done upon the database
     */
    public class InvalidTransactionException extends RuntimeException {
        public InvalidTransactionException(String msg) {
            super(msg);
        }
    }
}


