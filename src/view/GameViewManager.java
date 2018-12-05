package view;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.EnemyRed;
import model.GameButton;
import model.StickMan;

public class GameViewManager extends ViewManager{

	public static final int width = 960;
	public static final int height = 600;
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage hideStage;
	private GraphicsContext gc;
	private StickMan playerCharacter;
	private ArrayList<EnemyRed> RedBot;
	int lastAddRed;
	
	private GameButton button;
	
	public GameViewManager(String name) {
			
		InitializeStage();
		createKeyListener();
		createButton();
		playerCharacter = new StickMan(name);
		RedBot = new ArrayList<EnemyRed>();
		lastAddRed = 0;
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
				
				if(event.getCode() == KeyCode.LEFT) {
					playerCharacter.walkLeft();
				}else if(event.getCode() == KeyCode.RIGHT) {
					playerCharacter.walkRight();
				}else if(event.getCode() == KeyCode.UP) {
					//jump (if character is idle, this mode is able to kick only) HARD !! do later
				}else if(event.getCode() == KeyCode.DOWN) {
					//crouch (if character is idle, this mode is able to punch only)
				}else if(event.getCode() == KeyCode.A) {
					//try to punch all nearby enemy
				}else if(event.getCode() == KeyCode.S) {
					//try to kick all nearby enemy
				}else if(event.getCode() == KeyCode.D) {
					//blocking -> negate all damage
				}
				
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				playerCharacter.setIdle();
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
				double time = (currentNanoTime - startNanoTime) / 1000000000.0;
				if(((int) time) % 5 == 0 && (int) time != lastAddRed && RedBot.size() < 5) {
					RedBot.add(new EnemyRed());
					lastAddRed = (int) time;
				}
				gc.clearRect(0, 0, width, height);
				for(int i = 0; i < RedBot.size(); i++) {
					EnemyRed temp = RedBot.get(i);
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
