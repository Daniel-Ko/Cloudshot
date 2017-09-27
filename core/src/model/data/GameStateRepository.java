package model.data;

import java.util.Stack;

/**
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
        return gameStates.peek();
    }

    public GameState pullHard() {
        return gameStates.pop();
    }

    public int latestSaveNum() {
        return gameStates.size();
    }
}
