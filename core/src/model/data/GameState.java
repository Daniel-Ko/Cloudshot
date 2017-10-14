package model.data;

import com.badlogic.gdx.Preferences;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;

import java.util.List;

/**
 * Created by Dan Ko on 9/19/2017.
 */
public class GameState{
    private Preferences state;


    public GameState(Preferences pref) {
        state = pref;
    }

    public void getPref(Preferences state) {
        this.state = state;
    }

    public Preferences getPref() {
        return state;
    }

    
    /** puts the bytecode of a PlayerData
     *  into the Preference
     *  @param player as bytecode
     */
    public void setPlayerInPref(String player) {
        state.putString("Player", player);
    }
    
    /** puts the bytecode of a List<AbstractEnemies>
     *  into the Preference
     * @param enemies
     */
    public void setEnemiesInPref(String enemies) {
        state.putString("Enemies", enemies);
    }
    
    /** puts the bytecode of a List<AbstractCollectable>
     *  into the Preference
     * @param collectables
     */
    public void setCollectablesInPref(String collectables) {
        state.putString("Collectables", collectables);
    }

    /** puts the bytecode of an AbstractLevel
     * into the Preference
     * @param level
     */
    public void setLevelInPref(String level) {
        state.putString("Level", level);
    }

    /** puts the bytecode of a List<Rectangle> and List<Spawn>
     *  into the Preference
     * @param spawnTriggers
     * @param spawns
     */
    public void setSpawningInPref(String spawnTriggers, String spawns) {
        state.putString("SpawnTriggers", spawnTriggers);
        state.putString("Spawns", spawns);
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
