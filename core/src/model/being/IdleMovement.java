package model.being;

import com.sun.jmx.snmp.EnumRowStatus;

import java.util.Random;

public class IdleMovement implements EnemyState{
    @Override
    public void update(AbstractEnemy e, AbstractPlayer player) {
        Random r = new Random();
        int i = r.nextInt(10 - 1 + 1) + 1;
        if(i<8){
            e.body.setLinearVelocity(1f,e.body.getLinearVelocity().y);
        }else {
            e.body.setLinearVelocity(-1f,e.body.getLinearVelocity().y);
        }

    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        throw new Error("Shouldnt be Called");
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
        return "IdleMovementState";
    }
}
