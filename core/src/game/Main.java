package game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import controller.Controller;

public class Main extends ApplicationAdapter {
	Controller controller;
	Texture img;

	public int x = 50;
	public int y = 50;

	// Sprite animation.
	SpriteBatch batch;
	Texture sprite_img;
	TextureRegion[] animationFrames;
	Animation animation;
	float elapsedTime;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		controller = new Controller(this);
		Gdx.input.setInputProcessor(controller);//set the controller to recieve input when keys pressed
		img = new Texture(Gdx.files.internal("anticon.png"));

		// Sprite initialisation.
		sprite_img = new Texture(Gdx.files.internal("sprite-animation4.png"));
		TextureRegion[][] tmpFrames = TextureRegion.split(sprite_img, sprite_img.getWidth() / 6,
				sprite_img.getHeight() / 5);

		animationFrames = new TextureRegion[30];
		int index = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				animationFrames[index++] = tmpFrames[i][j];
			}
		}

		// 25 frames per second.
		animation = new Animation(1f/4f, animationFrames);



		
	}

	@Override
	public void render () {
		elapsedTime += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(1, 1, 1, 1);
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

		batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), 0, 0);
		batch.draw(img, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
