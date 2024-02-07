package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RulesController {
	
	@FXML
	Text rule1txt;
	
	@FXML
	Text rule2txt;
	
	@FXML
	Text rule3txt;
	
	@FXML
	Text rule4txt;
	
	@FXML
	Button menuButton;
	
	public void openStartingScreenPanel() {
    	try {
    		AnchorPane root = FXMLLoader.load(getClass().getResource("StartingScreenView.fxml"));
    		Scene scene = new Scene(root);
    		
    		//access primary Stage through button 
			Stage primaryStage = (Stage) menuButton.getScene().getWindow();
			
			primaryStage.setScene(scene);
    	} catch(Exception e) {
			e.printStackTrace();
		}
    }
}
