package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import model.GameModel;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

public class BossTwo extends AbstractEnemy{
    private int detectionRadius = 100;
    private int attackRadius = 2;
    public BossTwo(GameModel gameModel, Vector2 pos) {
        super(gameModel, pos);
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
    protected void movement() {

    }

    @Override
    public void update() {

        if(enemyState instanceof Death)return;

        position.set(body.getPosition());
        boundingBox.set(position.x,position.y,boundingBox.getWidth(),boundingBox.getHeight());
        //UPDATING STATES
        if(position.dst(player.getPos())<detectionRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            enemyState = new FlyingAggroMovement();
        }
        else{
            enemyState = new IdleMovement();
        }
        if(position.dst(player.getPos())<attackRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            //attackState
            enemyState = new MeleeAttack();
            attack();
        }
        System.out.println(enemyState);
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
