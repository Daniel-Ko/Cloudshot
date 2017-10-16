package view.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import model.GameModelInterface;
import model.collectable.AbstractWeapon;
import view.Assets;

/**
 * LabelFactory creates labels for specific purposes in the game.
 * @author Yi Sian Lim
 */
public class LabelFactory {

    /**
     * Displays the ammo count of the weapon.
     * @param weapon
     *          AbstractWeapon to display the ammo count
     * @param x coordinates of the label in the x-axis.
     * @param y coordinates of the label in the y-axis.
     * @return
     *      Label displaying weapon's ammo count.
     */
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

    /**
     * Displays a label that says "Game Over".
     * @return
     *      "Game Over" label.
     */
    public static Label gameOverLabel(){
        Label title = new Label("Game Over", Assets.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1.5f);
        return title;
    }

    /**
     * Displays a label which says "Inventory"
     * @return
     *      "Inventory" label.
     */
    public static Label InventoryLabel(){
        Label inventoryText = new Label("Inventory", Assets.gameSkin, "default");
        inventoryText.setY(50);
        inventoryText.setX(10);
        inventoryText.setWidth(Gdx.graphics.getWidth());
        inventoryText.setFontScale(1.5f);
        return inventoryText;
    }

    /**
     * Displays the current level of the game.
     * @param gameModel
     *          Model of the game to check the level.
     * @return
     *          Current level name label.
     *
     */
    public static Label levelLabel(GameModelInterface gameModel){
        Label levelText = new Label(gameModel.getLevelName(), Assets.gameSkin, "big");
        levelText.setAlignment(Align.center);
        levelText.setY(Gdx.graphics.getHeight() - 35);
        levelText.setWidth(Gdx.graphics.getWidth());
        levelText.setFontScale(1);
        return levelText;
    }

    /**
     * Displays a label which says "Main Menu"
     * @return
     *      "Main Menu" label.
     */
    public static Label mainMenuLabel(){
        Label title = new Label("Main Menu", Assets.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1);
        return title;
    }

    /**
     * Displays a label which says "You won the game"
     * @return
     *      "You won the game" label.
     */
    public static Label gameWinLabel(){
        Label title = new Label("You won the game!", Assets.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(1);
        return title;
    }

    /**
     * Displays a label which says "You won the game"
     * @return
     *      "You won the game" label.
     */
    public static Label enemyCountLabel(GameModelInterface gameModel){
        Label title = new Label("Enemy count: " + gameModel.getEnemies().size(), Assets.gameSkin, "big");
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(0.8f);
        return title;
    }

}
