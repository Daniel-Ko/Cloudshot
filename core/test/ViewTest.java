import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.junit.Test;
import view.CloudShotGame;

public class ViewTest{

    void launchAndExitIn3Seconds(Game game) throws InterruptedException {
        // Setup the launcher.
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 740;
        config.forceExit = false;

        // Launch the application.
        new LwjglApplication(game, config);

        // Close the app after 3 seconds.
        Thread.sleep(3000);
        Gdx.app.exit();
    }

    @Test
    public void testMenuScreen() throws InterruptedException {
        Game menu = new CloudShotGame(CloudShotGame.Screen.MENU);
        launchAndExitIn3Seconds(menu);
    }

}
