package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.being.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.StaticSprite;

import java.util.ArrayList;

/**
 * Created by Jake on 9/10/17.
 */
public class SemiAuto extends AbstractWeapon{
    public final int MAX_AMMO = 50;

    protected final float SemiAuto_DAMAGE = 4;

    private transient CustomSprite image;

    private transient CustomSprite bulImage;

    public SemiAuto(Vector2 position, float width, float height) {
        super(position, width, height);
        setAmmo(MAX_AMMO);
        this.image = new StaticSprite("Tec-9.png");
        this.image.flipHorizontal();
        this.bulImage = new StaticSprite("bullet.png");
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

        Vector2 aim = p.getAimedAt();
        Vector2 aimAbove = new Vector2(aim.x -1,  (float) (aim.y));
        Vector2 aimBelow =  new Vector2(aim.x +1, (float) (aim.y));
        Vector2 bul = new Vector2(aim.x +2, (float) (aim.y));

        bullets.add(new BulletImpl(p.getPos(),aim, getDamage(), getBulletImage()));
        bullets.add(new BulletImpl(p.getPos(), aimAbove, getDamage(), getBulletImage()));
        bullets.add(new BulletImpl(p.getPos(), aimBelow, getDamage(), getBulletImage()));
        bullets.add(new BulletImpl(p.getPos(), bul, getDamage(), getBulletImage()));
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

    @Override
    public CustomSprite getImage() {
        return this.image;
    }
}
