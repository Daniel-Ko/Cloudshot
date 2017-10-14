package model.mapObject.levels;

import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;

import java.io.Serializable;
import java.util.List;

public class Spawn implements Serializable{

    private static final long serialVersionUID = 8464898726152861533L;
    private AbstractEnemy.entity_type enemyType;
    private int number;
    private float x,y;//location


    public Spawn(AbstractEnemy.entity_type enemy, int number, float x, float y) {
        this.enemyType = enemy;
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public void spawn(List<AbstractEnemy> enemies, GameModel gm){
        for(int i = 0; i < number; i++){
            if(enemyType == AbstractEnemy.entity_type.slime){
                enemies.add(EntityFactory.produceEnemy(gm,new Vector2(this.getX()-i*50,this.getY()), AbstractEnemy.entity_type.slime));
            }
            else if(enemyType == AbstractEnemy.entity_type.rogue){
                enemies.add(EntityFactory.produceEnemy(gm,new Vector2(this.getX()-i*50,this.getY()), AbstractEnemy.entity_type.rogue));

            }
            else if(enemyType == AbstractEnemy.entity_type.archer){
                enemies.add(EntityFactory.produceEnemy(gm,new Vector2(this.getX()-i*50,this.getY()), AbstractEnemy.entity_type.archer));

            }
        }
    }

    public AbstractEnemy.entity_type getEnemyType() {
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
