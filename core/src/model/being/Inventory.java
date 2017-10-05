package model.being;

import model.collectable.AbstractCollectable;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the collectables present in the player's inventory.
 */
public class Inventory {

    /**
     * Represents the collection of collectables
     */
    private Set<AbstractCollectable> inventory;

    public Inventory(){
        inventory = new HashSet<>();
    }

    public void addToInventory(AbstractCollectable item){
        inventory.add(item);
    }

    public void removeFromInventory(AbstractCollectable item){
        inventory.remove(item);
    }

}
