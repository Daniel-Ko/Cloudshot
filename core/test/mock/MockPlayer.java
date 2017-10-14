package mock;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import model.being.enemies.AbstractEnemy;
import model.being.enemystates.EnemyState;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractWeapon;
import model.collectable.Shotgun;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan Ko on 10/5/2017.
 */
public class MockPlayer {
    /**
     * Used to represent the different states of the player
     */
    public enum player_state {
        ALIVE, DEAD
    }

    protected AbstractPlayer.player_state playerState = AbstractPlayer.player_state.ALIVE;

    /* variables used in player physics */
    protected Vector2 pos;
    protected Vector2 velocity;

    protected int health;
    protected int damage;
    protected Rectangle boundingBox;

    // Variables of player actions
    protected  boolean inAir = false;
    protected boolean attacking = false;
    protected boolean grounded = false;
    protected boolean movingLeft;
    protected boolean movingRight;



    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;
    private float maxSpeed = 2.5f;
    private float maxSpeedInAir = 0.5f;
    private int doubleJump = 0;
    private float meleeRange = 1;
    protected AbstractWeapon curWeapon;



    private CustomSprite current;

    private CustomSprite idle_right;
    private CustomSprite attack_right;
    private CustomSprite jump_right;
    private CustomSprite walk_right;
    private CustomSprite death;


    private CustomSprite idle_left;
    private CustomSprite attack_left;
    private CustomSprite jump_left;
    private CustomSprite walk_left;

    Shotgun pistol;
    List<BulletImpl> bullets = new ArrayList<>();

    //Box2D
    int numFootContact = 0;
    Body body;

    // Players inventory
    protected List<AbstractWeapon> inventory;

    // Position of the mouse
    protected Vector2 aimedAt = new Vector2(50,50);


    protected EnemyState state;



    public MockPlayer() {
        health = 10;
        velocity = new Vector2(0,0);
//        boundingBox = new Rectangle(pos.x,pos.y, 8/GameModel.PPM, 8/GameModel.PPM);
        bullets = new ArrayList<>();
        this.inventory = new ArrayList<AbstractWeapon>();

//        body = new Body();
    }




    public void update(List<AbstractEnemy> enemies){

        if(numFootContact< 1)inAir = true;
        if(numFootContact >= 1){
            inAir = false;
            doubleJump = 0;
        }

        ArrayList<BulletImpl> toRemove = new ArrayList<>();
        //updating players bullets
        for(BulletImpl b: bullets ){
            // b.update(enemies,);
            if (b.isToRemove()) {
                toRemove.add(b);
            }
        }
        for(BulletImpl b: toRemove){
            bullets.remove(b);
        }

    }

    /**
     * Expected to loop through 'enemies' and if the player is attacking
     * and there is a enemy within melee or attack_range then we can hurt it..

     * @param enemy Enemy which we are checking against
     *
     * @return true if the player attacked a enemy, o.w false
     * */
    public boolean attack(AbstractEnemy enemy){
        //Enemy is within range of melee
        if(getPos().dst(enemy.getPosition())<meleeRange){
            if(getIsAttacking()){
                //enemy.hit(damage);
                enemy.enemyState.damage(enemy,damage);
            }
        }
        return false;
    }

    public void shoot() {
        if(this.getCurWeapon()== null){return;}


    }

    /**
     * Defined what happens when moving right
     * */
    public void moveRight() {

        if(inAir &&  body.getLinearVelocity().x < maxSpeedInAir){
            body.applyLinearImpulse(new Vector2(0.07f,0),body.getWorldCenter(),true);
        }
        else if(!inAir){
            body.setLinearVelocity(maxSpeed,body.getLinearVelocity().y);
        }
    }

    /**
     * Defined what happens when moving left
     * */
    public void moveLeft()
    {
        //restrict movement speed in air
        if(inAir && body.getLinearVelocity().x>-maxSpeedInAir) {
            body.applyLinearImpulse(new Vector2(-0.07f, 0), body.getWorldCenter(), true);
        }
        //On ground and not yet at max speed
        else if(!inAir)
            body.setLinearVelocity(-maxSpeed,body.getLinearVelocity().y);
    }

