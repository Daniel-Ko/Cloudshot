package view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import model.being.player.AbstractPlayer;
import model.collectable.AbstractWeapon;
import view.labels.AmmoCountLabel;
import view.labels.InventoryLabel;

import java.util.List;

public class InventoryActor extends Actor {

    private AbstractPlayer player;

    public InventoryActor(AbstractPlayer player){
        this.player = player;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Inventory label.
        Label inventoryText = new InventoryLabel().createLabel();
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
            Label ammoCount = new AmmoCountLabel(weapon, getX() + 10 + spacing, getY()-15).createLabel();
            ammoCount.draw(batch, parentAlpha);
            spacing += region.getRegionWidth() + 10;
        }

    }
}
