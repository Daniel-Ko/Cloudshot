package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import mock.MockGameModel;
import model.being.EntityFactory;
import model.being.player.AbstractPlayer;
import org.junit.Test;

public class DrawPlayerBottomLeft extends ViewTest {
    /**
     * Test that the player can be drawn at the top right of the screen.
     * @throws InterruptedException
     */
    @Test
    public void testDrawPlayer_BottomLeft() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public AbstractPlayer getPlayer() {
                // Create and draw player the the bottom left of the screen.
                AbstractPlayer player = EntityFactory.producePlayer(this, new Vector2(0,0));
                player.setPos(new Vector2(1, 1));
                return player;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }
}
