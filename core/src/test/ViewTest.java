import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.being.Player;
import org.junit.Test;

public class ViewTest {

    @Test
    public void testPlayerSprite(){
        World world = new World(new Vector2(0, -10), true);
        Player player = new Player(new Vector2(50,500), 8, 8, 100, 3,world);
    }

}
