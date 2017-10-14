import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.Rogue;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.data.ModelData;
import model.mapObject.levels.Spawn;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

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
        GameStateTransactionHandler repoScraper = new GameStateTransactionHandler();

        AbstractPlayer pl = new Player();
        List<AbstractEnemy> enems = new ArrayList<>();
        List<AbstractCollectable> collects = new ArrayList<>();
        List<Rectangle> spawnTrigs = new ArrayList<>();
        List<Spawn> spawns = new ArrayList<>();

        enems.add(new Rogue(new World(new Vector2(0, -8), true), pl, new Vector2(15, 15)));

//        repoScraper.save(new ModelData(
//                pl, enems, collects, spawnTrigs, spawns
//        ));
    }


}
