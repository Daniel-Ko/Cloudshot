package model.being.enemystates;

import com.badlogic.gdx.math.Vector2;
import model.being.enemies.Boss1V2;
import model.being.player.AbstractPlayer;
import model.being.enemies.AbstractEnemy;
import model.projectile.BulletImpl;
import view.sprites.StaticSprite;

/**
 * State when the player has entered an enemy's aggro radius
 *
 * This state just moves the enemy to the location of the player in the X
 *
 * Just defines movement doesn't attack.
 * */
public class BossState1 implements EnemyState, java.io.Serializable {
    float theta;
    int num;
    @Override
    public void update(AbstractEnemy e, AbstractPlayer player) {
        movement(e,player);
        attack(e,player);
    }

    private void movement(AbstractEnemy e, AbstractPlayer player){
//        if(e.getX()<player.getX() && Math.abs(e.getX()-player.getX())>1)
//            e.getBody().setLinearVelocity(1f,e.getBody().getLinearVelocity().y);
//        if(Math.abs(e.getX()-player.getX())>1 &&e.getX()>player.getX())
//            e.getBody().setLinearVelocity(-1f,e.getBody().getLinearVelocity().y);
    }
    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        int numberOfTotalBullets = 100;
        int spreadOfBullets = 2;
        if(!(e instanceof Boss1V2))throw new Error("Should be Boxx1v2 type");
        Boss1V2 b = (Boss1V2)e;
        num = b.getBulletsShot().size()%numberOfTotalBullets;
        theta = (float)(spreadOfBullets*Math.PI/num);
        if(p.getPos().dst(e.getPosition())<e.attackRadius){
                Vector2 centerOfPlayer = new Vector2(p.getBoundingBox().x+p.getBoundingBox().width/2,p.getBoundingBox().y+p.getBoundingBox().height/2);
                //firing a new bullet
                b.getBulletsShot().add(new BulletImpl(b.getPosition(),new Vector2(centerOfPlayer.x*theta,centerOfPlayer.y*theta),2,new StaticSprite("player_jump.png"),false));
                return (int)b.getDamage();
        }
        return 0;
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
        return "BossState1";
    }
}
