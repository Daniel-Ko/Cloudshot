
package model.data;

import com.badlogic.gdx.Gdx;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.being.player.PlayerData;
import model.collectable.AbstractCollectable;

import java.io.*;
import java.util.Base64;
import java.util.List;


/**
 * Created by Dan Ko on 9/19/2017.
 */
public class GameStateTransactionHandler {
    private GameStateRepository repository;

    public GameStateTransactionHandler() {
        repository = new GameStateRepository();
    }

    public boolean save(AbstractPlayer pl, List<AbstractEnemy> enems, List<AbstractCollectable> collects) {
        GameState newState = new GameState(
                Gdx.app.getPreferences(
                        "save" + repository.latestSaveNum() //always a unique name and should stay dynamic + consistent throughout repo operations
                ));
        
        if(writeQuery(pl, enems, collects, newState)) {
            commit(newState);
            File f = new File(".");
            System.out.println(f.getAbsolutePath()); //print the path of where it was saved for testing
            return true;
        }
        return false;
    }

    private boolean writeQuery(AbstractPlayer pl, List<AbstractEnemy> enems, List<AbstractCollectable> collects, GameState newState) {

        /* spawnEnemies the newState with validated data, otherwise signal failed save */
        if(!validateAndUpdatePlayer(newState, pl)) {
            System.out.println("bad player");
            return false;
        }
        if(!validateAndUpdateEnemies(newState, enems)) {
            System.out.println("bad enemies");
            return false;
        }
        if(!validateAndUpdateCollectables(newState, collects)) {
            System.out.println("bad items");
            return false;
        }
        return true;
    }

    /** sends signal to repo to pull the latestSaveNum state and wrap it in a StateQuery to the model
     *
     * @return
     */
    public StateQuery load() throws InvalidTransactionException {
        GameState latest = repository.pullSoft();
        if(latest == null)
            return null;

        if(!latest.containsPlayer() || !latest.containsEnemies()) {
            repository.pullHard(); //cleanse the repo of the bad state
            throw new InvalidTransactionException("Corrupted state");
        }

        try {
            PlayerData validatedPlayerData = validateAndReturnPlayerData(latest);
            List<AbstractEnemy> validatedEnemies = validateAndReturnEnemies(latest);
            List<AbstractCollectable> validatedCollectables = validateAndReturnCollectables(latest);

            //if all data is valid, remove it from stack finally
            //repository.pullHard();
            System.out.println(repository.latestSaveNum());
            return new StateQuery(validatedPlayerData, validatedEnemies, validatedCollectables); //give the model a loader object to directly call validated data

        } catch (InvalidTransactionException e) {
            repository.pullHard(); //remove corrupted data
            throw new InvalidTransactionException(e.getMessage());
        }
    }


