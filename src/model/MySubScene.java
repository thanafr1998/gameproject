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
		
		TextFlow t = new TextFlow();
		Text L1 = new Text("\n\tuse\t'J'\tto move left\n\tuse\t'L'\tto move right\n"); L1.setFont(new Font(20));
		Text L2 = new Text("\tuse\t'I'\tto jump up\n\tuse\t'K'\tto drop down\n");	L2.setFont(new Font(20));
		Text L3 = new Text("\tuse\t'A'\tto punch\n\tuse\t'S'\tto kick\n");				L3.setFont(new Font(20));
		Text L4 = new Text("\tuse\t'D'\tto block\n\tuse\t'C'\tto crouch\n");			L4.setFont(new Font(20));
		Text L5 = new Text("\t*Note: attack keys must be used with move keys\n");	L5.setFont(new Font(20));
		t.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		t.getChildren().addAll(L1,L2,L3,L4,L5);
		rootSubScene.getChildren().addAll(t);
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
