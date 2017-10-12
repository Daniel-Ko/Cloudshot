package view.labels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import model.GameModel;
import model.GameModelInterface;
import view.CloudShotGame;

public class LevelLabel extends LabelFactory {

    private GameModelInterface gameModel;

    public LevelLabel(GameModelInterface gameModel){
        this.gameModel = gameModel;
    }

    @Override
    public Label createLabel() {
        Label levelText = new Label(gameModel.getLevelName(), CloudShotGame.gameSkin, "big");
        levelText.setAlignment(Align.center);
        levelText.setY(Gdx.graphics.getHeight() - 35);
        levelText.setWidth(Gdx.graphics.getWidth());
        levelText.setFontScale(1);
        return levelText;
    }
}
