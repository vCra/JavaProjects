package uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils;

/**
 * IDTracker
 * Created to keep track of creature name numbering
 * Bonks and Zaps Application
 * Created for the CS12320 main Assignment
 * @author Aaron Walker - aaw13@aber.ac.uk
 *
 */
public class IDTracker {
	private int numberOfItems;
	/**
	 * Constructor for the Tracker
	 */
	public IDTracker(){
		numberOfItems = 0;
	}
	/**
	 * Gets a new ID - The ID will be the same as the previous but one higher
	 * @return int a new ID
	 */
	public int getID(){
		return ++ numberOfItems;
	}
	/**
	 * Returns the number of items without incrementing the count
	 * @return
	 */
	public int getNumberOfItems(){
		return numberOfItems;
	}
}
