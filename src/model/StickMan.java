package model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import view.GameViewManager;

public class StickMan{
	public static final Image IDLE = new Image("model/resources/IDLE.png");
	public static final Image walkR1 = new Image("model/resources/WALK_RIGHT_1.png");
	public static final Image walkR2 = new Image("model/resources/WALK_RIGHT_2.png");
	public static final Image walkR3 = new Image("model/resources/WALK_RIGHT_3.png");
	public static final Image walkL1 = new Image("model/resources/WALK_LEFT_1.png");
	public static final Image walkL2 = new Image("model/resources/WALK_LEFT_2.png");
	public static final Image walkL3 = new Image("model/resources/WALK_LEFT_3.png");
	public static final Image[] toRight = {walkR1,walkR2,walkR3};
	public static final Image[] toLeft = {walkL1,walkL2,walkL3};
	public static final int WIDTH = 60;
	public static final int HEIGHT = 100;
	public static final int PUNCH_DAMAGE = 75;
	public static final int PUNCH_RANGE = 70;
	public static final int KICK_DAMAGE = 40;
	public static final int KICK_RANGE = 150;
	public static final int MAX_HP = 1000;
	public static final int WALK_SPEED = 3;

	private String name; 
	private Image state;
	private boolean alive, idle, walking, jumping, attacking, blocking;
	private int hp;
	private double hpBar;
	private int walkCounter;
	private double X,Y;

	private int n;
	private Thread t;
	
	public StickMan(String name) {
		this.name = name;
		Y = GameViewManager.height - HEIGHT;
		X = 50;
		hp = StickMan.MAX_HP;
		hpBar =  ((double) (hp * WIDTH) / (double) StickMan.MAX_HP);
		state = IDLE;
		alive = true; idle = true;
		walking = false; jumping = false; attacking = false; blocking = false;
		walkCounter = 0;
	}
	public void punch(StickMan target) {
		if(target.blocking) return;
		target.hp -= StickMan.PUNCH_DAMAGE;
		updateHp();
	}
	
	public void kick(StickMan target) {
		target.hp -= StickMan.KICK_DAMAGE;
		updateHp();
	}
	
	public void updateHp() {
		if(hp <= 0) {
			hpBar = 0;
			alive = false;
		}
		else {
			hpBar = (int) ((double) (hp * WIDTH) / (double) StickMan.MAX_HP);
		}
	}
	public void setState(Image newState) {
		state = newState;
	}
	public Image getState() {
		return state;
	}
	
	public void walkRight() {
		X += WALK_SPEED;
		if(X > GameViewManager.width - 60) X = GameViewManager.width - 60;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toRight[walkCounter / 3];
		walkCounter = (walkCounter + 1) % 9;
	}
	
	public void walkLeft() {
		X -= WALK_SPEED;
		if(X < 0) X = 0;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toLeft[walkCounter / 3];
		walkCounter = (walkCounter + 1) % 9;
	}
	
	public void jump() {
		n = 30;
		t = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(15);
					Platform.runLater(() -> {
						if(n > 15) {
							Y -= 1;
							walking = false; idle = false; jumping = true; attacking = false; blocking = false;
							walkCounter = (walkCounter + 1) % 9;
							n--;
						}else if(n == 0){
								t.interrupt();	
						}else if(n <= 15) {
							Y += 1;
							walking = false; idle = false; jumping = true; attacking = false; blocking = false;
							walkCounter = (walkCounter + 1) % 9;
							n--;
						}
					});
				}catch(InterruptedException e){
					break;
				}
			}
		});
		t.start();
		
	}
	
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public boolean isIdle() {
		return idle;
	}
	public void setIdle() {
		idle = true; walking = false; jumping = false; attacking = false; blocking = false;
		state = IDLE;
		walkCounter = 0;
	}
	public boolean isWalking() {
		return walking;
	}
	public void setWalking(boolean walking) {
		this.walking = walking;
	}
	public boolean isJumping() {
		return jumping;
	}
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	public boolean isAttacking() {
		return attacking;
	}
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public double getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public double getHpBar() {
		return hpBar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(state,X,Y,StickMan.WIDTH,StickMan.HEIGHT);
		gc.fillRect(X, Y - 10, hpBar, 5);
		gc.strokeText(name, X + 4.2*(7-name.length()), Y - 20);
	}

}
