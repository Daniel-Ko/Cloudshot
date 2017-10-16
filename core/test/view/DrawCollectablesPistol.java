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

public class DrawCollectablesPistol extends ViewTest {

    /**
     * Test that the pistol collectable is drawn successfully.
     * @throws InterruptedException
     */
    @Test
    public void testDrawCollectables_Pistol() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public List<AbstractCollectable> getCollectables() {
                List<AbstractCollectable> collectables = new ArrayList<>();
                AbstractWeapon pistol = CollectableFactory.produceAbstractWeapon(
                        AbstractWeapon.weapon_type.pistol,
                        new Vector2(10,10));
                collectables.add(pistol);
                return collectables;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }
}
