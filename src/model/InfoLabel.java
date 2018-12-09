package model;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class InfoLabel extends Label{
	
	public InfoLabel(String text){
		
		setPrefSize(400, 150);
		setPadding(new Insets(10, 10, 10, 10));
		setText(text);
		setFont(Font.loadFont(ClassLoader.getSystemResource("font/kenvector_future.ttf").toExternalForm(),18));
	}
	
	public InfoLabel(String text, int i, int j) {
		setPrefSize(400, 150);
		setPadding(new Insets(10, 10, 10, 10));
		setText(text);
		setLayoutX(i);
		setLayoutY(j);
		setFont(Font.loadFont(ClassLoader.getSystemResource("font/kenvector_future.ttf").toExternalForm(),18));
	}
}
