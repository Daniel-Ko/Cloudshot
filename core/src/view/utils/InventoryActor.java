package view.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractWeapon;
import view.factories.LabelFactory;

import java.util.List;

/**
 * InventoryActor is a subclass of Actor which will be used to add it into the stage during the game.
 * Effectively takes in an AbstractPlayer and query its inventory in order to display the state of the
 * inventory to the user.
 * @author Yi Sian Lim
 */
public class InventoryActor extends Actor {

    /**
     * Player to based the inventory from.
     */
    private AbstractPlayer player;

    public InventoryActor(AbstractPlayer player){
        this.player = player;
    }
    
    public void setPlayer(AbstractPlayer player){
        this.player = player;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Inventory label.
        Label inventoryText = LabelFactory.InventoryLabel();
        inventoryText.draw(batch, parentAlpha);

        // To space out the weapons when drawing.
        int spacing = 0;

        // Get the inventory from the player.
        List<AbstractWeapon> weapons = player.getInventory();

        // Iterate all of the weapons owned by the player.
        for(AbstractWeapon weapon : weapons){
            // Draw the weapon.
            TextureRegion region = weapon.getImage().getFrameFromTime(0.0f);
            batch.draw(region, getX() + spacing, getY());

            // Display the ammo count.
            Label ammoCount = LabelFactory.ammoCountLabel(weapon, getX() + 10 + spacing, getY()-15);
            ammoCount.draw(batch, parentAlpha);
            spacing += region.getRegionWidth() + 10;
        }

    }
}
