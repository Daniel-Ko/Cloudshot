package model.being;

//import com.sun.jmx.snmp.EnumRowStatus;

import java.util.Random;

public class IdleMovement implements EnemyState, java.io.Serializable{
    private float idleMovementSpeed = 1f;
    @Override
    public void update(AbstractEnemy e, AbstractPlayer player) {
        Random r = new Random();
        int i = r.nextInt(10 - 1 + 1) + 1;
        if(i<8){
            e.body.setLinearVelocity(idleMovementSpeed,e.body.getLinearVelocity().y);
        }else {
            e.body.setLinearVelocity(-idleMovementSpeed,e.body.getLinearVelocity().y);
        }

    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        return 0;
    }


    @Override
    public void damage(AbstractEnemy e, int damage) {
        e.internalDamage(damage);
        if(e.health <= 0){
            e.enemyState = new Death();
        }
    }
    public void setIdleMovementSpeed(float speed){
        this.idleMovementSpeed = speed;
    }
    @Override
    public String toString() {
        return "IdleMovementState";
    }
}
