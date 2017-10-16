package mock;

import com.badlogic.gdx.Gdx;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.being.player.PlayerData;
import model.data.GameState;
import model.data.GameStateRepository;
import model.data.GameStateTransactionHandler;


import java.io.*;
import java.util.Base64;
import java.util.List;

/**
 * Mock GameStateTransactionHandler class for testing, with all fields and methods as public
 *
 * Created by kodani on 16/10/17.
 */
public class MockSaveLoader {
    public GameStateRepository repository;

    public MockSaveLoader() {
        repository = new GameStateRepository();
    }

    public void save(MockModelData model) {
        GameState newState = new GameState(
                Gdx.app.getPreferences(
                        "save" + repository.latestSaveNum() //always a unique name and should stay dynamic + consistent throughout repo operations
                ));
        try {
            writeQuery(model, newState);
        } catch(GameStateTransactionHandler.InvalidTransactionException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }

        // Unit of work complete, we now commit the gamestate to the repository
        commit(newState);
    }

    private void writeQuery(MockModelData model, GameState newState) {

        /* spawnEnemies the newState with validated data, otherwise signal failed save */
        try {
            validateAndUpdatePlayer(newState, model.loadPlayer());
            validateAndUpdateEnemies(newState, model.loadEnemies());
            validateAndUpdateLevel(newState, model.loadLevel());
        } catch(GameStateTransactionHandler.InvalidTransactionException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }


    /**
     * sends signal to repo to pull the latestSaveNum state and wrap it in a StateQuery to the model
     *
     * @return
     */
    public MockModelData load() throws GameStateTransactionHandler.InvalidTransactionException {
        GameState latest = repository.pullSoft();
        if (latest == null)
            throw new GameStateTransactionHandler.InvalidTransactionException("No saved games!");

        if (!latest.containsPlayer() || !latest.containsEnemies()) {
            repository.pullHard(); //cleanse the repo of the bad state
            throw new GameStateTransactionHandler.InvalidTransactionException("Corrupted save");
        }

        try {

            //if all data is valid, remove it from stack finally
            MockModelData loadedData = new MockModelData();
            loadedData.setPlayerData(validateAndReturnPlayerData(latest));
            loadedData.setEnemies(validateAndReturnEnemies(latest));
            loadedData.setLevel(validateAndReturnLevel(latest));

            //unit of work complete, we now commit the change permanently
            repository.pullHard();

            return loadedData; //give the model a loader object to directly call validated data

        } catch (GameStateTransactionHandler.InvalidTransactionException e) {
            repository.pullHard(); //remove corrupted data
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }


    private void commit(GameState newState) {
        repository.push(newState);
    }


    /**
     * Convert the AbstractPlayer obtained from the model into a serialisable PlayerData object and store it
     * into the buffer GameState. Checks if the data to be stored exists in the right type
     * and can be stored correctly
     *
     * @param newState
     * @param newPlayer
     * @return
     */
    @SuppressWarnings("Duplicates")
    public boolean validateAndUpdatePlayer(GameState newState, AbstractPlayer newPlayer) {
        if (newPlayer == null) {
            return false;
        }

        //create a PlayerData object that makes serializable objects out of a not-entirely serializable AbstractPlayer
        //, particularly Box2D.
        PlayerData playerProps = new PlayerData(newPlayer);

        //now serialise the PlayerData object and add to Preferences

        String playerSer = "";
        try {
            playerSer = serializeInBase64(playerProps);

            newState.setPlayerInPref(playerSer);
            return true;

        } catch (IOException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }

    /**
     * Serialise the List of enemies obtained from the model and store it
     * into the buffer GameState. Checks if the data to be stored exists in the right type
     * and can be stored correctly
     *
     * @param newState
     * @param newFoes
     * @return
     */
    @SuppressWarnings("Duplicates")
    public boolean validateAndUpdateEnemies(GameState newState, List<AbstractEnemy> newFoes) {
        if (newFoes == null) {
            return false;
        }

        //now serialise the Enemies List and add to Preferences
        String enemiesSer = "";
        try {
            enemiesSer = serializeInBase64(newFoes);

            newState.setEnemiesInPref(enemiesSer);
            return true;

        } catch (IOException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }


    public boolean validateAndUpdateLevel(GameState newState, MockLevel newLevel) {
        if (newLevel == null) {
            return false;
        }

        //now serialise the Enemies List and add to Preferences
        String levelSer = "";
        try {
            levelSer = serializeInBase64(newLevel);

            newState.setLevelInPref(levelSer);
            return true;

        } catch (IOException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }


    /**
     * Deserialize the Player's data from the GameState and validate that the data is correct
     *
     * @param latest
     * @return validated PlayerData to be loaded into a StateQuery and transferred into a new
     * AbstractPlayer instance
     */
    public PlayerData validateAndReturnPlayerData(GameState latest) throws GameStateTransactionHandler.InvalidTransactionException {
        try {
            Object p = deserializeFromBase64(latest.getPref().getString("Player"));
            if (!(p instanceof PlayerData))
                throw new GameStateTransactionHandler.InvalidTransactionException("Deserialized player object isn't a PlayerData");

            return (PlayerData) p;
        } catch (IOException | ClassNotFoundException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }

    /**
     * Deserialize the List of enemies from the GameState and validate that the data is correct
     *
     * @param latest
     * @return validated List of AbstractEnemies to be loaded into model
     */
    public List<AbstractEnemy> validateAndReturnEnemies(GameState latest) throws GameStateTransactionHandler.InvalidTransactionException {
        try {
            Object e = deserializeFromBase64(latest.getPref().getString("Enemies"));

            if (!(e instanceof List))
                throw new GameStateTransactionHandler.InvalidTransactionException("Deserialized enemies object isn't a List");

            @SuppressWarnings("unchecked") List<AbstractEnemy> enemies = (List<AbstractEnemy>) e;

            for (int i = 0; i < enemies.size(); i++) {
                if (!(enemies.get(i) instanceof AbstractEnemy))
                    throw new GameStateTransactionHandler.InvalidTransactionException("Deserialized enemy list doesn't contain AbstractEnemies");
            }

            return enemies;
        } catch (IOException | ClassNotFoundException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }

    public MockLevel validateAndReturnLevel(GameState latest) throws GameStateTransactionHandler.InvalidTransactionException {
        try {
            Object l = deserializeFromBase64(latest.getPref().getString("Level"));

            if (!(l instanceof MockLevel))
                throw new GameStateTransactionHandler.InvalidTransactionException("Deserialized Level object isn't an MockLevel");

            return (MockLevel) l;

        } catch (IOException | ClassNotFoundException e) {
            throw new GameStateTransactionHandler.InvalidTransactionException(e.getMessage());
        }
    }


    /* ========================================================================================
       ============================= SERIALIZATION ============================================
       ========================================================================================
     */


    /**
     * Serialise an object and return its bytecode String
     *
     * @param o
     * @return
     * @throws IOException
     */
    public String serializeInBase64(Object o) throws IOException {
        String ser = "";
        try {
            //serialize our object
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(o);
            so.flush(); //persist

            ser = new String(Base64.getEncoder().encode(bo.toByteArray())); //convert the bytecode to a character String

            return ser;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


    public Object deserializeFromBase64(String ser) throws IOException, ClassNotFoundException {
        try {
            byte b[] = Base64.getDecoder().decode(ser.getBytes()); //decode the Base64 string into bytecode

            //deserialize the bytecode back into an Object
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);

            return si.readObject(); //return the object (will be casted in the parent method)
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            if (e instanceof IOException)
                throw new IOException(e.getMessage());
            else
                throw new ClassNotFoundException();
        }
    }
}
