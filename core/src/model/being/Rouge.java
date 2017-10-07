package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import model.GameModel;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;

public class Rouge extends AbstractEnemy {
    MovingSprite attack_right;
    MovingSprite attack_left;
    MovingSprite walk_right;
    MovingSprite walk_left;
    MovingSprite idle;
    public Rouge(GameModel gameModel, Vector2 pos) {
        super(gameModel, pos);
        this.detectionRadius = 4;
        this.attackRadius = 0.4f;
        this.health = 30;
        loadImages();
    }
    private void loadImages(){
        this.attack_right = new MovingSprite("rouge_attack.png",1,10);
        this.attack_left = new MovingSprite("rouge_attack_left.png",1,10);

        this.walk_right = new MovingSprite("rouge_walk1.png",1,10);
        this.walk_left = new MovingSprite("rouge_walk1_left.png",1,10);

        this.idle = new MovingSprite("rouge_idle.png",1,10);
    }

    /**
     * For Testing
     * */
    public Rouge(){
        super();
    }

    @Override
    protected boolean attack() {
        if(position.dst(player.getPos())<=attackRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            if(!(enemyState instanceof MeleeAttack)){
                enemyState = new MeleeAttack();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void defineBody() {
        //body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        //shape def for main fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.2f,0.2f);

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
        body.createFixture(fDef).setUserData("Rouge");
    }

    @Override
    public void update() {
        //this condition if testing
        if(world != null || body != null) {
            enemyState.update(this, player);
            enemyState.attack(this,player);
            if (enemyState instanceof Death) return;
            position.set(body.getPosition());
            boundingBox.set(position.x, position.y, boundingBox.getWidth(), boundingBox.getHeight());
        }
        //UPDATING STATES
        if(position.dst(player.getPos())<detectionRadius && player.getPlayerState() == AbstractPlayer.player_state.ALIVE){
            if(! (enemyState instanceof  AggroDash) )
            enemyState = new AggroDash();
            ((AggroDash)enemyState).setDashSpeed(5f);
        }
        //
        if(position.dst(player.getPos())>detectionRadius){
            enemyState = new IdleMovement();
            //make stationary
            ((IdleMovement)enemyState).setIdleMovementSpeed(0f);
        }
        attack();
    }

    @Override
    protected void movement() {
        throw new Error("Going to Remove, should be called");
    }

    @Override
    public CustomSprite getImage() {
        if(enemyState instanceof MeleeAttack){
            if(player.getX()<this.getX()){
                return attack_left;
            }
            return attack_right;
        }
        if(enemyState instanceof  AggroDash){

            if(player.getX()<this.getX()){
                return walk_left;
            }
            return walk_right;
        }
        return idle;
    }
}
