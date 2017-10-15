package model.data;

import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.being.EntityFactory;
import model.being.enemies.AbstractEnemy;
import model.being.player.AbstractPlayer;
import model.being.player.PlayerData;
import model.collectable.AbstractBuff;
import model.collectable.AbstractCollectable;
import model.collectable.AbstractWeapon;
import model.collectable.CollectableFactory;
import model.mapObject.levels.AbstractLevel;
import model.mapObject.levels.LevelOne;
import model.mapObject.levels.LevelThree;
import model.mapObject.levels.LevelTwo;

import java.util.ArrayList;
import java.util.List;

import static model.GameModel.PPM;

/**
 * Created by Deko on 15/10/2017.
 */
public class ModelLoader extends ModelData{
    private GameModel model;
    
    public ModelLoader(GameModel model) {
        this.model = model;
    }
    
    public void load() {
        loadInPlayer();
        loadInEnemies();
        loadInLevel();
    }
    
    private void loadInPlayer() {
        
        AbstractPlayer newPlayer= EntityFactory.producePlayer(model,
                new Vector2(
                        //scale newPlayerpos back down to the normal world scale
                        pData.getPos().x * PPM,
                        pData.getPos().y * PPM
                ));
    
        //reconfirm that newnewPlayerhas a new Box2D world (removes existing bodies)
        newPlayer.setWorld(java.util.Optional.of(model.getWorld()));
    
        //set all fields
        if (pData.isLiving())
            newPlayer.setPlayerState(AbstractPlayer.player_state.ALIVE);
        else
            newPlayer.setPlayerState(AbstractPlayer.player_state.DEAD);
    
        newPlayer.setHealth(pData.getHealth());
        newPlayer.setDamage(pData.getDamage());
        newPlayer.setBoundingBox(pData.getBoundingBox());
    
        if(!pData.getInventory().isEmpty()) {
            //set inventory with "deep clone" guns
            newPlayer.getInventory().clear();

            for (AbstractWeapon invWep : pData.getInventory()) {
                AbstractWeapon loadedWeapon = CollectableFactory.produceAbstractWeapon(
                        invWep.type,
                        new Vector2(invWep.getX(), invWep.getY()
                        ));
                loadedWeapon.setAmmo(invWep.getAmmo());
                loadedWeapon.setPickedUp(invWep.isPickedUp());

                newPlayer.getInventory().add(loadedWeapon);
            }

            // Now set cur weapon with another cloned weapon
            AbstractWeapon curWep = CollectableFactory.produceAbstractWeapon((
                            pData.getCurWeapon()).type,
                    new Vector2(pData.getCurWeapon().getX(), pData.getCurWeapon().getY()
                    ));
            curWep.setAmmo(pData.getCurWeapon().getAmmo());
            curWep.setPickedUp(pData.getCurWeapon().isPickedUp());
            newPlayer.setCurWeapon(curWep);
        }
            newPlayer.setInAir(pData.isInAir());
            newPlayer.setAttacking(pData.isAttacking());
            newPlayer.setGrounded(pData.isGrounded());
            newPlayer.setMovingLeft(pData.isMovingLeft());
            newPlayer.setMovingRight(pData.isMovingRight());
        
            this.player = newPlayer;
    }
    
    private void loadInEnemies() {
        List<AbstractEnemy> implementedEnemiesToLoad = new ArrayList<>();
        
        for (AbstractEnemy e : enemies) {
            AbstractEnemy newEnemy = EntityFactory.produceEnemy(model,
                    new Vector2(
                            e.getPosition().x * PPM,
                            e.getPosition().y * PPM
                    ),
                    e.type);
        
            newEnemy.setSpeed(e.getSpeed());
            newEnemy.setDamage(e.getDamage());
            newEnemy.setHealth(e.getHealth());
            newEnemy.setEnemyState(e.enemyState);
        
            newEnemy.setDrawingWidth(e.getDrawingWidth());
            newEnemy.setDrawingHeight(e.getDrawingHeight());
    
            implementedEnemiesToLoad.add(newEnemy);
        }
        
        this.enemies = implementedEnemiesToLoad;
    }
    
    private void loadInLevel() {
        AbstractLevel newLevel = null;
    
        if(level.levelNum == 1)
            newLevel = new LevelOne();
        else if(level.levelNum == 2)
            newLevel = new LevelTwo();
        else if(level.levelNum == 3)
            newLevel = new LevelThree();
        else if(level.levelNum == 4)
            newLevel = new LevelThree();
    
    
        loadInCollectables(newLevel, level.getCollectables()); //must load each collectable by itself
    
        newLevel.setPortals(level.getPortals());
        newLevel.setSpawnTriggers(level.getSpawnTriggers());
        newLevel.setSpawns(level.getSpawns());
    
        this.level = newLevel;
    }
    
    private void loadInCollectables(AbstractLevel newLevel, List<AbstractCollectable> collectsToLoad) {
        //clear the level's existing collectables
        newLevel.getCollectables().clear();
        
        for(AbstractCollectable c : collectsToLoad) {
            
            if(c instanceof AbstractBuff) {
                //create new buff and set the loaded properties in
                AbstractBuff loadedBuff = CollectableFactory.produceAbstractBuff((
                                (AbstractBuff) c).type,
                        new Vector2(c.getX(), c.getY()
                        ));
                loadedBuff.setPickedUp(c.isPickedUp());
    
                newLevel.getCollectables().add(loadedBuff); //add to level
                
            } else { //else case is if c instanceof AbstractWeapon
                
                //create new Weapon and set the loaded properties in
                AbstractWeapon loadedWeapon = CollectableFactory.produceAbstractWeapon((
                                (AbstractWeapon) c).type,
                        new Vector2(c.getX(), c.getY()
                        ));
                loadedWeapon.setAmmo(((AbstractWeapon) c).getAmmo());
                loadedWeapon.setPickedUp(c.isPickedUp());
    
                newLevel.getCollectables().add(loadedWeapon); //add to level
            }
        }
    }
    
    
}
