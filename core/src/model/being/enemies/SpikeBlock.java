package model.being.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.enemystates.HorizontalMovement;
import model.being.player.AbstractPlayer;
import view.Assets;
import view.sprites.CustomSprite;

public class SpikeBlock extends AbstractEnemy{

    private static final long serialVersionUID = -1155054338574041353L;
    public final AbstractEnemy.entity_type type = AbstractEnemy.entity_type.spikeblock;

    private Vector2 initPos;
    private float maxDist;

    public SpikeBlock(World world, AbstractPlayer player, Vector2 pos) {
        super(world,player,pos, AbstractEnemy.entity_type.spikeblock);
        damage = 2;
        initPos = body.getPosition();
        maxDist = 0.1f /GameModel.PPM;
        enemyState = new HorizontalMovement(body.getPosition(),3);
        width = 100/ GameModel.PPM;
        height = 100/ GameModel.PPM;
        boundingBox = new Rectangle(position.x,position.y,width,height);
    }

    public SpikeBlock(){
        damage = 5;
        initPos = new Vector2(0,0);
        position = new Vector2(0,0);
        maxDist = 0.1f /GameModel.PPM;
        enemyState = new HorizontalMovement(position,3);
        width = 100/ GameModel.PPM;
        height = 100/ GameModel.PPM;
        boundingBox = new Rectangle(position.x,position.y,width,height);

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
    public void update() {
        //update bounding box
        if(body != null){
            position = body.getPosition();
            boundingBox = new Rectangle(position.x-(width/2),position.y-(height/2),width,height);
        }
        //updates state
        enemyState.update(this,player);
    }

    @Override
    public CustomSprite getImage() {
        return Assets.spikeBlock;
    }
}
