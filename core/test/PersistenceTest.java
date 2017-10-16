import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import mock.MockLevel;
import mock.MockModelData;
import mock.MockSaveLoader;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.Rogue;
import model.being.enemies.Slime2;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.data.GameState;
import model.data.GameStateTransactionHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Uses a MockSaveLoader (GameStateTransactionHandler mock) and MockPlayer/Level/ModelData
 * in order to simulate test results.
 *
 * Tests the equivalent methods and functions in the src/data package, pertaining to Loading and Saving and data persistence
 *
 * Created by kodani on 28/09/17.
 */
public class PersistenceTest extends GameTest{

    //
    //  NULL CHECKS
    //      AND
    //  UNIT OF WORK FAIL
    //                 (as relevant classes are all marked as Serializable and transient fields are all marked clearly, the only way saving is
    //                 if passed in different classes. This is unlikely and can be checked in any case if Valid Save Tests fail.
    //                 Null values, however, could happen and are tested here)
    //
    //

    @Test
    public void testSavingPlayerNull() {
        MockSaveLoader nullCatcher = new MockSaveLoader();
        try {
            nullCatcher.validateAndUpdatePlayer(null, null);
            fail();
        } catch (GameStateTransactionHandler.InvalidTransactionException e) {}
    }

    @Test
    public void testSavingEnemiesNull() {
        MockSaveLoader nullCatcher = new MockSaveLoader();

        try {
            nullCatcher.validateAndUpdateEnemies(null, null);
            fail();
        } catch (GameStateTransactionHandler.InvalidTransactionException e) {}
    }

    @Test
    public void testSavingLevelNull() {
        MockSaveLoader nullCatcher = new MockSaveLoader();

        try {
            nullCatcher.validateAndUpdateLevel(null, null);
            fail();
        } catch (GameStateTransactionHandler.InvalidTransactionException e) {}
    }