    private void commit(GameState newState) {
        repository.push(newState);
    }
    
    
    /** Convert the AbstractPlayer obtained from the model into a serialisable PlayerData object and store it
     * into the buffer GameState. Checks if the data to be stored exists in the right type
     * and can be stored correctly
     * @param newState
     * @param newPlayer
     * @return
     */
    @SuppressWarnings("Duplicates")
    public boolean validateAndUpdatePlayer(GameState newState, AbstractPlayer newPlayer) {
        if(newPlayer == null) {
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
        
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** Serialise the List of enemies obtained from the model and store it
     * into the buffer GameState. Checks if the data to be stored exists in the right type
     * and can be stored correctly
     * @param newState
     * @param newFoes
     * @return
     */
    @SuppressWarnings("Duplicates")
    private boolean validateAndUpdateEnemies(GameState newState, List<AbstractEnemy> newFoes) {
        if(newFoes == null ) {
            return false;
        }
    
        //now serialise the Enemies List and add to Preferences
        String enemiesSer = "";
        try {
            enemiesSer = serializeInBase64(newFoes);
            
            newState.setEnemiesInPref(enemiesSer);
            return true;
            
        } catch(IOException e) {
            return false;
        }
    }

    /** Serialise the List of collectables obtained from the model and store it
     * into the buffer GameState. Checks if the data to be stored exists in the right type
     * and can be stored correctly
     * @param newState
     * @param newItems
     * @return
     */
    @SuppressWarnings("Duplicates")
    private boolean validateAndUpdateCollectables(GameState newState, List<AbstractCollectable> newItems) {
        if(newItems == null ) {
            return false;
        }

        //now serialise the Enemies List and add to Preferences
        String itemsSer = "";
        try {
            itemsSer = serializeInBase64(newItems);

            newState.setCollectablesInPref(itemsSer);
            return true;

        } catch(IOException e) {
            return false;
        }
    }

    /** Deserialize the Player's data from the GameState and validate that the data is correct
     *
     * @param latest
     * @return validated PlayerData to be loaded into a StateQuery and transferred into a new
     * AbstractPlayer instance
     */
    private PlayerData validateAndReturnPlayerData(GameState latest) throws InvalidTransactionException{
        try {
            Object p = deserializeFromBase64(latest.getPref().getString("Player"));
            if(!(p instanceof PlayerData))
                throw new InvalidTransactionException("Deserialized player object isn't a PlayerData");
    
            PlayerData playerdata = (PlayerData) p; //TODO any more checks on this player?

            return playerdata;
        } catch(IOException | ClassNotFoundException e) {
            throw new InvalidTransactionException(e.getMessage());
        }
    }

    /** Deserialize the List of enemies from the GameState and validate that the data is correct
     *
     * @param latest
     * @return validated List of AbstractEnemies to be loaded into model
     */
    private List<AbstractEnemy> validateAndReturnEnemies(GameState latest) throws InvalidTransactionException{
        try {
            Object e = deserializeFromBase64(latest.getPref().getString("Enemies"));

            if(!(e instanceof List))
                throw new InvalidTransactionException("Deserialized enemies object isn't a List");

            List<AbstractEnemy> enemies = (List<AbstractEnemy>) e; 

            for(int i = 0; i < enemies.size(); i++) {
                if(!(enemies.get(i) instanceof AbstractEnemy))
                    throw new InvalidTransactionException("Deserialized enemy list doesn't contain AbstractEnemies");
            }

            return enemies;
        } catch(IOException | ClassNotFoundException e) {
            throw new InvalidTransactionException(e.getMessage());
        }
    }

    /** Deserialize the List of Collectables from the GameState and validate that the data is correct
     *
     * @param latest
     * @return validated List of AbstractCollectables to be loaded into model
     */
    private List<AbstractCollectable> validateAndReturnCollectables(GameState latest) throws InvalidTransactionException{
        try {
            Object c = deserializeFromBase64(latest.getPref().getString("Collectables"));

            if(!(c instanceof List))
                throw new InvalidTransactionException("Deserialized collectables object isn't a List");

            List<AbstractCollectable> collectables = (List<AbstractCollectable>) c;
            
            //check each element in the list
            for(int i = 0; i < collectables.size(); i++) {
                if(!(collectables.get(i) instanceof AbstractCollectable))
                    throw new InvalidTransactionException("Deserialized enemy list doesn't contain Abstractcollectables");
            }

            return collectables;
        } catch(IOException | ClassNotFoundException e) {
            throw new InvalidTransactionException(e.getMessage());
        }
    }


    /* ========================================================================================
       ============================= SERIALIZATION ============================================
       ========================================================================================
     */



    /** Serialise an object and return its bytecode String
     *
     * @param o
     * @return
     * @throws IOException
     */
    private String serializeInBase64(Object o) throws IOException {
        String ser = "";
        try {
            //serialize our object
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(o);
            so.flush(); //persist
        
            ser = new String(Base64.getEncoder().encode(bo.toByteArray())); //convert the bytecode to a character String

            return ser;
        } catch(IOException e) {
            throw new IOException(e.getMessage());
        }
    }


    private Object deserializeFromBase64(String ser) throws IOException, ClassNotFoundException {
        try {
            byte b[] = Base64.getDecoder().decode(ser.getBytes()); //decode the Base64 string into bytecode

            //deserialize the bytecode back into an Object
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);

            return (Object) si.readObject(); //return the object (will be casted in the parent method)
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            if(e instanceof IOException)
                throw new IOException(e.getMessage());
            else
                throw new ClassNotFoundException();
        }
    }


    /** Upon invalid or missing data, this exception will be thrown to rollback all changes
     * done upon the database
     */
    public class InvalidTransactionException extends RuntimeException {
        public InvalidTransactionException(String msg) {
            super(msg);
        }
    }
}
