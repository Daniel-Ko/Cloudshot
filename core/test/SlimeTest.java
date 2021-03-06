import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.enemies.Slime2;
import model.being.enemystates.AggroMovement;
import model.being.enemystates.Death;
import model.being.enemystates.HorizontalMovement;
import model.being.enemystates.MeleeAttack;
import model.being.player.Player;
import model.mapObject.levels.LevelOne;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Jeremy Southon
 * */

public class SlimeTest extends GameTest {

    @Test
    public void TestDamagingEnemyAndKillingEnemy(){
        Slime2 s = new Slime2();
        int hp = s.getHealth();
        s.hit(1);
        assertTrue(hp>s.getHealth());//should be lower after hit
        //shouldnt be in dead state yet..
        assertFalse(s.enemyState instanceof Death);
        s.hit(hp);
        assertTrue(s.enemyState instanceof Death);
    }

    @Test
    public void TestAttackingPlayer(){
        Player p = new Player();
        p.setHealth(10);
        assertEquals(10,p.getHealth());
        Slime2 s = new Slime2();
        s.setPlayer(p);
        p.setPos(new Vector2(0,0));
        s.setPosition(new Vector2(0,0));
        s.update();
        assertTrue(p.getHealth()<10);
    }

    @Test
    public void tryingToAttackPlayerButFailed(){
        Player p = new Player();
        Slime2 s = new Slime2();
        p.setHealth(10);
        s.setPlayer(p);
        p.setPos(new Vector2(s.attackRadius+1,0));
        s.update();
        assertTrue(p.getHealth()==10);
        //Re checking that slime can hurt enemy if its within attack range
        s.setPosition(new Vector2(s.attackRadius+1,0));
        s.update();
        assertTrue(p.getHealth()<10);
    }

    @Test
    public void TestSlimesState(){
        Player p = new Player();
        Slime2 s = new Slime2();
        s.setPlayer(p);
        s.setPosition(new Vector2(0,0));
        //The player should now be just out of detection radius therefore
        //will be in its idle (horizontalMovement) state
        p.setPos(new Vector2(s.detectionRadius,0));
        s.update();
        assertTrue(s.enemyState instanceof HorizontalMovement);
        //Now player should be in aggro radius therefore state should be aggromovement
        p.setPos(new Vector2(s.detectionRadius-1,0));
        s.update();
        assertTrue(s.enemyState instanceof AggroMovement);
    }

    @Test
    public void TestAttackState(){
        Player p = new Player();
        Slime2 s = new Slime2();
        s.setPlayer(p);
        p.setHealth(50);
        //within attack range, state should change to attack melee state.
        p.setPos(new Vector2(s.attackRadius-0.1f,0));
        s.update();
        assertTrue(s.enemyState instanceof MeleeAttack);
        assertFalse(p.getHealth() == 50);

    }

    @Test
    public void TestMainConstructor(){
        World world = new World(new Vector2(0, 10), true);
        Player p = new Player();
        p.initBox2D(world,new Vector2(0,0));
        Slime2 s = new Slime2(world,p,new Vector2(0,1));

    }
    @Test
    public void TestUpdate(){
        World world = new World(new Vector2(0, 10), true);
        Player p = new Player();
        Slime2 s = new Slime2(world,p,new Vector2(0,0));// s.update();
        s.update();
    }

    @Test
    public void TestDeathAction(){
        World world = new World(new Vector2(0, 10), true);
        //Setting up model to give to slime to allow it to add its babys to game
        GameModel gm = new GameModel();
        gm.setLevel(new LevelOne(false));
        gm.setupGame();

        Player p = new Player();
        Slime2 s = new Slime2(world,p,new Vector2(0,0));// s.update();
        s.provideGameModel(gm);
        assertEquals(0,gm.getEnemies().size());
        s.hit(s.getHealth());
        s.update();
        assertTrue(s.enemyState instanceof Death);
    }
}
