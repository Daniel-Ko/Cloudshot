package view.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import view.CloudShotGame;
import view.screens.MenuScreen;

public class MenuButton extends ButtonFactory {
    public MenuButton(float x, float y) {
        super(x, y);
    }

    @Override
    public TextButton createButton() {
        TextButton menu = new TextButton("Menu", CloudShotGame.gameSkin);
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
}
