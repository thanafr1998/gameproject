package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import model.Sound;

public class GameButton extends Button{
	
	private final String FONT_PATH = "res/font/kenvector_future.ttf";
	private final String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/image/blue_button03.png')";
	private final String BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/image/blue_button04.png')";
	private final String BUTTON_ENTER = "-fx-background-color: transparent; -fx-background-image: url('/image/blue_button02.png')";
	
	private int fontSize = 18;
	
	public GameButton(String text) {
		
		setText(text);
		setButtonFont();
		setPrefWidth(190);
		setPrefHeight(45);
		setStyle(BUTTON_FREE);
		initializeButtonListeners();
	}
	
	private void setButtonFont() {
	
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH),fontSize));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Century", fontSize));
		}
	}
	
	public int setFontSize(int a) {
		fontSize = a;
		setButtonFont();
		return fontSize;
	}

	private void setButton(String style) {
		setStyle(style);
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
				Sound.pressSound.play();
				if(event.getButton().equals(MouseButton.PRIMARY)) {
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
				Sound.enterSound.play();
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
