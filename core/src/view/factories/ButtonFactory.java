package view.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import model.GameModel;
import model.GameModelInterface;
import model.data.GameStateTransactionHandler;
import view.Assets;
import view.screens.GameScreen;
import view.screens.MenuScreen;

/**
 * ButtonFactory creates subclasses of Buttons such as TextButton(s), ImageButton(s) to be rendered in
 * the screen in order for the user to interact with it. Each button have its own purpose when it has been
 * pressed up.
 * @author Yi Sian Lim
 */
public class ButtonFactory {

    /**
     * Create a start button which starts a new game.
     * @param x coordinate of the button in the x-axis.
     * @param y coordinate of the button in the y-axis.
     * @return
     *      TextButton to start the game.
     */
    public static TextButton startButton(float x, float y) {
        TextButton startButton = new TextButton("Start", Assets.gameSkin);
        startButton.setWidth(Gdx.graphics.getWidth() / 3);
        startButton.setPosition(
                x - startButton.getWidth() / 2,
                y - startButton.getHeight()
        );
        startButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameModel gameModel = new GameModel();
                MenuScreen.game.setScreen(new GameScreen(gameModel));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return startButton;
    }

    /**
     * Creates a resume game button which allows the player to resume the game after pausing (pressing ESC key).
     * @param x coordinate of the button in the x-axis.
     * @param y coordinate of the button in the y-axis.
     * @param gameScreen
     *          Screen to go back to in order to resume the game.
     * @return
     *          TextButton which resumes the game.
     */
    public static TextButton resumeGameButton(float x, float y, GameScreen gameScreen) {
        TextButton resumeGame = new TextButton("Resume Game", Assets.gameSkin);
        resumeGame.setWidth(Gdx.graphics.getWidth() / 3);
        resumeGame.setPosition(
                x - resumeGame.getWidth() / 2,
                y - resumeGame.getHeight() * 2
        );
        resumeGame.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.state = GameScreen.State.GAME_RUNNING;
                MenuScreen.game.setScreen(gameScreen);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return resumeGame;
    }

    /**
     * Create a button which saves the current game.
     * @param x coordinate of the button in the x-axis.
     * @param y coordinate of the button in the y-axis.
     * @param gameModel
     *          Model of the current game to save.
     * @return
     *          TextButton which saves the game.
     */
    public static TextButton saveButton(float x, float y, GameModelInterface gameModel) {
        TextButton saveButton = new TextButton("Save", Assets.gameSkin);
        saveButton.setWidth(Gdx.graphics.getWidth() / 3);
        saveButton.setPosition(
                x - saveButton.getWidth() / 2,
                y - saveButton.getHeight() * 3

        );
        saveButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    gameModel.save();
                    DialogFactory.saveSuccessfulDialog().show(MenuScreen.stage);
                } catch (GameStateTransactionHandler.InvalidTransactionException e) {
                    DialogFactory.saveFailedDialog().show(MenuScreen.stage);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return saveButton;
    }

    /**
     * Create a button which load the current game.
     * @param x coordinate of the button in the x-axis.
     * @param y coordinate of the button in the y-axis.
     * @param gameModel
     *          Model of the game to load.
     * @param gameScreen
     *          Screen to go back to in order to load the game.
     * @return
     *          TextButton which loads the game.
     */
    public static TextButton loadButton(float x, float y, GameModelInterface gameModel, GameScreen gameScreen) {
        TextButton loadButton = new TextButton("Load", Assets.gameSkin);
        loadButton.setWidth(Gdx.graphics.getWidth() / 3);
        loadButton.setPosition(
                x - loadButton.getWidth() / 2,
                y - loadButton.getHeight() * 4

        );
        loadButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    gameModel.load();
                    DialogFactory.loadSuccessfulDialog(gameScreen).show(MenuScreen.stage);
                } catch (GameStateTransactionHandler.InvalidTransactionException e) {
                    DialogFactory.loadFailedDialog().show(MenuScreen.stage);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return loadButton;
    }


    /**
     * Creates an ImageButton which mutes the music of the game.
     * @param x coordinate of the button in the x-axis.
     * @param y coordinate of the button in the y-axis.
     * @param gameModel
     *          gameModel to mute / play the music.
     * @return
     *          ImageButton which mutes/unmutes the music.
     */
    public static ImageButton muteButton(float x, float y, GameModelInterface gameModel) {
        ImageButton muteButton = new ImageButton(Assets.music_off, Assets.music_off, Assets.music_on);
        muteButton.setWidth(Gdx.graphics.getWidth()/20);
        muteButton.setPosition(
                x - muteButton.getWidth(),
                y

        );
        muteButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (gameModel instanceof GameModel) {
                    GameModel model = (GameModel) gameModel;
                    model.setMuted();
                    muteButton.setChecked(model.musicIsPlaying());
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        return muteButton;
    }

    /**
     * Creates a button which pauses or runs the game.
     * @param x coordinate of the button in the x-axis.
     * @param y coordinate of the button in the y-axis.
     * @return
     *      ImageButton which pauses or runs the game.
     */
    public static ImageButton pauseButton(float x, float y){
        ImageButton pauseButton = new ImageButton(Assets.pause_button, Assets.pause_button, Assets.play_button);
        pauseButton.setWidth(Gdx.graphics.getWidth()/20);
        pauseButton.setPosition(x - pauseButton.getWidth() * 2,
                y
        );
        pauseButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switch (GameScreen.state) {
                    case GAME_RUNNING:
                        GameScreen.state = GameScreen.State.GAME_PAUSED;
                        pauseButton.setChecked(true);
                        break;
                    case GAME_PAUSED:
                        GameScreen.state = GameScreen.State.GAME_RUNNING;
                        pauseButton.setChecked(false);
                        break;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return pauseButton;
    }

    /**
     * Create a button which restarts the game.
     * @param x coordinate of the button in the x-axis.
     * @param y coordinate of the button in the y-axis.
     * @return
     *      TextButton which restarts the game.
     */
    public static TextButton restartButton(float x, float y) {
        TextButton restartButton = new TextButton("Restart", Assets.gameSkin);
        restartButton.setWidth(Gdx.graphics.getWidth() / 3);
        restartButton.setPosition(
                x - restartButton.getWidth() / 2,
                y - restartButton.getHeight()
        );
        restartButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameModel gameModel = new GameModel();
                MenuScreen.game.setScreen(new GameScreen(gameModel));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return restartButton;
    }
}
