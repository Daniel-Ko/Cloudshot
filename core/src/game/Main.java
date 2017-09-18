package game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import controller.Controller;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	public int x = 50;
	public int y = 50;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		InputProcessor controller = (InputProcessor) new Controller(this);
		Gdx.input.setInputProcessor(controller);//set the controller to recieve input when keys pressed
		img = new Texture(Gdx.files.internal("anticon.png"));
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		
		
		batch.draw(img, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
