package model;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class InputField extends VBox {
	
	private TextField textField;
	
	public InputField(String promptText) {
		
		super.setPadding(new Insets(5, 5, 5, 5));
		super.setSpacing(5);
		super.setPrefSize(300, 50);
		
		textField = new TextField();
		textField.setPromptText(promptText);
		getChildren().add(textField);		
	}

	public String getText() {
		return textField.getText().trim();
	}
}
