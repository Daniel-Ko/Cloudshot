package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import view.screens.MenuScreen;

public class CloudShotGame extends Game {

    public static Skin gameSkin;

    @Override
    public void create() {
        gameSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
