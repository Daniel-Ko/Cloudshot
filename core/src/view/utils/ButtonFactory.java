package view.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import model.GameModel;
import model.GameModelInterface;
import model.data.GameStateTransactionHandler;
import view.Assets;
import view.screens.GameScreen;
import view.screens.MenuScreen;

public class ButtonFactory {

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
                gameModel.save();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return saveButton;
    }

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
                    GameScreen.state = GameScreen.State.GAME_RUNNING;
                    MenuScreen.game.setScreen(gameScreen);
                } catch (GameStateTransactionHandler.InvalidTransactionException e) {

                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return loadButton;
    }

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
