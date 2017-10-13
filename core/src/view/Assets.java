package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;
import view.sprites.StaticSprite;

public class Assets {

    public static Skin gameSkin;

    /**
     * Player sprites.
     */
    public static CustomSprite playerIdle;
    public static CustomSprite playerAttack;
    public static CustomSprite playerJump;
    public static CustomSprite playerWalk;
    public static CustomSprite playerDeath;

    /**
     * Shooting enemy sprites.
     */
    public static CustomSprite shootingEnemyBulletSprite;
    public static CustomSprite shootingEnemyAttackingRight;
    public static CustomSprite shootingEnemyAttackingLeft;
    public static CustomSprite shootingEnemyWalking;
    public static CustomSprite shootingEnemyIdle;

    public static void load(){
        gameSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        playerIdle = new MovingSprite("player_idle.png", 2, 2);
        playerAttack = new MovingSprite("player_attack.png", 2, 3);
        playerJump = new MovingSprite("player_jump.png", 2, 3);
        playerWalk = new MovingSprite("player_walk.png", 3, 3);
        playerDeath = new MovingSprite("player_death.png", 1, 1);

        shootingEnemyBulletSprite =  new StaticSprite("arrow.png");
        shootingEnemyAttackingRight = new MovingSprite("archer_attack.png",1,10);
        shootingEnemyAttackingLeft = new MovingSprite("archer_attack_left.png",1,10);
        shootingEnemyWalking = new MovingSprite("archer_walk.png",1,10);
        shootingEnemyIdle = new MovingSprite("archer_idle.png",1,10);
    }
}
