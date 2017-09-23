package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import model.being.AbstractEnemy;

import model.being.Player;
import model.mapObject.levels.AbstractLevel;

import java.util.ArrayList;
import java.util.List;


public class GameModel {

    Player player;
    List<AbstractEnemy> enemies;
    AbstractLevel level;

    private float elapsedTime = 0f;

    public GameModel(AbstractLevel level) {
        this.level = level;
        player = new Player(new Vector2(50,200), 50, 50, 100, 3);
        enemies = new ArrayList<>();
        Gdx.input.setInputProcessor(player);
    }

    public void updateState(float elapsedTime){
        this.elapsedTime = elapsedTime;
        updatePlayerModel();
        for(AbstractEnemy ae : enemies){
            ae.update();
        }
    }


    public void addEnemy(AbstractEnemy enemy){
        enemies.add(enemy);
    }

    public void draw(SpriteBatch sb){
        sb.draw(player.getImage().getFrameFromTime(elapsedTime),player.getX(),player.getY());
        for(AbstractEnemy ae : enemies){
            sb.draw(ae.getImage().getFrameFromTime(elapsedTime),ae.getX(),ae.getY());
        }
    }

    private void updatePlayerModel(){
        player.update(level.getTiles());
        for(AbstractEnemy e : enemies){
            player.attack(e);
        }
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return level.getTiledMapRenderer();
    }

    public Player getPlayer() {
        return player;
    }

    public List<AbstractEnemy> getEnemies() {
        return enemies;
    }

    public AbstractLevel getLevel() {
        return level;
    }
}
