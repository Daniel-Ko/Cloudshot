package model.mapObject.levels;

import com.badlogic.gdx.math.Rectangle;

import java.io.Serializable;

public class Portal implements Serializable{
    private Rectangle entry;
    private Rectangle exit;
    private boolean isActive;

    public Portal(Rectangle entry, Rectangle exit){
        this.entry = entry;
        this.exit = exit;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Rectangle getEntry() {
        return entry;
    }

    public Rectangle getExit() {
        return exit;
    }
}
