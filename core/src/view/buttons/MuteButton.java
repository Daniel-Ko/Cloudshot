package view.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import model.GameModel;
import model.GameModelInterface;
import view.CloudShotGame;

public class MuteButton extends ButtonFactory {

    private GameModelInterface gameModel;
    public MuteButton(float x, float y, GameModelInterface gameModel) {
        super(x, y);
        this.gameModel = gameModel;
    }

    @Override
    public TextButton createButton() {
        TextButton muteButton = new TextButton("Sound", CloudShotGame.gameSkin);
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
}
