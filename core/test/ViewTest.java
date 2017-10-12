import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import view.CloudShotGame;

public class ViewTest{
    LwjglApplicationConfiguration config;

    @Before
    public void initialise(){
        config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 740;
        config.forceExit = false;
    }

    @Test
    public void testMenuScreen() throws InterruptedException {
        new LwjglApplication(new CloudShotGame(CloudShotGame.Screen.MENU), config);

        // Close the app after 3 seconds.
        Thread.sleep(3000);
        Gdx.app.exit();
    }


}
