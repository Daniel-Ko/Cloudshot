import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import model.GameModel;
import model.being.AbstractEnemy;
import model.being.AbstractPlayer;
import model.being.Player;
import model.mapObject.levels.LevelOne;
import org.junit.Test;

import java.util.ArrayList;

/**
 * NOTE TO TUTOR: We only just recently found a way test our game with our 3rd person library
 *                The GameTest.java is the solution to that, however i am currently unaware of how to use that
 *                class to create a instance of GameModel to test my library.
 *
 * */
public class PlayerTest extends GameTest{
    GameModel gameModel;
    OrthographicCamera camera;

    public PlayerTest(){
        camera = new OrthographicCamera(1000/GameModel.PPM,((1000 * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()))/GameModel.PPM));
        gameModel = new GameModel(new LevelOne(),camera);
    }

    @Test
    public void TestBulletsFiredIncreased(){
        Player p = (Player) gameModel.getPlayer();
        assert p.getBullets().size() == 0 : "Bullets should be 0";
        p.shoot();
        assert p.getBullets().size() >= 1 : "Bullets should be 0";

     }

    @Test
    public void TestDamagingPlayer(){

        Player p = (Player) gameModel.getPlayer();
        int initHP = p.getHealth();
        p.hit(5);
        assert p.getHealth() == (initHP-5);


    }
    @Test
    public void TestPlayerDeath(){
        Player p = (Player) gameModel.getPlayer();
        int initHP = p.getHealth();
        //may need to first update the player
        p.update(new ArrayList<AbstractEnemy>());
        p.hit(initHP);
        assert p.getPlayerState() == AbstractPlayer.player_state.DEAD;

    }

    @Test
    public void TestSettingPLayerHealth(){
        Player  p = (Player) gameModel.getPlayer();
        p.setHealth(2);
        assert p.getHealth() == 2;
        p.setHealth(100);
        assert p.getHealth() == 100;
    }

    @Test
    public void TestPlayerInteractionWithMeleeEnemy(){
        Player  p = (Player) gameModel.getPlayer();
        p.setMeleeRange(100);
       // p.attack()

    }
    @Test
    public void TestPlayerInteractionWithBossOne(){}
    @Test
    public void TestPlayerInteractionWithShooter(){}
}
