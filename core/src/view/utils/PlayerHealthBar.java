package view.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import model.being.enemies.AbstractEnemy;

public class PlayerHealthBar extends ProgressBar {

    public PlayerHealthBar(int width, int height) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        getStyle().background = Utility.getColoredDrawable(width, height, Color.RED);
        getStyle().knob = Utility.getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = Utility.getColoredDrawable(width, height, Color.GREEN);
        setWidth(width);
        setHeight(height);
        setAnimateDuration(0.0f);
        setValue(1f);
        setAnimateDuration(0.25f);
    }
}
