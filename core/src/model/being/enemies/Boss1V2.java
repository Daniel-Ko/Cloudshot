package model.being.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.EntityFactory;
import model.being.enemystates.AggroMovement;
import model.being.enemystates.HorizontalMovement;
import model.being.player.AbstractPlayer;
import view.sprites.CustomSprite;

import java.util.Vector;

public class Boss1V2 extends AbstractEnemy{



    public Boss1V2(World world, AbstractPlayer player, Vector2 pos){
        super(world,player,pos, EntityFactory.entity_type.boss1);
        this.player = player;
        this.world = world;
        enemyState = new AggroMovement();

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
    protected void movement() {

    }

    @Override
    public void update() {
        enemyState.update(this,player);
        position.set(body.getPosition());
        boundingBox.set(position.x,position.y,boundingBox.getWidth(),boundingBox.getHeight());
    }

    @Override
    public CustomSprite getImage() {
        return null;
    }
}
