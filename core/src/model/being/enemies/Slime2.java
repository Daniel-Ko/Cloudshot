package model.being.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.GameModelInterface;
import model.being.enemystates.*;
import model.being.player.AbstractPlayer;
import view.Assets;
import view.sprites.CustomSprite;

public class Slime2 extends AbstractEnemy{

    private static final long serialVersionUID = -3298496178944027533L;
    public final AbstractEnemy.entity_type type = AbstractEnemy.entity_type.slime;

    private int splitID = 0;//0 = original smile,1 = second gen..

    private Vector2 initPos;

    public Slime2(World world, AbstractPlayer player, Vector2 pos){
        super(world,player,pos, AbstractEnemy.entity_type.slime);
        initPos = new Vector2(pos.x/ GameModel.PPM,pos.y/ GameModel.PPM);
        health = 50;

        damage = 1;

        detectionRadius = 3;
        attackRadius = 0.5f;
    }

    public void provideGameModel(GameModelInterface gm){
        game = gm;
    }

    public Slime2(){
        super();
        initPos = position;
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
        fDef.filter.groupIndex = -1;
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
        if(position.dst(player.getPos())<attackRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            //attackState
            if(!(enemyState instanceof MeleeAttack))enemyState = new MeleeAttack();
            enemyState.attack(this,player);
            return true;
        }
        return false;
    }

    /**
     * if the player is within this enemys detection radius then it follows the player
     * if the player is also in hitting range it damages the player
     *
     * finally updates the players position by the velocity
     * */
    @Override
    public void update() {

        if(world != null || body != null) {
            enemyState.update(this, player);
            if (enemyState instanceof Death) {
                splitOnDeath();
            }
            if (enemyState instanceof Death) return;
            position.set(body.getPosition());
            boundingBox.set(position.x, position.y, boundingBox.getWidth(), boundingBox.getHeight());
        }

        //UPDATING STATES
        if(position.dst(player.getPos())<detectionRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            if(enemyState instanceof HorizontalMovement){
                enemyState = new AggroMovement();
            }
        }
        else{
            if(!(enemyState instanceof  HorizontalMovement))
                enemyState = new HorizontalMovement(initPos,3);
        }
        attack();

    }
    /**Method splits slime into more sub slimes if it can when this slime dies*/
    private void splitOnDeath(){
        if (splitID < 1) {
            //split slime into 2 but half that stats
            Slime2 e1 = new Slime2(world,player, new Vector2((body.getPosition().x * GameModel.PPM) - 10, body.getPosition().y * GameModel.PPM));
            Slime2 e2 = new Slime2(world,player, new Vector2((body.getPosition().x * GameModel.PPM) + 10, body.getPosition().y * GameModel.PPM));
            e1.drawingWidth = drawingWidth / 1.5f;
            e1.drawingHeight = drawingHeight / 1.5f;
            e2.drawingWidth = drawingWidth / 1.5f;
            e2.drawingHeight = drawingHeight / 1.5f;
            e1.splitID = splitID + 1;
            e2.splitID = splitID + 1;
            e1.damage = damage / 2;
            e2.damage = damage / 2;
            game.addEnemy(e1);
            game.addEnemy(e2);
        }
    }


    @Override
    public CustomSprite getImage() {
        if(this.hurtThisFrame){
            System.out.println("hurt");
        }
        if(state == enemy_state.EDEAD){
            return Assets.slime2Dead;
        }

        if(enemyState instanceof MeleeAttack){
            if(getPosition().dst(player.getPos())<0){
                if(this.hurtThisFrame)
                    return  Assets.slime2AttackLeftHurt;
               return Assets.slime2AttackLeft;
            }
            if(this.hurtThisFrame)
                return  Assets.slime2AttackRightHurt;
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
