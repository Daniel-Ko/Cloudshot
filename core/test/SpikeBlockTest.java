import model.being.player.Player;
import model.being.enemies.SpikeBlock;
import org.junit.Test;

import static org.junit.Assert.*;
public class SpikeBlockTest extends GameTest{

    @Test
    public void TestSprikeBlockUpdate(){
        SpikeBlock s = new SpikeBlock();
        s.update();
    }

    @Test
    public void TestCannotAttackBlock(){
        SpikeBlock s = new SpikeBlock();
        Player p = new Player();
        int hp = s.getHealth();
        s.hit(p.getDamage());
        assertTrue(hp == s.getHealth());
    }
}
