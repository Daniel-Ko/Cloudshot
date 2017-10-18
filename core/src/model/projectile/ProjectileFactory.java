package model.projectile;

import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.being.EntityInterface;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.Boss1V2;
import model.being.enemies.ShootingEnemy;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.collectable.AbstractWeapon;
import model.collectable.CollectableFactory;

/**
 * Created by kodani on 18/10/17.
 */
public class ProjectileFactory {
    public static BulletImpl produceBullet(Vector2 start, Vector2 end, float damage, boolean owner, EntityInterface shooter) {

        if(owner) {
            AbstractPlayer playerShooter = (Player) shooter;
            AbstractWeapon playerWep = playerShooter
                            .getInventory()
                            .get(playerShooter.getCurWeapon());


            return new BulletImpl(
                    start,
                    end,
                    damage,
                    playerWep.BULLET_IMAGE,
                    owner
            );
        }

        if(shooter instanceof ShootingEnemy) {
            ShootingEnemy enemyShooter = (ShootingEnemy) shooter;

            return new BulletImpl(
                    start,
                    end,
                    damage,
                    enemyShooter.getBulletSprite(),
                    owner
            );

        } else if(shooter instanceof Boss1V2) {
            Boss1V2 enemyShooter = (Boss1V2) shooter;

            return new BulletImpl(
                    start,
                    end,
                    damage,
                    enemyShooter.getBulletSprite(),
                    owner
            );
        }
        return null;
    }
}
