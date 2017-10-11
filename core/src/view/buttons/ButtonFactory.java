package view.buttons;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public abstract class ButtonFactory {

    protected float x, y;

    public ButtonFactory(float x, float y){
        this.x = x;
        this.y = y;
    }

    public abstract TextButton createButton();
}
