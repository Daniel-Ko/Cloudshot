package model.mapObject.levels;

import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import model.being.enemies.Rogue;
import model.being.enemies.ShootingEnemy;
import model.being.enemies.Slime2;

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
                enemies.add(EntityFactory.getEnemy(gm,new Vector2(this.getX()-i*50,this.getY()), EntityFactory.entity_type.slime));
            }
            else if(enemyType == EnemyType.ROGUE){
                enemies.add(EntityFactory.getEnemy(gm,new Vector2(this.getX()-i*50,this.getY()), EntityFactory.entity_type.rogue));

            }
            else if(enemyType == EnemyType.SHOOTER){
                enemies.add(EntityFactory.getEnemy(gm,new Vector2(this.getX()-i*50,this.getY()), EntityFactory.entity_type.archer));

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
