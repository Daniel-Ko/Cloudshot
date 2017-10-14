package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.projectile.BulletImpl;
import view.Assets;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

import java.util.ArrayList;

/**
 * Created by Jake on 9/10/17.
 */
public class SemiAuto extends AbstractWeapon{
    public final int MAX_AMMO = 50;

    protected final float SemiAuto_DAMAGE = 8;


    public SemiAuto(Vector2 position, float width, float height) {
        super(position, width, height);
        setAmmo(MAX_AMMO);
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

        Vector2 aim = p.getAimedAt();
        Vector2 behind = new Vector2(p.getX() + 1.5f,  p.getY());
        Vector2 aimBelow =  new Vector2(p.getX() +1,  p.getY());
        Vector2 bul = new Vector2(p.getX() + 0.5f,  p.getY());

        bullets.add(new BulletImpl(p.getPos() ,aim, getDamage(), getBulletImage(), true));
        bullets.add(new BulletImpl(behind  , aim, getDamage(), getBulletImage(), true));
        bullets.add(new BulletImpl(aimBelow, aim, getDamage(), getBulletImage(), true ));
        bullets.add(new BulletImpl(bul, aim, getDamage(), getBulletImage(), true ));
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
