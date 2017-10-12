package view.labels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import model.collectable.AbstractWeapon;
import view.CloudShotGame;

public class AmmoCountLabel extends LabelFactory {

    AbstractWeapon weapon;
    float x, y;

    public AmmoCountLabel(AbstractWeapon weapon, float x, float y){
        this.weapon = weapon;
        this.x = x;
        this.y = y;
    }

    @Override
    public Label createLabel() {
        Label title = new Label(weapon.getAmmo() + "/" + weapon.getMaxAmmo(),
                CloudShotGame.gameSkin, "default");
        title.setAlignment(Align.center);
        title.setX(x);
        title.setY(y);
        title.setWidth(10);
        title.setFontScale(1.5f);
        return title;
    }
}
