package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import model.GameModel;
import view.CustomSprite;
import view.MovingSprite;
import view.StaticSprite;

public class BossTwo extends AbstractEnemy{
    private int detectionRadius = 60;
    private int attackRadius = 5;
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
        return false;
    }

    @Override
    protected void movement() {
        if(position.dst(player.getPos())< detectionRadius){
            if(getX()<player.getX())
                body.setLinearVelocity(1f,body.getLinearVelocity().y);
            if(getX()>player.getX())
                body.setLinearVelocity(-1f,body.getLinearVelocity().y);
            if(getY()<player.getY())
                body.setLinearVelocity(body.getLinearVelocity().x,1f);
            if(getY ()>player.getY())
                body.setLinearVelocity(body.getLinearVelocity().x,-1f);
        }
    }

    @Override
    public void update() {
        if(state == enemy_state.EDEAD)return;
        if(health <= 0 ){
            state = enemy_state.EDEAD;
            world.destroyBody(body);
        }

        position.set(body.getPosition());
        boundingBox.set(position.x,position.y,boundingBox.getWidth(),boundingBox.getHeight());
        movement();
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
