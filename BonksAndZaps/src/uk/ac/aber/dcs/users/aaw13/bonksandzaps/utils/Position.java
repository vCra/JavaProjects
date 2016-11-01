package uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils;

import java.util.Random;
/**
 * Position
 * Stores X and Y coordinates, as well as providing helper functions
 * 
 * Bonks and Zaps Application
 * Created for the CS12320 main Assignment
 * @author Aaron Walker - aaw13@aber.ac.uk
 *
 */
public class Position {
	public int x;
	public int y;
	
	/**
	 * Constructor for position
	 */
	public Position(){
		x = 0;
		y = 0;
	}
	
	/**
	 * Constructor with preset x and y variables
	 * @param x
	 * @param y
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * gets the x value of the position
	 * @return int the x value
	 */
	public int getX() {
		return x;
	}
	/**
	 * sets the x value of the position
	 * @param x the x value
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 *gets the y value of the position
	 * @return int the y value
	 */
	public int getY() {
		return y;
	}
	/**
	 * Sets the y value of the position
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Prints the position in english
	 */
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	
	/**
	 * Sets the values of position to a random
	 */
	public void genRandomPos(){
		Random rn = new Random();
		int x = rn.nextInt(20) + 1;
		int y = rn.nextInt(20) + 1;
		this.setX(x);
		this.setY(y);
		
	}
	
	/**
	 * Checks if the position is valid
	 * @return true if it is :Ds
	 */
	//NOrmally I would set the 20 as a global variable, but since they are packaged I can't do this.
	public boolean isValid(){
		boolean result = true;
		if (x < 1){
			result = false;
		}
		else if (x > 20){
			result = false;
		}
		else if (y < 1){
			result = false;
		}
		else if (y > 20){
			result = false;
		}
		return result;
	}
	
	
}
