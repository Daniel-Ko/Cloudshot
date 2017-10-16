package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import mock.MockGameModel;
import model.collectable.AbstractCollectable;
import model.collectable.AbstractWeapon;
import model.collectable.CollectableFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DrawCollectablesShotGun extends ViewTest {

    /**
     * Test that the shotgun collectable is drawn successfully.
     * @throws InterruptedException
     */
    @Test
    public void testDrawCollectables_Shotgun() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public List<AbstractCollectable> getCollectables() {
                List<AbstractCollectable> collectables = new ArrayList<>();
                AbstractWeapon pistol = CollectableFactory.produceAbstractWeapon(
                        AbstractWeapon.weapon_type.shotgun,
                        new Vector2(10,10));
                collectables.add(pistol);
                return collectables;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }
}
