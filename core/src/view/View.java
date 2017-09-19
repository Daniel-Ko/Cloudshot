package view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import controller.Controller;

public class View extends ApplicationAdapter{

    Controller controller;
    Texture img;

    public int x = 50;
    public int y = 50;

    // Sprite animation.
    SpriteBatch batch;
    SpriteDrawer walkingMan;
    float elapsedTime;


    @Override
    public void create () {
        batch = new SpriteBatch();
        controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);//set the controller to recieve input when keys pressed
        img = new Texture(Gdx.files.internal("anticon.png"));
        walkingMan = new SpriteDrawer("sprite-animation4.png", 5, 6);
        walkingMan.createSprite(0.25f);
    }

    @Override
    public void render () {
        elapsedTime += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(controller.keyPressed) {
            switch(controller.keycode) {
                case Input.Keys.A: {
                    this.x-=10;
                    break;
                }
                case Input.Keys.D: {
                    this.x+=10;
                    break;
                }
                case Input.Keys.W: {
                    this.y+=10;
                    break;
                }
                case Input.Keys.S: {
                    this.y-=10;
                    break;
                }
            }
        }

        batch.draw(walkingMan.getFrameFromTime(elapsedTime), 0, 0);
        batch.draw(img, x, y);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        //img.dispose();
    }}
