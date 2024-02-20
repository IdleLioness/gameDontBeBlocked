package main;

import javafx.event.EventHandler;

import java.io.IOException;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartingScreenController {
	
	@FXML
	AnchorPane anchorPaneRoot;

    @FXML
	Button playButton;

    @FXML
	Button rulesButton;
    
    public void initialize() {
    	
    	//*
    	
    	//initialize Rectangle
    	int sizeRectangle = 40;
    	Rectangle runningRectangle = new Rectangle();
    	runningRectangle.setId("runningRectangle");
    	runningRectangle.setWidth(sizeRectangle);
    	runningRectangle.setHeight(sizeRectangle);
    	runningRectangle.setFill(Color.GOLD);
    	runningRectangle.setStroke(Color.BLACK);
    	anchorPaneRoot.getChildren().add(runningRectangle);
    	//scene.getC().add(runningRectangle);
    	
    	//Transition
    	PathTransition pathTransition = new PathTransition();
    	
    	//Transition setup
    	pathTransition.setDuration(Duration.seconds(5));
//    	pathTransition.setAutoReverse(true);
    	pathTransition.setCycleCount(Transition.INDEFINITE);
    	pathTransition.setNode(runningRectangle);
    	
    	//set Path
    	Path path = new Path();
    	path.getElements().add(new MoveTo(sizeRectangle*2, sizeRectangle));
    	path.getElements().add(new LineTo(sizeRectangle*2, 400 - sizeRectangle));
    	path.getElements().add(new LineTo(600 - sizeRectangle*2, 400 - sizeRectangle));
    	path.getElements().add(new LineTo(600 - sizeRectangle*2, sizeRectangle));
    	path.getElements().add(new LineTo(sizeRectangle*2, sizeRectangle));

    	pathTransition.setPath(path);
    	
    	// Set the interpolator for linear motion
    	pathTransition.setInterpolator(Interpolator.LINEAR);
    	
    	
    	//start transition
    	pathTransition.play();
    	
    	//*/
    	
//    	Scene scene = new Scene(anchorPaneRoot);
    	
    }
    
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
