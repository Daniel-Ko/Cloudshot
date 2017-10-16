package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import mock.MockGameModel;
import model.collectable.AbstractBuff;
import model.collectable.AbstractCollectable;
import model.collectable.AbstractWeapon;
import model.collectable.CollectableFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kodani on 16/10/17.
 */
public class DrawMultipleCollectables extends ViewTest {
    /**
     * Test that the death pack collectable is drawn successfully.
     * @throws InterruptedException
     */
    @Test
    public void testDrawMultipleCollectables() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public List<AbstractCollectable> getCollectables() {
                List<AbstractCollectable> collectables = new ArrayList<>();
                AbstractBuff healthpack = CollectableFactory.produceAbstractBuff(
                        AbstractBuff.buff_type.health,
                        new Vector2(5,5));
                AbstractWeapon shotgun = CollectableFactory.produceAbstractWeapon(
                        AbstractWeapon.weapon_type.shotgun,
                        new Vector2(5,5));
                collectables.add(healthpack);
                collectables.add(shotgun);
                return collectables;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }
}
