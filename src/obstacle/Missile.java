package obstacle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Character;
import model.StickMan;
import view.GameViewManager;

public class Missile {

	public int width;
	public int height;
	private double X,Y;
	private int vX;
	private Image ms;
	public boolean isExplode;
	
	public Missile() {
		width = 100;
		height = 25;
		Y = Character.FLOOR_LEVEL[(int) (Math.random()*3.0)];
		vX = (int) (Math.random()*4.0 + 2.0);
		double temp = Math.random();
		if(temp < 0.5) { //L to R
			ms = Character.MISSILE_R;
			X = -200;
		}else { //R to L
			ms = Character.MISSILE_L;
			X = GameViewManager.width + 100;
			vX = -vX;
		}
		isExplode = false;
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(ms, X, Y, width, height);
	}
	
	public boolean move() {
		X += vX;
		if(vX > 0 && X > GameViewManager.width) return true;
		if(vX < 0 && X < -110) return true;
		return false;
	}
	
	public boolean checkHit(StickMan sm) {

		if(vX > 0) {
			if(Y == sm.getY() && !sm.isCrouching() && X > sm.getX() - width && X < sm.getX() + Character.WIDTH) {
				sm.takeDamage(sm.getHp() / 5);
				return true;
			}
		}else {
			if(Y == sm.getY() && !sm.isCrouching() && X > sm.getX() - width && X < sm.getX() + Character.WIDTH) {
				sm.takeDamage(sm.getHp() / 5);
				return true;
			}
		}
		return false;
	}
	
	public void setImage(Image image) {
		ms = image;
	}
	
	public void setSize(int width,int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getVX() {
		return vX;
	}
	
	public void setBomb(double X,double Y) {
		this.X = X;
		this.Y = Y;
		vX = 0;
	}

}
