package model;

import javafx.animation.TranslateTransition;
import javafx.geometry.NodeOrientation;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class MySubScene extends SubScene{

	private AnchorPane rootSubScene;
	private static boolean subSceneIsHidden;
	
	public MySubScene() {
		
		super(new AnchorPane(), 500, 300);
		rootSubScene = (AnchorPane) this.getRoot();
		BackgroundImage backgroundImage = new BackgroundImage(new Image(ClassLoader.getSystemResource("image/blue_panel.png").toString(),500,300,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		subSceneIsHidden = true;
		
		rootSubScene.setBackground(new Background(backgroundImage));
		setLayoutX(1000);
		setLayoutY(100);
		
	}

	public void moveSubScene() {
		
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if(subSceneIsHidden == true) {
			transition.setToX(-600);
			subSceneIsHidden = false;
		}else {
			transition.setToX(0);
			subSceneIsHidden = true;
		}
		transition.play();
	}
	
	public AnchorPane getRootSubScene() {
		return rootSubScene;
	}
}
