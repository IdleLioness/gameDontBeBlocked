package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class TetrisController {
	TetrisModel tetrisModel;
	private boolean gameRunning;
    private Thread gameThread;
    private int movementsTetris;
    private boolean gameEnded;
    private boolean gamePaused;
	
    @FXML
    Pane tetrisPane;
    
    @FXML
    Text gameOverTxt;
    
    @FXML
	Text gameRulesTxt;
    
    @FXML
	Text scoreTxt;
    
    @FXML
	Text energyTxt;
    
    @FXML
	Button restartButton;
    
    @FXML
	Button pauseButton;
    
    @FXML
	Button menuButton;
    
    
    public void initialize() {
        // Initialize the TetrisModel
		tetrisModel = new TetrisModel();
		createCells(8*16); //columns * rows
		
		gameRunning = true;
		gameEnded = false;
		gamePaused = false;
		// Start a separate thread for continuous block movement
        gameThread = new Thread(this::runGame);
        gameThread.setDaemon(true); // Set as a daemon thread to stop when the application closes
        gameThread.start();
    }
    
    public void handlePauseButton() {
    	if (!gameRunning == gamePaused) {
    		//change state of game
        	gamePaused = !gamePaused;
        	gameRunning = !gameRunning;
        	
        	if (gamePaused) {
        		pauseButton.setText("Unpause");
        	} else {
        		pauseButton.setText("Pause");
        	}
    	}
    }
    
    public void handleRestartButton() {
    	if (gameThread != null && gameThread.isAlive()) {
    		gameRunning = false;
//    		gameEnded = false;
//    		gameThread.interrupt();
    		
    		tetrisModel = new TetrisModel();
//    		initialize();
    		gameRunning = true;
    		
    		// Start a separate thread for continuous block movement
//          gameThread = new Thread(this::runGame);
//    		gameThread.setDaemon(true); // Set as a daemon thread to stop when the application closes
//    		gameThread.start();
    	} //else if (gameEnded == true) {
    		
    	//}
    	
    }
	
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
    
    //create cells as rectangles
    public void createCells(int number) {
    	int cellSize = 20;
    	int x = 0;
    	int y = 0;
    	
    	for (int current = 0; current < number; current++) {
    		Rectangle rect = new Rectangle();
    		rect.setId("cell" + current);
    		//set size of rect
    		rect.setWidth(cellSize);
    		rect.setHeight(cellSize);
    		
    		//paint rect
    		rect.setFill(Color.GRAY);
    		rect.setStroke(Color.BLACK);
    		rect.setStrokeWidth(1);
    		
    		//Position of x and y in Matrix (8 - columns from TetrisMatrix)
    		x = current % 8;
    		y = current / 8;
    		
    		//Correct Position due to size on Pane
    		//size for Pos, correction of strokes
    		x = cellSize * x + x * 2 + 51;
    		y = cellSize * y + y * 2 + 1;
    		
    		//set Position
    		rect.setX(x);
    		rect.setY(y);
    		
    		//add to Panel
    		tetrisPane.getChildren().add(rect);
    	}
    }
	
    @FXML
    public void onKeyPressed(KeyEvent event) {
    	if (gameRunning) {
    		switch (event.getCode()) {
            case A:
                tetrisModel.movePlayerLeft(); // Move the Player block left in the TetrisModel
                break;
            case D:
                tetrisModel.movePlayerRight(); // Move the Player block right in the TetrisModel
                break;
            case S:
                tetrisModel.playerSuperPower(); // Use player's superpower to destroy universe!!!!
                break;
            // Handle other key presses if needed
            default:
            	break;
    		}
    	}
    	
        // Update the game board and UI based on the movement
        updateGameView();
    }
	
	private void runGame() {
        while (!gameEnded) {

    		//set gameover text invisible if game running
        	//set gameover text visible if game not running
    		gameOverTxt.setVisible(!gameRunning && !gamePaused);
    		
    		//run the game if not gameover
        	if (gameRunning) {
                
            	if (tetrisModel.noFallingBlocksCheck()) {
            		Random rand = new Random();
                    int randomNumber = rand.nextInt(11);
//                    System.out.println("Model Nr: " + randomNumber);
                    tetrisModel.genFallingBlock(randomNumber);
                    movementsTetris = 6;
                } else {
                	if (movementsTetris > 0) {
//                		randomMove();
                		killingMove();
                    	movementsTetris--;
                	}
                	tetrisModel.moveBlockDown(); // Move block down at regular intervals
                }
//            	System.out.println(movementsTetris);
            	gameRunning = !tetrisModel.gameOverCheck();
                
                updateGameView(); // Update the game view/UI after block movement
//                updateGameViewConsole();
                
                try {
                    Thread.sleep(1000 / 4); // Adjust the interval for block movement (0.25 second)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
            	try {
                    Thread.sleep(1000 / 4); // Adjust the interval for block movement (0.25 second)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
		
//		gameEnded = true;
	}
	
	//method to move blocks in direction of player, trying to kill him
	private void killingMove() {
		int averagePosFalBlock = tetrisModel.getFallingBlockX();
		
		//if player is to the left of falling blocks
		//move them to the left, closer to players position
		if (tetrisModel.getPlayerX() < averagePosFalBlock) {
			tetrisModel.moveBlockLeft();
		} 
		//if player is to the right of falling blocks
		//move them to the right, closer to players position
		else if (tetrisModel.getPlayerX() > averagePosFalBlock) {
			tetrisModel.moveBlockRight();
		}
		
		//if player is direct under - do nothing
		
	}
	
	//method to move blocks randomly
	private void randomMove() {
		Random rand = new Random();
        int randomNumber = rand.nextInt(2);
        if (randomNumber == 0) {
        	tetrisModel.moveBlockLeft();
//        	System.out.println("left");
        } else {
        	tetrisModel.moveBlockRight();
//        	System.out.println("right");
        }
	}
	
	// Method to update the game view/UI based on the TetrisModel
    private void updateGameViewConsole() {
        // TODO: Update the game view/UI components based on the TetrisModel
    	tetrisModel.visualise();
    }
    
    private void updateGameView() {
    	int[][] board  = tetrisModel.getBoard();
    	String fxid = "";
    	
    	
    	//for (int row = 0; row < board[0].length; row++) {
    		//for (int column = 0; column < board.length; column++) {
    	for (int row = 0; row < board.length; row++) {
    		for (int column = 0; column < board[0].length; column++) {
    		
    			//find cell with suitable id
    			fxid = "#cell" + (column * 8 + row);
    			Rectangle cell = (Rectangle) tetrisPane.lookup(fxid);
    			
    			//update color
    			//cell.setFill(typeToColor(board[column][row]));
    			cell.setFill(typeToColor(board[row][column]));
    			
    		}
    	}
    	
    	//update score and energy
        scoreTxt.setText("Score: " + tetrisModel.getScore());
        energyTxt.setText("Energy: " + tetrisModel.getEnergy());
    }
    
    private Color typeToColor(int type) {
    	switch (type) {
    		case 0:
    			return Color.GRAY;
    		case 1:
    			return Color.CYAN;
    		case 2:
    			return Color.LIGHTBLUE;
    		case 3:
    			return Color.GOLD;
    		default:
    			return Color.RED; //in case of something very wrong
    	}
    }
}
