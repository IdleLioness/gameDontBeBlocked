package main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TetrisModelTest {

	@Test
	void boardTest() {
		//----------------------------
		// Arrange
		System.out.println("Testing creating model");
		TetrisModel tm = new TetrisModel();
		tm.visualise();
		System.out.println();
		
		System.out.println("Testing noFallingBlocksCheck without any falling Blocks");
		System.out.println(tm.noFallingBlocksCheck());
		System.out.println();
		
		System.out.println("Testing adding 1 Tetris Block");
		List<TetrisBlock> activeMovingBlocks = new ArrayList<TetrisBlock>();
		TetrisBlock tb1 = new TetrisBlock(1, 1, 0); 
		activeMovingBlocks.add(tb1);
		tm.addActiveMovingBlocks(activeMovingBlocks);
		tm.visualise();
		System.out.println();
		
		System.out.println("Testing noFallingBlocksCheck with falling Blocks");
		System.out.println(tm.noFallingBlocksCheck());
		System.out.println();
		
		System.out.println("Testing adding more than 1 Tetris Block");
		List<TetrisBlock> activeMovingBlocks2 = new ArrayList<TetrisBlock>();
		TetrisBlock tb2 = new TetrisBlock(1, 2, 0); 
		TetrisBlock tb3 = new TetrisBlock(1, 3, 0); 
		activeMovingBlocks2.add(tb2);
		activeMovingBlocks2.add(tb3);
		tm.addActiveMovingBlocks(activeMovingBlocks2);
		tm.visualise();
		System.out.println();
		
		System.out.println("Testing moving group left");
		tm.moveBlockLeft();
		tm.visualise();
		System.out.println();
		
		System.out.println("Testing moving group left in restricted");
		tm.moveBlockLeft();
		tm.visualise();
		System.out.println();
		
		System.out.println("Testing moving group right in normal, after in restricted");
		tm.moveBlockRight();
		tm.moveBlockRight();
		tm.moveBlockRight();
		tm.moveBlockRight();
		tm.moveBlockRight();
		tm.moveBlockRight();
		tm.visualise();
		System.out.println();
		
		System.out.println("Testing moving group down, after in restricted");
		for (int i = 0; i < 15; i++) {
			tm.moveBlockDown();
		}
		tm.visualise();
		System.out.println();
		
		System.out.println("Testing if there are any falling blocks");
		System.out.println(tm.noFallingBlocksCheck());
		System.out.println();
		
		System.out.println("Testing diffrent forms");
		activeMovingBlocks.clear();
		tm.genFallingBlock(9);
		tm.visualise();
		tm.moveBlockDown();
		tm.visualise();
		System.out.println();
	}
	
	@Test
	void gameOverTest() {
		TetrisModel tm = new TetrisModel();
		List<TetrisBlock> activeMovingBlocks1 = new ArrayList<TetrisBlock>();
		
		for (int i = 0; i < 13; i++) {
			TetrisBlock tb1 = new TetrisBlock(1, 0, i); 
			activeMovingBlocks1.add(tb1);
		}
		
		//testing active moving blocks not triggering gameover
		tm.addActiveMovingBlocks(activeMovingBlocks1);
		assertEquals(tm.gameOverCheck(), false);
		
		//testing static blocks not enough height not triggering gameover
		blocksDown(tm);
		assertEquals(tm.gameOverCheck(), false);
		
		//testing static blocks with enough height not triggering gameover
		activeMovingBlocks1.clear();
		TetrisBlock tb1 = new TetrisBlock(1, 0, 0); 
		TetrisBlock tb2 = new TetrisBlock(1, 0, 1); 
		activeMovingBlocks1.add(tb1);
		activeMovingBlocks1.add(tb2);
		tm.addActiveMovingBlocks(activeMovingBlocks1);
		blocksDown(tm);
		assertEquals(tm.gameOverCheck(), true);
	}
	
	@Test
	void playerTest() {
		//testing initializing of player
		TetrisModel tm = new TetrisModel();
		assertEquals(tm.getPlayerX(), 3);
		
		//testing moving player to the left once
		tm.movePlayerLeft();
		assertEquals(tm.getPlayerX(), 2);
		
		//testing moving player to the left through border (should be impossible)
		tm.movePlayerLeft();
		tm.movePlayerLeft();
		tm.movePlayerLeft();
		assertEquals(tm.getPlayerX(), 0);
		
		//testing moving player to the right once
		tm.movePlayerRight();
		assertEquals(tm.getPlayerX(), 1);
		
		//testing moving player to the right through border (should be impossible)
		tm.movePlayerRight();
		tm.movePlayerRight();
		tm.movePlayerRight();
		tm.movePlayerRight();
		tm.movePlayerRight();
		tm.movePlayerRight();
		tm.movePlayerRight();
		tm.movePlayerRight();
		assertEquals(tm.getPlayerX(), 7);
		
		//testing moving into blocks left and right (blocks are on 0 and 7 x pos)
		tm.movePlayerLeft();
		List<TetrisBlock> activeMovingBlocks1 = new ArrayList<TetrisBlock>();
		TetrisBlock tb1 = new TetrisBlock(1, 0, 0); 
		TetrisBlock tb2 = new TetrisBlock(1, 7, 0); 
		activeMovingBlocks1.add(tb1);
		activeMovingBlocks1.add(tb2);
		tm.addActiveMovingBlocks(activeMovingBlocks1);
		blocksDown(tm);
		assertEquals(tm.getPlayerX(), 6);
		tm.movePlayerRight();
		tm.visualise();
		assertEquals(tm.getPlayerX(), 6);
		tm.movePlayerLeft();
		tm.movePlayerLeft();
		tm.movePlayerLeft();
		tm.movePlayerLeft();
		tm.movePlayerLeft();
		tm.movePlayerLeft();
		assertEquals(tm.getPlayerX(), 1);
		
		//testing gameover before and after player's death
		activeMovingBlocks1.clear();
		TetrisBlock tb3 = new TetrisBlock(1, 1, 0); 
		activeMovingBlocks1.add(tb3);
		tm.addActiveMovingBlocks(activeMovingBlocks1);
		assertEquals(tm.gameOverCheck(), false);
		blocksDown(tm);
		assertEquals(tm.gameOverCheck(), true);
	}	
	
	public void blocksDown(TetrisModel tm) {
		for (int i = 0; i < 20; i++) {
			tm.moveBlockDown();
		}
	}
}
