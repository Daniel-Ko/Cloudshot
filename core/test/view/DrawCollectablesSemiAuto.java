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

public class DrawCollectablesSemiAuto extends ViewTest {

    /**
     * Test that the semiauto collectable is drawn successfully.
     * @throws InterruptedException
     */
    @Test
    public void testDrawCollectables_SemiAuto() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public List<AbstractCollectable> getCollectables() {
                List<AbstractCollectable> collectables = new ArrayList<>();
                AbstractWeapon pistol = CollectableFactory.produceAbstractWeapon(
                        AbstractWeapon.weapon_type.semiauto,
                        new Vector2(10,10));
                collectables.add(pistol);
                return collectables;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }
}
