package model.being;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import model.being.AbstractPlayer;
import model.collectable.AbstractWeapon;

import java.io.Serializable;
import java.util.List;

/**
 * Holds all properties of the AbstractPlayer
 * in a serializable form, especially for Box2D objects.
 * Box2D.World can be remade.
 */
public class PlayerData implements Serializable{
    
    private boolean isLiving = false;
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
    // Players inventory
    protected List<AbstractWeapon> inventory;
    // Position of the mouse
    protected Vector2 aimedAt = new Vector2(50,50);
    
    //
    //Box2D
    //
    
    //body
    private Vector2 bodyPos;
    private Vector2 bodyLinearVelocity;
    
    //fixturedef
    private Shape shape;
    private float friction = 0.2F;
    private float density = 0.0F;
    private boolean isSensor = false;
    
    
    public PlayerData(AbstractPlayer player) {
        setUIProperties(player);
        setActionProperties(player);
        setInventory(player);
        setPhysicsProperties(player);
        setBox2DProperties(player);
        setAimLocation(player);
        setLiving(player);
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
        this.shape = player.getPlayerProperties().shape;
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
    
    public boolean isLiving() {
        return isLiving;
    }
    
    public Vector2 getPos() {
        return pos;
    }
    
    public Vector2 getVelocity() {
        return velocity;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public Rectangle getBoundingBox() {
        return boundingBox;
    }
    
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
    
    public List<AbstractWeapon> getInventory() {
        return inventory;
    }
    
    public Vector2 getAimedAt() {
        return aimedAt;
    }
    
    public Vector2 getBodyPos() {
        return bodyPos;
    }
    
    public Vector2 getBodyLinearVelocity() {
        return bodyLinearVelocity;
    }
    
    public Shape getShape() {
        return shape;
    }
    
    public float getFriction() {
        return friction;
    }
    
    public float getDensity() {
        return density;
    }
    
    public boolean isSensor() {
        return isSensor;
    }
}
