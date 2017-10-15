package model.being.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.being.enemystates.AggroDash;
import model.being.enemystates.Death;
import model.being.enemystates.IdleMovement;
import model.being.enemystates.MeleeAttack;
import model.being.player.AbstractPlayer;
import view.Assets;
import view.sprites.CustomSprite;

public class Rogue extends AbstractEnemy {
    private static final long serialVersionUID = -7180543831834015770L;
    public final AbstractEnemy.entity_type type = AbstractEnemy.entity_type.rogue;

    public Rogue(World world, AbstractPlayer player, Vector2 pos) {
        super(world,player,pos, AbstractEnemy.entity_type.rogue);
        this.detectionRadius = 4;
        this.attackRadius = 0.4f;
        this.health = 30;
    }

    /**
     * For Testing
     * */
    public Rogue(){
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
        body.createFixture(fDef).setUserData("Rogue");
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
            if(! (enemyState instanceof AggroDash) )
            enemyState = new AggroDash();
            ((AggroDash)enemyState).setDashSpeed();
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
    public CustomSprite getImage() {
        if(enemyState instanceof MeleeAttack){
            if(player.getX()<this.getX()){
                return Assets.rogueEnemyAttackLeft;
            }
            return Assets.rogueEnemyAttackRight;
        }
        if(enemyState instanceof  AggroDash){

            if(player.getX()<this.getX()){
                return Assets.rogueEnemyAttackLeft;
            }
            return Assets.rogueEnemyWalkRight;
        }

        return Assets.rogueEnemyIdle;
    }
}
