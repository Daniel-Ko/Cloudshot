package view.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import model.GameModel;
import view.CloudShotGame;
import view.screens.GameScreen;
import view.screens.MenuScreen;

public class StartButton extends ButtonFactory {

    public StartButton(float x, float y){
        super(x, y);
    }

    @Override
    public TextButton createButton() {
        TextButton startButton = new TextButton("Start", CloudShotGame.gameSkin);
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
}
