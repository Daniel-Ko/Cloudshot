package model.mapObject.levels;

import model.being.AbstractEnemy;

public class Spawn {

    private EnemyType enemyType;
    private int number;
    private float x,y;//location

    public enum EnemyType {
        SLIME,

    };

    public Spawn(EnemyType enemy, int number, float x, float y) {
        this.enemyType = enemy;
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public int getNumber() {
        return number;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
