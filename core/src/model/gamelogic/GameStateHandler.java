package model.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Stack;

/**
 * Created by Dan Ko on 9/19/2017.
 */
public class GameStateHandler {
    private Stack<GameState> gameStates;

    public GameStateHandler() {
        gameStates = new Stack<>();
    }

    public void save() {
        gameStates.push(new GameState(Gdx.app.getPreferences("yo")));
    }

    public GameState load() {
        return gameStates.pop();
    }
}
