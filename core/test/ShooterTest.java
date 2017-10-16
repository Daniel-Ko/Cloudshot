import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.being.enemies.ShootingEnemy;
import model.being.player.Player;
import org.junit.Test;

public class ShooterTest extends GameTest{

    @Test
   public void TestCreation(){
        World w = new World(new Vector2(0,-10),true);
        Player p = new Player();
        ShootingEnemy s = new ShootingEnemy(w,p,new Vector2(0,0));

    }
}
