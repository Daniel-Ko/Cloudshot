package game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import controller.Controller;

public class Main extends ApplicationAdapter {
	Controller controller;
	
	SpriteBatch batch;
	Texture img;
	
	public int x = 50;
	public int y = 50;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		controller = new Controller(this);
		Gdx.input.setInputProcessor(controller);//set the controller to recieve input when keys pressed
		img = new Texture(Gdx.files.internal("anticon.png"));
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		if(controller.keyPressed) {
			switch(controller.keycode) {
			case Keys.A: {
				this.x-=10;
				break;
			}
			case Keys.D: {
				this.x+=10;
				break;
			}
			case Keys.W: {
				this.y+=10;
				break;
			}
			case Keys.S: {
				this.y-=10;
				break;
			}
			}
		}
		
		batch.draw(img, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
