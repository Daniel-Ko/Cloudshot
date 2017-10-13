package view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import model.GameModelInterface;
import model.collectable.AbstractWeapon;
import view.Assets;
import view.CloudShotGame;

public class LabelFactory {

    public static Label ammoCountLabel(AbstractWeapon weapon, float x, float y){
        Label title = new Label(weapon.getAmmo() + "/" + weapon.getMaxAmmo(),
                Assets.gameSkin, "default");
        title.setAlignment(Align.center);
        title.setX(x);
        title.setY(y);
        title.setWidth(10);
        title.setFontScale(1.5f);
        return title;
    }

    public static Label gameOverLabel(){
        Label title = new Label("Game Over", Assets.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1.5f);
        return title;
    }

    public static Label InventoryLabel(){
        Label inventoryText = new Label("Inventory", Assets.gameSkin, "default");
        inventoryText.setY(50);
        inventoryText.setX(10);
        inventoryText.setWidth(Gdx.graphics.getWidth());
        inventoryText.setFontScale(1.5f);
        return inventoryText;
    }

    public static Label levelLabel(GameModelInterface gameModel){
        Label levelText = new Label(gameModel.getLevelName(), Assets.gameSkin, "big");
        levelText.setAlignment(Align.center);
        levelText.setY(Gdx.graphics.getHeight() - 35);
        levelText.setWidth(Gdx.graphics.getWidth());
        levelText.setFontScale(1);
        return levelText;
    }

    public static Label mainMenuLabel(){
        Label title = new Label("Main Menu", Assets.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1);
        return title;
    }

}
