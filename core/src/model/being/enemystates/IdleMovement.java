package model.being.enemystates;

//import com.sun.jmx.snmp.EnumRowStatus;

import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;

import java.util.Random;

public class IdleMovement implements EnemyState, java.io.Serializable{
    private static final long serialVersionUID = 4525408048766636312L;
    private float idleMovementSpeed = 1f;
    @Override
    public void update(AbstractEnemy e, AbstractPlayer player) {
        Random r = new Random();
        int i = r.nextInt(10 - 1 + 1) + 1;
        if(i<8){
            e.getBody().setLinearVelocity(idleMovementSpeed,e.getBody().getLinearVelocity().y);
        }else {
            e.getBody().setLinearVelocity(-idleMovementSpeed,e.getBody().getLinearVelocity().y);
        }

    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        return 0;
    }


    @Override
    public void damage(AbstractEnemy e, int damage) {
        e.internalDamage(damage);
        System.out.println("dam dealt: "+ damage+" cur hp:  "+e.getHealth());
        if(e.getHealth() <= 0){
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
