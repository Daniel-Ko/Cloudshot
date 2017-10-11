package view.labels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import view.CloudShotGame;

public class MainMenuLabel extends LabelFactory {

    @Override
    public Label createLabel() {
        Label title = new Label("Main Menu", CloudShotGame.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1);
        return title;
    }
}
