package model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;

import java.util.List;

public interface GameModelInterface {

    AbstractPlayer getPlayer();

    List<AbstractEnemy> getEnemies();

    List<AbstractCollectable> getCollectables();

    OrthographicCamera getCamera();

    TiledMapRenderer getTiledMapRenderer();

    World getWorld();

    String getLevelName();

    void update();

    void load();

    void save();

    void setRepoScraper(GameStateTransactionHandler saveLoadHandler);

    void setLevel(AbstractLevel level);
}
