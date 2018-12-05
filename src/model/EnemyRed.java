package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import view.GameViewManager;

public class EnemyRed {
	public static final Image RED_IDLE = new Image("model/resources/RED_IDLE.png");
	public static final Image RED_walkR1 = new Image("model/resources/RED_WALK_RIGHT_1.png");
	public static final Image RED_walkR2 = new Image("model/resources/RED_WALK_RIGHT_2.png");
	public static final Image RED_walkR3 = new Image("model/resources/RED_WALK_RIGHT_3.png");
	public static final Image RED_walkL1 = new Image("model/resources/RED_WALK_LEFT_1.png");
	public static final Image RED_walkL2 = new Image("model/resources/RED_WALK_LEFT_2.png");
	public static final Image RED_walkL3 = new Image("model/resources/RED_WALK_LEFT_3.png");
	public static final Image[] toRight = {RED_walkR1,RED_walkR2,RED_walkR3};
	public static final Image[] toLeft = {RED_walkL1,RED_walkL2,RED_walkL3};
	public static final int WIDTH = 60;
	public static final int HEIGHT = 100;
	public static final int PUNCH_DAMAGE = 120;
	public static final int PUNCH_RANGE = 75;
	public static final int KICK_DAMAGE = 70;
	public static final int KICK_RANGE = 150;
	public static final int MAX_HP = 200;
	public static final int WALK_SPEED = 6;
	

	private Image state;
	private boolean alive, idle, walking, jumping, attacking, blocking;
	private int hp;
	private double hpBar;
	private int walkCounter;
	private double X,Y;
	private String action;
	private double actionDuration;
	private double actionEnd;
	
	public EnemyRed() {
		state = RED_IDLE;
		hp = EnemyRed.MAX_HP;
		hpBar = ((double) (hp * WIDTH) / (double) EnemyRed.MAX_HP);
		X = Math.random()*(GameViewManager.width - EnemyRed.WIDTH);
		Y = GameViewManager.height - EnemyRed.HEIGHT;
		alive = true; idle = true;
		walking = false; jumping = false; attacking = false; blocking = false;
		walkCounter = 0;
		action = "";
		actionDuration = 0;
		actionEnd = 0;
	}
	
	public void updateHp() {
		if(hp <= 0) {
			hpBar = 0;
			alive = false;
		}
		else {
			hpBar = (int) ((double) (hp * WIDTH) / (double) EnemyRed.MAX_HP);
		}
	}
	
	public void walkRight() {
		X += WALK_SPEED;
		if(X > GameViewManager.width - 60) X = GameViewManager.width - 60;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toRight[walkCounter / 2];
		walkCounter = (walkCounter + 1) % 6;
	}
	
	public void walkLeft() {
		X -= WALK_SPEED;
		if(X < 0) X = 0;
		walking = true; idle = false; jumping = false; attacking = false; blocking = false;
		state = toLeft[walkCounter / 2];
		walkCounter = (walkCounter + 1) % 6;
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

	public void setIdle(boolean idle) {
		this.idle = idle;
		state = RED_IDLE;
		walkCounter = 0;
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
	}

	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
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
		return walkCounter;
	}

	public void setWalkCounter(int walkCounter) {
		this.walkCounter = walkCounter;
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
		gc.drawImage(this.state, X, Y, WIDTH, HEIGHT);
		gc.fillRect(X, Y - 10, hpBar, 5);
	}
	
	public void randomAction(double startTime) {
		double temp = (Math.random()*100.0) + 1.0;
		if(temp > 0 && temp <= 60) {
			action = "idle";
			actionDuration = Math.random()*3.0 + 2.0;
			actionEnd = startTime + actionDuration;
			return;
		}
		else if(temp > 60 && temp <= 80) {
			action = "walkLeft";
			actionDuration = Math.random();
			actionEnd = startTime + actionDuration;
			return;
		}
		else if(temp > 80 && temp <= 100) {
			action = "walkRight";
			actionDuration = Math.random();
			actionEnd = startTime + actionDuration;
			return;
		}
		
	}
	
	public void act() {

		if(action.equals("idle")) {
			state = RED_IDLE;
			walkCounter = 0;
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
}
