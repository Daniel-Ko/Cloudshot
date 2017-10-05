package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import model.GameModel;
import view.CustomSprite;
import view.MovingSprite;

public class Slime2 extends AbstractEnemy{

    private int splitID = 0;//0 = original smile,1 = second gen..

    //DIFFERENT IMAGES FOR DIFFERENT STATES
    private String walking = "Skeleton Walk.png";
    private String attacking = "Skeleton Walk.png";

    private CustomSprite attack;
    private CustomSprite dead;
    private CustomSprite idle;
    private CustomSprite walk;
    private CustomSprite walk_l;

    public Slime2(GameModel gameModel, Vector2 pos){
        super(gameModel,pos);
        health = 20;
        attack =  new MovingSprite("slime_attack.png",1,7);
        dead = new MovingSprite("Skeleton Dead.png",1,1);
        idle = new MovingSprite("slime_walk.png",1, 9);
        walk = new MovingSprite("slime_walk.png",1, 9);
        walk_l = new MovingSprite("slime_walk.png",1, 9);
        walk_l.flipHorizontal();
        damage = 1;

        detectionRadius = 3;
        attackRadius = 0.5f;
    }

    protected void defineBody(){
        //body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        //shape def for main fixture
        //PolygonShape shape = new PolygonShape();
        CircleShape shape = new CircleShape();
        shape.setRadius(10f/ GameModel.PPM);
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
        body.createFixture(fDef).setUserData("slime");

    }

    /**
     * DESC
     * @return true if landed and attack o.w false
     * */
    @Override
    protected boolean attack() {
        player.hit(damage);
        return true;
    }

    /**
     * if the player is within this enemys detection radius then it follows the player
     * if the player is also in hitting range it damages the player
     *
     * finally updates the players position by the velocity
     * */
    @Override
    public void update() {
        enemyState.update(this,player);
        if(enemyState instanceof Death){
            if(splitID < 1 ){
                //split slime into 2 but half that stats
                Slime2 e1 = new Slime2(game,new Vector2((body.getPosition().x* GameModel.PPM)-10,body.getPosition().y * GameModel.PPM));
                Slime2 e2 = new Slime2(game,new Vector2((body.getPosition().x* GameModel.PPM)+10,body.getPosition().y * GameModel.PPM));
                e1.drawingWidth = drawingWidth/1.5f;
                e1.drawingHeight = drawingHeight/1.5f;
                e2.drawingWidth = drawingWidth/1.5f;
                e2.drawingHeight = drawingHeight/1.5f;
                e1.splitID=splitID+1;
                e2.splitID=splitID+1;
                e1.damage = damage/2;
                e2.damage = damage/2;
                game.addEnemy(e1);
                game.addEnemy(e2);
            }
            //world.destroyBody(body);
        }
        if(enemyState instanceof Death)return;

        position.set(body.getPosition());
        boundingBox.set(position.x,position.y,boundingBox.getWidth(),boundingBox.getHeight());

        //UPDATING STATES
        if(position.dst(player.getPos())<detectionRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            if(enemyState instanceof IdleMovement){
                enemyState = new AggroDash();
            }
        }
        else{
            enemyState = new IdleMovement();
        }
        if(position.dst(player.getPos())<attackRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            //attackState
            enemyState = new MeleeAttack();
            attack();
        }
    }

    @Override
    public void movement(){


    }


    @Override
    public CustomSprite getImage() {
        if(state == enemy_state.EDEAD){
            return dead;
        }

        if(enemyState instanceof MeleeAttack){
            if(body.getLinearVelocity().x<0){
                MovingSprite m = new MovingSprite("slime_attack.png",1,7);
                m.flipHorizontal();
                return m;
            }
            return attack;
        }
        //IDLE STATE
        if(enemyState instanceof IdleMovement){
            return idle;
        }
        if(body.getLinearVelocity().x<0){
            walk_l = new MovingSprite("slime_walk.png",1, 9);
            walk_l.flipHorizontal();
            return walk_l;
        }
        return walk;
    }
}
