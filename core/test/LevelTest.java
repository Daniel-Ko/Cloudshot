import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.player.Player;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.levels.LevelTwo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static model.GameModel.PPM;
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
    public void test2(){
        //assertTrue(level.getNextLevel() instanceof LevelTwo);
    }

    @Test
    public void test3(){
        World world = new World(new Vector2(0,-10),true);
        AbstractLevel lvl = new LevelOne(false);
        lvl.getPlayerSpawnPoint();
        for (Rectangle r : lvl.getTiles()) {
            // Load the terrains.
            BodyDef terrainPiece = new BodyDef();
            terrainPiece.type = BodyDef.BodyType.StaticBody;
            terrainPiece.position.set(new Vector2((r.x + r.width / 2) / 50, (r.y + r.height / 2) / 50));

            // Load the ground body.
            Body groundBody = world.createBody(terrainPiece);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox((r.width / 2) / PPM, (r.height / 2) / PPM);

            // User data to tell us what things are colliding.
            groundBody.createFixture(groundBox, 0.0f).setUserData("platform");
            groundBox.dispose();
        }
        Player p = new Player();
        p.initBox2D(world,lvl.getPlayerSpawnPoint());
        p.update(new ArrayList<>());

        System.out.println(p.getPos());
        p.setPos(new Vector2(-10,0));
        world.step(20, 12, 4);
        p.update(null);

    }


}
