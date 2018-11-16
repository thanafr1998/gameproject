package view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.GameButton;

public class GameViewManager{

	private static final int width = 960;
	private static final int height = 600;
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage hideStage;
	
	private GameButton button;
	
	public GameViewManager() {
			
		InitializeStage();
		createKeyListener();
		createButton();
		
	}
	
	private void createButton() {

		button = new GameButton("MAINMENU");
		button.setLayoutX(770);
		button.setLayoutY(555);
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
				
				if(event.getCode() == KeyCode.UP) {
					
				}else if(event.getCode() == KeyCode.DOWN) {
					
				}
				
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				hideGameScene();
				
			}
		});
		
	}
	
	
	private void InitializeStage() {
		
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane,width,height);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		
	}
	
	public void hideMenuScene(Stage menuStage) {
		
		hideStage = menuStage;
		hideStage.hide();
		gameStage.show();
		
	}

	private void hideGameScene() {
		
		gameStage.hide();
		hideStage.show();
		
		
		
	}
	
	public AnchorPane getGamePane() {
		return gamePane;
	}

	
}
