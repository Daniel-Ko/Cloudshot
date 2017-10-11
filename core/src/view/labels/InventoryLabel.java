package view.labels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import view.CloudShotGame;

public class InventoryLabel extends LabelFactory {

    @Override
    public Label createLabel() {
        Label inventoryText = new Label("Inventory", CloudShotGame.gameSkin, "default");
        inventoryText.setY(10);
        inventoryText.setX(10);
        inventoryText.setWidth(Gdx.graphics.getWidth());
        inventoryText.setFontScale(1.5f);
        return inventoryText;
    }
}
