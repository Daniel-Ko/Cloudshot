//
//import com.badlogic.gdx.math.Vector2;
//import model.being.player.Player;
//import model.collectable.*;
//import org.junit.Test;
//
//
//import static org.junit.Assert.assertEquals;
//
//public class CollectableTests extends GameTest {
//
//    public CollectableTests() {
//        // TODO Auto-generated constructor stub
//    }
//
//    @Test
//    public void TestHealthPackIncreaseHealth() {
//        Player p = new Player();
//        int initHealth = p.getHealth();
//        HealthPack pck = new HealthPack(new Vector2(p.getX(), p.getY()), 10, 10);
//        pck.pickedUp(p);
//        assertEquals(initHealth + 50, p.getHealth());
//    }
//
//    @Test
//    public void TestDeathPackDecreaseHealth() {
//        Player p = new Player();
//        int initHealth = p.getHealth();
//        DeathPack pck = new DeathPack(new Vector2(p.getX(), p.getY()), 10, 10);
//        pck.pickedUp(p);
//        assertEquals(initHealth - 50, p.getHealth());
//    }
//
//    @Test
//    public void TestPickUpPistol() {
//        Player p = new Player();
//        assert p.getInventory().isEmpty();
//        Pistol pistol = new Pistol(new Vector2(p.getX(), p.getY()), 10, 10);
//        pistol.pickedUp(p);
//        assert p.getInventory().contains(pistol);
//    }
//
//    @Test
//    public void TestPickUpShotgun() {
//        Player p = new Player();
//        assert p.getInventory().isEmpty();
//        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
//        shotgun.pickedUp(p);
//        assert p.getInventory().contains(shotgun);
//    }
//
//    @Test
//    public void TestDecreaseAmmo() {
//        Player p = new Player();
//        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
//        p.setCurWeapon(shotgun);
//        int ammo = shotgun.getAmmo();
//        p.shoot();
//        p.shoot();
//        assert shotgun.getAmmo() == ammo - 2;
//    }
//
//    @Test
//    public void TestLightAmmoPack() {
//        Player p = new Player();
//        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
//        p.setCurWeapon(shotgun);
//        int ammo = shotgun.getAmmo();
//        p.shoot();
//        p.shoot();
//        assert shotgun.getAmmo() == ammo - 2;
//        LightAmmoPack pack = new LightAmmoPack(new Vector2(p.getX(), p.getY()), 10, 10);
//        pack.pickedUp(p);
//        assert shotgun.getAmmo() == shotgun.getMaxAmmo();
//    }
//
//    @Test
//    public void TestHeavyAmmoPack() {
//        Player p = new Player();
//        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
//        //NEED the statement below.
//        shotgun.pickedUp(p);
//        int ammo = shotgun.getAmmo();
//        p.shoot();
//        p.shoot();
//        assert shotgun.getAmmo() == ammo - 2;
//        HeavyAmmoPack pack = new HeavyAmmoPack(new Vector2(p.getX(), p.getY()), 10, 10);
//        pack.pickedUp(p);
//        assert shotgun.getAmmo() == shotgun.getMaxAmmo();
//    }
//
//    @Test
//    public void TestPickUp2guns() {
//        Player p = new Player();
//        Shotgun shotgun = new Shotgun(new Vector2(p.getX(), p.getY()), 10, 10);
//        shotgun.pickedUp(p);
//        Pistol pistol = new Pistol(new Vector2(p.getX(), p.getY()), 10, 10);
//        pistol.pickedUp(p);
//        assert p.getInventory().contains(pistol);
//        assert p.getInventory().contains(shotgun);
//    }
