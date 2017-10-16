import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import model.being.EntityFactory;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.player.Player;
import model.collectable.Pistol;
import model.collectable.Sniper;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.levels.LevelTwo;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
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

    private final float LEVEL_HEIGHT = 1920;

    AbstractLevel level;
    World world;
    Player p;

    @Before
    public void setup(){
        level = new LevelOne(false);
    }

    private void initialiseWorld(){
        world = new World(new Vector2(0,-10),true);

        for (Rectangle r : level.getTiles()) {
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

    }

    private void initialisePlayer(Vector2 loc) {
        p = new Player();
        p.initBox2D(world,loc);
        p.update(null);
    }

    private void stepWorld(int numTimes, GameModel gm){
        for(int i = 0; i < numTimes; i++) {
            world.step(1 /30f, 12, 4);
            p.update(null);
            level.update(p,gm);
        }
    }

    private void stepWorld(int numTimes){
        for(int i = 0; i < numTimes; i++) {
            world.step(1 / 30f, 12, 4);
            p.update(null);
        }
    }

    private void movePlayerRight(int numTimes, GameModel gm){
        for (int i = 0; i < numTimes; i++) {
            p.moveRight();
            stepWorld(10,gm);
        }
    }

    @Test
    public void testLevelName(){
        assertTrue(level.getLevelName().equals("Welcome to Cloudshot"));
    }

    @Test
    public void testSpawnLocation() {
        initialiseWorld();
        initialisePlayer(level.getPlayerSpawnPoint());
        assertTrue(p.getPos().equals(level.getPlayerSpawnPoint()));
    }

    /**
     * This test ensures player dies when it falls off map.
     * It places player in a position with no terrain under it, steps the world, then checks they have falled to their death.
     */
    @Test
    public void testPlayerDiesFromFalling(){
        initialiseWorld();
        initialisePlayer(new Vector2(-10,0));

        stepWorld(100);
        GameModel gm = new GameModel();
        gm.setEnemies(new ArrayList<>());
        level.update(p,gm);
        assertTrue(p.getHealth()==0);//player should be dead
    }

    @Test
    public void testPlayerCollidesWithTerrain(){
        initialiseWorld();
        initialisePlayer(new Vector2(2, 10));
        assertTrue(p.getPos().equals(new Vector2(2,10)));
        stepWorld(100);
        float yCord = p.getY();
        assertTrue(p.getX()==2);//x coordinate should be same
        assertTrue(p.getY() < 10);//player shouldve fallen
        stepWorld(100);
        assertTrue(yCord == p.getY());//player should have stopped falling when it collides with terrain
    }

    /**
     * Tests that a player is teleported when entering a portal.
     * This test puts the player within the portals bounding box, then updates the game.
     * First it checks that the player is no longer in the original position, then it checks that the player is in the new position
     * of the portal exit.
     */
    @Test
    public void testPortal(){
        initialiseWorld();
        initialisePlayer(new Vector2(2260/GameModel.PPM, (LEVEL_HEIGHT-1870)/GameModel.PPM));
        Vector2 oldPos = p.getPos();
        GameModel gm = new GameModel();
        gm.setEnemies(new ArrayList<>());
        level.update(p,gm);
        assertFalse(p.getPos().equals(oldPos));//Player should have been teleported away from this location.

        //Check x/y coordinates are coords of the opposite portal, ie, check player has been teleported
        //y value is casted to int as it must not be precise.
        assertTrue(p.getPos().x == 2752.92f/GameModel.PPM);
        assertTrue((int)p.getPos().y == (int)((LEVEL_HEIGHT-1055.06f-65.09f)/GameModel.PPM));
    }

    @Test
    public void testSpawnEnemies(){
        initialiseWorld();
        initialisePlayer(new Vector2(level.getPlayerSpawnPoint()));

        GameModel gm = new GameModel();
        gm.setWorld(world);
        gm.setEnemies(new ArrayList<>());

        assertTrue(gm.getEnemies().isEmpty());//there should be no enemies at level begin
        movePlayerRight(3, gm);//move player through location which triggers spawn
        assertFalse(gm.getEnemies().isEmpty());//there should now be enemies
        assertTrue(gm.getEnemies().size() == 2);//there should be 2 enemies spawned, as specified in the spawn property.
    }

    @Test
    public void testCollectiblesLoad(){
        assertTrue(level.getCollectables().size() == 8);//there are 8 collectibles on this level
    }

    @Test
    public void testCollisionsLoad(){
        assertTrue(level.getTiles().size == 18);
    }

    @Test
    public void testPortalsLoad(){
        assertTrue(level.getPortals().size() == 1);
    }

    @Test
    public void testSpawnsLoad(){
        assertTrue(level.getSpawns().size() == 5);
    }

    @Test
    public void testSpikesLoad(){
        assertTrue(level.getHurtyTiles().size == 5);
    }

    @Test
    public void testLevelTileMap(){
        assertTrue(level.getLevelDimension().equals(new Dimension(3200,1920)));
    }






}
