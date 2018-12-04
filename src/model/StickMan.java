package model;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import view.GameViewManager;

public class StickMan extends ProgressBar{
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

	private String name; 
	private Image state;
	private boolean alive, idle, walking, jumping, attacking, blocking;
	private int hp;
	private int hpBar;
	private int walkCounter;
	private int X,Y;
	
	
	public StickMan(String name) {
		this.name = name;
		Y = GameViewManager.height - 100;
		X = 50;
		hp = StickMan.MAX_HP;
		hpBar = (int) ((double) (hp * WIDTH) / (double) StickMan.MAX_HP);
		state = IDLE;
		alive = true;
		idle = true;
		walking = false;
		jumping = false;
		attacking = false;
		blocking = false;
		walkCounter = 0;
	}
	public void punch(StickMan target) {
		if(target.blocking) return;
		target.hp -= StickMan.PUNCH_DAMAGE;
		updateStatus();
	}
	
	public void kick(StickMan target) {
		target.hp -= StickMan.KICK_DAMAGE;
		updateStatus();
	}
	
	public void updateStatus() {
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
		X += 3;
		if(X > GameViewManager.width - 60) X = GameViewManager.width - 60;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toRight[walkCounter / 3];
		walkCounter = (walkCounter + 1) % 9;
	}
	
	public void walkLeft() {
		X -= 3;
		if(X < 0) X = 0;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toLeft[walkCounter / 3];
		walkCounter = (walkCounter + 1) % 9;
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
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getHpBar() {
		return hpBar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
