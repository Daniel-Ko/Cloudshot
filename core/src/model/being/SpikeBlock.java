package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import model.GameModel;
import view.sprites.CustomSprite;

public class SpikeBlock extends AbstractEnemy {
    private Vector2 initPos;
    private float maxDist;
    public SpikeBlock(GameModel gameModel, Vector2 pos) {
        super(gameModel, pos);
        initPos = body.getPosition();
        maxDist = 0.1f /GameModel.PPM;
    }

    @Override
    protected boolean attack() {
        return false;
    }

    @Override
    protected void defineBody() {
        //body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;

        //shape def for main fixture
        PolygonShape shape = new PolygonShape();
        //shape.setRadius(10f/ GameModel.PPM);
        shape.setAsBox(0.8f,0.8f);

        //fixture def
        fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 0.7f;

        //
        bodyDef.position.set(position.x / GameModel.PPM,position.y/GameModel.PPM);
        body = world.createBody(bodyDef);
        //adding main fixture
        body.createFixture(fDef).setUserData("spikeBlock");
    }

    @Override
    protected void movement() {
//        if(initPos.dst(body.getPosition())<maxDist){
//            body.setLinearVelocity(body.getLinearVelocity().x,0.3f);
//        }
//        else {
//
//        }
    }

    @Override
    public void update() {
        movement();
    }

    @Override
    public CustomSprite getImage() {
        return null;
    }
}
