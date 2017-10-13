package view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import view.utils.ButtonFactory;
import view.utils.LabelFactory;

public class MenuScreen extends ScreenAdapter {

    public static Game game;
    private Stage stage;

    public MenuScreen(Game game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        stage.addActor(LabelFactory.mainMenuLabel());

        TextButton startButton = ButtonFactory.startButton(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2
        );

        stage.addActor(startButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1,1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
