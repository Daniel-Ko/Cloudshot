package model.being;

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
	 * @param enemyType
	 *            the enemy that is being created.
	 * @return returns the enemy which matches enemyType
	 */
	public static AbstractEnemy getEnemy(enemy_type enemyType) {
		if (enemyType == enemy_type.shooter) {
			// ..
			// return new ShootingEnemy(..)
		} else if (enemyType == enemy_type.melee) {
			// ..
		}
		return null;
	}
}
