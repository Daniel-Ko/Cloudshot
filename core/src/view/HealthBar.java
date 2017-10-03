package view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class HealthBar extends ProgressBar {
    public HealthBar(int width, int height) {
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
