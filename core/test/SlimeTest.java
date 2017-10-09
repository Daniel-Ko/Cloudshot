
import com.badlogic.gdx.math.Vector2;
import model.being.AbstractPlayer;
import model.being.Death;
import model.being.Player;
import model.being.Slime2;
import model.collectable.Pistol;
import model.collectable.Shotgun;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class SlimeTest extends GameTest {

    /*@Test
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
    }*/
}
