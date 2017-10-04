package model.being;

public class Death implements EnemyState {
    @Override
    public void update(AbstractEnemy e, AbstractPlayer p) {
        System.out.println("DEATH UPDATE");
        e.world.destroyBody(e.body);//DOESN'T WORK
    }

    @Override
    public int attack(AbstractEnemy e, AbstractPlayer p) {
        throw new Error("Shouldnt be Called");
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
