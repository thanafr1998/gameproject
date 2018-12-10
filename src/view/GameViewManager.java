package view;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import buff.Buffs;
import buff.DamageBuff;
import buff.DefenceBuff;
import buff.HealBuff;
import buff.SpeedBuff;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.EnemyBlue;
import model.EnemyGrey;
import model.EnemyRed;
import model.GameButton;
import model.Sound;
import model.StickMan;
import model.Character;
import model.ScoreBoard;
import obstacle.Missile;

public class GameViewManager extends ViewManager{

	public static final int width = 960;
	public static final int height = 600;
	public static final int GroundThickness = 20;
	
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage hideStage;
	
	private Thread t, g;
	private GraphicsContext gc;
	
	private StickMan playerCharacter;
	private ArrayList<EnemyRed> RedBot;
	private ArrayList<EnemyBlue> BlueBot;
	private ArrayList<EnemyGrey> GreyBot;
	private ArrayList<Buffs> buffs;
	private CopyOnWriteArrayList<Missile> missiles;
	
	private int lastAddRed, lastAddBlue, lastAddGrey, lastAddMissile, lastAddBuff;
	private boolean redWasFilled, blueWasFilled, greyWasFilled;
	private ArrayList<String> input;
	private boolean attacked;
	public AnimationTimer TIMER;
	private GameButton button;
	//private boolean gamePaused;
	
	
	public GameViewManager(String playerName) {
		
		InitializeStage();
		createKeyListener();
		createButton();
		playerCharacter = new StickMan(playerName);
		input = new ArrayList<String>();
		RedBot = new ArrayList<EnemyRed>();
		BlueBot = new ArrayList<EnemyBlue>();
		GreyBot = new ArrayList<EnemyGrey>();
		missiles = new CopyOnWriteArrayList<Missile>();
		buffs = new ArrayList<Buffs>();
		lastAddRed = 0; lastAddBlue = 0; lastAddGrey = 0; lastAddMissile = 0; lastAddBuff = 0;
		redWasFilled = false; blueWasFilled = false; greyWasFilled = false;
		//gamePaused = false;
	}

