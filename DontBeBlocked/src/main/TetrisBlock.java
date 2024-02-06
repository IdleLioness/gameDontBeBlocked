package main;

public class TetrisBlock {
	private int type; // Represents the type of block
    private int x; // X position
    private int y; // Y position
    
    public TetrisBlock(int type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
    
    //Setter
    public void setType(int type) {
    	this.type = type;
    }
    
    public void setPos(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    //Getter
    public int getType() {
    	return type;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }
}
