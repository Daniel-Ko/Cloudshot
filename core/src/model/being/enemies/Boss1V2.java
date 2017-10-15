package model.being.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.EntityFactory;
import model.being.enemystates.*;
import model.being.player.AbstractPlayer;
import model.projectile.BulletImpl;
import view.Assets;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;
import view.sprites.StaticSprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Boss1V2 extends AbstractEnemy{
    private static final long serialVersionUID = 7387484870840919165L;
    
    //bullet and attacking fields
    private Queue<BulletImpl> bulletsShot = new LinkedList<>();

    //For spawning slimes
    private transient GameModel game;
    private long lastSlimeSpawned;
    private int secondsBetweenSpawss = 1;

    public Boss1V2(World world, AbstractPlayer player, Vector2 pos){
        super(world,player,pos, entity_type.boss1);
        this.player = player;
        this.world = world;
        enemyState = new HorizontalMovement(body.getPosition(),3);
        attackRadius = 30;
        drawingHeight = 5;
        drawingWidth = 5;
        damage = 20;

    }

    public void provideGameModel(GameModel gm){
        game = gm;
    }

    @Override
    protected boolean attack() {
        return false;
    }

    @Override
    protected void defineBody() {
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
        fDef.filter.groupIndex = -1;

        //
        bodyDef.position.set(position.x / GameModel.PPM,position.y/GameModel.PPM);
        body = world.createBody(bodyDef);
        //adding main fixture
        body.createFixture(fDef);
    }

    @Override
    protected void movement() {

    }

    @Override
    public void update() {
        updateBullets();
        enemyState.update(this,player);

        if(lastSlimeSpawned+1<System.currentTimeMillis()/1000){
            lastSlimeSpawned = System.currentTimeMillis()/1000;
            game.addEnemy(EntityFactory.produceEnemy(game, new Vector2((body.getPosition().x * GameModel.PPM) - 10, body.getPosition().y * GameModel.PPM), entity_type.slime));

        }

        position.set(body.getPosition());
        boundingBox.set(position.x,position.y,boundingBox.getWidth(),boundingBox.getHeight());
    }


    private void updateBullets(){
        //updating bullets enemy has fired
        for(BulletImpl b : getBulletsShot())
            b.update(new ArrayList<AbstractEnemy>(),player);//FIXME
        //Cleans up bullets
        if(getBulletsShot().size() > 300){
            getBulletsShot().clear();//remove the oldest 1st
        }
    }

    public Queue<BulletImpl> getBulletsShot() {
        return bulletsShot;
    }


    public CustomSprite getBulletSprite() {
        return Assets.bossEnemyBulletSprite;
    }

    @Override
    public CustomSprite getImage() {
        if(state == enemy_state.EDEAD){
            return Assets.slime2Dead;
        }

        if(enemyState instanceof MeleeAttack){
            if(getPosition().dst(player.getPos())<0){
                return Assets.slime2AttackLeft;
            }
            return Assets.slime2AttackRight;
        }
        //IDLE STATE
        if(enemyState instanceof IdleMovement){
            return Assets.slime2Idle;
        }

        if(body.getLinearVelocity().x<0){
            return Assets.slime2WalkLeft;
        }
        return Assets.slime2WalkRight;
    }
}
