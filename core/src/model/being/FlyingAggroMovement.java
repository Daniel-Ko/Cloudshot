package model.being;

/**
 * FlyingAggroMovementState, if the player is within the aggro radius of an enemy
 *                           the enemy will enter this aggro movement state which will
 *                           just follow the player on X & Y axis.
 *
 * Unable to attack anything during this state, will change to approp Attack state when in attack range
 * Can get damaged in this state
 *
 *
 * @author Jeremy Southon
 *
 * */
public class FlyingAggroMovement implements EnemyState {

    @Override
    public void update(AbstractEnemy e, AbstractPlayer player) {
        if(e.getX()<player.getX())
            e.body.setLinearVelocity(1f,e.body.getLinearVelocity().y);
        if(e.getX()>player.getX())
            e.body.setLinearVelocity(-1f,e.body.getLinearVelocity().y);
        if(e.getY()<player.getY())
            e.body.setLinearVelocity(e.body.getLinearVelocity().x,1f);
        if(e.getY ()>player.getY())
            e.body.setLinearVelocity(e.body.getLinearVelocity().x,-1f);
    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        return 0;
    }

    @Override
    public void damage(AbstractEnemy e, int damage) {
        e.hit(damage);
        if(e.health <= 0){
            e.enemyState = new Death();
        }
    }

    @Override
    public String toString() {
        return "FlyingAggroMovementState";
    }
}
