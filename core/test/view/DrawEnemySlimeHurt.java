package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import mock.MockGameModel;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DrawEnemySlimeHurt extends ViewTest {

    /**
     * Test that the slime enemy as well as its HP bar with its damage is drawn.
     * @throws InterruptedException
     */
    @Test
    public void testDrawEnemy_Slime() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public List<AbstractEnemy> getEnemies() {
                List<AbstractEnemy> enemies = new ArrayList<>();
                AbstractEnemy enemy = EntityFactory.produceEnemy(this,
                        new Vector2(5, 5),
                        AbstractEnemy.entity_type.slime);
                enemy.internalDamage(10);
                enemies.add(enemy);
                return enemies;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }
}
