package model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import view.GameViewManager;

public class StickMan{
	
	public static final Image[] toRight = {Character.walkR1,Character.walkR2,Character.walkR3};
	public static final Image[] toLeft = {Character.walkL1,Character.walkL2,Character.walkL3};
	public static final Image[] punchLeft = {Character.punchL1,Character.punchL2};
	public static final Image[] punchRight = {Character.punchR1,Character.punchR2};
	public static final Image[] kickLeft = {Character.kickL1,Character.kickL2};
	public static final Image[] kickRight = {Character.kickR1,Character.kickR2};
	public static final int PUNCH_DAMAGE = 75;
	public static final int KICK_DAMAGE = 40;
	public static final int MAX_HP = 1000;
	public static final double WALK_SPEED = 3.5;

	private String name; 
	private Image state;
	private boolean alive, idle, walking, jumping, attacking, blocking;
	private int hp;
	private double hpBar;
	private int actionCounter;
	private double X,Y;
	
	private boolean jump,down;
	private int n;
	private Thread t;
	
	public StickMan(String name) {
		this.name = name;
		Y = GameViewManager.height - Character.HEIGHT - GameViewManager.GroundThickness;
		X = 50;
		hp = StickMan.MAX_HP;
		hpBar =  ((double) (hp * Character.WIDTH) / (double) StickMan.MAX_HP);
		state = Character.IDLE;
		alive = true; idle = true;
		walking = false; jumping = false; attacking = false; blocking = false;
		actionCounter = 0;
	}
	public void punch(EnemyGrey target) {
		if(target.isBlocking()) return;
		target.takeDamage(StickMan.PUNCH_DAMAGE);
	}
	public void punch(EnemyRed target) {
		if(target.isBlocking()) return;
		target.takeDamage(StickMan.PUNCH_DAMAGE);
	}
	public void punch(EnemyBlue target) {
		if(target.isBlocking()) return;
		target.takeDamage(StickMan.PUNCH_DAMAGE);
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
			hpBar = (int) ((double) (hp * Character.WIDTH) / (double) StickMan.MAX_HP);
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
		state = toRight[actionCounter / 3];
		actionCounter = (actionCounter + 1) % 9;
	}
	
	public void walkLeft() {
		X -= WALK_SPEED;
		if(X < 0) X = 0;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toLeft[actionCounter / 3];
		actionCounter = (actionCounter + 1) % 9;
	}
	
	public void punchLeft() {
		walking = false; idle = false; jumping = false; attacking = true; blocking = false;
		if(actionCounter == 2) {
			this.setIdle(); return;
		}
		else state = punchLeft[actionCounter];
		actionCounter++;
	}
	
	public void punchRight() {
		walking = false; idle = false; jumping = false; attacking = true; blocking = false;
		if(actionCounter == 2) {
			this.setIdle(); return;
		}
		else state = punchRight[actionCounter];
		actionCounter++;
	}
	
	public void kickRight() {
		walking = false; idle = false; jumping = false; attacking = true; blocking = false;
		if(actionCounter == 2) {
			this.setIdle(); return;
		}
		else state = kickRight[actionCounter];
		actionCounter++;
	}
	
	public void kickLeft() {
		walking = false; idle = false; jumping = false; attacking = true; blocking = false;
		if(actionCounter == 2) {
			this.setIdle(); return;
		}
		else state = kickLeft[actionCounter];
		actionCounter++;
	}
	
	public void jump() {
		jumping = true;
		n = 450;
		jump = false;
		state = Character.JUMP;
		t = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(1);
					if(jump == false){
						if(n > 225) {
							Y -= 1;
							walking = false; idle = false; jumping = true; attacking = false; blocking = false;
							actionCounter = (actionCounter + 1) % 9;
							n--;
						}else if(n == 225) {
							Y += 1;
							walking = false; idle = false; jumping = true; attacking = false; blocking = false;
							actionCounter = (actionCounter + 1) % 9;
							n--;
							jump = true;
							state = Character.IDLE;
						}
					}else {
						if(Y == 300 || Y == 100) {
							state = Character.IDLE;
							t.interrupt();
						}else if(n < 225) {
							Y += 1;
							walking = false; idle = false; jumping = true; attacking = false; blocking = false;
							actionCounter = (actionCounter + 1) % 9;							
							n--;
							state = Character.IDLE;
						}
					}
				}catch(InterruptedException e){
					jumping = false;
					break;
				}
			}
		});
		t.start();
	}
	
	public void crouch() {
		state = Character.CROUCH;
	}
	
	public void down() {
		down = true;
		n = 200;
		t = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(1);
					if(Y < 480) {
						if(n > 0) {
							Y += 1;
							walking = false; idle = false; attacking = false; blocking = false;
							actionCounter = (actionCounter + 1) % 9;
							n--;
							down = true;
						}else {
							t.interrupt();
						}
					}else if(Y >= 480) {
						t.interrupt();
					}
				}catch(InterruptedException e){
					down = false;
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
		idle = true; walking = false; attacking = false; blocking = false;
		state = Character.IDLE;
		actionCounter = 0;
	}
	public boolean isWalking() {
		return walking;
	}
	public void setWalking(boolean walking) {
		if(walking) { this.walking = true; idle = false; this.attacking = false; blocking = false;}
		else { this.walking = false; idle = false; this.attacking = false; blocking = false; }
	}
	public boolean isJumping() {
		return jumping;
	}
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	public boolean isDowning() {
		return down;
	}
	public boolean isAttacking() {
		return attacking;
	}
	public void setAttacking(boolean attacking) {
		if(attacking) { walking = false; idle = false; this.attacking = true; blocking = false;}
		else { walking = false; idle = true; this.attacking = false; blocking = false; }
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
	
		gc.drawImage(state,X,Y,Character.WIDTH,Character.HEIGHT);
		gc.fillRoundRect(X, Y - 10, hpBar, 5, 5, 5);
		gc.strokeText(name, X + 4.2*(7-name.length()), Y - 20);
	}

}
