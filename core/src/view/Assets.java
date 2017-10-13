package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    public static Skin gameSkin;

    public static void load(){
        gameSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }
}
