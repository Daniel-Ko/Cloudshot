import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.being.enemies.Rogue;
import model.being.enemystates.AggroDash;
import model.being.enemystates.Death;
import model.being.enemystates.IdleMovement;
import model.being.enemystates.MeleeAttack;
import model.being.player.Player;
import org.junit.Test;
import static org.junit.Assert.*;
public class RogueTest extends GameTest {

    @Test
    public void TestCreation(){
        World w = new World(new Vector2(0,-10),true);
        Player p = new Player();
        Rogue r = new Rogue(w,p,new Vector2(0,0));
        Rogue r2 = new Rogue();
        assertEquals(r.getMaxHealth(),r.getHealth());
    }

    @Test
    public void TestStates(){
        World w = new World(new Vector2(0,-10),true);
        Player p = new Player();
        Rogue r = new Rogue(w,p,new Vector2(0,0));
        //within detection radius
        p.setPos(new Vector2(r.detectionRadius-0.1f,0));
        r.update();
        assertTrue(r.enemyState instanceof AggroDash);
        p.setPos(new Vector2(r.detectionRadius+0.1f,0));
        r.update();
        assertTrue(r.enemyState instanceof IdleMovement);
        p.setPos(new Vector2(r.attackRadius,0));
        r.update();
        assertTrue(r.enemyState instanceof MeleeAttack);
        r.hit(r.getHealth());
        r.update();
        assertTrue(r.enemyState instanceof Death);
    }

    @Test
    public void TestDamagingPlayer(){
        World w = new World(new Vector2(0,-10),true);
        Player p = new Player();
        p.setHealth(50);
        Rogue r = new Rogue(w,p,new Vector2(0,0));
        //within detection radius
        p.setPos(new Vector2(r.attackRadius,0));
        r.update();
        r.update();//FIXME
        assertTrue(p.getHealth()<50);
    }
}