    @Test
    public void invalidSaveUnitOfWork() {
        MockSaveLoader repoScraper = new MockSaveLoader();

        // Repo should be empty upon initialisation
        assertTrue(repoScraper.repository.latestSaveNum() == 0);

        try {
            repoScraper.save(setUpValidModelData());

            // Unit of Work should have worked and saved 1 GameState to the repo
            assertTrue(repoScraper.repository.latestSaveNum() == 1);

            // Make an invalid MockModelData instance
            MockModelData invalid = setUpValidModelData();
            invalid.setPlayer(null);

            repoScraper.save(invalid); // should throw exception

            fail();


        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            assertTrue(repoScraper.repository.latestSaveNum() == 1);

            // Check the previous save was pulled from the repo (invalid values were not saved to repo)
            GameState successfulSave = repoScraper.repository.pullSoft();
            assertTrue(successfulSave.containsPlayer());
            assertTrue(successfulSave.containsEnemies());
            assertTrue(successfulSave.containsLevel());

            // Finally, check that this preference saved the exact same values we passed in

            assertEquals(
                    repoScraper.serVals.get("Player"),
                    successfulSave.getPref().getString("Player")
            );

            assertEquals(
                    repoScraper.serVals.get("Enemies"),
                    successfulSave.getPref().getString("Enemies")
            );

            assertEquals(
                    repoScraper.serVals.get("Level"),
                    successfulSave.getPref().getString("Level")
            );
        }
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

    @Test
    public void repoNumChanges() {
        MockSaveLoader repoScraper = new MockSaveLoader();

        // Repo should be empty upon initialisation
        assertTrue(repoScraper.repository.latestSaveNum() == 0);

        repoScraper.save(setUpValidModelData()); //adding to the repo

        // Should have saved 1 GameState to the repo
        assertTrue(repoScraper.repository.latestSaveNum() == 1);

        repoScraper.repository.pullHard(); //remove one state

        assertTrue(repoScraper.repository.latestSaveNum() == 0);
    }

    @Test
    public void testPrefNameIsUnique() {
        MockSaveLoader repoScraper = new MockSaveLoader();

        // Initial state
        repoScraper.save(setUpValidModelData());

        GameState state0 = repoScraper.repository.pullSoft();

        assertEquals(Gdx.app.getPreferences(
                "save0"),
                state0.getPref());

        // Consistent after adding
        repoScraper.save(setUpValidModelData());

        GameState state1 = repoScraper.repository.pullSoft();

        assertEquals(Gdx.app.getPreferences(
                "save1"),
                state1.getPref());

        // Consistent after removing
        assertEquals(Gdx.app.getPreferences(
                "save0"),
                state0.getPref());
    }

    //
    //  VALID SAVES
    //

    @Test
    public void testValidSavePlayer() {
        MockSaveLoader playerSaver = new MockSaveLoader();

        assertTrue(playerSaver.validateAndUpdatePlayer(setUpValidState("save0"), new Player()));
    }

    @Test
    public void testValidSaveEnemies() {
        MockSaveLoader enemiesSaver = new MockSaveLoader();

        // Test empty list
        assertTrue(enemiesSaver.validateAndUpdateEnemies(setUpValidState("save0"), new ArrayList<AbstractEnemy>()));

        // Test populated enemies
        assertTrue(enemiesSaver.validateAndUpdateEnemies(setUpValidState("save0"), setUpValidEnemies()));
    }

    @Test
    public void testValidSaveLevel() {
        MockSaveLoader levelSaver = new MockSaveLoader();

        // Test level with uninitialised fields
        assertTrue(levelSaver.validateAndUpdateLevel(setUpValidState("save0"), new MockLevel()));

        // Test level with initialised fields
        assertTrue(levelSaver.validateAndUpdateLevel(setUpValidState("save0"), setUpValidLevel()));
    }

    @Test
    public void testValidSave() {
        MockSaveLoader repoScraper = new MockSaveLoader();

        // Repo should be empty upon initialisation
        assertTrue(repoScraper.repository.latestSaveNum() == 0);


        repoScraper.save(setUpValidModelData());

        // Unit of Work should have worked and saved 1 GameState to the repo
        assertTrue(repoScraper.repository.latestSaveNum() == 1);

        // Check it was stored to the repository
        GameState successfulSave = repoScraper.repository.pullSoft();
        assertTrue(successfulSave.containsPlayer());
        assertTrue(successfulSave.containsEnemies());
        assertTrue(successfulSave.containsLevel());

        // Finally, check that this preference saved the exact same values we passed in

        assertEquals(
                repoScraper.serVals.get("Player"),
                successfulSave.getPref().getString("Player")
        );

        assertEquals(
                repoScraper.serVals.get("Enemies"),
                successfulSave.getPref().getString("Enemies")
        );

        assertEquals(
                repoScraper.serVals.get("Level"),
                successfulSave.getPref().getString("Level")
        );
    }


    //
    //  VALID LOADS
    //

    @Test
    public void testValidLoadPlayer() {
        MockSaveLoader playerLoader = new MockSaveLoader();

        AbstractPlayer player = new Player();

        GameState validLoadState = setUpValidState("save0");

        try {
            assertTrue(playerLoader.validateAndUpdatePlayer(validLoadState, player));

            playerLoader.validateAndReturnPlayerData(validLoadState);
            assertEquals(playerLoader.serVals.get("Player"), playerLoader.deserVals.get("Player"));


        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            fail();
        }
    }

    @Test
    public void testValidLoadEnemies() {
        MockSaveLoader enemiesLoader = new MockSaveLoader();

        List<AbstractEnemy> enemies = setUpValidEnemies();

        GameState validLoadState = setUpValidState("save0");

        try {
            assertTrue(enemiesLoader.validateAndUpdateEnemies(validLoadState, enemies));

            enemiesLoader.validateAndReturnEnemies(validLoadState);
            assertEquals(enemiesLoader.serVals.get("Enemies"), enemiesLoader.deserVals.get("Enemies"));


        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            fail();
        }
    }

    @Test
    public void testValidLoadLevel() {
        MockSaveLoader levelLoader = new MockSaveLoader();

        MockLevel level = setUpValidLevel();

        GameState validLoadState = setUpValidState("save0");

        try {
            assertTrue(levelLoader.validateAndUpdateLevel(validLoadState, level));

            levelLoader.validateAndReturnLevel(validLoadState);
            assertEquals(levelLoader.serVals.get("Level"), levelLoader.deserVals.get("Level"));


        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            fail();
        }
    }

    @Test
    public void testValidLoad() {

    }


    //
    //  HELPER METHODS
    //

    private MockModelData setUpValidModelData() {
        AbstractPlayer pl = new Player();
        List<AbstractEnemy> enems = setUpValidEnemies();
        MockLevel level = setUpValidLevel();

        pl.setHealth(20);
        pl.setInAir(true);

        MockModelData data = new MockModelData();

        data.setPlayer(pl);
        data.setEnemies(enems);
        data.setLevel(level);

        return data;
    }

    private GameState setUpValidState(String name) {
        return new GameState(
                Gdx.app.getPreferences(name));
    }

    private List<AbstractEnemy> setUpValidEnemies() {
        List<AbstractEnemy> enems = new ArrayList<>();

        enems.add(new Rogue());
        enems.add(new Slime2());

        return enems;
    }

    private MockLevel setUpValidLevel() {
        MockLevel level = new MockLevel();

        level.loadCollectables();
        //loadEndPoint();
        level.loadSpawnTriggerPoints();
        level.loadSpawns();

        level.loadPortals();
        return level;
    }
}
