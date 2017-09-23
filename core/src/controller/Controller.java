package controller;

import com.badlogic.gdx.InputAdapter;

import view.screens.GameScreen;

/**
 * Extends InputAdapter rather than InputProcessor so that we can just override the methods we need,
 * rather than having to implement all of them.
 * This Controller is set as the input processor in the create() function in GameScreen.java. It gets
 * pinged when an event happens. 
 * @author tomherdson
 *
 */
public class Controller extends InputAdapter {
	
	GameScreen view; // store a reference to view so we can update x/y positions
	//view will probably be replaced with a reference to main gamemodel class.
	
	public boolean keyPressed = false;
	public int keycode;
	
	public Controller(GameScreen view) {
		this.view = view;
	}

	
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		keyPressed = true;
		this.keycode = keycode;
		return true;
	}

	
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		keyPressed = false;
		return true;
	}
	
	


}
