package view;

import com.badlogic.gdx.Game;
import org.junit.Test;

public class DrawMenuScreen extends ViewTest{

    /**
     * Test that the MenuScreen can be successfully rendered without errors.
     * @throws InterruptedException
     */
    @Test
    public void testDrawMenuScreen() throws InterruptedException {
        Game menu = new CloudShotGame(CloudShotGame.Screen.MENU);
        launchAndExitIn3Seconds(menu);
    }
}
