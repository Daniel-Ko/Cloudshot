package view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import model.being.AbstractPlayer;
import model.collectable.AbstractWeapon;

import java.util.List;

public class InventoryActor extends Actor {

    private AbstractPlayer player;

    public InventoryActor(AbstractPlayer player){
        this.player = player;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // To space out the weapons when drawing.
        int spacing = 0;

        // Get the inventory from the player.
        List<AbstractWeapon> weapons = player.getInventory();

        // Iterate all of the weapons owned by the player and draw them.
        for(AbstractWeapon weapon : weapons){
            TextureRegion region = weapon.getImage().getFrameFromTime(0.0f);
            batch.draw(region, getX() + spacing, getY());
            spacing += region.getRegionWidth() + 10;
        }
    }
}
