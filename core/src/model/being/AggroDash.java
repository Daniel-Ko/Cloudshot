package model.being;

import com.badlogic.gdx.math.Vector2;

public class AggroDash implements EnemyState, java.io.Serializable {
    private Vector2 target_pos;
    private long timeBetweenLastDash = 0;

    private float dashSpeed = 3;
    public AggroDash() {
    }
    /**
     *  target_pos will be null when we enter or re-enter this enemy's detection radius.
     *  therefore set a target location to move to.
     *
     *  if this enemy is at the target location then wait n seconds then dash again...
     * */
    @Override
    public void update(AbstractEnemy e, AbstractPlayer p) {
        if(target_pos == null){
            //Target is the aggro distance in the players direction
            computeTargetLocation(e,p);

        }
        //AT THE TARGET LOCATION
        if (e.getPosition().dst(target_pos)<0.5) {//FIXME change this constant
            if(timeBetweenLastDash+1<System.currentTimeMillis()/1000)
            {
                computeTargetLocation(e,p);
            }
        }
        else{
            //Haven't reached that location therefore move!
            timeBetweenLastDash = System.currentTimeMillis()/1000;
            if(target_pos.x<=e.getX()){
                //Dash to left
                e.body.setLinearVelocity(-dashSpeed,e.body.getLinearVelocity().y);
            }else if(target_pos.x>e.getX()){
                //Dash to right
                e.body.setLinearVelocity(dashSpeed,e.body.getLinearVelocity().y);
            }
        }
    }

    /**
     * Target location is that in the direction of the player and the aggro (detection) radius
     * in distance.
     * */
    private void computeTargetLocation(AbstractEnemy e, AbstractPlayer p ){
        if(e.getX()<=p.getX()){
            target_pos = new Vector2(e.getX()+e.detectionRadius,e.getY());
        }
        else if(e.getX()>=p.getX()){
            target_pos = new Vector2(e.getX()-e.detectionRadius,e.getY());
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

    public void setDashSpeed(float speed){
        this.dashSpeed = speed;
    }
}
