package mock;

import com.badlogic.gdx.math.Vector2;
import model.being.player.Player;
import model.projectile.BulletImpl;
import view.sprites.CustomSprite;
import view.sprites.MovingSprite;
import view.sprites.StaticSprite;

import java.util.ArrayList;

/**
 * Created by Dan Ko on 10/5/2017.
 */
public class MockWeapon {

    private int ammo;

    public final int MAX_AMMO = 100;

    protected final float PISTOL_DAMAGE = 5;

    private CustomSprite image;

    private CustomSprite bulImage;

    public MockWeapon(Vector2 position, float width, float height) {
//        super(position, width, height);
//        this.setDamage(PISTOL_DAMAGE);
        bulImage = new MovingSprite("bullet.png", 3, 2);
        this.image = new StaticSprite("pistol.png");
        this.ammo = MAX_AMMO;
    }

    public CustomSprite getImage() {
        return this.image;
    }

    public CustomSprite getBulletImage() {
        return this.bulImage;
    }

    public ArrayList<BulletImpl> shoot(Player p) {
        //shoots single pistol bullet.
        //dont shoot if theres no ammo

        if(this.ammo <= 0){return null;}
        ArrayList<BulletImpl> bullets = new ArrayList<>();
        this.ammo --;
//        bullets.add(new BulletImpl(p.getPos(), p.getAimedAt(), getDamage(), getBulletImage()));
        return bullets;
    }


    public void setAmmo(int i) {
        this.ammo = i;
    }

    public int getAmmo() {
        return this.ammo ;
    }


    public int getMaxAmmo() {
        return this.MAX_AMMO;
    }
}
