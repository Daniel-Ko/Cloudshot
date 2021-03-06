package model.being.enemystates;

import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;

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
public class MeleeAttack implements EnemyState, java.io.Serializable {

    private static final long serialVersionUID = 8087909869459216496L;

    @Override
    public void update(AbstractEnemy e, AbstractPlayer p) {
        //what do you want e doing at its attacking?
    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        p.hit(e.getDamage());
        //apply knock back only if on same y
        if(e.getX()<p.getX() && ((int)e.getY()) == ((int)p.getY()))p.applyKnockBack(AbstractPlayer.knock_back.EAST);
        if(e.getX()>p.getX() && ((int)e.getY()) == ((int)p.getY()))p.applyKnockBack(AbstractPlayer.knock_back.WEST);
        return (int)e.getDamage();
    }

    @Override
    public void damage(AbstractEnemy e, int damage) {
        e.internalDamage(damage);
        if(e.getHealth() <= 0){
            e.enemyState = new Death();
        }
    }

    @Override
    public String toString() {
        return "MeleeAttackState";
    }
}
