package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	Texture img;
	int x;
	int y;
	public Player(){
		img = new Texture("badlogic.jpg");
	}
	
	public void draw(){
		
	}
	public void update(){
		x++;
	}
}
