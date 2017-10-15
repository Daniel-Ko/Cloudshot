package model.data;

import com.badlogic.gdx.math.Rectangle;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.being.player.PlayerData;
import model.collectable.AbstractCollectable;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.Spawn;

import java.util.List;

/**
 * Created by kodani on 13/10/17.
 */
public class ModelData {
    private AbstractPlayer player;
    private List<AbstractEnemy> enemies;
    private PlayerData playerData;
    private AbstractLevel level;

    //
    //  GETTERS
    //

    public AbstractPlayer loadPlayer() {
        return player;
    }
    
    public PlayerData loadPlayerData() {
        return playerData;
    }

    public List<AbstractEnemy> loadEnemies() {
        return enemies;
    }

    public AbstractLevel loadLevel() {
        return level;
    }


    //
    //  SETTERS
    //

    public void setPlayer(AbstractPlayer player) {
        this.player = player;
    }

    public void setEnemies(List<AbstractEnemy> enemies) {
        this.enemies = enemies;
    }

    public void setLevel(AbstractLevel level) {
        this.level = level;
    }
 
    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }


}
