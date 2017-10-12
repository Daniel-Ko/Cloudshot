package mock;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import model.GameModelInterface;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractCollectable;

import java.util.List;

public class MockGameModel implements GameModelInterface {

    @Override
    public AbstractPlayer getPlayer() {
        return null;
    }

    @Override
    public List<AbstractEnemy> getEnemies() {
        return null;
    }

    @Override
    public List<AbstractCollectable> getCollectables() {
        return null;
    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }

    @Override
    public TiledMapRenderer getTiledMapRenderer() {
        return null;
    }

    @Override
    public void update() {

    }
}
