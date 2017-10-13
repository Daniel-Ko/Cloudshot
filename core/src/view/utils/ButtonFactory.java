package view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import model.GameModel;
import model.GameModelInterface;
import model.data.GameStateTransactionHandler;
import view.Assets;
import view.screens.GameScreen;
import view.screens.MenuScreen;

public class ButtonFactory {

    public static TextButton loadButton(float x, float y, GameModelInterface gameModel) {
        TextButton loadButton = new TextButton("Load", Assets.gameSkin);
        loadButton.setWidth(Gdx.graphics.getWidth() / 8);
        loadButton.setPosition(
                x - loadButton.getWidth() * 4,
                y

        );
        loadButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    gameModel.load();
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

        public static TextButton menuButton(float x, float y){
            TextButton menu = new TextButton("Menu", Assets.gameSkin);
            menu.setWidth(Gdx.graphics.getWidth()/8);
            menu.setPosition(
                    x - menu.getWidth()*3,
                    y
            );
            menu.addListener(new InputListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    MenuScreen.game.setScreen(new MenuScreen(MenuScreen.game));
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            });
            return menu;
        }

        public static TextButton muteButton(float x, float y, GameModelInterface gameModel){
            TextButton muteButton = new TextButton("Sound", Assets.gameSkin);
            muteButton.setWidth(Gdx.graphics.getWidth()/8);
            muteButton.setPosition(
                    x - muteButton.getWidth()*2,
                    y

            );
            muteButton.addListener(new InputListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(gameModel instanceof GameModel) {
                        GameModel model = (GameModel) gameModel;
                        model.setMuted();
                        muteButton.setText(model.musicIsPlaying() ? "Mute" : "Sound");
                    }
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            });
            return muteButton;
        }

        public static TextButton pauseButton(float x, float y){
            TextButton pauseButton = new TextButton("Pause", Assets.gameSkin);
            pauseButton.setWidth(Gdx.graphics.getWidth()/8);
            pauseButton.setPosition(
                    x - pauseButton.getWidth()*5,
                    y

            );
            pauseButton.addListener(new InputListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    switch(GameScreen.state) {
                        case GAME_RUNNING:
                            GameScreen.state = GameScreen.State.GAME_PAUSED;
                            pauseButton.setText("Resume");
                            break;
                        case GAME_PAUSED:
                            GameScreen.state = GameScreen.State.GAME_RUNNING;
                            pauseButton.setText("Pause");
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

        public static TextButton saveButton(float x, float y, GameModelInterface gameModel){
            TextButton saveButton = new TextButton("Save", Assets.gameSkin);
            saveButton.setWidth(Gdx.graphics.getWidth()/8);
            saveButton.setPosition(
                    x - saveButton.getWidth(),
                    y

            );
            saveButton.addListener(new InputListener(){
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

        public static TextButton startButton(float x, float y){
            TextButton startButton = new TextButton("Start", Assets.gameSkin);
            startButton.setWidth(Gdx.graphics.getWidth()/2);
            startButton.setPosition(
                    x - startButton.getWidth()/2,
                    y - startButton.getHeight()/2
            );
            startButton.addListener(new InputListener(){
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

        public static TextButton restartButton(float x, float y){
            TextButton restartButton = new TextButton("Restart", Assets.gameSkin);
            restartButton.setWidth(Gdx.graphics.getWidth()/2);
            restartButton.setPosition(
                    x - restartButton.getWidth()/2,
                    y - restartButton.getHeight()/2
            );
            restartButton.addListener(new InputListener(){
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
