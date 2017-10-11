package model.being;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.being.enemies.AbstractEnemy;
import model.being.enemystates.EnemyState;
import model.being.player.AbstractPlayer;
//import view.CustomSprite;

/**
 * Created by kodani on 6/10/17.
 */
public class EnemyData {
    protected Rectangle boundingBox;

    //BOX2D
    private Vector2 bodyPos;
    private Vector2 bodyLinearVelocity;
    private float density;
    private float friction;
    private boolean isSensor;



    protected Vector2 position;
    protected int speed;
    protected int health;
    protected float damage;
    protected AbstractPlayer player;

    protected AbstractEnemy.enemy_state state = AbstractEnemy.enemy_state.EALIVE;

    public static enum enemy_state{
        EALIVE,EDEAD,EATTACKING,EIDLE
    }

    //Box2D
    protected World world;
    protected Body body;
    protected FixtureDef fDef;

    public EnemyState enemyState;

    //for drawing using sprite batch
    protected float drawingWidth =0.6f;
    protected float drawingHeight = 0.8f;

    public EnemyData(AbstractEnemy enem){
        setEnemyProperties(enem);
        setEnemyState(enem);
        setBox2DProperties(enem);
    }

    private void setEnemyState(AbstractEnemy enem) {
    }

    private void setBox2DProperties(AbstractEnemy enem) {
        setBox2DBody(enem);
        setBox2DFixtureDef(enem);
    }

    private void setBox2DBody(AbstractEnemy enem) {
        this.bodyPos = player.getBody().getPosition();
        this.bodyLinearVelocity = player.getBody().getLinearVelocity();
    }

    private void setBox2DFixtureDef(AbstractEnemy enem) {
        //this.shape = player.getPlayerProperties().shape;
        this.density = player.getPlayerProperties().density;
        this.friction = player.getPlayerProperties().friction;
        this.isSensor = player.getPlayerProperties().isSensor;
    }

    private void setEnemyProperties(AbstractEnemy enem) {
        this.boundingBox = enem.getBoundingBox();
    }



    public Rectangle getBoundingBox() {
        return boundingBox;
    }


    public Vector2 getPosition() {
        return position;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public float getDamage() {
        return damage;
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    public AbstractEnemy.enemy_state getState() {
        return state;
    }

    public World getWorld() {
        return world;
    }

    public Body getBody() {
        return body;
    }

    public FixtureDef getfDef() {
        return fDef;
    }

    public EnemyState getEnemyState() {
        return enemyState;
    }

    public float getDrawingWidth() {
        return drawingWidth;
    }

    public float getDrawingHeight() {
        return drawingHeight;
    }


}
