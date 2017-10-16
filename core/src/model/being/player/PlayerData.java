package model.being.player;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import model.collectable.AbstractWeapon;

import java.io.Serializable;
import java.util.List;

/**
 * Holds all properties of the AbstractPlayer
 * in a serializable form, especially for Box2D objects.
 * Box2D.World can be remade.
 */
public class PlayerData implements Serializable{
    private static final long serialVersionUID = -7251883622678858138L;

    //Player-specific methods (not AbstractPlayer)

    //protected AbstractWeapon curWeapon;


    //player state
    private boolean isLiving = false;

    /* variables used in player physics */
    protected Vector2 pos;
    protected Vector2 velocity;
    
    protected int health;
    protected int damage;
    protected Rectangle boundingBox;
    
    // Variables of player actions
    protected boolean inAir = false;
    protected boolean attacking = false;
    protected boolean grounded = false;
    protected boolean movingLeft;
    protected boolean movingRight;

    // Players inventory
    protected List<AbstractWeapon> inventory;
    protected int curWeapon;

    // Position of the mouse
    protected Vector2 aimedAt = new Vector2(50,50);


    
    //
    //Box2D
    //
    
    //body
    private Vector2 bodyPos;
    private Vector2 bodyLinearVelocity;
    
    //fixturedef
    private float friction = 0.2F;
    private float density = 0.0F;
    private boolean isSensor = false;
    
    
    public PlayerData(AbstractPlayer player) {
        setUIProperties(player);
        setActionProperties(player);
        setInventory(player);
        setPhysicsProperties(player);
        if(player.getBody() != null) {
            setBox2DProperties(player);
        }
        setAimLocation(player);
        setLiving(player);
        setInstanceProperties(player);
    }

    private void setInstanceProperties(AbstractPlayer player) {
        curWeapon = player.getCurWeapon();
    }

    private void setUIProperties(AbstractPlayer player) {
        this.health = player.getHealth();
        this.damage = player.getDamage();
        this.boundingBox = player.getBoundingBox();
    }
    
    private void setInventory(AbstractPlayer player) {
        this.inventory = player.getInventory();
    }
    
    private void setActionProperties(AbstractPlayer player) {
        this.inAir = player.isInAir();
        this.attacking = player.isAttacking();
        this.grounded = player.isGrounded();
        this.movingLeft = player.isMovingLeft();
        this.movingRight = player.isMovingRight();
    }
    
    private void setPhysicsProperties(AbstractPlayer player) {
        this.pos = player.getPos();
    }
    
    private void setBox2DProperties(AbstractPlayer player) {
        setBox2DBody(player);
        setBox2DFixtureDef(player);
    }
    
    private void setBox2DBody(AbstractPlayer player) {
        this.bodyPos = player.getBody().getPosition();
        this.bodyLinearVelocity = player.getBody().getLinearVelocity();
    }
    
    private void setBox2DFixtureDef(AbstractPlayer player) {
        this.density = player.getPlayerProperties().density;
        this.friction = player.getPlayerProperties().friction;
        this.isSensor = player.getPlayerProperties().isSensor;
    }
    
    private void setAimLocation(AbstractPlayer player) {
        this.aimedAt = player.getAimedAt();
    }
    
    private void setLiving(AbstractPlayer player) {
        if(player.getPlayerState() == AbstractPlayer.player_state.ALIVE) {
            isLiving = true;
        }
    }
    
    /** GETTERS */


    public int getCurWeapon() {
        return curWeapon;
    }

    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

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

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public List<AbstractWeapon> getInventory() {
        return inventory;
    }

    public void setInventory(List<AbstractWeapon> inventory) {
        this.inventory = inventory;
    }

    public Vector2 getAimedAt() {
        return aimedAt;
    }

    public void setAimedAt(Vector2 aimedAt) {
        this.aimedAt = aimedAt;
    }

    public Vector2 getBodyPos() {
        return bodyPos;
    }

    public void setBodyPos(Vector2 bodyPos) {
        this.bodyPos = bodyPos;
    }

    public Vector2 getBodyLinearVelocity() {
        return bodyLinearVelocity;
    }

    public void setBodyLinearVelocity(Vector2 bodyLinearVelocity) {
        this.bodyLinearVelocity = bodyLinearVelocity;
    }


    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public void setSensor(boolean sensor) {
        isSensor = sensor;
    }


}
