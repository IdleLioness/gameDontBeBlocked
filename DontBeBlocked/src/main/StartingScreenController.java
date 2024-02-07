package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartingScreenController {

    @FXML
	Button playButton;

    @FXML
	Button rulesButton;

    @FXML
	Button settingsButton;
    
    public void openGamePanel() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TetrisView.fxml"));
			Parent root = loader.load();
			TetrisController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					controller.onKeyPressed(event);
//					System.out.println(event.getCode());
				}
				
			});
			
			//access primary Stage through button 
			Stage primaryStage = (Stage) playButton.getScene().getWindow();
			
			primaryStage.setScene(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void openRulesPanel() {
    	try {
    		AnchorPane root = FXMLLoader.load(getClass().getResource("RulesView.fxml"));
//    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RulesView.fxml"));
//			Parent root = loader.load();
    		Scene scene = new Scene(root); 
    		
    		//access primary Stage through button 
			Stage primaryStage = (Stage) rulesButton.getScene().getWindow();
			
			primaryStage.setScene(scene);
    	} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void openSettingsPanel() {
    	try {
    		AnchorPane root = FXMLLoader.load(getClass().getResource("SettingsView.fxml"));
    		Scene scene = new Scene(root);
    		
    		//access primary Stage through button 
			Stage primaryStage = (Stage) rulesButton.getScene().getWindow();
			
			primaryStage.setScene(scene);
    	} catch(Exception e) {
			e.printStackTrace();
		}
    }
}
