package view;

import com.badlogic.gdx.Game;
import model.GameModel;
import model.GameModelInterface;
import view.screens.GameScreen;
import view.screens.MenuScreen;

public class CloudShotGame extends Game {

    public enum Screen {
        MENU, GAME, TEST
    };

    private GameModelInterface model;

    /**
     * Screen that the game should be displaying.
     */
    private Screen screen;

    public CloudShotGame(Screen screen){
        this.screen = screen;
    }

    public CloudShotGame(Screen screen, GameModelInterface model){
        this.screen = screen;
        this.model = model;
    }


    @Override
    public void create() {

        Assets.load();

        switch (screen){
            case MENU:
                this.setScreen(new MenuScreen(this));
                break;
            case GAME:
                this.setScreen(new GameScreen(new GameModel()));
                break;
            case TEST:
                this.setScreen(new GameScreen(model));
        }

    }

    @Override
    public void render() {
        super.render();
    }
}
