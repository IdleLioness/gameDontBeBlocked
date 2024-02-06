package main;

import java.util.List;
import java.util.ArrayList;

public class TetrisModel {
	//----------------------------
	// Attribute
	private int score = 0;
	private int energy = 0;
	
	private int[][] board; // Represents the game board
    private List<TetrisBlock> activeMovingBlocks; // Currently active moving blocks
    private List<TetrisBlock> activeBlocks; // Currently active blocks
    private TetrisBlock player;
    private boolean playerGameOver;
	
	//----------------------------
	// Constructor
	public TetrisModel() {
		// Initialize the game board
        board = new int[8][16]; // Example: 8 columns, 16 rows
        
        // Initialize the board with zeros indicating empty spaces
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
        
        // Initialize other properties as needed
        activeMovingBlocks = new ArrayList<TetrisBlock>(); //Moving Blocks
        activeBlocks = new ArrayList<TetrisBlock>();		//Static Blocks
        player = new TetrisBlock(3, board.length / 2 - 1, board[0].length - 1);
        board[player.getX()][player.getY()] = player.getType();
        
        playerGameOver = false;
	}
	
	//----------------------------
	// Methods
	
	public int[][] getBoard(){
		return board;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public int getPlayerX() {
		return player.getX();
	}
	
	//return average X-position of Falling Blocks
	public int getFallingBlockX() {
		int averageX = 0;
		for (TetrisBlock tb: activeMovingBlocks) {
			averageX += tb.getX();
		}
		averageX = averageX / activeMovingBlocks.size();
		return averageX;
	}
	
	public void addActiveMovingBlocks(List<TetrisBlock> ltb) {
		activeMovingBlocks.addAll(ltb);
		for (TetrisBlock tb : ltb) {
			board[tb.getX()][tb.getY()] = tb.getType();
		}
	}
	
	//check if there are any actively falling blocks
	public boolean noFallingBlocksCheck() {
		return activeMovingBlocks.isEmpty();
	}
	
	//choose id from 0 to 10 to generate block
	public void genFallingBlock(int formID) {
		int centerPosX = (int) board.length / 2 - 1;
		
		TetrisBlock t1 = new TetrisBlock(1, 0, 0);
		TetrisBlock t2 = new TetrisBlock(1, 0, 0);
		TetrisBlock t3 = new TetrisBlock(1, 0, 0);
		TetrisBlock t4 = new TetrisBlock(1, 0, 0);
		
		switch (formID) {
			case 0:
				t1.setPos(centerPosX, 0);
				t2.setPos(centerPosX, 1);
				t3.setPos(centerPosX, 2);
				t4.setPos(centerPosX + 1, 2);
				break;
			case 1:
				t1.setPos(centerPosX + 1, 0);
				t2.setPos(centerPosX + 1, 1);
				t3.setPos(centerPosX + 1, 2);
				t4.setPos(centerPosX, 2);
				break;
			case 2:
				t1.setPos(centerPosX, 0);
				t2.setPos(centerPosX, 1);
				t3.setPos(centerPosX, 2);
				t4.setPos(centerPosX + 1, 0);
				break;
			case 3:
				t1.setPos(centerPosX + 1, 0);
				t2.setPos(centerPosX + 1, 1);
				t3.setPos(centerPosX + 1, 2);
				t4.setPos(centerPosX, 0);
				break;
			case 4:
				t1.setPos(centerPosX, 0);
				t2.setPos(centerPosX, 1);
				t3.setPos(centerPosX + 1, 1);
				t4.setPos(centerPosX + 2, 1);
				break;
			case 5:
				t1.setPos(centerPosX + 2, 0);
				t2.setPos(centerPosX, 1);
				t3.setPos(centerPosX + 1, 1);
				t4.setPos(centerPosX + 2, 1);
				break;
			case 6:
				t1.setPos(centerPosX, 0);
				t2.setPos(centerPosX, 1);
				t3.setPos(centerPosX + 1, 1);
				t4.setPos(centerPosX + 2, 1);
				break;
			case 7:
				t1.setPos(centerPosX, 0);
				t2.setPos(centerPosX + 1, 0);
				t3.setPos(centerPosX + 2, 0);
				t4.setPos(centerPosX + 2, 1);
				break;
			case 8:
				t1.setPos(centerPosX, 0);
				t2.setPos(centerPosX, 1);
				t3.setPos(centerPosX, 2);
				t4.setPos(centerPosX, 3);
				break;
			case 9:
				t1.setPos(centerPosX - 1, 0);
				t2.setPos(centerPosX, 0);
				t3.setPos(centerPosX + 1, 0);
				t4.setPos(centerPosX + 2, 0);
				break;
			case 10:
				t1.setPos(centerPosX, 0);
				t2.setPos(centerPosX + 1, 0);
				t3.setPos(centerPosX, 1);
				t4.setPos(centerPosX + 1, 1);
				break;
		}
		
		activeMovingBlocks.add(t1);
		activeMovingBlocks.add(t2);
		activeMovingBlocks.add(t3);
		activeMovingBlocks.add(t4);
		
		for (TetrisBlock tb : activeMovingBlocks) {
			//write new position of activeMovingBlocks in board
			board[tb.getX()][tb.getY()] = tb.getType();
		}
	}
	
	public void moveBlockLeft() {
		//check if can move left 
		boolean canMove = true;
		for (TetrisBlock tb : activeMovingBlocks) {
			//check border
			if (tb.getX() == 0) {
				canMove = false;
				break;
			}
			
			//check player
			if (tb.getX() - 1 == player.getX() && tb.getY() == player.getY()) {
				canMove = false;
				break;
			}
			
			//check block collision
			for (TetrisBlock tbStatic : activeBlocks) {
				if ((tb.getX() - 1) == tbStatic.getX() && tb.getY() == tbStatic.getY()) {
					canMove = false;
					break;
				}
			}
		}
		
		//move all blocks left if can
		if (canMove) {
			for (TetrisBlock tb : activeMovingBlocks) {
				//empty old moving blocks
				board[tb.getX()][tb.getY()] = 0;
			}
			
			for (TetrisBlock tb : activeMovingBlocks) {
				//move activeMovingBlocks
				tb.moveLeft();
				
				//write new position of activeMovingBlocks in board
				board[tb.getX()][tb.getY()] = tb.getType();
			}
		}
    }

    public void moveBlockRight() {
		//check if can move right
		boolean canMove = true;
		for (TetrisBlock tb : activeMovingBlocks) {
			//check border
			if (tb.getX() == board.length - 1) {
				canMove = false;
				break;
			}
			
			//check player
			if (tb.getX() + 1 == player.getX() && tb.getY() == player.getY()){
				canMove = false;
				break;
			}
			
			//check block collision
			for (TetrisBlock tbStatic : activeBlocks) {
				if ((tb.getX() + 1) == tbStatic.getX()  && tb.getY() == tbStatic.getY()) {
					canMove = false;
					break;
				}
			}
		}
			
		//move all blocks right if can
		if (canMove) {
			for (TetrisBlock tb : activeMovingBlocks) {
				//empty old moving blocks
				board[tb.getX()][tb.getY()] = 0;
			}
			
			for (TetrisBlock tb : activeMovingBlocks) {
				//move activeMovingBlocks
				tb.moveRight();
				
				//write new position of activeMovingBlocks in board
				board[tb.getX()][tb.getY()] = tb.getType();
			}
		}
    }

    public void moveBlockDown() {
    	boolean setInactive = false;
    	
    	//check if player is under
    	for (TetrisBlock tb : activeMovingBlocks) {
    		if (tb.getX() == player.getX() && tb.getY() + 1 == player.getY()) {
    			playerGameOver = true;
    			setInactive = true;
    			break;
    		}
    	}
    	
    	if (!playerGameOver) {
    		//move activeMovingBlocks
        	for (TetrisBlock tb : activeMovingBlocks) {
        		//empty old moving blocks
    			board[tb.getX()][tb.getY()] = 0;
        	}
        	
        	for (TetrisBlock tb : activeMovingBlocks) {
        		tb.moveDown();
    			//write new position of activeMovingBlocks in board
    			board[tb.getX()][tb.getY()] = tb.getType();
    		}
        	
        	//check if on the ground
        	for (TetrisBlock tb : activeMovingBlocks) {
        		//check if on the ground - move to active Blocks
        		if (tb.getY() == board[0].length - 1) {
        			setInactive = true;
        			break;
        		}
        		
        		//check collision
        		for (TetrisBlock tbStatic : activeBlocks) {
    				if (tb.getX() == tbStatic.getX() && (tb.getY() + 1) == tbStatic.getY()) {
    					setInactive = true;
    					break;
    				}
    			}
        	}
    	}
    	
    	//transfer all activeMovingBlocks to activeBlocks, making them static
    	if (setInactive) {
    		for (TetrisBlock tb : activeMovingBlocks) {
    			//change type of Blocks
    			tb.setType(2);
    			//update type of Block in board
    			board[tb.getX()][tb.getY()] = tb.getType();
    			
    			activeBlocks.add(tb);
//    			activeMovingBlocks.remove(tb);
        	}
    		activeMovingBlocks.clear();
    		
    		//update score and energy
    		score += 2;
    		energy++;
    	}
    }
    
    public void movePlayerLeft() {
    	//check if can move left
		boolean canMove = true;
		//check border
		if (player.getX() == 0) {
			canMove = false;
		}
		
		//check block collision (no need for active - they will become instantly static if hit the ground)
		for (TetrisBlock tbStatic : activeBlocks) {
			if ((player.getX() - 1) == tbStatic.getX() && player.getY() == tbStatic.getY()) {
				canMove = false;
				break;
			}
		}
		
		if (canMove) {
			board[player.getX()][player.getY()] = 0;
			player.moveLeft();
			board[player.getX()][player.getY()] = player.getType();
		}		
    }
    
    public void movePlayerRight() {
    	//check if can move right
		boolean canMove = true;
		//check border
		if (player.getX() == board.length - 1) {
			canMove = false;
		}
		
		//check block collision (no need for active - they will become instantly static if hit the ground)
		for (TetrisBlock tbStatic : activeBlocks) {
			if ((player.getX() + 1) == tbStatic.getX() && player.getY() == tbStatic.getY()) {
				canMove = false;
				break;
			}
		}
		
		if (canMove) {
			board[player.getX()][player.getY()] = 0;
			player.moveRight();
			board[player.getX()][player.getY()] = player.getType();
		}		
    }
    
    public void playerSuperPower() {
    	//check if enough energy
    	if (energy >= 7) {
    		//lose 7 energy
    		energy -= 7;
    		
    		//clear static blocks in board
    		for (TetrisBlock tbStatic : activeBlocks) {
    			board[tbStatic.getX()][tbStatic.getY()] = 0;
    			
    			//increase score for each destroyed block
    			score++;
    		}
    		//clear static blocks in list
    		activeBlocks.clear();
    	}
    }
    public boolean gameOverCheck() {
    	//check if gameOver for player in case of falling blocks
    	if (playerGameOver) {
    		return playerGameOver;
    	}
    	
    	//check if board is full
    	for (int column = 0; column < board.length; column++) {
    		if (board[column][2] == 2) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public void visualise() {
		System.out.println("---------------");
		
        for (int row = 0; row < board[0].length; row++) {
        	for (int colomn = 0; colomn < board.length; colomn++) {
        		System.out.print(board[colomn][row] + " ");
        	}       	
        	System.out.println();
        }
        
		System.out.println("---------------");
	}

}
