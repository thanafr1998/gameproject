package view;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
import model.StickMan;
import model.Character;
import exception.*;

public class GameViewManager extends ViewManager{

	public static final int width = 960;
	public static final int height = 600;
	public static final int GroundThickness = 20;
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage hideStage;
	
	private GraphicsContext gc;
	private StickMan playerCharacter;
	private ArrayList<EnemyRed> RedBot;
	private ArrayList<EnemyBlue> BlueBot;
	private ArrayList<EnemyGrey> GreyBot;
	int lastAddRed, lastAddBlue, lastAddGrey;
	boolean redWasFilled, blueWasFilled, greyWasFilled;
	private Thread t;
	private ArrayList<String> input;
	private boolean attacked;

	private GameButton button;
	
	
	public GameViewManager(String name) {
			
		InitializeStage();
		createKeyListener();
		createButton();
		playerCharacter = new StickMan(name);
		RedBot = new ArrayList<EnemyRed>();
		BlueBot = new ArrayList<EnemyBlue>();
		GreyBot = new ArrayList<EnemyGrey>();
		lastAddRed = 0; lastAddBlue = 0; lastAddGrey = 0;
		redWasFilled = false; blueWasFilled = false; greyWasFilled = false;
		input = new ArrayList<String>();
	}

	private void createButton() {

		button = new GameButton("MAINMENU");
		button.setLayoutX(770);
		button.setLayoutY(0);
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
				if(input.isEmpty()) playerCharacter.setIdle();
				System.out.println(k);
				if(!input.contains(k)) {
					input.add(k);
				}
				
				if(input.contains("LEFT") && input.contains("A") && !playerCharacter.isAttacking() && !attacked) {
					playerCharacter.setAttacking(true);
					attacked = true;
					t = new Thread(() -> {
						try {
							playerCharacter.punchLeft();
							Thread.sleep(50);
							playerCharacter.punchLeft();
							Thread.sleep(50);
							playerCharacter.setIdle();
						}catch(Exception e) {
							playerCharacter.setIdle();
						}
					});
					t.start();
				}else if(input.contains("RIGHT") && input.contains("A") && !playerCharacter.isAttacking() && !attacked) {
					attacked = true;
					playerCharacter.setAttacking(true);
					t = new Thread(() -> {
						try {
							playerCharacter.punchRight();
							Thread.sleep(50);
							playerCharacter.punchRight();
							Thread.sleep(50);
							playerCharacter.setIdle();
						}catch(Exception e) {
							playerCharacter.setIdle();
						}
					});
					t.start();
				}else if(input.contains("RIGHT") && !input.contains("A")) {
					playerCharacter.walkRight();
					playerCharacter.setWalking(true);
				}else if(input.contains("LEFT") && !input.contains("A")) {
					playerCharacter.walkLeft();
					playerCharacter.setWalking(true);
				}else if(input.contains("UP") && !playerCharacter.isJumping()) {
					playerCharacter.jump();
				}else if(input.contains("DOWN") && !playerCharacter.isJumping() ) {
					playerCharacter.crouch();
				}else if(input.contains("X") && !playerCharacter.isJumping()) {
					System.out.println("i'm trying");
					playerCharacter.down();
				}
				
				

			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT) {
					input.remove("LEFT");
					playerCharacter.setIdle();
				}
				if(event.getCode() == KeyCode.RIGHT) {
					input.remove("RIGHT");
					playerCharacter.setIdle();
				}
				if(event.getCode() == KeyCode.DOWN) {
					input.remove("DOWN");
					playerCharacter.setIdle();
				}
				if(event.getCode() == KeyCode.UP) {
					input.remove("UP");
				}
				if(event.getCode() == KeyCode.A) {
					input.remove("A");
					playerCharacter.setAttacking(false);
					attacked = false;
				}
				if(event.getCode() == KeyCode.X) {
					input.remove("X");
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
		gameStage.setTitle("Rambo Stackman");
		final long startNanoTime = System.nanoTime();
		gc.setFill(Color.BLUEVIOLET);
		gc.setStroke(Color.DARKCYAN);
		gc.setLineWidth(1);
		gc.setFont(Font.font("Century", 15));
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				gc.clearRect(0, 0, width, height);
				gc.drawImage(Character.SOLID_GREY, 0, GameViewManager.height - 20 , GameViewManager.width, GameViewManager.GroundThickness);
				gc.drawImage(Character.SOLID_GREY, 0, GameViewManager.height - 200, GameViewManager.width, GameViewManager.GroundThickness);
				gc.drawImage(Character.SOLID_GREY, 0, GameViewManager.height - 400, GameViewManager.width, GameViewManager.GroundThickness);
				double time = (currentNanoTime - startNanoTime) / 1000000000.0;
				//Add new Red enemy
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
					if(temp.getActionEnd() <= time) {
						temp.randomAction(time);
					}
					temp.act();
					temp.draw(gc);
				}
				//random action for blue then draw
				for(int i = 0; i < BlueBot.size(); i++) {
					EnemyBlue temp = BlueBot.get(i);
					if(temp.getActionEnd() <= time) {
						temp.randomAction(time);
					}
					temp.act();
					temp.draw(gc);
				}
				//random action for red then draw
				for(int i = 0; i < GreyBot.size(); i++) {
					EnemyGrey temp = GreyBot.get(i);
					if(temp.getActionEnd() <= time) {
						temp.randomAction(time);
					}
					temp.act();
					temp.draw(gc);
				}
				playerCharacter.draw(gc);
				gc.strokeText(String.format("Time: %.2f", time),20,20);
				
			}
			
		}.start();
		gameStage.show();
	}

	
	private void hideGameScene() {
		
		gameStage.hide();
		hideStage.show();
		
	}
	
	public AnchorPane getGamePane() {
		return gamePane;
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
