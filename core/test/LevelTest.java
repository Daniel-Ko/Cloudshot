import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import org.junit.Before;
import org.junit.Test;


import org.junit.Test;


import static org.junit.Assert.*;

public class LevelTest extends GameTest{

    TiledMap tm;

    @Before
    public void setUp(){
        tm = new TmxMapLoader().load("levels/testMap.tmx");
    }

    @Test
    public void testLevel01() {
        assertTrue(tm.getLayers().getCount()==2);//there should be two layers.

    }

    @Test
    public void testLevel02() {
        MapLayer layer = tm.getLayers().get("Object Layer 1");
        assertTrue(layer.getObjects().getCount() == 5);//there are 5 objects on the test map.
    }

    /**
     * Tests that the first bounding box rectangle contains a point.
     */
    @Test
    public void testLevel03(){
        MapLayer layer = tm.getLayers().get("Object Layer 1");
        RectangleMapObject r = (RectangleMapObject)layer.getObjects().get(0);
        assertTrue(r.getRectangle().contains(50,50));//should contain this point
        assertFalse(r.getRectangle().contains(700,50));//shouldn't contain this point
    }

    @Test
    public void testLevel04(){
        //AbstractLevel level = new LevelOne();
    }



}
