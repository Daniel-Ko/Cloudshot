
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

    /**
     * Test that the MenuScreen can be successfully rendered without errors.
     * @throws InterruptedException
     */
    @Test
    public void testMenuScreen() throws InterruptedException {
        Game menu = new CloudShotGame(CloudShotGame.Screen.MENU);
        launchAndExitIn3Seconds(menu);
    }

    /**
     * Test that the player can be drawn at the bottom left of the screen.
     * @throws InterruptedException
     */
    @Test
    public void testDrawPlayer_BottomLeft() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public AbstractPlayer getPlayer() {
                // Create and draw player the the bottom left of the screen.
                AbstractPlayer player = EntityFactory.producePlayer(this, new Vector2(0,0));
                player.setPos(new Vector2(1.0f, 1.0f));
                return player;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }

    /**
     * Test that the player can be drawn at the top right of the screen.
     * @throws InterruptedException
     */
    @Test
    public void testDrawPlayer_TopRight() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public AbstractPlayer getPlayer() {
                // Create and draw player the the bottom left of the screen.
                AbstractPlayer player = EntityFactory.producePlayer(this, new Vector2(0,0));
                player.setPos(new Vector2(10.0f, 10.0f));
                return player;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }

}
