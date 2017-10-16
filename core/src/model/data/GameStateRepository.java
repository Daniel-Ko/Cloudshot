package model.data;

import java.util.Stack;

/**
 * As part of the Repository design pattern, this repository stores GameStates.
 * The behaviour acts as a simplified Stack (only the latest game save stored can be accessed)
 *
 * Created by Dan Ko on 9/19/2017.
 */
public class GameStateRepository{
    private Stack<GameState> gameStates;

    public GameStateRepository() {
        gameStates = new Stack<>();
    }

    public void push(GameState savestate) {
        gameStates.push(savestate);
    }

    /**
     * Hands over a reference to the most recent game state only, without losing it in the repo. The clone is to ensure that
     * in the case of a failed load, we can rollback the data and not lose this savestate.
     * @return
     */
    public GameState pullSoft() {
        if(gameStates.isEmpty())
            return null;
        return gameStates.peek();
    }

    public GameState pullHard() {
        if(gameStates.isEmpty())
            return null;
        return gameStates.pop();
    }

    public int latestSaveNum() {
        return gameStates.size();
    }
}
