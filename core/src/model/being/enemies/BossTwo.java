package model.being.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.enemystates.Death;
import model.being.enemystates.FlyingAggroMovement;
import model.being.enemystates.IdleMovement;
import model.being.enemystates.MeleeAttack;
import model.being.player.AbstractPlayer;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

public class BossTwo extends AbstractEnemy{
    private static final long serialVersionUID = 5928068042381925690L;
    public final AbstractEnemy.entity_type type = AbstractEnemy.entity_type.boss2;


    public BossTwo(World world, AbstractPlayer player, Vector2 pos) {
        super(world,player,pos, AbstractEnemy.entity_type.boss2);
        drawingWidth = 4;
        drawingHeight =4;
    }


    @Override
    protected void defineBody() {
        //body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
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
        body.createFixture(fDef);

    }


    @Override
    protected boolean attack() {
        enemyState.attack(this,player);
        return true;
    }


    @Override
    public void update() {

        if(enemyState instanceof Death)return;

        position.set(body.getPosition());
        boundingBox.set(position.x,position.y,boundingBox.getWidth(),boundingBox.getHeight());
        //UPDATING STATES
        int detectionRadius = 100;
        if(position.dst(player.getPos())< detectionRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            enemyState = new FlyingAggroMovement();
        }
        else{
            enemyState = new IdleMovement();
        }
        int attackRadius = 2;
        if(position.dst(player.getPos())< attackRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            //attackState
            enemyState = new MeleeAttack();
            attack();
        }
        enemyState.update(this,player);
    }

    @Override
    public CustomSprite getImage() {
        if(body.getLinearVelocity().x < 0){
            StaticSprite s = new StaticSprite("flamboLeft.png");
            s.flipHorizontal();
            return s;
        }
        return new StaticSprite("flamboLeft.png");
    }
}
