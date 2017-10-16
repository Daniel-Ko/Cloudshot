import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import model.being.EntityFactory;
import model.being.player.Player;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.levels.LevelTwo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LevelTest extends GameTest{
    //test spikes hurt player.
    //test player dies when falling off map
    //test player stands on collidable
    //test player is teleported by portal
    //test player cannot use portal twice
    //test player goes to next level when in endzone
    //test correct weapons are spawned in locations

    AbstractLevel level;

    @Before
    public void setup(){
        level = new LevelOne(false);
    }

    @Test
    public void test1(){
        assertTrue(level.getLevelName().equals("Welcome to Cloudshot"));
    }

    @Test
    public void testSpawnLocation(){
        Player p = new Player();

    }


}
