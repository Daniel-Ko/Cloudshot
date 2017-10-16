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

    /**
     * Get the player of the game.
     * @return
     *      AbstractPlayer of model of the game.
     */
    AbstractPlayer getPlayer();

    /**
     * Get the enemies of the game
     * @return
     *      Collections of the enemies in the model of the game at the current level.
     */
    List<AbstractEnemy> getEnemies();

    /**
     * Get the collectables of the game.
     * @return
     *      Collections of the collectables in the model of the game at the current level.
     */
    List<AbstractCollectable> getCollectables();

    /**
     * Get the camera of the game.
     * @return
     *      OrthographicCamera of model of the game.
     */
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
