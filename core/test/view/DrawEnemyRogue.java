package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import mock.MockGameModel;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DrawEnemyRogue extends ViewTest {

    /**
     * Test that the rogue enemy as well as its HP bar is drawn.
     * @throws InterruptedException
     */
    @Test
    public void testDrawEnemy_Rogue() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public List<AbstractEnemy> getEnemies() {
                List<AbstractEnemy> enemies = new ArrayList<>();
                AbstractEnemy enemy = EntityFactory.produceEnemy(this,
                        new Vector2(5, 5),
                        AbstractEnemy.entity_type.rogue);
                enemies.add(enemy);
                return enemies;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }
}
