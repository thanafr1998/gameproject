package obstacle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Character;
import model.StickMan;
import view.GameViewManager;

public class Missile {

	public static final int width = 100;
	public static final int height = 25;
	private int X,Y;
	private int vX;
	private Image ms;
	
	public Missile() {
		Y = Character.FLOOR_LEVEL[(int) (Math.random()*3.0)];
		vX = (int) (Math.random()*4.0 + 2.0);
		double temp = Math.random();
		if(temp < 0.5) { //L to R
			ms = Character.MISSILE_R;
			X = -100;
		}else { //R to L
			ms = Character.MISSILE_L;
			X = GameViewManager.width + 100;
			vX = -vX;
		}
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(ms, X, Y, width, height);
	}
	
	public boolean move() {
		X += vX;
		if(vX > 0 && X > GameViewManager.width) return true;
		if(vX < 0 && X < 0) return true;
		return false;
	}
	
	public boolean checkHit(StickMan sm) {
		if(vX > 0) {
			if(Y == sm.getY() && !sm.isCrouching() && X + width > sm.getX() && X + width < sm.getX() + Character.WIDTH) {
				sm.takeDamage(sm.getHp() / 2);
				return true;
			}
		}else {
			if(Y == sm.getY() && !sm.isCrouching() && X > sm.getX() && X < sm.getX() + Character.WIDTH) {
				sm.takeDamage(sm.getHp() / 2);
				return true;
			}
		}
		return false;
	}
}
