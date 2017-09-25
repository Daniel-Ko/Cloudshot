package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.collectable.AbstractWeapon;
import view.CustomSprite;
import view.MovingSprite;


/**
 * Represents the main playable character that the user controls
 * 
 * @author Jeremy Southon
 * */
public class Player extends AbstractPlayer {
	private int meleeRange = 30;
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

	public Player(Vector2 position, int width, int height, int hp, float speed, World world) {
		super(position, width, height, hp, speed,world);
		damage = 1;

		//load sprites
		current = new MovingSprite("player_idle.png",2,2);
		idle_right = new MovingSprite("player_idle.png",2,2);
		attack_right = new MovingSprite("player_attack.png",2,3);
		jump_right = new MovingSprite("player_jump.png",2,3);
		walk_right = new MovingSprite("player_walk.png",3,3);
		death = new MovingSprite("player_death.png",1,1);

		idle_left = new MovingSprite("player_idle.png",2,2);
		attack_left = new MovingSprite("player_attack.png",2,3);
		jump_left = new MovingSprite("player_jump.png",2,3);
		walk_left = new MovingSprite("player_walk.png",3,3);
		idle_left.flipHorizontal();
		attack_left.flipHorizontal();
		jump_left.flipHorizontal();
		walk_left.flipHorizontal();
		// TODO
	}


	/**
	 * Expected to loop through 'enemies' and if the player is attacking
	 * and there is a enemy within melee or attack_right range then we can hurt it..
	 *
	 * @param enemy Enemy which we are checking against
	 *
	 * @return true if the player attacked a enemy, o.w false
	 * */
	public boolean attack(AbstractEnemy enemy){
		//Enemy is within range of melee
		if(getPos().dst(enemy.getPosition())<meleeRange){
			if(getIsAttacking()){
				enemy.hit(damage);
			}
		}
		return false;
	}

	@Override
	public void shoot() {

	}

	@Override
		public float getX() {
			return getPos().x;
		}

		@Override
		public float getY() {
			return getPos().y;
		}



	@Override
	public CustomSprite getImage() {
		if(playerState == player_state.DEAD){
			return new MovingSprite("player_death.png", 1, 1);
		}

		if(getIsAttacking()){
			MovingSprite attacking =  new MovingSprite("player_attack.png", 2, 3);
			if(movingLeft)
				attacking.flipHorizontal();
			return  attacking;
		}
		//FIXME temp effect for jumping
		//JUMPING ANIMATION
		if(this.jumping){
			MovingSprite jump = new MovingSprite("player_jump.png", 2, 3);
			if(movingLeft)
				jump.flipHorizontal();
			return jump;
		}
		//IDLE ANIMATION
		if(velocity.x ==0 && velocity.y ==0){
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

}
