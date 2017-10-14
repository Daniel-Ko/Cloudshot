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
    //moving
    public static CustomSprite playerWalkHurt;
    public static CustomSprite playerIdleHurt;

    /**
     * Shooting enemy sprites.
     */
    public static CustomSprite shootingEnemyBulletSprite;
    public static CustomSprite shootingEnemyAttackingRight;
    public static CustomSprite shootingEnemyAttackingLeft;
    public static CustomSprite shootingEnemyWalking;
    public static CustomSprite shootingEnemyIdle;

    /**
     * Rogue enemy sprites.
     */
    public static CustomSprite rogueEnemyAttackRight;
    public static CustomSprite rogueEnemyAttackLeft;
    public static CustomSprite rogueEnemyWalkRight;
    public static CustomSprite rogueEnemyWalkLeft;
    public static CustomSprite rogueEnemyIdle;

    public static CustomSprite slimeWalk;

    public static CustomSprite slime2AttackLeft;
    public static CustomSprite slime2AttackRight;
    public static CustomSprite slime2Dead;
    public static CustomSprite slime2Idle;
    public static CustomSprite slime2WalkRight;
    public static CustomSprite slime2WalkLeft;

    /**
     * Boss1
     * */

    public static CustomSprite bossEnemyBulletSprite;
    public static CustomSprite bossWalk;
    public static CustomSprite bossWalkRight;
    public static CustomSprite bossIdle;

    public static void load(){
        gameSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        playerIdle = new MovingSprite("player_idle.png", 2, 2);
        playerAttack = new MovingSprite("player_attack.png", 2, 3);
        playerJump = new MovingSprite("player_jump.png", 2, 3);
        playerWalk = new MovingSprite("player_walk.png", 3, 3);
        playerDeath = new MovingSprite("player_death.png", 1, 1);
        playerWalkHurt = new MovingSprite("player_walk_hurt.png", 3, 3);
        playerIdleHurt = new MovingSprite("player_idle_hurt.png", 2, 2);

        shootingEnemyBulletSprite =  new StaticSprite("bullet.png");
        shootingEnemyAttackingRight = new MovingSprite("archer_attack.png",1,10);
        shootingEnemyAttackingLeft = new MovingSprite("archer_attack_left.png",1,10);
        shootingEnemyWalking = new MovingSprite("archer_walk.png",1,10);
        shootingEnemyIdle = new MovingSprite("archer_idle.png",1,10);

        rogueEnemyAttackRight = new MovingSprite("rouge_attack.png",1,10);
        rogueEnemyAttackLeft = new MovingSprite("rouge_attack_left.png",1,10);
        rogueEnemyWalkRight = new MovingSprite("rouge_walk1.png",1,10);
        rogueEnemyWalkLeft = new MovingSprite("rouge_walk1_left.png",1,10);
        rogueEnemyIdle = new MovingSprite("rouge_idle.png",1,10);

        slimeWalk = new MovingSprite("slime_walk.png",1, 9);

        slime2AttackLeft =  new MovingSprite("slime_attack.png",1,7);
        slime2AttackRight =  new MovingSprite("slime_attack_right.png",1,7);
        slime2Dead = new MovingSprite("Skeleton Dead.png",1,1);
        slime2Idle = new MovingSprite("slime_walk.png",1, 9);
        slime2WalkRight = new MovingSprite("slime_walk.png",1, 9);
        slime2WalkLeft = new MovingSprite("slime_walk_left.png",1, 9);

        bossEnemyBulletSprite = new MovingSprite("fireBullet.png",4,4);
        bossWalk = new MovingSprite("kingSlime_walk.png",1,7);
        bossWalkRight = new MovingSprite("kingSlime_walk_right.png",1,7);
        bossIdle = new MovingSprite("kingSlime_idle.png",1,2);
    }
}
