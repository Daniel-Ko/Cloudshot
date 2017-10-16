import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.Slime2;
import model.being.player.AbstractPlayer;
import model.being.player.Player;
import model.collectable.*;
import model.projectile.BulletImpl;
import org.junit.Assert;
import org.junit.Test;
import sun.plugin2.gluegen.runtime.BufferFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.GameModel;

import java.util.ArrayList;

public class CollectableTests extends GameTest {

    public CollectableTests() {
        // TODO Auto-generated constructor stub
    }

    @Test
    public void testHealthPackIncreaseHealth() {
        Player p = new Player();
        HealthPack pck = new HealthPack(new Vector2(p.getX(), p.getY()), 10, 10);
        p.setHealth(p.getHealth()- 50);
        int initHealth = p.getHealth();
        pck.pickedUp(p);
        assertEquals(initHealth + 50, p.getHealth());
    }

    @Test
    public void testDeathPackDecreaseHealth() {
        Player p = new Player();
        int initHealth = p.getHealth();
        DeathPack pck = new DeathPack(new Vector2(p.getX(), p.getY()), 10, 10);
        pck.pickedUp(p);
        assertEquals(initHealth - 50, p.getHealth());
    }

    @Test
    public void testPickUpPistol() {
        Player p = new Player();
        assertTrue(p.getInventory().isEmpty());
        Pistol pistol = new Pistol(new Vector2(p.getX(), p.getY()), 10, 10);
        pistol.pickedUp(p);
        assertTrue(p.getInventory().contains(pistol));
    }

    @Test
    public void testPickUpShotgun() {
        Player p = new Player();
        assertTrue(p.getInventory().isEmpty());
        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
        shotgun.pickedUp(p);
        assertTrue(p.getInventory().contains(shotgun));
    }
    @Test
    public void testPickUpSemiAuto() {
        Player p = new Player();
        assertTrue(p.getInventory().isEmpty());
        SemiAuto semiAuto = new SemiAuto(new Vector2(p.getX(), p.getY()), 10, 10);
        semiAuto.pickedUp(p);
        semiAuto.shoot(p);
        assertEquals(semiAuto.getMaxAmmo() -1, semiAuto.getAmmo());
        assertEquals(p.getInventory().get(p.getCurWeapon()),semiAuto);
        assertTrue(p.getInventory().contains(semiAuto));
    }
    @Test
    public void testPickUpSniper() {
        Player p = new Player();
        assertTrue(p.getInventory().isEmpty());
        Sniper sniper = new Sniper(new Vector2(p.getX(), p.getY()), 10, 10);
        sniper.pickedUp(p);
        assertTrue(p.getInventory().contains(sniper));
    }

    @Test
    public void testDecreaseAmmo() {
        Player p = new Player();
        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);

        p.getInventory().add(shotgun);
//        p.setCurWeapon(0);
        assertEquals(0, p.getCurWeapon());

