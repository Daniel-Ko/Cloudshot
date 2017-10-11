public class PlayerTest extends GameTest{

    /*@Test
    public void TestBulletsFiredIncreased(){

        Player p = new Player();
        Shotgun gun = new Shotgun(p.getPos(),1,1);
        p.setCurWeapon(gun);
        p.shoot();
        //expect shotgun to fire 3 bullets
        assertEquals(3, p.getBullets().size());
        p.shoot();
        assertEquals(6, p.getBullets().size());
        //changing to 1 bullet shots
        Pistol gun2 = new Pistol(p.getPos(),1,1);
        p.setCurWeapon(gun2);
        assertEquals(p.getCurWeapon(), gun2);
        p.shoot();
        assertEquals(7, p.getBullets().size());
        p.shoot();
        p.shoot();
        assertEquals(9, p.getBullets().size());

     }

    @Test
    public void TestDamagingPlayer(){

        Player p = new Player();
        int initHP = p.getHealth();
        assertEquals(initHP,p.getHealth());
        p.hit(5);
        assertEquals(p.getHealth(),initHP-5);
        assert p.getHealth() == (initHP-5);

    }
    @Test
    public void TestPlayerDeath(){
        Player p = new Player();
        int initHP = p.getHealth();
        p.hit(initHP);//TODO spawnEnemies check and spawnEnemies for death state in hit
        //TODO spawnEnemies player state method
        assertEquals(AbstractPlayer.player_state.DEAD,p.getPlayerState());
    }

    @Test
    public void TestSettingPLayerHealth(){
        Player p = new Player();
        p.setHealth(2);
        assertEquals( 2,p.getHealth()) ;
        p.setHealth(100);
        assertEquals( 100,p.getHealth()) ;
    }


    @Test
    public void TestPlayerInteractionWithShooter(){
        assertTrue(true);
    }

    @Test
    public void TestPlayerMeleeAttackInterationWithSlime(){
        Slime2 s = new Slime2();
        int slimeinitHP = s.getHealth();
        Player p = new Player();
        float playersAttackRange = p.getMeleeRange();
        s.setPosition(new Vector2(0,0));
        p.setPos(new Vector2(0,0));
        //Make the enemy within the players melee attack range
        assertTrue(s.getPosition().dst(p.getPos())< playersAttackRange);
        p.setAttacking(true);//use this since we dont have keyboard input
        p.attack(s);
        assertTrue(s.getHealth()<slimeinitHP);

        p.setMeleeRange(0);
        //shouldn't be able to hit enemy now
        int slimeHP = s.getHealth();
        p.attack(s);
        assertTrue(slimeHP == s.getHealth());
    }

    @Test
    public void TestGetImage(){
        //TODO
        //change state -> check image returned from getImage, repeat.
    }

    @Test
    public void TestPlayersInventory(){
        Player p = new Player();
        Shotgun gun = new Shotgun(p.getPos(),1,1);
        assertNull(p.getCurWeapon());
        gun.pickedUp(p);//player picks up shot gun
        assertNotNull(p.getCurWeapon());
    }

    @Test
    public void TestInitBox2D(){
        Player p = new Player();
        World world = new World(new Vector2(0, -50), true);
        Vector2 pos = new Vector2(0,0);
        p.initBox2D(world,pos);
        assertNotNull(p.getWorld());
        assertNotNull(p.getBody());
    }*/

}
