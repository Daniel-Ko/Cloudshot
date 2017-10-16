package view.utils;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import view.Assets;

/**
 * CustomDialog customized the dialog to be displayed by subclassing the Dialog class.
 * Reference: http://pimentoso.blogspot.co.nz/2014/01/libgdx-scene2d-dialog-resize-and-style.html
 * @author Yi Sian Lim
 */
public class CustomDialog extends Dialog {

    public CustomDialog (String title) {
        super(title, Assets.gameSkin);
        initialize();
    }

    private void initialize() {
        // Set the padding on the top of the dialog title.
        padTop(60);

        // Set buttons height.
        getButtonTable().defaults().height(60);

        setModal(true);
        setMovable(true);
        setResizable(false);
    }

    @Override
    public CustomDialog text(String text) {
        super.text(new Label(text, Assets.gameSkin, "big"));
        return this;
    }

    /**
     * Adds a text button to the button table.
     * @param listener
     *          InputListener with the respective action to carry out prior to button click
     *          that will be attached to the button.
     */
    public CustomDialog button(String buttonText, InputListener listener) {
        TextButton button = new TextButton(buttonText, Assets.gameSkin);
        button.addListener(listener);
        button(button);
        return this;
    }

    @Override
    public float getPrefWidth() {
        return 480f;
    }

    @Override
    public float getPrefHeight() {
        return 240f;
    }
}
