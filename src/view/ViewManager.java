package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.gameButton;
import model.infoLabel;
import model.inputField;
import model.subScene;

public class ViewManager {

	private static final int width = 960;
	private static final int height = 600;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private static final int button_x = 100;
	private static final int button_y = 120;
	List<gameButton> menuButtons;
	
	private subScene playSubScene;
	private subScene howToPlaySubScene;
	private subScene creditSubScene;
	private subScene exitSubScene;
	
	private subScene showSubScene;
	
	public ViewManager() {
		
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane,width,height);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createButton();
		createBackground();
		createSubScene();
		createLogo();
		
		subScene sub = new subScene();
		sub.setLayoutX(200);
		sub.setLayoutY(100);
		
	}
	
	private void createSubScene() {
		
		howToPlaySubScene = new subScene();
		creditSubScene = new subScene();
		exitSubScene = new subScene();
		createPlaySubScene();
		mainPane.getChildren().add(howToPlaySubScene);
		mainPane.getChildren().add(creditSubScene);
		mainPane.getChildren().add(exitSubScene);
	}
	
	private void createPlaySubScene() {
		playSubScene = new subScene();
		infoLabel label = new infoLabel("Enter Your Name :");
		gameButton enterButton = new gameButton("ENTER");
		inputField enterName = new inputField("", "Enter Your Name");
		
		enterName.setLayoutX(100);
		enterName.setLayoutY(100);
		
		enterButton.setFontSize(15);
		enterButton.setPrefSize(190, 50);
		enterButton.setLayoutX(250);
		enterButton.setLayoutY(200);
		enterButton.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					
					gameViewManager gameView = new gameViewManager();
					gameView.hideMenuScene(mainStage);
				}
				
			}

		});
		
		mainPane.getChildren().add(playSubScene);
		playSubScene.getRoot2().getChildren().add(label);
		playSubScene.getRoot2().getChildren().add(enterName);
		playSubScene.getRoot2().getChildren().add(enterButton);
	}
	
	public Stage getMainStage(){
		return mainStage;
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("view/resoucres/Contra-Energy-Logo.png");
		logo.setLayoutX(450);
		logo.setLayoutY(450);
		logo.setFitWidth(450);
		logo.setFitHeight(100);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
		});
		
		mainPane.getChildren().add(logo);
	}
	
	private void addMenuButton(gameButton button) {
		button.setLayoutX(button_x);
		button.setLayoutY(button_y + menuButtons.size()*100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	private void createButton() {
		createStartButton();
		createHowToPlayButton();
		createCreditButton();
		createExitButton();
	}
	
	private void createStartButton() {
		gameButton startButton = new gameButton("Start");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if(showSubScene == playSubScene) {
					showSubScene = new subScene();
				}else{
					checkSubScene();
					showSubScene = playSubScene;
				}
				
				playSubScene.moveSubScene();
				
			}
		});
	}
	
	private void createHowToPlayButton() {
		gameButton howToPlayButton = new gameButton("How To Play");
		addMenuButton(howToPlayButton);
		
		howToPlayButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(showSubScene == howToPlaySubScene) {
					showSubScene = new subScene();
				}else{
					checkSubScene();
					showSubScene = howToPlaySubScene;
				}
				
				howToPlaySubScene.moveSubScene();
			}
		});
	}
	
	private void createCreditButton() {
		gameButton creditButton = new gameButton("Credit");
		addMenuButton(creditButton);
		
		creditButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(showSubScene == creditSubScene) {
					showSubScene = new subScene();
				}else{
					checkSubScene();
					showSubScene = creditSubScene;
				}
				
				creditSubScene.moveSubScene();
			}
		});
	}
	
	private void createExitButton() {
		gameButton exitButton = new gameButton("Exit");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}
		});
	}
	
	private void checkSubScene() {
		if(showSubScene == playSubScene) {
			playSubScene.moveSubScene();
		}else if(showSubScene == howToPlaySubScene) {
			howToPlaySubScene.moveSubScene();
		}else if(showSubScene == creditSubScene) {
			creditSubScene.moveSubScene();
		}else {
			showSubScene = new subScene();
		}
	}
	
	private void createBackground() {
		Image backgroundImage = new Image("view/resoucres/the-background-2819000_960_720.png",960,600,false,true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	
	
}
