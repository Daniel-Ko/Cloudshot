package view.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import model.GameModel;
import model.GameModelInterface;
import view.CloudShotGame;

public class SaveButton extends ButtonFactory {

    private GameModelInterface gameModel;

    public SaveButton(float x, float y, GameModelInterface gameModel) {
        super(x, y);
        this.gameModel = gameModel;
    }

    @Override
    public TextButton createButton() {
        TextButton saveButton = new TextButton("Save", CloudShotGame.gameSkin);
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
}
