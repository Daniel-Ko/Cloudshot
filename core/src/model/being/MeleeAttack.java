package model.being;

/**
 * MeleeAttackState - pre-conditions: changes to this state when enemy is the specified distance away
 *                    from the player
 *
 * In this state the enemy does not move (Currently)
 * Only applies the specified attack damage onto the player
 * Can get hit in this state.
 *
 * @author Jeremy Southon
 * */
public class MeleeAttack implements EnemyState {

    @Override
    public void update(AbstractEnemy e, AbstractPlayer p) {
        //what do you want e doing at its attacking?
    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        p.hit(e.damage);
        return (int)e.damage;
    }

    @Override
    public void damage(AbstractEnemy e, int damage) {
        e.internalDamage(damage);
        if(e.health <= 0){
            e.enemyState = new Death();
        }
    }

    @Override
    public String toString() {
        return "MeleeAttackState";
    }
}
