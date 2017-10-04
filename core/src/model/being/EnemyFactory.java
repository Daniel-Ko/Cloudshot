package model.being;

import com.badlogic.gdx.math.Vector2;

/**
 * This class is used to produce different Enemy objects.
 * 
 * @author Jeremy Southon
 * 
 */
public class EnemyFactory {

	public static enum enemy_type {
		shooter, melee, ant;
	}

	/**
	 * Method is used to produce the requested Enemy
	 *
	 * @param player gives each enemy a reference to our player
	 *
	 * @param hp the enemies health
	 *
	 * @param enemyType
	 *            the enemy that is being created.
	 *
	 * @return returns the enemy which matches enemyType
	 */
	public static AbstractEnemy getEnemy(Player player, enemy_type enemyType, int hp, Vector2 pos) {
		if (enemyType == enemy_type.shooter) {
			// ..
			// return new ShootingEnemy(..)
		} else if (enemyType == enemy_type.melee) {
			//return new Slime(hp, player,pos);
		}
		return null;
	}
}
