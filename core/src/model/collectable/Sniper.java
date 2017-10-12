package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
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
        this.setDamage(SNIPER_DAMAGE);
    }

    @Override
    public CustomSprite getImage() {
        return this.image;
    }

    @Override
    public CustomSprite getBulletImage() {
        return this.bulImage;
    }

    /**
     * shoots one very strong & fast bullet.
     * @param p abstract player
     * @return bullet to be shot.
     */
    @Override
    public ArrayList<BulletImpl> shoot(Player p) {
        if(this.ammo <= 0){return null;}
        ArrayList<BulletImpl> bullets = new ArrayList<>();
        this.ammo --;
        BulletImpl bul = new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage(), true);
        bul.setSpeed(5);
        bullets.add(bul);
        return bullets;
    }

    /**
     * adds a sniper to the inventory if there is one there.
     * @param p
     */
    @Override
    public void pickedUp(AbstractPlayer p) {
        for (AbstractWeapon w: p.getInventory()) {
            if(w.getClass().equals(this.getClass())){
                w.setAmmo(getMaxAmmo());
                return;
            }
        }
        //adds the weapon to the players inventory.

        p.getInventory().add(this);
        Player player = (Player)p;
        player.setCurWeapon(this);

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
