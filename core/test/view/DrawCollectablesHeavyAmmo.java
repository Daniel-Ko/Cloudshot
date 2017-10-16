package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import mock.MockGameModel;
import model.collectable.AbstractBuff;
import model.collectable.AbstractCollectable;
import model.collectable.CollectableFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DrawCollectablesHeavyAmmo extends ViewTest {
    /**
     * Test that the heavy ammo collectable is drawn successfully.
     * @throws InterruptedException
     */
    @Test
    public void testDrawCollectables_HeavyAmmo() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public List<AbstractCollectable> getCollectables() {
                List<AbstractCollectable> collectables = new ArrayList<>();
                AbstractBuff healthpack = CollectableFactory.produceAbstractBuff(
                        AbstractBuff.buff_type.heavyammo,
                        new Vector2(10,10));
                collectables.add(healthpack);
                return collectables;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }
}
