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
import model.data.GameStateTransactionHandler;
import model.data.StateQuery;
import view.CloudShotGame;

public class MenuScreen extends ScreenAdapter {

    private Game game;
    private Stage stage;

    public MenuScreen(Game game){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Label title = new Label("Main Menu", CloudShotGame.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1);
        stage.addActor(title);

        TextButton startButton = createStartButton();
        TextButton loadButton = createLoadButton();
        stage.addActor(startButton);
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

    private TextButton createStartButton(){
        TextButton startButton = new TextButton("Start", CloudShotGame.gameSkin);
        startButton.setWidth(Gdx.graphics.getWidth()/2);
        startButton.setPosition(
                Gdx.graphics.getWidth()/2 - startButton.getWidth()/2,
                Gdx.graphics.getHeight()/2 - startButton.getHeight()/2
        );
        startButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return startButton;
    }

    private TextButton createLoadButton() {
        TextButton loadButton = new TextButton("Load", CloudShotGame.gameSkin);
        loadButton.setWidth(Gdx.graphics.getWidth()/2);
        loadButton.setPosition(
                Gdx.graphics.getWidth()/2 - loadButton.getWidth()/2,
                Gdx.graphics.getHeight()/2 - loadButton.getHeight()*2
        );
        loadButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try{
                    StateQuery loader = new GameStateTransactionHandler().load();
                } catch(GameStateTransactionHandler.InvalidTransactionException e) {

                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return loadButton;
    }
}
