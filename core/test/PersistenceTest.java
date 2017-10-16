import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import mock.MockLevel;
import mock.MockModelData;
import mock.MockSaveLoader;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.Rogue;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.being.player.PlayerData;
import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.data.ModelData;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.levels.Spawn;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Created by kodani on 28/09/17.
 */
public class PersistenceTest extends GameTest{

    //
    //  NULL CHECKS
    //

    @Test
    public void testSavingPlayerNull() {
        MockSaveLoader nullCatcher = new MockSaveLoader();

        assertFalse(nullCatcher.validateAndUpdatePlayer(null, null));
    }

    @Test
    public void testSavingEnemiesNull() {
        MockSaveLoader nullCatcher = new MockSaveLoader();

        assertFalse(nullCatcher.validateAndUpdateEnemies(null, null));
    }

    @Test
    public void testSavingLevelNull() {
        MockSaveLoader nullCatcher = new MockSaveLoader();

        assertFalse(nullCatcher.validateAndUpdateLevel(null, null));
    }

    //
    //  TRANSACTION STATE CHECKS
    //

    @Test
    public void repoStartsAsEmpty() {
        MockSaveLoader emptyRepoChecker = new MockSaveLoader();

        // Repo should be empty upon initialisation
        assertTrue(emptyRepoChecker.repository.latestSaveNum() == 0);
    }

    @Test
    public void loadFromEmptyRepoFails() {
        MockSaveLoader emptyRepoLoader = new MockSaveLoader();

        try {
            emptyRepoLoader.load();
            fail();

        } catch (GameStateTransactionHandler.InvalidTransactionException e) {

        }
    }

    //
    //  VALID SAVES
    //

    @Test
    public void testValidSavePerson() {
        MockSaveLoader nullCatcher = new MockSaveLoader();
        assertFalse(false);

        //assertFalse(nullCatcher.validateAndUpdatePlayer(null
    }

    @Test
    public void testValidSave() {
        MockSaveLoader repoScraper = new MockSaveLoader();

        // Repo should be empty upon initialisation
        assertTrue(repoScraper.repository.latestSaveNum() == 0);

        AbstractPlayer pl = new Player();
        List<AbstractEnemy> enems = new ArrayList<>();
        MockLevel level = new MockLevel();

        enems.add(new Rogue(new World(new Vector2(0, -8), true), pl, new Vector2(15, 15)));

        MockModelData data = new MockModelData();

        data.setPlayerData(new PlayerData(pl));
        data.setEnemies(enems);
        data.setLevel(level);

        repoScraper.save(data);

        // Unit of Work should have worked and saved 1 GameState to the repo
        assertTrue(repoScraper.repository.latestSaveNum() == 1);
    }


}
