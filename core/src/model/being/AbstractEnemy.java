package model.being;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import model.GameObjectInterface;
import view.CustomSprite;

/**
 *  Class contains attributes which is common among all 'enemys'
 *  
 *  @author Jeremy Southon
 * 
 * */
public abstract class AbstractEnemy implements GameObjectInterface, EntityInterface {
	/** Used for collisions and getting X & Y coords */
	protected Rectangle boundingBox;
	protected Vector2 position;
	protected Vector2 velocity;
	protected int speed;
	protected int health;
	protected AbstractPlayer player;

	protected enemy_state state = enemy_state.EALIVE;
	public static enum enemy_state{
		EALIVE,EDEAD,EATTACKING
	}

	public AbstractEnemy(int hp,AbstractPlayer player,Vector2 pos){
		health = hp;
		speed = 2;//TODO
		position = pos;
		velocity = new Vector2(0,0);
		boundingBox = new Rectangle(position.x,position.y,50,50);//FIXME
		this.player = player;
	}

	protected abstract boolean attack();


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
