package model.being;

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
            se.getBullets().add(new BulletImpl(se.position,se.player.getPos(),2,new StaticSprite("player_jump.png")));
            return (int)e.damage;
        }
        return -1;
    }

    @Override
    public void damage(AbstractEnemy e, int damage) {
        e.internalDamage(damage);
        if(e.health <= 0){
            e.enemyState = new Death();
        }
    }

    public void setSecondsBetweenShots(int seconds){this.secondsBetweenShots = seconds;}
}
