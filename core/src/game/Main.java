package game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import controller.Controller;
import controller.PlayerController;
import model.being.Player;

public class Main extends ApplicationAdapter {
	Controller controller;
	PlayerController pcontroller;
	Player p ;
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		p = new Player(new Vector2(50,160), 100, 100, 10, new Vector2(3,3), new Texture(Gdx.files.internal("anticon.png")));
		pcontroller= new PlayerController(p);
		Gdx.input.setInputProcessor(pcontroller);//set the controller to recieve input when keys pressed
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Updating game entities
		updatePlayer();
		
		//Displaying game
		batch.begin();
		batch.draw(p.getPlayerImage(),p.getPos().x,p.getPos().y);
		batch.end();
	}
	
	public void updatePlayer(){
		pcontroller.applyMovement();
		p.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
