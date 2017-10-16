import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.enemies.Boss1V2;
import model.being.enemies.Rogue;
import model.being.enemies.ShootingEnemy;
import model.being.enemies.Slime2;
import model.being.player.Player;
import model.collectable.AbstractWeapon;
import model.collectable.Pistol;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static model.GameModel.PPM;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author tomherdson
 * Test class for JUnit testing the level and its components.
 * LevelOne and its .tmx file are used to test the level.
 * When spawning the player, the specific values used are to put the player in a position desired for the test.
 */
public class LevelTest extends GameTest{

    private final float LEVEL_HEIGHT = 1920;

    AbstractLevel level;
    World world;
    Player p;

    @Before
    public void setup(){
        level = new LevelOne(false);
    }

    /**
     * Sets up game world in Box2D, so that gravity affects player.
     */
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

    /**
     * Initialises player in gameworld
     * @param loc location of player in game world
     */
    private void initialisePlayer(Vector2 loc) {
        p = new Player();
        p.initBox2D(world,loc);
        p.update(null);
    }

    /**
     * update game world by 1/30f timeStep
     * @param numTimes number of times to update
     * @param gm the game model
     */
    private void stepWorld(int numTimes, GameModel gm){
        for(int i = 0; i < numTimes; i++) {
            world.step(1 /30f, 12, 4);
            p.update(null);
            level.update(p,gm);
        }
    }

    /**
     * Step the game world numTimes by 1/30f
     * @param numTimes numTimes to step the world
     */
    private void stepWorld(int numTimes){
        for(int i = 0; i < numTimes; i++) {
            world.step(1 / 30f, 12, 4);
            p.update(null);
        }
    }

    /**
     * Move the player to the right numTimes
     * @param numTimes numTimes to call player.moveRight();
     * @param gm the gamemodel
     */
    private void movePlayerRight(int numTimes, GameModel gm){
        for (int i = 0; i < numTimes; i++) {
            p.moveRight();
            stepWorld(10,gm);
        }
    }

    /**
     * Checks the level name is correct
     */
    @Test
    public void testLevelName(){
        assertTrue(level.getLevelName().equals("Welcome to Cloudshot"));
    }

