package model.being;

/**
 * State when the player has entered an enemy's aggro radius
 *
 * This state just moves the enemy to the location of the player in the X
 *
 * Just defines movement doesn't attack.
 * */
public class AggroMovement implements EnemyState {


    @Override
    public void update(AbstractEnemy e,AbstractPlayer player) {
        if(e.getX()<player.getX())
            e.body.setLinearVelocity(1f,e.body.getLinearVelocity().y);
        if(e.getX()>player.getX())
            e.body.setLinearVelocity(-1f,e.body.getLinearVelocity().y);
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
        return "AggroMovementState";
    }
}
