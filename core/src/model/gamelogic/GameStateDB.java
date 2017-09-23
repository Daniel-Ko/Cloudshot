package model.gamelogic;

import model.being.AbstractPlayer;
import view.CustomSprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Dan Ko on 9/23/2017.
 */
public class GameStateDB extends Observable {
    private List<CustomSprite> sprites = new ArrayList<>();
    private AbstractPlayer player;

    /** the unit of work for saving the game state
     *
     * @param model
     * @return
     */
    public boolean write(GameModel model) {
        if(!updatePlayer(model.getPlayer())) {
            return false;
        }
        if(!addEnemies(model.getEnemies())) {
            return false;
        }
    }

    public boolean updatePlayer(AbstractPlayer p) {
        if(p == null || ! (p instanceof AbstractPlayer)) {
            return false;
        }
        player = p;
        return true;
    }


    /** Upon invalid or missing data, this exception will be thrown to rollback all changes
     * done upon the database
     */
    class InvalidTransactionException extends RuntimeException {
        public InvalidTransactionException(String msg) {
            super(msg);
        }
    }
}


