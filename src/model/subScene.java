package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class subScene extends SubScene{

	private final String pic = "model/resources/blue_panel.png";
	
	
	private boolean isHidden;
	
	public subScene() {
		super(new AnchorPane(), 500, 300);
		
		BackgroundImage backgroundImage = new BackgroundImage(new Image(pic,500,300,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		isHidden = true;
		
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(backgroundImage));
		
		setLayoutX(1000);
		setLayoutY(100);
		
	}

	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if(isHidden == true) {
			transition.setToX(-600);
			isHidden = false;
		}else {
			transition.setToX(0);
			isHidden = true;
		}
		
		transition.play();
	}
	
}
