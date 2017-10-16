package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import mock.MockGameModel;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DrawMultipleEnemies2 extends ViewTest {

    /**
     * Test that the mutiple enemies as well as their HP bars are drawn.
     * @throws InterruptedException
     */
    @Test
    public void testDrawMultipleEnemies() throws InterruptedException {
        MockGameModel gameModel = new MockGameModel(){
            @Override
            public List<AbstractEnemy> getEnemies() {
                List<AbstractEnemy> enemies = new ArrayList<>();
                AbstractEnemy enemy1 = EntityFactory.produceEnemy(this,
                        new Vector2(5, 5),
                        AbstractEnemy.entity_type.spikeblock);
                AbstractEnemy enemy2 = EntityFactory.produceEnemy(this,
                        new Vector2(10, 5),
                        AbstractEnemy.entity_type.slime);
                AbstractEnemy enemy3 = EntityFactory.produceEnemy(this,
                        new Vector2(10, 10),
                        AbstractEnemy.entity_type.rogue);
                AbstractEnemy enemy4 = EntityFactory.produceEnemy(this,
                        new Vector2(15, 5),
                        AbstractEnemy.entity_type.boss1);
                enemy3.internalDamage(50);
                enemy4.internalDamage(80);
                enemies.add(enemy1);
                enemies.add(enemy2);
                enemies.add(enemy3);
                enemies.add(enemy4);
                return enemies;
            }
        };
        Game game = new CloudShotGame(CloudShotGame.Screen.TEST, gameModel);
        launchAndExitIn3Seconds(game);
    }

}
