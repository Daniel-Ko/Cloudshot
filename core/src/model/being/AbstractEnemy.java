package model.being;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
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

	protected Vector2 position;
	protected Vector2 velocity;
	protected int speed;
	protected int health;
	protected int damage;
	protected AbstractPlayer player;

	protected enemy_state state = enemy_state.EALIVE;

	public static enum enemy_state{
		EALIVE,EDEAD,EATTACKING
	}

	//Box2D
	protected World world;
	protected Body body;
	protected FixtureDef fDef;

	public AbstractEnemy(int hp, AbstractPlayer player, Vector2 pos, World world){
		health = hp;
		speed = 2;//TODO
		damage = 1;
		position = pos;
		this.world = world;
		velocity = new Vector2(0,0);
		boundingBox = new Rectangle(position.x,position.y,50,50);//FIXME
		this.player = player;
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

	public void hit(int damage){
		assert damage > 0 : "Damage should be a positive number";
		if(state == enemy_state.EDEAD)return;
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

	public Vector2 getPosition() {
		return position;
	}

	@Override
	public abstract CustomSprite getImage();
}
