package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import model.being.Player;

public class PlayerController extends InputAdapter {
	
	//which keys are being pressed
	//allow us to move in multiple directions
	private boolean up;
	private boolean down;
	boolean right;
	public boolean left;
	
	private boolean jumping;
	//Player who's this controller belongs to
	private Player player;
	
	public PlayerController(Player p){
		player = p;
	}
	
	public void applyMovement(){
		if(right)player.moveRight();
		if(left)player.moveLeft();
		//TODO
	}
	
	//only returns false if key pressed isnt wasd
	@Override
	public boolean keyDown (int keycode) {
		if(Input.Keys.W == keycode){
			up = true;
			return true;
		}
		if(Input.Keys.S == keycode){
			down = true;
			return true;
		}
		if(Input.Keys.A == keycode){
			left = true;
			return true;
		}
		if(Input.Keys.D == keycode){
			right = true;
			return true;
		}

		if(Input.Keys.SPACE == keycode){
			jumping = true;
			return jumping;
		}
		return false;
	}
	
	//Only returns false if the key released wasnt wasd
	@Override
	public boolean keyUp (int keycode) {
		if(Input.Keys.W == keycode){
			up = false;
			return true;
		}
		if(Input.Keys.S == keycode){
			down = false;
			return true;
		}
		if(Input.Keys.A == keycode){
			left = false;
			return true;
		}
		if(Input.Keys.D == keycode){
			right = false;
			return true;
		}
		return false;
	}
}
