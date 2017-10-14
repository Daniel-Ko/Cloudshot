package view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.GameModelInterface;
import view.utils.ButtonFactory;
import view.utils.LabelFactory;

public class MenuScreen extends ScreenAdapter {

    public static Game game;
    private Stage stage;

    public MenuScreen(Game game){
        MenuScreen.game = game;
        this.stage = new Stage(new ScreenViewport());

        stage.addActor(LabelFactory.mainMenuLabel());

        TextButton startButton = ButtonFactory.startButton(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2
        );

        stage.addActor(startButton);
    }

    public MenuScreen(Game game, GameScreen gameScreen, GameModelInterface gameModel){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        stage.addActor(LabelFactory.mainMenuLabel());

        TextButton startButton = ButtonFactory.startButton(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2
        );

        TextButton resumebutton = ButtonFactory.resumeGameButton(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2,
                gameScreen
        );

        TextButton saveButton = ButtonFactory.saveButton(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2,
                gameModel
        );

        TextButton loadButton = ButtonFactory.loadButton(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2,
                gameModel,
                gameScreen
        );

        stage.addActor(resumebutton);
        stage.addActor(startButton);
        stage.addActor(saveButton);
        stage.addActor(loadButton);

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
