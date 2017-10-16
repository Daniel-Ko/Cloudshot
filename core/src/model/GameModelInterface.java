package model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractCollectable;
import model.data.GameStateTransactionHandler;
import model.mapObject.levels.AbstractLevel;

import java.util.List;

/**
 * GameModelInterface have methods that other classes use in order to get and set the
 * data in the model of the game. GameModelInterface is useful for testing the view.
 */
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
    void addEnemy(AbstractEnemy enemy);
    void setupCamera();
    void setupGame();
    void loadTerrain();
    void loadMusic();
    AbstractLevel getLevel();
    Array<Rectangle> getScaledTerrain();
}
