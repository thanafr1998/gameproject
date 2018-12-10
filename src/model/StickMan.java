package model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import view.GameViewManager;

public class StickMan{
	
	private static final Image[] toRight = {Character.walkR1,Character.walkR2,Character.walkR3,Character.walkR1,Character.walkR2,Character.walkR3};
	private static final Image[] toLeft = {Character.walkL1,Character.walkL2,Character.walkL3,Character.walkL1,Character.walkL2,Character.walkL3};
	private static final Image[] punchLeft = {Character.punchL1,Character.punchL2};
	private static final Image[] punchRight = {Character.punchR1,Character.punchR2};
	private static final Image[] kickLeft = {Character.kickL1,Character.kickL2};
	private static final Image[] kickRight = {Character.kickR1,Character.kickR2};
	public static final int MAX_HP = 2000;
	public static int PUNCH_DAMAGE = 75;
	public static int KICK_DAMAGE = 40;
	public static int DEFENCE = 0;
	public static double WALK_SPEED = 6;

	private String name; 
	private Image state;
	private boolean alive, idle, walking, jumping, attacking, blocking, crouching;
	private boolean buffedDamage, buffedDefence, buffedSpeed;
	private int hp;
	private double hpBar, damageBuffExpire, defenceBuffExpire, speedBuffExpire;
	private double damageBuffDuration, defenceBuffDuration, speedBuffDuration;
	private int actionCounter;
	private double X,Y;
	private boolean jump,down;
	private int n, dv;
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
		actionCounter = 0; dv = 4;
	}
	public void punch(EnemyGrey target) {
		if(target.isBlocking()) {
			Sound.blockSound.play(0.5);
		}else {
			Sound.punchSound.play(0.2);
			target.takeDamage(StickMan.PUNCH_DAMAGE);
			ScoreBoard.increaseScore(20);
		}
	}
	public void punch(EnemyRed target) {
		if(target.isBlocking()) {
			Sound.blockSound.play(0.5);
		}else {
			Sound.punchSound.play(0.2);
			target.takeDamage(StickMan.PUNCH_DAMAGE);
			ScoreBoard.increaseScore(20);
		}
	}
	public void punch(EnemyBlue target) {
		if(target.isBlocking()) {
			Sound.blockSound.play(0.5);
		}else {
			Sound.punchSound.play(0.2);
			target.takeDamage(StickMan.PUNCH_DAMAGE);
			ScoreBoard.increaseScore(20);
		}
	}
	public void kick(EnemyGrey target) {
		target.takeDamage(StickMan.KICK_DAMAGE);
		Sound.kickSound.play(0.2);
		ScoreBoard.increaseScore(30);
	}
	public void kick(EnemyRed target) {
		target.takeDamage(StickMan.KICK_DAMAGE);
		Sound.kickSound.play(0.2);
		ScoreBoard.increaseScore(30);
	}
	public void kick(EnemyBlue target) {
		target.takeDamage(StickMan.KICK_DAMAGE);
		Sound.kickSound.play(0.2);
		ScoreBoard.increaseScore(30);
	}
	
	public void takeDamage(int dmg) {
		if(this.isBlocking()) return;
		dmg -= DEFENCE;
		if(dmg < 0) dmg = 0;
		this.hp -= dmg;
		this.updateHp();
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
	
	public void block() {
		walking = false; idle = false; jumping = false; attacking = false; blocking = true;
		state = Character.BLOCK;
		actionCounter = 0;
	}
	public void walkRight() {
		X += WALK_SPEED;
		if(X > GameViewManager.width - 60) X = GameViewManager.width - 60;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toRight[actionCounter / dv];
		actionCounter = (actionCounter + 1) % (3*dv);
	}
	
	public void walkLeft() {
		X -= WALK_SPEED;
		if(X < 0) X = 0;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toLeft[actionCounter / dv];
		actionCounter = (actionCounter + 1) % (3*dv);
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
		crouching = true;
	}
	
	public void down() {
		state = Character.JUMP;
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
							state = Character.IDLE;
							t.interrupt();
						}
					}else if(Y >= 480) {
						state = Character.IDLE;
						t.interrupt();
					}
				}catch(InterruptedException e){
					state = Character.IDLE;
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
	public boolean isDown() {
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
		if(hp < 0) hp = 0;
		if(hp > MAX_HP) hp = MAX_HP;
		this.hp = hp;
		updateHp();
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
		if(buffedDamage) gc.drawImage(Character.SOLID_RED, X, Y - 15 , (damageBuffDuration*20.0) / 5.0 , 5);
		if(buffedDefence) gc.drawImage(Character.SOLID_BLUE, X + 20, Y - 15 , (defenceBuffDuration*20.0) / 5.0 , 5);
		if(buffedSpeed) gc.drawImage(Character.SOLID_YELLOW, X + 40, Y - 15 , (speedBuffDuration*20.0) / 5.0 , 5);
	}
	public boolean isBlocking() {
		return blocking;
	}
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
		walking = false; idle = false; jumping = false; attacking = false; down = false; jump = false;
	}
	public boolean isCrouching() {
		return crouching;
	}
	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
	}

	public void increaseDamage() {
		PUNCH_DAMAGE = 120;
		KICK_DAMAGE = 60;
	}
	public void decreaseDamage() {
		PUNCH_DAMAGE = 75;
		KICK_DAMAGE = 40;
	}
	public void increaseDefence() {
		DEFENCE = 30;
	}
	public void decreaseDefence() {
		DEFENCE = 0;
	}
	public void increaseSpeed() {
		WALK_SPEED = 10; dv = 2;
	}
	public void decreaseSpeed() {
		WALK_SPEED = 6; dv = 4;
	}
	public void manageBuff(double time) {
		damageBuffDuration = damageBuffExpire - time;
		defenceBuffDuration = defenceBuffExpire - time;
		speedBuffDuration = speedBuffExpire - time;
		if(damageBuffDuration < 0.0) damageBuffDuration = 0.0;
		if(defenceBuffDuration < 0.0) defenceBuffDuration = 0.0;
		if(speedBuffDuration < 0.0) speedBuffDuration = 0.0;
		if(time > damageBuffExpire && this.buffedDamage) {
			this.decreaseDamage(); this.setBuffedDamage(false);
		}
		if(time > defenceBuffExpire && this.buffedDefence) {
			this.decreaseDefence(); this.setBuffedDefence(false);
		}
		if(time > speedBuffExpire && this.buffedSpeed) {
			this.decreaseSpeed(); this.setBuffedSpeed(false);
		}
	}
	public boolean isBuffedDamage() {
		return buffedDamage;
	}
	public void setBuffedDamage(boolean buffedDamage) {
		this.buffedDamage = buffedDamage;
	}
	public boolean isBuffedDefence() {
		return buffedDefence;
	}
	public void setBuffedDefence(boolean buffedDefence) {
		this.buffedDefence = buffedDefence;
	}
	public boolean isBuffedSpeed() {
		return buffedSpeed;
	}
	public void setBuffedSpeed(boolean buffedSpeed) {
		this.buffedSpeed = buffedSpeed;
	}
	public double getDamageBuffExpire() {
		return damageBuffExpire;
	}
	public void setDamageBuffExpire(double damageBuffExpire) {
		this.damageBuffExpire = damageBuffExpire;
	}
	public double getDefenceBuffExpire() {
		return defenceBuffExpire;
	}
	public void setDefenceBuffExpire(double defenceBuffExpire) {
		this.defenceBuffExpire = defenceBuffExpire;
	}
	public double getSpeedBuffExpire() {
		return speedBuffExpire;
	}
	public void setSpeedBuffExpire(double speedBuffExpire) {
		this.speedBuffExpire = speedBuffExpire;
	}
}
