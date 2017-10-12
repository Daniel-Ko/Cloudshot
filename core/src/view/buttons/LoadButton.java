package view.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import model.GameModel;
import model.GameModelInterface;
import model.data.GameStateTransactionHandler;
import view.CloudShotGame;

public class LoadButton extends ButtonFactory {
    private GameModelInterface gameModel;

    public LoadButton(float x, float y, GameModelInterface gameModel) {
        super(x, y);
        this.gameModel = gameModel;
    }

    @Override
    public TextButton createButton() {
        TextButton loadButton = new TextButton("Load", CloudShotGame.gameSkin);
        loadButton.setWidth(Gdx.graphics.getWidth()/8);
        loadButton.setPosition(
                x - loadButton.getWidth()*4,
                y

        );
        loadButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try{
                    gameModel.load();
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
