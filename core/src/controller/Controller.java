package controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

import game.Main;

/**
 * Extends InputAdapter rather than InputProcessor so that we can just override the methods we need,
 * rather than having to implement all of them.
 * This Controller is set as the input processor in the create() function in Main.java. It gets
 * pinged when an event happens. 
 * @author tomherdson
 *
 */
public class Controller extends InputAdapter {
	
	Main main; // store a reference to main so we can update x/y positions
	//Main will probably be replaced with a reference to main gamemodel class.
	
	public Controller(Main main) {
		this.main = main;
	}

	
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode) {
		case Keys.A: {
			main.x-=50;
			break;
		}
		case Keys.D: {
			main.x+=50;
			break;
		}
		case Keys.W: {
			main.y+=50;
			break;
		}
		case Keys.S: {
			main.y-=50;
			break;
		}
		}
		return true;
	}

	
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	
	


}
