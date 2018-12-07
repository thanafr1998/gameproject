package buff;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Character;
import view.GameViewManager;
import model.StickMan;

public abstract class Buffs {
	
	private int X,Y,destination;
	private int dropSpeed;
	
	public Buffs() {
		dropSpeed = 4;
		X = (int) (Math.random()* (GameViewManager.width - 25) );
		Y = -200;
		destination = Character.FLOOR_LEVEL[(int) (Math.random()*3.0)] + 75;
	}
	
	public abstract boolean pickUpBy(StickMan sm, double pickTime);
	public abstract void draw(GraphicsContext gc);

	public void move() {
		if(Y >= destination) {
			Y = destination;
			dropSpeed = 0;
		}
		Y += dropSpeed;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
}
