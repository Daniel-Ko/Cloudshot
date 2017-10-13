package model.being;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.javafx.css.parser.DeriveColorConverter;
import model.GameModel;
import model.GameModelInterface;
import model.being.enemies.*;
import model.being.player.AbstractPlayer;
import model.being.player.Player;

/**
 * This class is used to produce different Enemy objects.
 *
 * @author Jeremy Southon
 *
 */
public class EntityFactory {

	public static enum entity_type {
		archer, slime, rogue,spikeblock, boss2, boss1;
	}

	/**
	 * Method is used to produce the requested Enemy
	 *
	 * @param
	 *
	 * @param enemyType
	 *            the enemy that is being created.
	 *
	 * @return returns the enemy which matches enemyType
	 */
	public static AbstractEnemy produceEnemy(GameModel game, Vector2 position, entity_type enemyType) {
		if (enemyType == entity_type.archer) {
			ShootingEnemy shooter = new ShootingEnemy(game.getWorld(),game.getPlayer(),position);
			return shooter;
		} else if (enemyType == entity_type.slime) {
			Slime2 slime = new Slime2(game.getWorld(),game.getPlayer(),position);
			//to allow the slime to dynamically add itself at runtime
			slime.provideGameModel(game);
			return slime;
		}
		else if (enemyType == entity_type.spikeblock) {
			SpikeBlock spikeBlock = new SpikeBlock(game.getWorld(),game.getPlayer(),position);
			return spikeBlock;
		}
		else if (enemyType == entity_type.rogue) {
			Rogue r = new Rogue(game.getWorld(),game.getPlayer(),position);
			return r ;
		}
		return null;
	}

	/**
	 *
	 * @param gameModel used to gain access to the Box2D world in which to place our player, also used to get
	 *                  the camera to provide to init method for player.
	 * @param pos position in pixels where the player is located, definePlayer will scale this down into approp
	 *            box2D world cords.
	 *
	 * @return a player who has been fully init and placed in gameModels Box2D world
	 * */
	public static AbstractPlayer producePlayer(GameModelInterface gameModel, Vector2 pos){
		Player p = new Player();
		p.loadImage();
		p.initBox2D(gameModel.getWorld(),pos);
		p.setCamera(gameModel.getCamera());
		return p;
	}
}
