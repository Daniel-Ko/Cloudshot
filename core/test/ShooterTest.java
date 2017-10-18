import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.being.enemies.ShootingEnemy;
import model.being.enemystates.Death;
import model.being.enemystates.IdleMovement;
import model.being.enemystates.ShooterAttack;
import model.being.player.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class ShooterTest extends GameTest{

    @Test
   public void TestCreation(){
        World w = new World(new Vector2(0,-10),true);
        Player p = new Player();
        ShootingEnemy s = new ShootingEnemy(w,p,new Vector2(0,0));
        assertEquals(s.getMaxHealth(),s.getHealth());
    }
    @Test
    public void TestShootersStates(){
        World w = new World(new Vector2(0,-10),true);
        Player p = new Player();
        ShootingEnemy s = new ShootingEnemy(w,p,new Vector2(0,0));
        //Should be just out of range of attack radius.
        p.setPos(new Vector2(s.attackRadius+1,0));
        s.update();
        p.update(new ArrayList<>());
        assertTrue(s.enemyState instanceof IdleMovement);

        //Should now be within attack range therefore state change to attacking.
        p.setPos(new Vector2(s.attackRadius-1,0));
        s.update();
        assertTrue(s.enemyState instanceof ShooterAttack);
        //changing back to idle state from ShooterAttack
        p.setPos(new Vector2(s.attackRadius+1,0));
        s.update();
        assertTrue(s.enemyState instanceof IdleMovement);
        s.hit(s.getHealth());
        s.update();
        assertTrue(s.enemyState instanceof Death);

    }
    @Test
    public void TestShooterFireBulletAtPlayer(){
        World w = new World(new Vector2(0,-10),true);
        Player p = new Player();
        ShootingEnemy s = new ShootingEnemy(w,p,new Vector2(0,0));
        //Should be within firing range.
        p.setPos(new Vector2(s.attackRadius-1,0));
        assertEquals(0,s.getBulletsShot().size());
        s.update();
        assertEquals(1,s.getBulletsShot().size());
        Vector2 position = s.getBulletsShot().peek().getPos();
        Vector2 oldPos = new Vector2(position.x, position.y);
        s.update();
        assertTrue(oldPos != position);
        //Checking that the actual bullet pos updates

    }
    @Test
    public void TestBulletDamagesPlayer() {


    }
    @Test
    public void TestNumberOfBulletsFired(){

    }

    @Test
    public void TestMaxNumberOfBulletsShotIs10(){

    }

    @Test
    public void TestVariablesDontUpdateIfDead(){

    }
}
