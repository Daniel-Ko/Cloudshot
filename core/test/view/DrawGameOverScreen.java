package view;

import com.badlogic.gdx.Game;
import org.junit.Test;

public class DrawGameOverScreen extends ViewTest {

    /**
     * Test that the GameOverScreen can be successfully rendered without errors.
     * @throws InterruptedException
     */
    @Test
    public void testDrawGameOverScreen() throws InterruptedException {
        Game menu = new CloudShotGame(CloudShotGame.Screen.GAME_OVER);
        launchAndExitIn3Seconds(menu);
    }
}
