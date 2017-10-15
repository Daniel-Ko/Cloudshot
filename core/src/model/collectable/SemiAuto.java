package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.projectile.BulletImpl;
import view.Assets;
import view.sprites.CustomSprite;

import java.util.ArrayList;

/**
 * Created by Jake on 9/10/17.
 */
public class SemiAuto extends AbstractWeapon{
    private static final long serialVersionUID = 4845354169570635749L;
    public final int MAX_AMMO = 50;

    protected final float SemiAuto_DAMAGE = 8;


    public SemiAuto(Vector2 position, float width, float height) {
        super(position, width, height, weapon_type.semiauto);
        setAmmo(MAX_AMMO);
        this.setDamage(SemiAuto_DAMAGE);
    }

    @Override
    public CustomSprite getBulletImage() {
        return Assets.semiAutoBullet;
    }

    /**
     * shoots a line of four bullets.
     * @param p player
     * @return Arraylist of bullets
     */
    @Override
    public ArrayList<BulletImpl> shoot(Player p) {
        if(this.ammo <= 0){return null;}
        ArrayList<BulletImpl> bullets = new ArrayList<>();
        this.ammo --;

        BulletImpl bul1 = new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage(), true);
        BulletImpl bul2 = new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage(), true);
        BulletImpl bul3 = new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage(), true);
        BulletImpl bul4 = new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage(), true);
        bul1.setSpeed(3.1f);
        bul2.setSpeed(3.2f);
        bul3.setSpeed(3.3f);
        bullets.add(bul1);
        bullets.add(bul2);
        bullets.add(bul3);
        bullets.add(bul4);

        return bullets;
    }

    /**
     * adds a semiAuto to players inventory
     * if there isnt one there.
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

    @Override
    public CustomSprite getImage() {
        return Assets.semiAuto;
    }
}
