package view;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.GameButton;
import model.InfoLabel;
import model.InputField;
import model.Sound;
import model.ScoreBoard;
import model.MySubScene;

public class ViewManager {

	private static final int width = 950;
	private static final int height = 580;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private static final int button_x = 100;
	private static final int button_y = 80;
	private List<GameButton> menuButtons;
	//private GameButton resumeButton;
	
	public static String playerName;
	
	private MySubScene playSubScene;
	private MySubScene howToPlaySubScene;
	private MySubScene scoreSubScene;
	private MySubScene showSubScene;
	
	private GameViewManager gameViewManager;
	
	public ViewManager() {
		
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane,width,height);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		mainStage.setResizable(false);
		playerName = "";
		createButton();
		createBackground();
		createSubScene();
	}
	
	private void createSubScene() {
		
		createPlaySubScene();
		createHowToPlaySubScene();
		createScoreSubScene();
	}
	
	private void createPlaySubScene() {
		playSubScene = new MySubScene();
		InfoLabel label = new InfoLabel("Enter Your Name :");
		GameButton enterButton = new GameButton("ENTER");
		InputField enterName = new InputField("Enter Name (max 7 characters)");
		
		label.setLayoutX(50);
		
		enterName.setLayoutX(100);
		enterName.setLayoutY(125);
		
		enterButton.setFontSize(15);
		enterButton.setPrefSize(190, 45);
		enterButton.setLayoutX(250);
		enterButton.setLayoutY(200);
		enterButton.setOnMousePressed(new EventHandler<MouseEvent>() {
		
			@Override
			public void handle(MouseEvent event) {

				playerName = enterName.getText();
				Sound.pressSound.play(0.5);
				if(playerName.equals("")) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Name cannot be blank");
					alert.showAndWait();
				}else if(playerName.length() > 7) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Name is too long (max 7 characters)");
					alert.showAndWait();
				}
				else {
					if(event.getButton().equals(MouseButton.PRIMARY)) {
						//resumeButton.setDisable(false);
						gameViewManager = new GameViewManager(playerName);
						gameViewManager.hideMenuScene(mainStage);

					}
				}
			}
			
		});
		
		mainPane.getChildren().add(playSubScene);
		playSubScene.getRootSubScene().getChildren().add(label);
		playSubScene.getRootSubScene().getChildren().add(enterName);
		playSubScene.getRootSubScene().getChildren().add(enterButton);
		
	}
	
	private void createHowToPlaySubScene() {
		howToPlaySubScene = new MySubScene();
		InfoLabel label1 = new InfoLabel("Press 'j' to walk left",30,-50,15);
		InfoLabel label2 = new InfoLabel("Press 'l' to walk right",30,-10,15);
		InfoLabel label3 = new InfoLabel("Press 'i' to jump",30,30,15);
		InfoLabel label4 = new InfoLabel("Press 'k' to down the floor",30,70,15);
		InfoLabel label5 = new InfoLabel("Press 'a' + 'direction' to punch",30,110,15);
		InfoLabel label6 = new InfoLabel("Press 's' + 'direction' to kick",30,150,15);
		InfoLabel label7 = new InfoLabel("Press 'd' to block",30,190,15);
		InfoLabel label8 = new InfoLabel("Press 'c' to crouch",30,230,15);
		mainPane.getChildren().add(howToPlaySubScene);
		howToPlaySubScene.getRootSubScene().getChildren().addAll(label1,label2,label3,label4,label5,label6,label7,label8);
	}

	private void createScoreSubScene() {
		scoreSubScene = new ScoreBoard();
		InfoLabel label = new InfoLabel("HighScore :",50,-30,18);
		mainPane.getChildren().add(scoreSubScene);
		scoreSubScene.getRootSubScene().getChildren().add(label);
	}
	
	public Stage getMainStage(){
		return mainStage;
	}
	
	
	private void addMenuButton(GameButton button) {
		button.setLayoutX(button_x);
		button.setLayoutY(button_y + menuButtons.size()*100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	private void createButton() {
		//createResumeButton();
		createNewGameButton();
		createHowToPlayButton();
		createScoreButton();
		createExitButton();
	}
	
//	private void createResumeButton() {
//		resumeButton = new GameButton("Resume");
//		resumeButton.setDisable(true);
//		addMenuButton(resumeButton);
//		
//		resumeButton.setOnMousePressed(new EventHandler<MouseEvent>() {
//			
//			@Override
//			public void handle(MouseEvent event) {
//
//				if(event.getButton().equals(MouseButton.PRIMARY)) {
//				
//				gameViewManager.hideMenuScene(mainStage);
//								
//				}
//			}
//		
//		});
//	}
	
	private void createNewGameButton() {
		GameButton newGameButton = new GameButton("New Game");
		addMenuButton(newGameButton);
		
		newGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if(showSubScene == playSubScene) {
					playSubScene.moveSubScene();
					showSubScene = new MySubScene();
				}else{
					checkSubScene();
					playSubScene.moveSubScene();
					showSubScene = playSubScene;
				}
			}
		});
	}
	
	private void createHowToPlayButton() {
		GameButton howToPlayButton = new GameButton("How To Play");
		addMenuButton(howToPlayButton);
		
		howToPlayButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(showSubScene == howToPlaySubScene) {
					howToPlaySubScene.moveSubScene();
					showSubScene = new MySubScene();
				}else{
					checkSubScene();
					howToPlaySubScene.moveSubScene();
					showSubScene = howToPlaySubScene;
				}
			}
		});
	}
	
	private void createScoreButton() {
		GameButton scoreButton = new GameButton("Score");
		addMenuButton(scoreButton);
		
		scoreButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(showSubScene == scoreSubScene) {
					scoreSubScene.moveSubScene();
					showSubScene = new MySubScene();
				}else{
					checkSubScene();
					scoreSubScene.moveSubScene();
					showSubScene = scoreSubScene;
				}
			}
		});
	}
	
	private void createExitButton() {
		GameButton exitButton = new GameButton("Exit");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					ScoreBoard.clearScore();
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				mainStage.close();
			}
		});
	}
	
	private void checkSubScene() {
		if(showSubScene == playSubScene) {
			playSubScene.moveSubScene();
		}else if(showSubScene == howToPlaySubScene) {
			howToPlaySubScene.moveSubScene();
		}else if(showSubScene == scoreSubScene) {
			scoreSubScene.moveSubScene();
		}else {
			showSubScene = new MySubScene();
		}
	}
	
	private void createBackground() {
		Image backgroundImage = new Image(ClassLoader.getSystemResource("image/the-background-2819000_960_720.png").toString(),960,600,false,true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	
	public List<GameButton> getMenuButtons() {
		return menuButtons;
	}

	public void setMenuButtons(List<GameButton> menuButtons) {
		this.menuButtons = menuButtons;
	}
	
	public Stage getStage() {
		return mainStage;
	}
}