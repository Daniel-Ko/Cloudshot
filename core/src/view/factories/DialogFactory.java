package view.factories;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import view.screens.GameScreen;
import view.screens.MenuScreen;
import view.utils.CustomDialog;

/**
 *  DialogFactory creates various dialog boxes to notify user in different situations
 *  such as load / save game has failed or succeeded.
 * @author Yi Sian Lim
 */
public class DialogFactory {

    /**
     * Dialog displayed when the game save has succeeded.
     * @return
     *      CustomDialog notifying the user that save have succeeded.
     */
    public static CustomDialog saveSuccessfulDialog(){
        return new CustomDialog("Save status") // this is the dialog title
            .text("Save successful!") // text appearing in the dialog
            .button("Ok", new InputListener() { // button to exit app
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            });
    }

    /**
     * Dialog displayed when the game load has succeeded.
     * @param gameScreen
     *          GameScreen to display the load game after user clicks "Ok"
     * @return
     *      CustomDialog notifying the user that load have succeeded.
     */
    public static CustomDialog loadSuccessfulDialog(GameScreen gameScreen){
        return new CustomDialog("Load status") // this is the dialog title
                .text("Load successful!") // text appearing in the dialog
                .button("Ok", new InputListener() { // button to exit app
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        GameScreen.state = GameScreen.State.GAME_RUNNING;
                        MenuScreen.game.setScreen(gameScreen);
                        return true;
                    }
                });
    }

    /**
     * Dialog displayed when the game save has failed.
     * @return
     *      CustomDialog notifying the user that save have failed
     */
    public static CustomDialog saveFailedDialog(){
        return new CustomDialog("Save status") // this is the dialog title
                .text("Save failed :(\n") // text appearing in the dialog
                .button("Ok", new InputListener() { // button to exit app
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                });
    }

    /**
     * Dialog displayed when the game load has failed.
     * @return
     *      CustomDialog notifying the user that load have failed.
     */
    public static CustomDialog loadFailedDialog(String error){
        return new CustomDialog("Load status") // this is the dialog title
                .text("Load failed :(\n" + error + "\n") // text appearing in the dialog
                .button("Ok", new InputListener() { // button to exit app
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                });
    }

}
