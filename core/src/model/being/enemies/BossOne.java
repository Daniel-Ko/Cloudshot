package model.being.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.player.AbstractPlayer;
import model.projectile.BulletImpl;
import view.Assets;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

import java.util.*;

public class BossOne extends AbstractEnemy{
    private static final long serialVersionUID = -6972282922822093237L;
    public final AbstractEnemy.entity_type type = AbstractEnemy.entity_type.boss1;

    private int attackRadius = 9;


    //bullet and attacking fields
    private long lastBulletFired;
    public Queue<BulletImpl> bullets = new LinkedList<>();

    //TESTING
    public List<BulletImpl> huh = new ArrayList<>();
    
    public BossOne(World world, AbstractPlayer player, Vector2 pos){
        super(world,player,pos, AbstractEnemy.entity_type.boss1);
        speed = 6;
        damage = 10;
        drawingWidth = 4;
        drawingHeight =4;
    }

    protected void defineBody(){
        //body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        //shape def for main fixture
        //PolygonShape shape = new PolygonShape();
        CircleShape shape = new CircleShape();
        shape.setRadius(60f/ GameModel.PPM);
        //shape.setAsBox(1,2);

        //fixture def
        fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 0.7f;
        fDef.friction = 15;

        //
        bodyDef.position.set(position.x / GameModel.PPM,position.y/GameModel.PPM);
        body = world.createBody(bodyDef);
        //adding main fixture
        body.createFixture(fDef).setUserData("mob1");

    }

    /**
     * DESC
     *
     * @return true if landed and attack o.w false
     * */
    @Override
    protected boolean attack() {
        int secondsBetweenShots = 0;
        if(lastBulletFired+secondsBetweenShots<System.currentTimeMillis()/1000){
            lastBulletFired = System.currentTimeMillis()/1000;
            bullets.add(new BulletImpl(position,player.getPos(),2,new StaticSprite("player_jump.png"),false));
            return true;
        }
        return false;
    }

    private void attackIfPossible(){
        if(state == enemy_state.EDEAD)return;
        if(position.dst(player.getPos())<attackRadius){
            state = enemy_state.EATTACKING;
            if(player.getPlayerState() == AbstractPlayer.player_state.ALIVE)attack();
        }
    }
    /**
     * if the player is within this enemys detection radius then it follows the player
     * if the player is also in hitting range it damages the player
     *
     * finally updates the players position by the velocity
     * */
    @Override
    public void update() {
        updateBullets();
        if(state == enemy_state.EDEAD)return;
        if(health <= 0 ){
            state = enemy_state.EDEAD;
            world.destroyBody(body);
        }
        if(state == enemy_state.EDEAD)return;

        position.set(body.getPosition());
        boundingBox.set(position.x,position.y,boundingBox.getWidth(),boundingBox.getHeight());
        movement();
        attackIfPossible();
    }

    @Override
    public void movement(){
        if(position.dst(player.getPos())>attackRadius)state = enemy_state.EIDLE;
        if(position.dst(player.getPos())<attackRadius){
            if(player.getPlayerState() == AbstractPlayer.player_state.ALIVE)attack();
        }
        //MOVEMENT
        if(state == enemy_state.EIDLE){
            idleMovement();
        }else {
            foundPlayerMovement();
        }
        //if not within attacking range
        if(position.dst(player.getPos())>attackRadius){ state = enemy_state.EIDLE;}
    }

    private void updateBullets(){
        //updating bullets enemy has fired
        for(BulletImpl b : bullets)
            b.update(new ArrayList<AbstractEnemy>(),player);//FIXME
        //Cleans up bullets
        if(bullets.size() > 50){
            bullets.poll();//remove the oldest 1st
        }
    }

    private void foundPlayerMovement(){
        if(state == enemy_state.EDEAD)return;
        int detectionRadius = 10;
        if(position.dst(player.getPos())< detectionRadius){
            if(getX()<player.getX())
                body.setLinearVelocity(1f,body.getLinearVelocity().y);
            if(getX()>player.getX())
                body.setLinearVelocity(-1f,body.getLinearVelocity().y);
        }
    }
    private void idleMovement() {
        Random r = new Random();
        int i = r.nextInt(10 - 1 + 1) + 1;
        if(i<8){
            body.setLinearVelocity(1f,body.getLinearVelocity().y);

        }else {
            body.setLinearVelocity(-1f,body.getLinearVelocity().y);

        }
    }

    @Override
    public CustomSprite getImage() {
        return Assets.slimeWalk;
    }
}