    /**
     * applies players jump speed onto Box2D body
     * */
    public void jump(){
        //players feet is not in contact with ground therefore cant jump
        if(numFootContact<1){
            inAir =true;
        }
        //limiting jump speed
        if(body.getLinearVelocity().y<maxSpeed && !inAir || doubleJump <= 1){
            body.applyLinearImpulse(new Vector2(0,0.3f),body.getWorldCenter(),true);
            this.grounded = false;
            doubleJump++;
            inAir = true;
        }
    }

    public float getX() {
        return getPos().x;
    }

    public float getY(){ return getPos().y; }

    protected void handleInput(){
        if(movingLeft){
            //only want to move left
            moveLeft();
        }
        else if(movingRight){
            moveRight();
        }
    }
    /**
     * Method constantly updates the fields which indicate the actions the player is
     * preforming such as moving left,right..
     * */
    private void updateActionsPlayerDoing(){
        //checks if dead
        if(health<=0){
            playerState = AbstractPlayer.player_state.DEAD;
        }
    }

    public List<BulletImpl> getBullets(){ return this.bullets; }

    public float getMeleeRange() {
        return meleeRange;
    }

    public void setMeleeRange(float meleeRange) {
        this.meleeRange = meleeRange;
    }

    public AbstractWeapon getCurWeapon(){ return this.curWeapon; }

    public CustomSprite getImage() {
        if(playerState == AbstractPlayer.player_state.DEAD){
            return new MovingSprite("player_death.png", 1, 1);
        }

        if(getIsAttacking()){
            MovingSprite attacking =  new MovingSprite("player_attack.png", 2, 3);
            if(movingLeft)
                attacking.flipHorizontal();
            return  attacking;
        }
        //FIXME temp effect for inAir
        //JUMPING ANIMATION
        if(this.inAir){
            MovingSprite jump = new MovingSprite("player_jump.png", 2, 3);
            if(movingLeft)
                jump.flipHorizontal();
            return jump;
        }
        //IDLE ANIMATION
        if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0){
            MovingSprite idle = new MovingSprite("player_idle.png", 2, 2);
            //idle
            if(movingLeft) {
                idle.flipHorizontal();
                return idle;
            }
            return idle;
        }
        MovingSprite walking = new MovingSprite("player_walk.png", 3, 3);
        if(movingLeft)
            walking.flipHorizontal();
        return walking;


    }


    /**
     * Inflicts param damage onto players current health
     *
     * @param damage to inflict on player
     * */
    public void hit(float damage){
        this.health-=damage;
    }

    /**
     * @return List of AbstractWeapons which the player has in inventory
     * */
    public List<AbstractWeapon> getInventory() {
        return inventory;
    }

    public boolean keyDown(int keycode) {
        //Player is dead cant move
        if(playerState == AbstractPlayer.player_state.DEAD)return false;
        switch (keycode){
            case Input.Keys.A:
                movingLeft = true;
                movingRight = false;
                break;
            case Input.Keys.D:
                movingRight = true;
                movingLeft = false;
                break;
            case Input.Keys.F:
                attacking = true;
                break;

            case Input.Keys.SPACE:
                jump();
                break;

            default:
                break;
        }
        return true;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
                movingLeft = false;
                break;
            case Input.Keys.D:
                movingRight = false;
                break;
            case Input.Keys.W:
                break;
            case Input.Keys.F:
                attacking = false;
                break;
            default:
                break;
        }
        return true;
    }

    //
	/* GETTERS + SETTERS */
    //

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public AbstractPlayer.player_state getPlayerState() {
        return playerState;
    }

    public void setPlayerState(AbstractPlayer.player_state playerState) {
        this.playerState = playerState;
    }

    public void setPos(Vector2 newPos) {pos = newPos;}

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setBullets(List<BulletImpl> newBullets) {
        bullets = newBullets;
    }

    public Vector2 getAimedAt(){ return aimedAt; }

    public Rectangle getBoundingBox()
    {
        return boundingBox;
    }

    public boolean getIsAttacking(){ return attacking; }

    public boolean isInAir() {
        return inAir;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }


    //==========================================

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setInventory(List<AbstractWeapon> inventory) {
        this.inventory = inventory;
    }

    public void setAimedAt(Vector2 aimedAt) {
        this.aimedAt = aimedAt;
    }
}
