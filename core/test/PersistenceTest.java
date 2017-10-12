import model.data.GameStateTransactionHandler;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Created by kodani on 28/09/17.
 */
public class PersistenceTest extends GameTest{

    @Test
    public void testSavingPlayerNull() {
        GameStateTransactionHandler nullCatcher = new GameStateTransactionHandler();

        assertFalse(nullCatcher.validateAndUpdatePlayer(null, null));
    }

    @Test
    public void popEmptyStack() {

    }

    @Test
    public void testValidSavePerson() {
        GameStateTransactionHandler nullCatcher = new GameStateTransactionHandler();
        assertFalse(false);

        //assertFalse(nullCatcher.validateAndUpdatePlayer(null
    }

    @Test
    public void testValidSave() {
        GameStateTransactionHandler nullCatcher = new GameStateTransactionHandler();

        //assertFalse(nullCatcher.validateAndUpdatePlayer(null
    }
}
