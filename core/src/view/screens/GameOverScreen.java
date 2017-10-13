package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import view.ButtonFactory;
import view.LabelFactory;

public class GameOverScreen extends ScreenAdapter {

    private Stage stage;

    public GameOverScreen(){
        this.stage = new Stage(new ScreenViewport());

        stage.addActor(LabelFactory.gameOverLabel());

        TextButton restartButton = ButtonFactory.restartButton(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stage.addActor(restartButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1,1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
