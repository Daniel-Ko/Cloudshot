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

    /**
     * Rogue enemy sprites.
     */
    public static CustomSprite rogueEnemyAttackRight;
    public static CustomSprite rogueEnemyAttackLeft;
    public static CustomSprite rogueEnemyWalkRight;
    public static CustomSprite rogueEnemyWalkLeft;
    public static CustomSprite rogueEnemyIdle;

    public static CustomSprite slimeWalk;

    /**
     * Slime2 sprites.
     */
    public static CustomSprite slime2AttackLeft;
    public static CustomSprite slime2AttackRight;
    public static CustomSprite slime2Dead;
    public static CustomSprite slime2Idle;
    public static CustomSprite slime2WalkRight;
    public static CustomSprite slime2WalkLeft;

    public static CustomSprite spikeBlock;

    /**
     * Buff sprites.
     */
    public static CustomSprite deathPack;
    public static CustomSprite healthPack;
    public static CustomSprite heavyAmmoPack;
    public static CustomSprite lightAmmoPack;

    /**
     * Weapon sprites.
     */
    public static CustomSprite pistol;
    public static CustomSprite pistolBullet;
    public static CustomSprite semiAuto;
    public static CustomSprite semiAutoBullet;
    public static CustomSprite shotgun;
    public static CustomSprite shotgunBullet;
    public static CustomSprite sniper;
    public static CustomSprite sniperBullet;

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

        spikeBlock = new StaticSprite("spikeBlock.png");

        deathPack = new StaticSprite("deathpack.png");
        healthPack = new StaticSprite("healthpack.png");
        heavyAmmoPack = new StaticSprite("ammo.png");
        lightAmmoPack = new StaticSprite("ammo.png");
        pistol = new StaticSprite("pistol.png");
        pistolBullet = new StaticSprite("bullet.png");

        semiAuto = new StaticSprite("Tec-9.png");
        semiAuto.flipHorizontal();
        semiAutoBullet = new StaticSprite("bullet.png");

        shotgun = new StaticSprite("shotgun.png");
        shotgunBullet = new StaticSprite("bullet.png");

        sniper = new StaticSprite("sniper.png");
        sniperBullet = new StaticSprite("bullet.png");

    }
}
