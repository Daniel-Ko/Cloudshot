package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

import java.util.ArrayList;

/**
 * Created by Jake on 11/10/17.
 */

public class Sniper extends AbstractWeapon {
    protected final int MAX_AMMO = 10;
    protected final int SNIPER_DAMAGE = 40;
    private transient CustomSprite bulImage;
    private transient CustomSprite image;


    public Sniper(Vector2 position, float width, float height) {
        super(position, width, height);
        this.bulImage = new StaticSprite("bullet.png");
        this.image = new StaticSprite("sniper.png");
        this.ammo = getMaxAmmo();
    }

    @Override
    public CustomSprite getImage() {
        return this.image;
    }

    @Override
    public CustomSprite getBulletImage() {
        return this.bulImage;
    }

    @Override
    public ArrayList<BulletImpl> shoot(Player p) {
        if(this.ammo <= 0){return null;}
        ArrayList<BulletImpl> bullets = new ArrayList<>();
        this.ammo --;
        bullets.add(new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage()));
        return bullets;
    }

    @Override
    public void setAmmo(int i) {
        this.ammo = i;
    }

    @Override
    public int getAmmo() {
        return this.ammo;
    }


    @Override
    public int getMaxAmmo() {
        return this.MAX_AMMO;
    }
}
