package model.being;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import model.collectable.AbstractWeapon;
import view.MovingSprite;


/**
 * Represents the main playable character that the user controls
 * 
 * @author Jeremy Southon
 * */
public class Player extends AbstractPlayer {
	private int meleeRange = 30;
	protected AbstractWeapon curWeapon;
	public Player(Vector2 position, int width, int height, int hp, float speed) {
		super(position, width, height, hp, speed);
		damage = 1;
		// TODO
	}

	/**
	 * Expected to loop through 'enemies' and if the player is attacking
	 * and there is a enemy within melee or attack range then we can hurt it..
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
	public MovingSprite getImage() {
		if(playerState == player_state.DEAD){
			return new MovingSprite("player_death.png", 1, 1);
		}
		if(getIsAttacking()){
			return new MovingSprite("player_attack.png", 2, 3);
		}
		//FIXME temp effect for jumping
		if(velocity.y > 0){
			return new MovingSprite("player_jump.png", 2, 3);
		}
		if(velocity.x ==0 && velocity.y ==0){
			//idle
			return new MovingSprite("player_idle.png", 2, 2);
		}
		return new MovingSprite("spritesheet.png", 3, 3);
	}

}
