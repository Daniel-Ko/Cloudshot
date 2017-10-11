package view.labels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import view.CloudShotGame;

public class LevelLabel extends LabelFactory {
    @Override
    public Label createLabel() {
        Label levelText = new Label("", CloudShotGame.gameSkin, "big");
        levelText.setAlignment(Align.center);
        levelText.setY(Gdx.graphics.getHeight() - 20);
        levelText.setWidth(Gdx.graphics.getWidth());
        levelText.setFontScale(1);
        return levelText;
    }
}
