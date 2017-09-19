package view;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import controller.PlayerController;
import model.being.Player;

public class View extends ApplicationAdapter {

	// TODO implement master controller
	PlayerController playerController;
	Player player;

	public int x = 50;
	public int y = 50;

	// Sprite animation.
	SpriteBatch batch;
	SpriteDrawer walkingMan;
	float elapsedTime;

	// Map
	private OrthographicCamera cam;
	private Sprite mapSprite;

	@Override
	public void create() {
		batch = new SpriteBatch();
		player = new Player(new Vector2(50, 50), 50, 50, 10, new Vector2(5, 2));
		playerController = new PlayerController(player);

		// Game Controller
		Gdx.input.setInputProcessor(playerController);

		walkingMan = new SpriteDrawer("sprite-animation4.png", 5, 6);
		walkingMan.createSprite(0.25f);

		mapSprite = new Sprite(new Texture(Gdx.files.internal("game_map.jpg")));
		mapSprite.setPosition(0, 0);
		mapSprite.setSize(100, 100);

		float w = Gdx.graphics.getWidth() * 0.5f;
		float h = Gdx.graphics.getHeight() * 0.5f;

		// Constructs a new OrthographicCamera, using the given viewport width
		// and height
		// Height is multiplied by aspect ratio.
		cam = new OrthographicCamera(30, 30 * (h / w));
		cam.position.set(cam.viewportWidth, cam.viewportHeight, 0);
		cam.update();
	}

	@Override
	public void render() {
		//updating model
		updatePlayer();
		
		handleInput();
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		elapsedTime += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(mapSprite, 0, 0);
		batch.draw(walkingMan.getFrameFromTime(elapsedTime), 100, 100);
		batch.end();
	}

	private void updatePlayer() {
		playerController.applyMovement();
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			cam.zoom += 0.1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			cam.zoom -= 0.1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.translate(-10, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.translate(10, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.translate(0, 3, 0);
		}

		// cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

		float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

		// cam.position.x = MathUtils.clamp(cam.position.x,
		// effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
		// cam.position.y = MathUtils.clamp(cam.position.y,
		// effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
	}

	@Override
	public void dispose() {
		batch.dispose();
		// img.dispose();
	}
}
