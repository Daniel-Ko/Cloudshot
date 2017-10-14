package view.factories;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import view.utils.CustomDialog;

public class DialogFactory {

    public static CustomDialog saveSuccessfulDialog(){
        return new CustomDialog("Save status") // this is the dialog title
            .text("Save succesful!") // text appearing in the dialog
            .button("EXIT", new InputListener() { // button to exit app
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            });
    }
}
