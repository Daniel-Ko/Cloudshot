package model.being.enemystates;

import model.being.player.AbstractPlayer;
import model.being.enemies.AbstractEnemy;

public interface EnemyState {
    public void update(AbstractEnemy e, AbstractPlayer p);
    public int attack(AbstractEnemy e, AbstractPlayer p);
    public void damage(AbstractEnemy e, int damage);
}
