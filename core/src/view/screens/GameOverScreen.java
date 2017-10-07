package view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import view.CloudShotGame;

public class GameOverScreen extends ScreenAdapter {

    private Game game;
    private Stage stage;

    public GameOverScreen(Game game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Label title = new Label("Game Over", CloudShotGame.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1.5f);
        stage.addActor(title);

        TextButton restartButton = createRestartButton();
        stage.addActor(restartButton);
    }

    private TextButton createRestartButton() {
        TextButton restartButton = new TextButton("Restart", CloudShotGame.gameSkin);
        restartButton.setWidth(Gdx.graphics.getWidth()/2);
        restartButton.setPosition(
                Gdx.graphics.getWidth()/2 - restartButton.getWidth()/2,
                Gdx.graphics.getHeight()/2 - restartButton.getHeight()/2
        );
        restartButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return restartButton;
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
