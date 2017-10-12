package model.being.enemystates;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import model.being.enemies.AbstractEnemy;

public class HorizontalMovement implements EnemyState, java.io.Serializable {

    //range of how much it can move
    private float range =3;
    private Vector2 leftTargetLocation;
    private Vector2 rightTargetLocation;
    private boolean reachedLeft;
    private boolean reachedRight;
    /**
     *
     * @param initPostion initial starting position of this enemy
     * @param range the horizontal moving dst, recommended 3.
     * */
    public HorizontalMovement(Vector2 initPostion,int range){
        this.leftTargetLocation = new Vector2(initPostion.x-range,initPostion.y);
        this.rightTargetLocation = new Vector2(initPostion.x+range,initPostion.y);
        this.reachedLeft = false;
        this.reachedRight = true;
        this.range = range;
    }
    @Override
    public void update(AbstractEnemy e, AbstractPlayer p) {
        if(e.getBody() == null)return;
        movement(e,p);
        attack(e,p);
        //cannot damage enemy in this state
    }
    private void movement(AbstractEnemy e, AbstractPlayer p ){
        //until it reaches left target location move left
        if(!reachedLeft){
            if(e.getPosition().x > leftTargetLocation.x){
                e.getBody().setLinearVelocity(-1f,e.getBody().getLinearVelocity().y);
            }
            else {
                //reached left location
                reachedLeft = true;
                reachedRight = false;
            }
        }
        //if it reached left, move right until reaches right target location
        if(!reachedRight){
            if(e.getPosition().x < rightTargetLocation.x){
                e.getBody().setLinearVelocity(1f,e.getBody().getLinearVelocity().y);
            }
            else {
                //reached right location
                reachedLeft = false;
                reachedRight = true;
            }
        }
    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        applyAppropKnockBack(e,p);
        if(e.getBoundingBox().overlaps(p.getBoundingBox())){
            p.hit(e.getDamage());
            return (int)e.getDamage();
        }
        return 0;
    }

    /**
     * Method checks where the player is in relation to this enemy's bounding box/position
     * and if the Player and this enemy's bounding boxes overlap an approp knock back is applied
     * to the player.
     * */
    public void applyAppropKnockBack(AbstractEnemy e ,AbstractPlayer p){
        if(p.getBoundingBox().overlaps(e.getBoundingBox())){
            //On Top
            if(p.getPos().x> e.getBoundingBox().x && p.getPos().x<e.getBoundingBox().x+e.getBoundingBox().width){
                if(p.getPos().y>e.getPosition().y){
                    p.applyKnockBack(AbstractPlayer.knock_back.NORTH);
                }
                //Underneath
                if(p.getPos().y<e.getBoundingBox().y){
                    p.applyKnockBack(AbstractPlayer.knock_back.SOUTH);
                }
                //On left an right
                if(p.getPos().y> e.getBoundingBox().y && p.getPos().y<e.getBoundingBox().y+e.getBoundingBox().height){
                    if(p.getPos().x<=e.getPosition().x) p.applyKnockBack(AbstractPlayer.knock_back.WEST);
                    if(p.getPos().x>=e.getPosition().x) p.applyKnockBack(AbstractPlayer.knock_back.EAST);
                }

            }

        }
    }
    @Override
    public void damage(AbstractEnemy e, int damage) {

    }
}