        int ammo = shotgun.getAmmo();
        p.shoot();
        p.shoot();
        assertEquals(shotgun.getAmmo() ,ammo - 2);
    }

    @Test
    public void testLightAmmoPack() {
        Player p = new Player();
        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
        Pistol pistol = new Pistol(new Vector2(p.getX(), p.getY()), 10,10);


        pistol.pickedUp(p);
//        p.setCurWeapon(0);
        assertEquals(0, p.getCurWeapon());

        p.shoot();
        p.shoot();
        int ammo2 = pistol.getAmmo();

        shotgun.pickedUp(p);
//        p.setCurWeapon(1);
        assertEquals(1, p.getCurWeapon());

        int ammo = shotgun.getAmmo();
        p.shoot();
        p.shoot();
        assertEquals(shotgun.getAmmo(), ammo - 2);
        LightAmmoPack pack = new LightAmmoPack(new Vector2(p.getX(), p.getY()), 10, 10);
        pack.pickedUp(p);
        //check shotguns ammo has been reset but pistols hasnt.
        assertEquals(shotgun.getAmmo(), shotgun.getMaxAmmo());
        assertEquals(ammo2, pistol.getAmmo());
    }

    @Test
    public void testHeavyAmmoPack() {
        //Heavy Ammo pack should reset all guns in inventories ammo
        Player p = new Player();
        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
        Sniper sniper = new Sniper(new Vector2(p.getX(), p.getY()), 10, 10);
        sniper.pickedUp(p);
        p.shoot();
        p.shoot();
        int ammoS = sniper.getAmmo();
        //NEED the statement below.
        shotgun.pickedUp(p);
        int ammo = shotgun.getAmmo();
        p.shoot();
        p.shoot();
        assertEquals(shotgun.getAmmo(), ammo - 2);
        HeavyAmmoPack pack = new HeavyAmmoPack(new Vector2(p.getX(), p.getY()), 10, 10);
        pack.pickedUp(p);
        assertEquals(shotgun.getAmmo(), shotgun.getMaxAmmo());
        assertEquals( sniper.getAmmo(), sniper.getMaxAmmo());
    }

    @Test
    public void testPickUp2guns() {
        //Should be able to pick up different guns.
        Player p = new Player();
        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
        shotgun.pickedUp(p);
        Pistol pistol = new Pistol(new Vector2(p.getX(), p.getY()), 10, 10);
        pistol.pickedUp(p);
        assertTrue(p.getInventory().contains(pistol));
        assertTrue (p.getInventory().contains(shotgun));
    }
    @Test
    public void testCollectableCollisionDetection(){
        Player p = new Player();
        p.setPos(new Vector2(0,0));
        HealthPack hp = new HealthPack(new Vector2(0,0), 10,10);
        assertFalse(hp.isPickedUp());
        hp.checkCollide(p);
        assertTrue(hp.isPickedUp());
    }


    @Test
    public void testPickUp2SameGuns() {
        //Shouldnt be able to pick up 2 of the same type
        Player p = new Player();
        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
        shotgun.pickedUp(p);
        p.shoot();
        Shotgun shotgun2 = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
        shotgun2.pickedUp(p);
        assertFalse(p.getInventory().contains(shotgun2));
        //Ensure that ammo is reloaded if same instance
        assertEquals(shotgun.getAmmo() , shotgun.getMaxAmmo());
    }

    @Test
    public void testGunsCorrectDamage(){
        Player p = new Player();
        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
        Pistol pistol = new Pistol(new Vector2(p.getX(), p.getY()), 10, 10);
        SemiAuto semiAuto = new SemiAuto(new Vector2(p.getX(), p.getY()), 10, 10);
        Sniper sniper = new Sniper(new Vector2(p.getX(), p.getY()), 10, 10);
        assertTrue(sniper.getDamage() == 40);
        assertTrue(shotgun.getDamage() == 15);
        assertTrue(pistol.getDamage() == 8);
        assertTrue(semiAuto.getDamage() == 8);
        pistol.shoot(p);
        sniper.shoot(p);
        semiAuto.shoot(p);
        shotgun.shoot(p);
        assertTrue(pistol.getAmmo() == pistol.getMaxAmmo() -1);
        assertTrue(sniper.getAmmo() == sniper.getMaxAmmo() -1);
        assertTrue(shotgun.getAmmo() == shotgun.getMaxAmmo() -1);
        assertTrue(semiAuto.getAmmo() == semiAuto.getMaxAmmo() -1);

    }

    @Test
    public void testBuffFactory(){
        AbstractBuff test = CollectableFactory.produceAbstractBuff( AbstractBuff.buff_type.heavyammo, new Vector2(0,0));
        assertTrue(test instanceof HeavyAmmoPack);
        AbstractBuff test2 = CollectableFactory.produceAbstractBuff( AbstractBuff.buff_type.lightammo, new Vector2(0,0));
        assertTrue(test2 instanceof LightAmmoPack);
        AbstractBuff test3 = CollectableFactory.produceAbstractBuff( AbstractBuff.buff_type.health, new Vector2(0,0));
        assertTrue(test3 instanceof HealthPack);
        AbstractBuff test4 = CollectableFactory.produceAbstractBuff( AbstractBuff.buff_type.death, new Vector2(0,0));
        assertTrue(test4 instanceof DeathPack);

    }
    @Test
    public void testWepFactory(){
        AbstractWeapon test = CollectableFactory.produceAbstractWeapon(AbstractWeapon.weapon_type.pistol, new Vector2(0,0));
        assertTrue(test instanceof Pistol);
        AbstractWeapon test2 = CollectableFactory.produceAbstractWeapon(AbstractWeapon.weapon_type.sniper, new Vector2(0,0));
        assertTrue(test2 instanceof Sniper);
        AbstractWeapon test3 = CollectableFactory.produceAbstractWeapon(AbstractWeapon.weapon_type.semiauto, new Vector2(0,0));
        assertTrue(test3 instanceof SemiAuto );
        AbstractWeapon test4 = CollectableFactory.produceAbstractWeapon(AbstractWeapon.weapon_type.shotgun, new Vector2(0,0));
        assertTrue(test4 instanceof Shotgun);
    }
    @Test
    public void testAbstractCollectableMethods(){
        HealthPack hp = new HealthPack(new Vector2(0,0), 5,5);
        assertTrue(hp.getBoundingBox().getWidth() == 5/GameModel.PPM);
        assertTrue(hp.getBoundingBox().getHeight() == 5/GameModel.PPM);
        assertTrue(hp.getX() == 0);
        assertTrue(hp.getY() == 0);
    }









}
