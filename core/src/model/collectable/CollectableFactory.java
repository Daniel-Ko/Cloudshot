package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.GameModel;

/**
 * Created by Jake on 14/10/17.
 */
public class CollectableFactory {
    public static AbstractBuff produceAbstractBuff(AbstractBuff.buff_type type, Vector2 pos) {
        if (type == AbstractBuff.buff_type.death) {
            return new DeathPack(pos, 10, 10);
        }
        if (type == AbstractBuff.buff_type.health) {
            return new HealthPack(pos, 10, 10);
        }
        if (type == AbstractBuff.buff_type.heavyammo) {
            return new HeavyAmmoPack(pos, 10, 10);
        }
        if (type == AbstractBuff.buff_type.lightammo) {
            return new LightAmmoPack(pos, 10, 10);
        }
        return null;
    }

    public static AbstractWeapon produceAbstractWeapon(AbstractWeapon.weapon_type type, Vector2 pos){
        if (type == AbstractWeapon.weapon_type.pistol){
            return new Pistol(pos, 10, 10);
        }
        if (type == AbstractWeapon.weapon_type.semiauto){
            return new SemiAuto(pos, 10,10);
        }
        if (type == AbstractWeapon.weapon_type.shotgun){
            return new Shotgun(pos, 10,10);
        }
        if (type == AbstractWeapon.weapon_type.sniper){
            return new Sniper(pos,10,10);
        }
        return null;

    }
}
