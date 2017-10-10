package model.being;

public class Death implements EnemyState, java.io.Serializable {
    @Override
    public void update(AbstractEnemy e, AbstractPlayer p) {
        e.world.destroyBody(e.body);
    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        return 0;
    }

    @Override
    public void damage(AbstractEnemy e, int damage) {
        //dont do anything, bec dead.
    }

    @Override
    public String toString() {
        return "DeathState";
    }
}
