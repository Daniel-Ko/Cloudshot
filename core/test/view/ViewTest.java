
package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import mock.MockGameModel;
import model.being.EntityFactory;
import model.being.player.AbstractPlayer;
import org.junit.Test;
import view.CloudShotGame;

import java.io.File;

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
        game.dispose();
        Gdx.app.exit();
    }

}
