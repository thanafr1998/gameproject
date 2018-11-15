package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class infoLabel extends Label{
	
	private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	
	public infoLabel(String text) {
		
		setPrefSize(400, 150);
		setPadding(new Insets(10, 10, 10, 10));
		setLayoutX(50);
		setText(text);
		setStringFont();
		setWrapText(true);
		
	}
	
	private void setStringFont() {
		
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH),18));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Arial", 18));
		}
	}
	
}
