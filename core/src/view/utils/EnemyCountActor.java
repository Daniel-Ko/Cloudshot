package view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import model.GameModelInterface;
import view.factories.LabelFactory;

public class EnemyCountActor extends Actor {

    /**
     * GameModel to based the enemy count on.
     */
    private GameModelInterface gameModel;

    public EnemyCountActor(GameModelInterface gameModel){
        this.gameModel = gameModel;
    }

    public void setGameModel(GameModelInterface gameModel){
        this.gameModel = gameModel;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the enemy count label.
        Label enemyCount = LabelFactory.enemyCountLabel(gameModel);
        enemyCount.setX(Gdx.graphics.getWidth() - 200);
        enemyCount.setY(Gdx.graphics.getHeight() - 30);
        enemyCount.draw(batch, parentAlpha);
    }
}
