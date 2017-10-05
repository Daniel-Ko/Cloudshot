import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.Player;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import org.junit.Test;
import view.HealthBar;
import view.screens.GameScreen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewTest extends GameTest{

    @Test
    public void testHealthBar_Full(){
        int fullHP = 150;
        int currentHP = 150;
        HealthBar healthBar = new HealthBar(100, 10);
        healthBar.setValue(currentHP/fullHP);
        assertTrue(healthBar.getPercent()*100 == 100);
    }

    @Test
    public void testHealthBar_Half(){
        int halfHP = 75;
        int fullHP = 150;
        HealthBar healthBar = new HealthBar(100, 10);
        healthBar.setValue((float)halfHP/(float)fullHP);
        assertTrue(healthBar.getPercent()*100 == 50);
    }
}
