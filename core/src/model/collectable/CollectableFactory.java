package model.collectable;

import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.mapObject.levels.AbstractLevel;

/**
 * Created by Jake on 14/10/17.
 */
public class CollectableFactory {
    public static AbstractBuff produceAbstractBuff(AbstractBuff.buff_type type, Vector2 pos) {
        if (type == AbstractBuff.buff_type.death) {
            return new DeathPack(pos, AbstractLevel.COLLECTABLE_SIZE, AbstractLevel.COLLECTABLE_SIZE);
        }
        if (type == AbstractBuff.buff_type.health) {
            return new HealthPack(pos, AbstractLevel.COLLECTABLE_SIZE, AbstractLevel.COLLECTABLE_SIZE);
        }
        if (type == AbstractBuff.buff_type.heavyammo) {
            return new HeavyAmmoPack(pos, AbstractLevel.COLLECTABLE_SIZE, AbstractLevel.COLLECTABLE_SIZE);
        }
        if (type == AbstractBuff.buff_type.lightammo) {
            return new LightAmmoPack(pos, AbstractLevel.COLLECTABLE_SIZE, AbstractLevel.COLLECTABLE_SIZE);
        }
        return null;
    }

    public static AbstractWeapon produceAbstractWeapon(AbstractWeapon.weapon_type type, Vector2 pos){
        if (type == AbstractWeapon.weapon_type.pistol){
            return new Pistol(pos, AbstractLevel.COLLECTABLE_SIZE, AbstractLevel.COLLECTABLE_SIZE);
        }
        if (type == AbstractWeapon.weapon_type.semiauto){
            return new SemiAuto(pos, AbstractLevel.COLLECTABLE_SIZE,AbstractLevel.COLLECTABLE_SIZE);
        }
        if (type == AbstractWeapon.weapon_type.shotgun){
            return new Shotgun(pos, AbstractLevel.COLLECTABLE_SIZE,AbstractLevel.COLLECTABLE_SIZE);
        }
        if (type == AbstractWeapon.weapon_type.sniper){
            return new Sniper(pos, AbstractLevel.COLLECTABLE_SIZE,AbstractLevel.COLLECTABLE_SIZE);
        }
        return null;

    }
}
