package model.being;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameModel;
import model.GameObjectInterface;
import view.CustomSprite;

/**
 *  Class contains attributes which is common among all 'enemys'
 *  
 *  @author Jeremy Southon
 * 
 * */
public abstract class AbstractEnemy implements GameObjectInterface, EntityInterface, java.io.Serializable {

	private static final long serialVersionUID = -5230554639550482142L;
	/** Used for collisions and getting X & Y coords */
	protected Rectangle boundingBox;
	public int detectionRadius = 3;
	public float attackRadius = 0.5f;

	protected Vector2 position;
	protected Vector2 velocity;
	protected int speed;
	protected int health;
	protected float damage;
	protected AbstractPlayer player;

	protected enemy_state state = enemy_state.EALIVE;

	public static enum enemy_state{
		EALIVE,EDEAD,EATTACKING,EIDLE
	}

	//Box2D
	protected World world;
	protected Body body;
	protected FixtureDef fDef;

	protected  GameModel game;

	public EnemyState enemyState;
	//for drawing using sprite batch
	protected float drawingWidth =0.6f;
	protected float drawingHeight = 0.8f;
	public AbstractEnemy(GameModel gameModel, Vector2 pos){
		this.game = gameModel;
		this.world = gameModel.getWorld();
		this.player = gameModel.getPlayer();

		position = pos;
		boundingBox = new Rectangle(position.x,position.y,50/ GameModel.PPM,50/ GameModel.PPM);//FIXME
		health = 10;
		speed = 2;//TODO
		damage = 1;
		defineBody();
		enemyState = new IdleMovement();
	}

	protected abstract boolean attack();

	protected abstract void defineBody();
	/**
	 * Method used to define this enemy's movement patterns
	 * */
	protected abstract void movement();

	/**
	 * Classic update method which should be called each 'frame'/update
	 * */
	public abstract void update();

	/**
	 * For external use for hurting the enemy
	 * */
	public void hit(int damage){
		assert damage > 0 : "Damage should be a positive number";
		enemyState.damage(this,damage);
	}
	/**
	 * Used internally by being package members
	 * */
	public void internalDamage(int damage){
		assert damage > 0 : "Damage should be a positive number";
		health-=damage;
	}

	@Override
	public float getX() {
		return position.x;
	}

	@Override
	public float getY() {
		return position.y;
	}

	public Rectangle getBoundingBox(){return boundingBox;}

	public Vector2 getPosition() {
		return position;
	}
	public enemy_state getState(){return this.state; }
	public float getDrawingWidth(){ return  drawingWidth;}
	public float getDrawingHeight(){return  drawingHeight;}
	@Override
	public abstract CustomSprite getImage();
}