    /**
     * Test player is spawned at spawn location.
     */
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
        level.setSpikeBlocksLoaded(true);
        level.update(p,gm);
        assertTrue(p.getHealth()==0);//player should be dead
    }

    /**
     * Tests that, when the player is placed above game world, it falls onto the terrain and no further.
     */
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
        level.setSpikeBlocksLoaded(true);
        level.update(p,gm);
        assertFalse(p.getPos().equals(oldPos));//Player should have been teleported away from this location.

        //Check x/y coordinates are coords of the opposite portal, ie, check player has been teleported
        //y value is casted to int as it must not be precise.
        assertTrue(p.getPos().x == 2752.92f/GameModel.PPM);
        assertTrue((int)p.getPos().y == (int)((LEVEL_HEIGHT-1055.06f-65.09f)/GameModel.PPM));
    }

    /**
     * Tests that when player moves through an area that triggers enemy spawns, enemies are spawned.
     */
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
        assertTrue(gm.getEnemies().size() == 2+level.getNumSpikeBlocks());//there should be 2 enemies spawned, as specified in the spawn property.
        assertTrue(gm.getEnemies().get(3) instanceof Slime2);// Slime should be spawned
    }

    @Test
    public void testSpawnEnemiesRogue(){
        initialiseWorld();
        initialisePlayer(new Vector2(1134/GameModel.PPM,(LEVEL_HEIGHT-1298)/GameModel.PPM));

        GameModel gm = new GameModel();
        gm.setWorld(world);
        gm.setEnemies(new ArrayList<>());

        level.update(p,gm);
        assertTrue(gm.getEnemies().size() == 2+level.getNumSpikeBlocks());//accounting for 3 spike blocks
        assertTrue(gm.getEnemies().get(3) instanceof Rogue);
    }

    @Test
    public void testSpawnEnemiesShooter(){
        initialiseWorld();
        initialisePlayer(new Vector2(1228/GameModel.PPM,(LEVEL_HEIGHT-1503)/GameModel.PPM));

        GameModel gm = new GameModel();
        gm.setWorld(world);
        gm.setEnemies(new ArrayList<>());

        level.update(p,gm);
        assertTrue(gm.getEnemies().size() == 2+level.getNumSpikeBlocks());
        assertTrue(gm.getEnemies().get(3) instanceof ShootingEnemy);
    }

    @Test
    public void testSpawnEnemiesBoss(){
        initialiseWorld();
        initialisePlayer(new Vector2(461/GameModel.PPM,(LEVEL_HEIGHT-1640)/GameModel.PPM));

        GameModel gm = new GameModel();
        gm.setWorld(world);
        gm.setEnemies(new ArrayList<>());

        level.update(p,gm);
       /* assertTrue(gm.getEnemies().size() == 1);
        assertTrue(gm.getEnemies().get(0) instanceof Boss1V2);*/
    }

    /**
     * Tests that collectibles are loaded into the Level correctly.
     */
    @Test
    public void testCollectiblesLoad(){
        assertTrue(level.getCollectables().size() == 11);//there are 8 collectibles on this level
    }

    /**
     * Tests that collisions are loaded into the Level correctly.
     */
    @Test
    public void testCollisionsLoad(){
        assertTrue(level.getTiles().size == 18);
    }

    /**
     * Tests that portals are loaded into the Level correctly.
     */
    @Test
    public void testPortalsLoad(){
        assertTrue(level.getPortals().size() == 1);
    }

    /**
     * Tests that spawns are loaded into the Level correctly.
     */
    @Test
    public void testSpawnsLoad(){
        assertTrue(level.getSpawns().size() == 7);
    }

    /**
     * Tests that spikes are loaded into the Level correctly.
     */
    @Test
    public void testSpikesLoad(){
        assertTrue(level.getHurtyTiles().size == 5);
    }

    /**
     * Tests that dimensions of level are loaded into the Level correctly.
     */
    @Test
    public void testLevelTileMap(){
       // assertTrue(level.getLevelDimension().equals(new Dimension(3200,1920)));
    }

    /**
     * This test places a player above spike tiles and checks that they fall and are damaged by the spikes.
     */
    @Test
    public void testSpikeTiles(){
        initialiseWorld();
        initialisePlayer(new Vector2(1375/GameModel.PPM,(LEVEL_HEIGHT-1515)/GameModel.PPM));//spawn player above spikes

        GameModel gm = new GameModel();
        gm.setWorld(world);
        gm.setEnemies(new ArrayList<>());

        stepWorld(15, gm);//allow player to fall onto spikes
        assertTrue(p.getHealth()<150);//player should be damaged by spikes

    }

    @Test
    public void testSpikeBlocksLoaded(){
        initialiseWorld();
        initialisePlayer(level.getPlayerSpawnPoint());

        assertTrue(level.getNumSpikeBlocks() == 3);//There should be 3 spike blocks on test level
    }

    @Test
    public void testPlayerPicksUpCollectibles(){
        initialiseWorld();
        initialisePlayer(new Vector2(23/GameModel.PPM,level.getPlayerSpawnPoint().y));
        assertTrue(p.getInventory().isEmpty());

        GameModel gm = new GameModel();
        gm.setPlayer(p);
        gm.setLevel(level);
        gm.setWorld(world);
        gm.setEnemies(new ArrayList<>());

        for (int i = 0; i < 10; i++) {//run over collectible spawn
            p.moveRight();
            stepWorld(10,gm);
            gm.updateCollectables();
        }

        assertTrue(p.getInventory().size() == 1);
        assertTrue(p.getInventory().get(0).type == AbstractWeapon.weapon_type.pistol);//pistol shouldve been picked up
    }

    @Test
    public void testGoToNextLevel(){
        initialiseWorld();
        initialisePlayer(new Vector2(3017/GameModel.PPM,(LEVEL_HEIGHT-1064)/GameModel.PPM));//spawn on end zone

        GameModel gm = new GameModel();
        level.setSpikeBlocksLoaded(true);
        gm.setEnemies(new ArrayList<>());
        // level.update(p,gm);
        assertTrue(!level.hasPlayerWon(p,gm));

    }

}
