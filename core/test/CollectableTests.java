import model.being.AbstractPlayer;
import model.collectable.*;
import org.junit.Test;import com.brashmonkey.spriter.Player;

public class CollectableTests extends GameTest {

	public CollectableTests() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void TestHealtPackIncreaseHealth(){
		AbstractPlayer  p = gameModel.getPlayer();
		int initHealth = p.getHealth();
		new HealthPack(new Vector2(p.getX(),p.getY()), 10, 10);
		gameModel.update();
		assertEquals(initHealth + 50, p.getHealth());
	}
	@Test
	public void TestDeathPackDecreaseHealth(){
		AbstractPlayer  p = gameModel.getPlayer();
		int initHealth = p.getHealth();
		new DeathPack(new Vector2(p.getX(),p.getY()), 10, 10);
		gameModel.update();
		assertEquals(initHealth - 50, p.getHealth());
	}
	@Test
	public void TestPickUpPistol(){
		AbstractPlayer  p = gameModel.getPlayer();
		assert p.getInventory() == null;
		Pistol pistol = new Pistol(new Vector2(p.getX(),p.getY()), 10, 10);
		assert p.getInventory().contains(pistol);
	}
	@Test
	public void TestPickUpShotgun(){
		AbstractPlayer  p = gameModel.getPlayer();
		assert p.getInventory() == null;
		Shotgun shotgun = new Shotgun(new Vector2(p.getX(),p.getY()), 10, 10);
		assert p.getInventory().contains(shotgun);
	}
//	@Test
//	public void TestDecreaseAmmo(){
//		AbstractPlayer  p = gameModel.getPlayer();
//		AbstractWeapon wep = p.getWeaponInHand();
//		int ammo = wep.getAmmo();
//		p.shoot();
//		p.soot();
//		assert wep.getammo() == ammo - 2;
//	}
//	@Test
//	public void TestDecreaseAmmo(){
	//need 
//		AbstractPlayer  p = gameModel.getPlayer();
//		AbstractWeapon wep = p.getWeaponInHand();
//		int ammo = wep.getAmmo();
//		p.shoot();
//		p.soot();
//		AbstractCollectable = new AmmoPack(p.getPos());
		
//	}
	
	
	
	

	
}
