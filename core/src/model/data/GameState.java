package model.data;

import com.badlogic.gdx.Preferences;
import model.being.AbstractEnemy;
import model.being.AbstractPlayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by Dan Ko on 9/19/2017.
 */
public class GameState{
    private Preferences state;

    private AbstractPlayer player;
    private List<AbstractEnemy> enemies;

    public GameState(Preferences pref) {
        state = pref;
    }

    public void getPref(Preferences state) {
        this.state = state;
    }

    public Preferences getPref() {
        return state;
    }

    
    /** puts the bytecode of a AbstractPlayer
     *  into the Preference
     *  @param player as bytecode
     */
    public void setPlayer(String player) {
        state.putString("Player", player);
    }
    
    /** puts the bytecode of a List<AbstractEnemies>
     *  into the Preference
     * @param enemies
     */
    public void setEnemies(String enemies) {
        state.putString("Enemies", enemies);
    }

    /** As the model will load this value in, assure the TransactionHandler
     * that this value exists inside the GameState
     * @return assurance.
     */
    public boolean containsPlayer() {
        return state.contains("Player");
    }

    /** As the model will load this value in, assure the TransactionHandler
     * that this value exists inside the GameState
     * @return assurance.
     */
    public boolean containsEnemies() {
        return state.contains("Enemies");
    }
}
