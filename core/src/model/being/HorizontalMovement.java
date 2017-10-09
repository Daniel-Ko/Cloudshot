package model.being;

import com.badlogic.gdx.math.Vector2;

public class HorizontalMovement implements EnemyState {

    //range of how much it can move
    private float range =3;
    public Vector2 leftTargetLocation;
    public Vector2 rightTargetLocation;
    boolean reachedLeft;
    boolean reachedRight;
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
      movement(e,p);
      attack(e,p);
    }
    private void movement(AbstractEnemy e, AbstractPlayer p ){

        //until it reaches left target location move left
        if(!reachedLeft){
            if(e.getPosition().x > leftTargetLocation.x){
                e.body.setLinearVelocity(-1f,e.body.getLinearVelocity().y);
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
                e.body.setLinearVelocity(1f,e.body.getLinearVelocity().y);
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
        if(p.getBoundingBox().overlaps(e.getBoundingBox())){
            //On Top
            if(p.getPos().x> e.getBoundingBox().x && p.getPos().x<e.getBoundingBox().x+e.getBoundingBox().width){
                if(p.getPos().y>e.getPosition().y){
                    p.applyKnockBack(AbstractPlayer.knock_back.NORTH);
                    return 0;
                }
                //Underneath
                if(p.getPos().y<e.getBoundingBox().y){
                    p.applyKnockBack(AbstractPlayer.knock_back.SOUTH);
                    return 0;
                }
                //On left an right
                if(p.getPos().y> e.getBoundingBox().y && p.getPos().y<e.getBoundingBox().y+e.getBoundingBox().height){
                    if(p.getPos().x<=e.getPosition().x) p.applyKnockBack(AbstractPlayer.knock_back.WEST);
                    if(p.getPos().x>=e.getPosition().x) p.applyKnockBack(AbstractPlayer.knock_back.EAST);
                    return 0;
                }

            }

        }
        return 0;
    }

    @Override
    public void damage(AbstractEnemy e, int damage) {

    }
}
