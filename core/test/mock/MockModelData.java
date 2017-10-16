package mock;

import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.being.player.PlayerData;
import model.mapObject.levels.AbstractLevel;

import java.util.List;

/**
 * Created by kodani on 16/10/17.
 */
public class MockModelData {
    private AbstractPlayer player;
    private List<AbstractEnemy> enemies;
    private PlayerData playerData;
    private MockLevel level;

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

    public MockLevel loadLevel() {
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

    public void setLevel(MockLevel level) {
        this.level = level;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }
}