	private void createButton() {

		button = new GameButton("MAINMENU");
		button.setLayoutX(770);
		button.setLayoutY(0);
		button.setVisible(false);
		button.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					hideGameScene();
				}
			}

		});
		
		gamePane.getChildren().add(button);
		
	}
	
	private void createKeyListener() {
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				String k = event.getCode().toString();
				if(!input.contains(k)) {
					input.add(k);
				}
				if(input.contains("J") && input.contains("A") && !playerCharacter.isAttacking() && !attacked) {
					Sound.missSound.play(0.5);
					playerCharacter.setAttacking(true);
					attacked = true;
					t = new Thread(() -> {
						try {
							playerCharacter.punchLeft();
							Thread.sleep(100);
							playerCharacter.punchLeft();
							Thread.sleep(100);
							playerCharacter.setIdle();
						}catch(Exception e) {
							playerCharacter.setIdle();
						}
					});
					t.start();
					for(int i = GreyBot.size() - 1; i >= 0 ; i--) {
						if(GreyBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = playerCharacter.getX() - GreyBot.get(i).getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.punch(GreyBot.get(i));
							if(!GreyBot.get(i).isAlive()) GreyBot.remove(i);
						}
					}
					for(int i = RedBot.size() - 1; i >= 0 ; i--) {
						if(RedBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = playerCharacter.getX() - RedBot.get(i).getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.punch(RedBot.get(i));
							if(!RedBot.get(i).isAlive()) RedBot.remove(i);
						}
					}
					for(int i = BlueBot.size() - 1; i >= 0 ; i--) {
						if(BlueBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = playerCharacter.getX() - BlueBot.get(i).getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.punch(BlueBot.get(i));
							if(!BlueBot.get(i).isAlive()) BlueBot.remove(i);
						}
					}
				}else if(input.contains("L") && input.contains("A") && !playerCharacter.isAttacking() && !attacked) {
					Sound.missSound.play(0.5);
					attacked = true;
					playerCharacter.setAttacking(true);
					t = new Thread(() -> {
						try {
							playerCharacter.punchRight();
							Thread.sleep(100);
							playerCharacter.punchRight();
							Thread.sleep(100);
							playerCharacter.setIdle();
						}catch(Exception e) {
							playerCharacter.setIdle();
						}
					});
					t.start();
					for(int i = GreyBot.size() - 1; i >= 0 ; i--) {
						if(GreyBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = GreyBot.get(i).getX() - playerCharacter.getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.punch(GreyBot.get(i));
							if(!GreyBot.get(i).isAlive()) GreyBot.remove(i);
						}
					}
					for(int i = RedBot.size() - 1; i >= 0 ; i--) {
						if(RedBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = RedBot.get(i).getX() - playerCharacter.getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.punch(RedBot.get(i));
							if(!RedBot.get(i).isAlive()) RedBot.remove(i);
						}
					}
					for(int i = BlueBot.size() - 1; i >= 0 ; i--) {
						if(BlueBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = BlueBot.get(i).getX() - playerCharacter.getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.punch(BlueBot.get(i));
							if(!BlueBot.get(i).isAlive()) BlueBot.remove(i);
						}
					}
				}if(input.contains("J") && input.contains("S") && !playerCharacter.isAttacking() && !attacked) {
					Sound.missSound.play(0.5);
					playerCharacter.setAttacking(true);
					attacked = true;
					t = new Thread(() -> {
						try {
							playerCharacter.kickLeft();
							Thread.sleep(100);
							playerCharacter.kickLeft();
							Thread.sleep(100);
							playerCharacter.setIdle();
						}catch(Exception e) {
							playerCharacter.setIdle();
						}
					});
					t.start();
					for(int i = GreyBot.size() - 1; i >= 0 ; i--) {
						if(GreyBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = playerCharacter.getX() - GreyBot.get(i).getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.kick(GreyBot.get(i));
							if(!GreyBot.get(i).isAlive()) GreyBot.remove(i);
						}
					}
					for(int i = RedBot.size() - 1; i >= 0 ; i--) {
						if(RedBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = playerCharacter.getX() - RedBot.get(i).getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.kick(RedBot.get(i));
							if(!RedBot.get(i).isAlive()) RedBot.remove(i);
						}
					}
					for(int i = BlueBot.size() - 1; i >= 0 ; i--) {
						if(BlueBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = playerCharacter.getX() - BlueBot.get(i).getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.kick(BlueBot.get(i));
							if(!BlueBot.get(i).isAlive()) BlueBot.remove(i);
						}
					}
				}else if(input.contains("L") && input.contains("S") && !playerCharacter.isAttacking() && !attacked) {
					Sound.missSound.play(0.5);
					attacked = true;
					playerCharacter.setAttacking(true);
					t = new Thread(() -> {
						try {
							playerCharacter.kickRight();
							Thread.sleep(100);
							playerCharacter.kickRight();
							Thread.sleep(100);
							playerCharacter.setIdle();
						}catch(Exception e) {
							playerCharacter.setIdle();
						}
					});
					t.start();
					for(int i = GreyBot.size() - 1; i >= 0 ; i--) {
						if(GreyBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = GreyBot.get(i).getX() - playerCharacter.getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.kick(GreyBot.get(i));
							if(!GreyBot.get(i).isAlive()) GreyBot.remove(i);
						}
					}
					for(int i = RedBot.size() - 1; i >= 0 ; i--) {
						if(RedBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = RedBot.get(i).getX() - playerCharacter.getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.kick(RedBot.get(i));
							if(!RedBot.get(i).isAlive()) RedBot.remove(i);
						}
					}
					for(int i = BlueBot.size() - 1; i >= 0 ; i--) {
						if(BlueBot.get(i).getY() != playerCharacter.getY()) continue;
						double diff = BlueBot.get(i).getX() - playerCharacter.getX();
						if(diff > 0.0 && diff < Character.ATTACK_RANGE) {
							playerCharacter.kick(BlueBot.get(i));
							if(!BlueBot.get(i).isAlive()) BlueBot.remove(i);
						}
					}
				}else if(input.contains("L") && !input.contains("A")) {
					playerCharacter.walkRight();
					playerCharacter.setWalking(true);
				}else if(input.contains("J") && !input.contains("A")) {
					playerCharacter.walkLeft();
					playerCharacter.setWalking(true);
				}else if(input.contains("I") && !playerCharacter.isJumping() && !playerCharacter.isDown()) {
					playerCharacter.jump();
				}else if(input.contains("C")  && !playerCharacter.isJumping() && !playerCharacter.isDown()){
					playerCharacter.crouch();
				}else if(input.contains("K") && !playerCharacter.isJumping() && !playerCharacter.isDown()) {
					playerCharacter.down();
				}else if(input.contains("D") && !playerCharacter.isJumping() && !playerCharacter.isDown() && !playerCharacter.isWalking()) {
					playerCharacter.block();
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.J) {
					input.remove("J");
					playerCharacter.setIdle();
				}
				if(event.getCode() == KeyCode.L) {
					input.remove("L");
					playerCharacter.setIdle();
				}
				if(event.getCode() == KeyCode.C) {
					input.remove("C");
					playerCharacter.setIdle();
					playerCharacter.setCrouching(false);
				}
				if(event.getCode() == KeyCode.I) {
					input.remove("I");
				}
				if(event.getCode() == KeyCode.A) {
					input.remove("A");
					playerCharacter.setAttacking(false);
					attacked = false;
				}
				if(event.getCode() == KeyCode.K) {
					input.remove("K");
				}
				if(event.getCode() == KeyCode.S) {
					input.remove("S");
					playerCharacter.setAttacking(false);
					attacked = false;
				}
				if(event.getCode() == KeyCode.D) {
					input.remove("D");
					playerCharacter.setIdle();
				}
			}
		});
		
	}
	
	
	private void InitializeStage() {
		
		Canvas canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		gamePane = new AnchorPane();
		gamePane.setStyle("-fx-background-color: aliceblue");
		gamePane.getChildren().add(canvas); 
		gameScene = new Scene(gamePane,width,height);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
	}
	
	public void hideMenuScene(Stage menuStage) {
		
		hideStage = menuStage;
		hideStage.hide();
		start(gameStage);
		
	}

	private void start(Stage gameStage) {
		gameStage.setTitle("Rambo Stickman");
		final long startNanoTime = System.nanoTime();
		gc.setFill(Color.BLUEVIOLET);
		gc.setStroke(Color.DARKCYAN);
		gc.setLineWidth(1);
		gc.setFont(Font.font("Century", 15));
		g = new Thread( () -> {
			try {
			TIMER =	new AnimationTimer() {
					public void handle(long currentNanoTime) {
						if(!playerCharacter.isAlive()) { 
							TIMER.stop();
							ScoreBoard.addList(playerCharacter.getName(),(int)ScoreBoard.getScore());
							ScoreBoard.save();
							Alert gameover = new Alert(Alert.AlertType.INFORMATION);
							gameover.setTitle("GAMEOVER!");
							gameover.setContentText(String.format("YOUE'RE DEAD...Good luck next time \n You score = %d",(int) ScoreBoard.getScore()));
							Platform.runLater(gameover::showAndWait);
							hideGameScene();
						}
						gc.clearRect(0, 0, width, height);
						gc.drawImage(Character.SOLID_GREY, 0, GameViewManager.height - 20 , GameViewManager.width, GameViewManager.GroundThickness);
						gc.drawImage(Character.SOLID_GREY, 0, GameViewManager.height - 200, GameViewManager.width, GameViewManager.GroundThickness);
						gc.drawImage(Character.SOLID_GREY, 0, GameViewManager.height - 400, GameViewManager.width, GameViewManager.GroundThickness);
						double time = (currentNanoTime - startNanoTime) / 1000000000.0;
						//Add new Red enemy
						if(((int) time) % 3 == 0 && ((int) time) != lastAddMissile) {
							missiles.add(new Missile());
							lastAddMissile = (int) time;
						}
						if(((int) time) % 5 == 0 && ((int) time) != lastAddBuff) {
							double temp = Math.random();
							if(temp < 0.25) buffs.add(new DamageBuff());
							else if(temp < 0.50) buffs.add(new DefenceBuff());
							else if(temp < 0.75) buffs.add(new SpeedBuff());
							else if(temp < 1.00) buffs.add(new HealBuff());
							lastAddBuff= (int) time;
						}
						if(((int) time) % 17 == 0 && (int) time != lastAddRed  && !redWasFilled) {
							if(RedBot.size() < 5)  RedBot.add(new EnemyRed());
							if(RedBot.size() == 5) redWasFilled = true;
							lastAddRed = (int) time;
						}
						//Add new Blue enemy
						if(((int) time) % 26 == 0 && (int) time != lastAddBlue  && !blueWasFilled) {
							if(BlueBot.size() < 5)  BlueBot.add(new EnemyBlue());
							if(BlueBot.size() == 5) blueWasFilled = true;
							lastAddBlue = (int) time;
						}//Add new Grey enemy
						if(((int) time) % 10 == 0 && (int) time != lastAddGrey  && !greyWasFilled) {
							if(GreyBot.size() < 10)  GreyBot.add(new EnemyGrey());
							if(GreyBot.size() == 10) greyWasFilled = true;
							lastAddGrey = (int) time;
						}
						//random action for red then draw
						for(int i = 0; i < RedBot.size(); i++) {
							EnemyRed temp = RedBot.get(i);
							if(temp.getY() == playerCharacter.getY()) {
								double diffL = temp.getX() - playerCharacter.getX();
								double diffR = playerCharacter.getX() - temp.getX();
								if(diffL > 0 && diffL <= Character.ATTACK_RANGE) {
									temp.randomAttackLeft(playerCharacter, (int) time);
								}else if(diffR > 0 && diffR <= Character.ATTACK_RANGE) {
									temp.randomAttackRight(playerCharacter, (int) time);
								}else {
									if(temp.getActionEnd() <= time) {
										temp.randomAction(time);
									}
								}
							}
							else{
								if(temp.getActionEnd() <= time) {
									temp.randomAction(time);
								}
							}
							temp.act();
							temp.draw(gc);
						}
						//random action for blue then draw
						for(int i = 0; i < BlueBot.size(); i++) {
							EnemyBlue temp = BlueBot.get(i);
							if(temp.getY() == playerCharacter.getY()) {
								double diffL = temp.getX() - playerCharacter.getX();
								double diffR = playerCharacter.getX() - temp.getX();
								if(diffL > 0 && diffL <= Character.ATTACK_RANGE) {
									temp.randomAttackLeft(playerCharacter, (int) time);
								}else if(diffR > 0 && diffR <= Character.ATTACK_RANGE) {
									temp.randomAttackRight(playerCharacter, (int) time);
								}else {
									if(temp.getActionEnd() <= time) {
										temp.randomAction(time);
									}
								}
							}
							else{
								if(temp.getActionEnd() <= time) {
									temp.randomAction(time);
								}
							}
							temp.act();
							temp.draw(gc);
						}
						//random action for grey then draw
						for(int i = 0; i < GreyBot.size(); i++) {
							EnemyGrey temp = GreyBot.get(i);
							if(temp.getY() == playerCharacter.getY()) {
								double diffL = temp.getX() - playerCharacter.getX();
								double diffR = playerCharacter.getX() - temp.getX();
								if(diffL > 0 && diffL <= Character.ATTACK_RANGE) {
									temp.randomAttackLeft(playerCharacter, (int) time);
								}else if(diffR > 0 && diffR <= Character.ATTACK_RANGE) {
									temp.randomAttackRight(playerCharacter, (int) time);
								}else {
									if(temp.getActionEnd() <= time) {
										temp.randomAction(time);
									}
								}
							}
							else{
								if(temp.getActionEnd() <= time) {
									temp.randomAction(time);
								}
							}
							temp.act();
							temp.draw(gc);
						}
						for(int i = buffs.size() - 1; i >= 0; i--) {
							Buffs b = buffs.get(i);
							b.move();
							b.draw(gc);
							if(b.pickUpBy(playerCharacter, time)) buffs.remove(b);
						}
						
						for(int i = missiles.size() - 1; i >= 0; i--) {
							Missile M = missiles.get(i);
							M.draw(gc);
							if(M.move()) {
								missiles.remove(M);
								continue;
							}
							else if(!M.isExplode) {
								if(M.checkHit(playerCharacter)) { 
								M.isExplode = true;
								t = new Thread(() -> {
									try {
										M.setImage(new Image(ClassLoader.getSystemResource("image/EXPLOSION.png").toString()));
										Sound.explosionSound.play(0.3);
										M.setBomb(playerCharacter.getX()-20,playerCharacter.getY()-20);
										M.setSize(100,100);
										Thread.sleep(500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									missiles.remove(M);
								});
								t.start();
							}
						}
						}
						playerCharacter.manageBuff(time);
						playerCharacter.draw(gc);
						gc.strokeText(String.format("Time: %.2f", time),20,20);
						ScoreBoard.increaseScore(0.017);
						String updateScore = String.format("Score: %d", (int) ScoreBoard.getScore()); 
						gc.fillText(updateScore, 850, 20);
						gc.strokeText(updateScore,850,20);
					}
					
				};
				TIMER.start();
				Alert gameover = new Alert(AlertType.INFORMATION);
				gameover.setContentText("GAME OVER...YOUE'RE DEAD!");
				g.interrupt();
			}
			catch(Exception e) {
			}
		} );
		g.start();
		gameStage.show();
	}

	
	private void hideGameScene() {
		
		gameStage.hide();
		ViewManager view = new ViewManager();
		view.getMainStage().setTitle("Rambo Stickman");
		view.getMainStage().show();
	}
	
	public Scene getGameScene() {
		return gameScene;
	}

	public void setGameScene(Scene gameScene) {
		this.gameScene = gameScene;
	}

	public Stage getGameStage() {
		return gameStage;
	}

	public void setGameStage(Stage gameStage) {
		this.gameStage = gameStage;
	}
	
	public AnchorPane getGamePane() {
		return gamePane;
	}
	
	public void setGamePane(AnchorPane gamePane) {
		this.gamePane = gamePane;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

}
