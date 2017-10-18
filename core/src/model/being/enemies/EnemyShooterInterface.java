package model.being.enemies;

import model.projectile.BulletImpl;
import view.sprites.CustomSprite;

import java.util.Queue;

/**
 * Created by kodani on 18/10/17.
 */
public interface EnemyShooterInterface {

    void updateBullets();

    Queue<BulletImpl> getBulletsShot();

    CustomSprite getBulletSprite();
}
