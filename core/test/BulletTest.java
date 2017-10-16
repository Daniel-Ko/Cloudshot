import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.Slime2;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.projectile.BulletImpl;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jake on 16/10/17.
 * Externally tested by Tom.
 */
public class BulletTest extends GameTest{

    @Test
    public void testBulletDamgesEnemy(){
        BulletImpl b = new BulletImpl(new Vector2(0,0),new Vector2(0,0),10,null,true);
        Slime2 slime = new Slime2();
        slime.setPosition(new Vector2(0,0));
        slime.setHealth(20);
        ArrayList<AbstractEnemy> enemies = new ArrayList<>();
        enemies.add(slime);
        AbstractPlayer p = new Player();
        Array mine = new Array<>();
        b.update(enemies,p,mine);
        assertTrue(slime.getHealth() == 10);
    }

    @Test
    public void testBulletDamgesPlayer(){
        BulletImpl b = new BulletImpl(new Vector2(0,0),new Vector2(0,0),10,null,false);
        ArrayList<AbstractEnemy> enemies = new ArrayList<>();
        AbstractPlayer p = new Player();
        p.setPos(new Vector2(0,0));
        Array mine = new Array<>();
        b.update(enemies,p,mine);
        assertTrue(p.getHealth() == 140);
    }


    /**
     * External test by Tom.
     * tests that when bullet is fired at enemy, the bullet is set to be removed so that it doesnt continue flying across map.
     */
    @Test
    public void testBulletSetToRemove(){
        BulletImpl b = new BulletImpl(new Vector2(0,0),new Vector2(0,0),10,null,true);
        Slime2 slime = new Slime2();
        slime.setPosition(new Vector2(0,0));
        slime.setHealth(20);
        ArrayList<AbstractEnemy> enemies = new ArrayList<>();
        enemies.add(slime);
        AbstractPlayer p = new Player();
        Array mine = new Array<>();
        b.update(enemies,p,mine);
        assertTrue(b.isToRemove());
    }

    /**
     * External test by Tom.
     * Tests that player cannot damage itself with its own bullet (by running into them)
     */
    @Test
    public void testPlayerBulletDoesntDamagePlayer(){
        //true = player bullet
        BulletImpl b = new BulletImpl(new Vector2(0,0),new Vector2(0,0),10,null,true);
        ArrayList<AbstractEnemy> enemies = new ArrayList<>();
        AbstractPlayer p = new Player();
        p.setPos(new Vector2(0,0));
        Array mine = new Array<>();
        b.update(enemies,p,mine);
        assertTrue(p.getHealth() == 150);
        assertFalse(b.isToRemove());
    }

    /**
     * External test by Tom.
     * Tests that enemy bullets dont damage enemies.
     */
    @Test
    public void testEnemyBulletDoesntDamageEnemy(){
        //false for enemy bullet
        BulletImpl b = new BulletImpl(new Vector2(0,0),new Vector2(0,0),10,null,false);
        Slime2 slime = new Slime2();
        slime.setPosition(new Vector2(0,0));
        slime.setHealth(20);
        ArrayList<AbstractEnemy> enemies = new ArrayList<>();
        enemies.add(slime);
        AbstractPlayer p = new Player();
        Array mine = new Array<>();
        b.update(enemies,p,mine);
        assertTrue(slime.getHealth() == 20);
    }
    /**
     * External test by Tom.
     * Tests that bullets are stopped when they hit terrain.
     */
    @Test
    public void testBulletRemovedByTerrain(){
        Rectangle r = new Rectangle(0,0,10,10);
        BulletImpl b = new BulletImpl(new Vector2(0,0),new Vector2(0,0),10,null,false);
        ArrayList<AbstractEnemy> enemies = new ArrayList<>();
        AbstractPlayer p = new Player();
        Array mine = new Array<>();
        mine.add(r);
        b.update(enemies,p,mine);
        assertTrue(b.isToRemove());
    }
}
