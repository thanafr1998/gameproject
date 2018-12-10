package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import view.GameViewManager;

public class EnemyBlue {
	
	public static final Image[] toRight = {Character.BLUE_walkR1,Character.BLUE_walkR2,Character.BLUE_walkR3};
	public static final Image[] toLeft = {Character.BLUE_walkL1,Character.BLUE_walkL2,Character.BLUE_walkL3};
	private static final Image[] punchLeft = {Character.BLUE_punchL1,Character.BLUE_punchL2};
	private static final Image[] punchRight = {Character.BLUE_punchR1,Character.BLUE_punchR2};
	private static final Image[] kickLeft = {Character.BLUE_kickL1,Character.BLUE_kickL2};
	private static final Image[] kickRight = {Character.BLUE_kickR1,Character.BLUE_kickR2};
	public static final int PUNCH_DAMAGE = 30;
	public static final int KICK_DAMAGE = 15;
	public static final int MAX_HP = 1500;
	public static final double WALK_SPEED = 0.3;
	public static final int ARMOR = 30;
	
	private Thread t;
	private Image state;
	private boolean alive, idle, walking, jumping, attacking, blocking;
	private int hp, nextAttackTime;
	private double hpBar;
	private int actionCounter;
	private double X,Y;
	private String action;
	private double actionDuration;
	private double actionEnd;
	
	public EnemyBlue() {
		state = Character.BLUE_IDLE;
		hp = EnemyBlue.MAX_HP;
		hpBar = ((double) (hp * Character.WIDTH) / (double) EnemyBlue.MAX_HP);
		X = Math.random()*(GameViewManager.width - Character.WIDTH);
		Y = Character.FLOOR_LEVEL[(int) (Math.random()*3.0)];
		alive = true; idle = true;
		walking = false; jumping = false; attacking = false; blocking = false;
		actionCounter = 0;
		action = "";
		actionDuration = 0;
		actionEnd = 0;
	}
	
	public void updateHp() {
		if(hp <= 0) {
			hpBar = 0;
			ScoreBoard.increaseScore(1000);
			alive = false;
		}
		else {
			hpBar = (int) ((double) (hp * Character.WIDTH) / (double) EnemyBlue.MAX_HP);
		}
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
	
	public void walkRight() {
		X += WALK_SPEED;
		if(X > GameViewManager.width - 60) X = GameViewManager.width - 60;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toRight[actionCounter / 10];
		actionCounter = (actionCounter + 1) % 30;
	}
	
	public void walkLeft() {
		X -= WALK_SPEED;
		if(X < 0) X = 0;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toLeft[actionCounter / 10];
		actionCounter = (actionCounter + 1) % 30;
	}

	public Image getState() {
		return state;
	}

	public void setState(Image state) {
		this.state = state;
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
		state = Character.BLUE_IDLE;
		actionCounter = 0;
		action = "";
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
		idle = false; walking = false; jumping = false; blocking = false;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
		idle = false; walking = false; jumping = false; attacking = false;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public double getHpBar() {
		return hpBar;
	}

	public void setHpBar(double hpBar) {
		this.hpBar = hpBar;
	}

	public int getWalkCounter() {
		return actionCounter;
	}

	public void setWalkCounter(int actionCounter) {
		this.actionCounter = actionCounter;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(this.state, X, Y, Character.WIDTH, Character.HEIGHT);
		gc.fillRoundRect(X, Y - 10, hpBar, 5, 5, 5);
	}
	
	public void randomAttackLeft(StickMan sm, int attackTime) {
		if(attackTime < nextAttackTime) return;
		nextAttackTime = attackTime + Character.ATTACK_COOLDOWN;
		double val = Math.random()*100.0;
		if(val < 40.0) {
			this.setIdle();
		}else if(val < 70.0) { //kick left
			sm.takeDamage(KICK_DAMAGE);
			t = new Thread(() -> {
				try {
					kickLeft();
					Thread.sleep(100);
					kickLeft();
					Thread.sleep(100);
					setIdle();
				}catch(Exception e) {
					setIdle();
				}
			});
			t.start();
		}else if(val < 100.0) { //punch left
			sm.takeDamage(PUNCH_DAMAGE);
			t = new Thread(() -> {
				try {
					punchLeft();
					Thread.sleep(100);
					punchLeft();
					Thread.sleep(100);
					setIdle();
				}catch(Exception e) {
					setIdle();
				}
			});
			t.start();
		}
	}
	
	public void randomAttackRight(StickMan sm, int attackTime) {
		if(attackTime < nextAttackTime) return;
		nextAttackTime = attackTime + Character.ATTACK_COOLDOWN;
		double val = Math.random()*100.0;
		if(val < 40.0) {
			this.setIdle();
		}else if(val < 70.0) { //kick right
			sm.takeDamage(KICK_DAMAGE);
			t = new Thread(() -> {
				try {
					kickRight();
					Thread.sleep(100);
					kickRight();
					Thread.sleep(100);
					setIdle();
				}catch(Exception e) {
					setIdle();
				}
			});
			t.start();
		}else if(val < 100.0) { //punch right
			sm.takeDamage(PUNCH_DAMAGE);
			t = new Thread(() -> {
				try {
					punchRight();
					Thread.sleep(100);
					punchRight();
					Thread.sleep(100);
					setIdle();
				}catch(Exception e) {
					setIdle();
				}
			});
			t.start();
		}
	}

	
	public void randomAction(double startTime) {
		this.setIdle();
		double temp = (Math.random()*100.0) + 1.0;
		if(temp > 0 && temp <= 30) {
			action = "idle";
			actionDuration = Math.random()*3.0 + 2.0;
			actionEnd = startTime + actionDuration;
			return;
		}
		else if(temp > 30 && temp <= 60) {
			action = "block";
			actionDuration = Math.random()*3.0 + 2.0;
			actionEnd = startTime + actionDuration;
			return;
		}
		else if(temp > 60 && temp <= 80) {
			action = "walkLeft";
			actionDuration = Math.random()*3.0 + 2.0;
			actionEnd = startTime + actionDuration;
			return;
		}
		else if(temp > 80 && temp <= 100) {
			action = "walkRight";
			actionDuration = Math.random()*3.0 + 2.0;
			actionEnd = startTime + actionDuration;
			return;
		}
		
	}
	
	public void act() {

		if(action.equals("idle")) {
			state = Character.BLUE_IDLE;
			actionCounter = 0;
		}
		else if(action.equals("block")){
			blocking = true;
			state = Character.BLUE_BLOCK;
			actionCounter = 0;
		}
		else if(action.equals("walkLeft")) {
			walkLeft();
		}
		else if(action.equals("walkRight")) {
			walkRight();
		}
	}
	
	public double getActionDuration() {
		return actionDuration;
	}

	public void setActionDuration(double duration) {
		this.actionDuration = duration;
	}
	
	public double getActionEnd() {
		return this.actionEnd;
	}
	
	public String getAction() {
		return action;
	}
	
	public void takeDamage(int damage) {
		damage -= ARMOR;
		if(damage < 0) damage = 0;
		this.hp -= damage;
		updateHp();
	}
}
