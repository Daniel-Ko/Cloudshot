import org.junit.Test;
import view.HealthBar;

import static org.junit.Assert.assertTrue;

public class GUIElementTest extends GameTest {
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
