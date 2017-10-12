package mock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.GameModelInterface;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractCollectable;

import java.util.ArrayList;
import java.util.List;

public class MockGameModel implements GameModelInterface {

    @Override
    public AbstractPlayer getPlayer() {
        return EntityFactory.producePlayer(null, new Vector2(50,500));
    }

    @Override
    public List<AbstractEnemy> getEnemies() {
        return new ArrayList<>();
    }

    @Override
    public List<AbstractCollectable> getCollectables() {
        return new ArrayList<>();
    }

    @Override
    public OrthographicCamera getCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        OrthographicCamera camera = new OrthographicCamera(1000 / GameModel.PPM,
                ((1000 * (h / w)) / GameModel.PPM)
        );
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        return camera;
    }

    @Override
    public TiledMapRenderer getTiledMapRenderer() {
        return null;
    }

    @Override
    public String getLevelName() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
