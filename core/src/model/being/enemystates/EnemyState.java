package model.being.enemystates;

import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;

public interface EnemyState {
    void update(AbstractEnemy e, AbstractPlayer p);
    int attack(AbstractEnemy e, AbstractPlayer p);
    void damage(AbstractEnemy e, int damage);
}
