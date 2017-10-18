package view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.GameModel;
import model.GameModelInterface;
import view.factories.ButtonFactory;
import view.factories.LabelFactory;

/**
 * MenuScreen displays the main menu to the user. There are 2 different types of main menu:
 *  - Menu that we see when we start up the game. The player can only start the game.
 *  - Menu that we see when we pause the game by clicking "ESC". The player can choose to restart, resume, save or
 *    load a saved game.
 * @author Yi Sian Lim
 */
public class MenuScreen extends ScreenAdapter {

    /**
     * Cloudshot game, allows us to change between screens.
     */
    public static Game game;

    /**
     * Stage of the MenuScreen.
     */
    public static Stage stage;

    public MenuScreen(Game game){
        MenuScreen.game = game;
        this.stage = new Stage(new ScreenViewport());

        // Add UI elements.
        stage.addActor(LabelFactory.mainMenuLabel());
        stage.addActor(ButtonFactory.startButton(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2
        ));
    }

    public MenuScreen(Game game, GameScreen gameScreen, GameModelInterface gameModel){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        TextButton restartButton = ButtonFactory.restartButton(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2,
                gameModel
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

        // Add label.
        stage.addActor(LabelFactory.mainMenuLabel());

        // Add the buttons into the stage.
        stage.addActor(resumebutton);
        stage.addActor(restartButton);
        stage.addActor(saveButton);
        stage.addActor(loadButton);
    }

    @Override
    public void show() {
        // Set the button controllers.
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
