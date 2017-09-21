package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import model.being.Player;

/**
 * Keeps track on input from user and applys the appropriate actions on the this
 * controllers Player object
 * 
 * @author Jeremy Southon
 */
public class PlayerController extends InputAdapter {

	// which keys are being pressed
	// allow us to move in multiple directions
	boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	private boolean jumping;

	/**
	 * Player who's this controller belongs to
	 */
	private Player player;

	public PlayerController(Player p) {
		player = p;
	}

	/**
	 * Should be called each frame/tick in order to apply the appropriate
	 * updates to the Player recieved from userinput
	 * 
	 * Without this, the player would not be updated.
	 */
	public void applyMovement() {
		if (right)
			player.moveRight();
		if (left)
			player.moveLeft();
		if(down)
			player.moveDown();
		if(up)
			player.moveUp();
		// TODO
	}

	// only returns false if key pressed isnt wasd
	@Override
	public boolean keyDown(int keycode) {

		if (Input.Keys.A == keycode) {
			left = true;
			return true;
		}
		if (Input.Keys.D == keycode) {
			right = true;
			return true;
		}

		if (Input.Keys.W == keycode) {
			up = true;
			return true;
		}

		if (Input.Keys.S == keycode) {
			down = true;
			return true;
		}

		if (Input.Keys.SPACE == keycode) {
			jumping = true;
			return jumping;
		}
		return false;
	}

	// Only returns false if the key released wasnt wasd
	@Override
	public boolean keyUp(int keycode) {
		if (Input.Keys.A == keycode) {
			left = false;
			return true;
		}
		if (Input.Keys.D == keycode) {
			right = false;
			return true;
		}
		if (Input.Keys.W == keycode) {
			up = false;
			return true;
		}
		if (Input.Keys.S == keycode) {
			down = false;
			return true;
		}
		return false;
	}
}
