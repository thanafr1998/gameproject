package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import model.Sound;

public class GameButton extends Button{
	
	private final String BUTTON_PRESSED = "image/blue_button03.png";
	private final String BUTTON_FREE = "image/blue_button04.png";
	private final String BUTTON_ENTER = "image/blue_button02.png";
	
	public GameButton(String text) {
		
		setText(text);
		setFontSize(18);
		setPrefWidth(190);
		setPrefHeight(45);
		setButton(BUTTON_FREE);
		initializeButtonListeners();
	}

	public void setFontSize(int a) {
		setFont(Font.loadFont(ClassLoader.getSystemResource("font/kenvector_future.ttf").toExternalForm(),a));
	}

	private void setButton(String style) {
		
		Image image = new Image(ClassLoader.getSystemResource(style).toString(),190,45,false,true);
		BackgroundImage imageButton = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(imageButton));
		setPrefSize(190, 45);
	}
	private void setButtonPressed() {
		setButton(BUTTON_PRESSED);
	}
	private void setButtonFree() {
		setButton(BUTTON_FREE);
	}
	private void setButtonEnter() {
		setButton(BUTTON_ENTER);
	}
	
	private void initializeButtonListeners() {
		
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					Sound.pressSound.play(0.5);
					setButtonPressed();
					setEffect(new DropShadow());
				}
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonEnter();
					setEffect(null);
				}
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Sound.enterSound.play(0.5);
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressed();
				}
				else{
					setButtonEnter();
					
				}
			}
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressed();
				}
				else{
					setButtonFree();
				}
			}
		});
		
	}
}
