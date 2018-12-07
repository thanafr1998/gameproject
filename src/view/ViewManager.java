package view;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import model.GameButton;
import model.InfoLabel;
import model.InputField;
import model.StickMan;
import model.subScene;

public class ViewManager {

	private static final int width = 960;
	private static final int height = 600;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private static final int button_x = 100;
	private static final int button_y = 80;
	private List<GameButton> menuButtons;
	private GameButton resumeButton;
	
	private String name = "";
	
	private subScene playSubScene;
	private subScene howToPlaySubScene;
	private subScene scoreSubScene;
	private subScene exitSubScene;
	private subScene showSubScene;
	
	private GameViewManager gameViewManager;
	
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
	}
	
	private void createSubScene() {
		
		howToPlaySubScene = new subScene();
		scoreSubScene = new subScene();
		exitSubScene = new subScene();
		createPlaySubScene();
		mainPane.getChildren().add(howToPlaySubScene);
		mainPane.getChildren().add(scoreSubScene);
		mainPane.getChildren().add(exitSubScene);
	}
	
	private void createPlaySubScene() {
		playSubScene = new subScene();
		InfoLabel label = new InfoLabel("Enter Your Name :");
		GameButton enterButton = new GameButton("ENTER");
		InputField enterName = new InputField("Enter Name (max 7 characters)");
		
		label.setLayoutX(50);
		
		enterName.setLayoutX(100);
		enterName.setLayoutY(125);
		
		enterButton.setFontSize(15);
		enterButton.setPrefSize(190, 50);
		enterButton.setLayoutX(250);
		enterButton.setLayoutY(200);
		enterButton.setOnMousePressed(new EventHandler<MouseEvent>() {
		
			@Override
			public void handle(MouseEvent event) {

				name = enterName.getText();
				if(name.equals("")) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Name cannot be blank");
					alert.showAndWait();
				}else if(name.length() > 7) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Name is too long (max 7 characters)");
					alert.showAndWait();
				}
				else {
					if(event.getButton().equals(MouseButton.PRIMARY)) {
					resumeButton.setDisable(false);
					gameViewManager = new GameViewManager(name);
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

	public Stage getMainStage(){
		return mainStage;
	}
	
	private void createLogo() {
		ImageView logo = new ImageView(ClassLoader.getSystemResource("image/Contra-Energy-Logo.png").toString());
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
	
	protected void addMenuButton(GameButton button) {
		button.setLayoutX(button_x);
		button.setLayoutY(button_y + menuButtons.size()*100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	private void createButton() {
		createResumeButton();
		createNewGameButton();
		createHowToPlayButton();
		createScoreButton();
		createExitButton();
	}
	
	private void createResumeButton() {
		resumeButton = new GameButton("Resume");
		resumeButton.setDisable(true);
		addMenuButton(resumeButton);
		
		resumeButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {

				if(event.getButton().equals(MouseButton.PRIMARY)) {
				
				gameViewManager.hideMenuScene(mainStage);
								
				}
			}
		
		});
	}
	
	private void createNewGameButton() {
		GameButton newGameButton = new GameButton("New Game");
		addMenuButton(newGameButton);
		
		newGameButton.setOnAction(new EventHandler<ActionEvent>() {
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
		GameButton howToPlayButton = new GameButton("How To Play");
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
	
	private void createScoreButton() {
		GameButton scoreButton = new GameButton("Score");
		addMenuButton(scoreButton);
		
		scoreButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(showSubScene == scoreSubScene) {
					showSubScene = new subScene();
				}else{
					checkSubScene();
					showSubScene = scoreSubScene;
				}
				
				scoreSubScene.moveSubScene();
			}
		});
	}
	
	private void createExitButton() {
		GameButton exitButton = new GameButton("Exit");
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
		}else if(showSubScene == scoreSubScene) {
			scoreSubScene.moveSubScene();
		}else {
			showSubScene = new subScene();
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
	
}