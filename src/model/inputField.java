package model;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class inputField extends VBox {
	
	private TextField textField;
	
	public inputField(String title, String promptText) {

		super.setPadding(new Insets(5, 5, 5, 5));
		super.setSpacing(5);
		super.setPrefSize(250, 20);
		
		Label label = new Label(title);
		textField = new TextField();
		textField.setPromptText(promptText);
		getChildren().addAll(label, textField);
		
	}

	public String getText() {
		return textField.getText().trim();
	}
	

	
}
