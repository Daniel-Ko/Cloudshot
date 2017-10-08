package model.mapObject.levels;

import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.being.AbstractEnemy;
import model.being.Rogue;
import model.being.ShootingEnemy;
import model.being.Slime2;

import java.util.List;

public class Spawn {

    private EnemyType enemyType;
    private int number;
    private float x,y;//location

    public enum EnemyType {
        SLIME,
        ROGUE,
        SHOOTER
    };

    public Spawn(EnemyType enemy, int number, float x, float y) {
        this.enemyType = enemy;
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public void spawn(List<AbstractEnemy> enemies, GameModel gm){
        for(int i = 0; i < number; i++){
            if(enemyType == EnemyType.SLIME){
                enemies.add(new Slime2(gm,new Vector2(this.getX()-i*50,this.getY())));
            }
            else if(enemyType == EnemyType.ROGUE){
                enemies.add(new Rogue(gm,new Vector2(this.getX()-i*50,this.getY())));

            }
            else if(enemyType == EnemyType.SHOOTER){
                enemies.add(new ShootingEnemy(gm,new Vector2(this.getX()-i*50,this.getY())));

            }
        }
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
