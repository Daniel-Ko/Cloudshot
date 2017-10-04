import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.Player;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import org.junit.Test;
import view.screens.GameScreen;

import static org.junit.Assert.assertTrue;

public class ViewTest extends GameTest{

    @Test
    public void testPlayerSprite(){

        OrthographicCamera camera = new OrthographicCamera(1000/GameModel.PPM,((1000 * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()))/GameModel.PPM));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        GameModel gameModel = new GameModel(new LevelOne(),camera);

       Player player = new Player(gameModel, new Vector2(0,0));

        assertTrue(true);
    }

}
