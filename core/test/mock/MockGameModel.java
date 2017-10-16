package mock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import model.GameModel;
import model.GameModelInterface;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;

import java.util.ArrayList;
import java.util.List;

public class MockGameModel implements GameModelInterface {

    @Override
    public AbstractPlayer getPlayer() {
        return EntityFactory.producePlayer(this, new Vector2(-50,-50));
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
        return new LevelOne().getTiledMapRenderer();
    }

    @Override
    public World getWorld() {
        return new World(new Vector2(0, -8), true);
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

    @Override
    public void setupCamera() {

    }

    @Override
    public void setupGame() {

    }

    @Override
    public void loadTerrain() {

    }

    @Override
    public void loadMusic() {

    }

    @Override
    public Array<Rectangle> getScaledTerrain() {
        return null;
    }

    @Override
    public void setRepoScraper(GameStateTransactionHandler saveLoadHandler) {

    }

    @Override
    public void setLevel(AbstractLevel level) {

    }

    @Override
    public void addEnemy(AbstractEnemy enemy) {

    }
}
