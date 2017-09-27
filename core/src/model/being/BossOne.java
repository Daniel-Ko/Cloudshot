package model.being;

import com.badlogic.gdx.math.Vector2;
import model.projectile.BulletImpl;
import view.CustomSprite;
import view.MovingSprite;
import view.StaticSprite;

import java.util.ArrayList;
import java.util.List;

public class BossOne extends AbstractEnemy{
    //Fields for
    private int detectionRadius = 200;
    private int attackRadius = 100;



    //TESTING
    List<BulletImpl> huh = new ArrayList<>();
    public BossOne(int hp,AbstractPlayer player,Vector2 pos){
        super(hp,player,pos);
        speed = 6;
        damage = 10;
    }

    @Override
    public void update() {
        movement();
        //FIXME
        for (BulletImpl b:huh) {
            //temp
            b.update(new ArrayList<AbstractEnemy>());
        }
    }

    @Override
    protected void movement() {
        if(position.dst(player.pos)<detectionRadius){
            if(position.dst(player.pos)<attackRadius){
                if(player.getPlayerState() == AbstractPlayer.player_state.ALIVE)attack();
            }
            if(getX()<player.getX())
                velocity.x = speed;
            if(getX()>player.getX())
                velocity.x = -speed;
            if(getY()<player.getY()+24)//FIXME replace 24 with image height
                velocity.y = speed;
            if(getY()>player.getY()+24)
                velocity.y = -speed;
        }
        //apply velocity onto position
        position.add(velocity);
    }

    @Override
    protected boolean attack() {
        player.hit(damage);
        //adds a bullet from this enemy location to the player location
        huh.add(new BulletImpl(getPosition(),player.getPos(),5,new StaticSprite("Skeleton Attack.png",1,18)));
        return true;
    }

    @Override
    public CustomSprite getImage() {
        //FIXME TEMP IMAGE
        return new MovingSprite("Skeleton Attack.png",1,18);
    }
}
