package model.being.enemystates;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.ShootingEnemy;
import model.projectile.BulletImpl;
import view.sprites.StaticSprite;
/**
 * This state provided the behaviour of a enemy shooting, no movement, just shoots.
 *
 * @author Jeremy Southon
 *
 * */
public class ShooterAttack implements EnemyState {
    private long lastBulletFired;
    private int secondsBetweenShots = 1;

    @Override
    public void update(AbstractEnemy e, AbstractPlayer p) {
        //no updates as this state doesnt move
    }

    /**
     * Makes sure this is a shooter type enemy, if secondsBetweenShots has passed fires a new
     * Bullet in the direction of the player.
     * */
    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        //Has to be a shooter type enemy
        if(!(e instanceof ShootingEnemy))throw new Error("Except shooter type enemy");
        ShootingEnemy se = (ShootingEnemy)e;
        if(lastBulletFired+secondsBetweenShots<System.currentTimeMillis()/1000){
            lastBulletFired = System.currentTimeMillis()/1000;
            Vector2 centerOfPlayer = new Vector2(p.getBoundingBox().x+p.getBoundingBox().width/2,p.getBoundingBox().y+p.getBoundingBox().height/2);
            se.getBullets().add(new BulletImpl(se.getPosition(),centerOfPlayer,2,new StaticSprite("player_jump.png"),false));
            return (int)e.getDamage();
        }
        return -1;
    }

    @Override
    public void damage(AbstractEnemy e, int damage) {
        e.internalDamage(damage);
        if(e.getHealth() <= 0){
            e.enemyState = new Death();
        }
    }

    public void setSecondsBetweenShots(int seconds){this.secondsBetweenShots = seconds;}
}
