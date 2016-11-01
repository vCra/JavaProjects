package uk.ac.aber.dcs.users.aaw13.bonksandzaps.main;
import java.util.LinkedList;

import uk.ac.aber.dcs.users.aaw13.bonksandzaps.except.CannotActException;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.IDTracker;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.Position;
/**
 * Board class - provides a 2d array of rooms  to store creatures, has a general arraylist for all creatures
 *  and keeps track of the numbers of objects on the board using an IDTracker
 * 
 * Bonks and Zaps Application
 * Created for the CS12320 main Assignment
 * @author Aaron Walker - aaw13@aber.ac.uk
 *
 */
public class Board {
	private Room[][] world;
	private LinkedList<Creature> creatureList;
	private int countDeadBonks = 0;
	private int countAliveBonks = 0;
	private int countChildBonks =0 ;
	private int countZaps =0 ;
	private final int X;
	private final int Y;
	private IDTracker creatureTracker;
	

	/**
	 * Constructor for board - creates a blank array and populates it with rooms. 
	 */
	public Board(){
		
		X = Application.BOARDX;
		Y = Application.BOARDY;
		creatureTracker = new IDTracker();
		this.world = new Room[X][Y];
		this.creatureList = new LinkedList<Creature>();
		//Note we use values 1-x rather than 0-x
		for (int a = 1; a < X; a++) {
			for (int b = 1; b < Y; b++) {
				this.world[a][b] = new Room();
			}
		}
	}
	
	
	
	/**
	 * Runs all of the act methods of creatures in the creatureList
	 */
	public void actAllCreatures(){
		//for (Creature thing: creatureList){
		int length = creatureList.size();
		for (int i = 0; i < length; i++){
			Creature thing = creatureList.get(i);
			try {
				thing.act();
			} catch (CannotActException e) {
				System.out.println("A thing can not act... :/ (It's probably dead. RIP Thing 2016-2016");
				//e.printStackTrace(); //We don't really want to show the inner workings - especially on non-dev builds
			}
		}
	}



	/**
	 * Gets the number of dead bonks on the board
	 * @return the number of dead bonks as an integer
	 */
	public int getCountDeadBonks() {
		return countDeadBonks;
	}
	
	/**
	 * Gets the number of Alive bonks on the board
	 * @return the number of Alive bonks as an integer
	 */
	public int getCountAliveBonks() {
		return countAliveBonks;
	}

	/**
	 * Gets the number of Child bonks on the board
	 * @return the number of Child bonks as an integer
	 */
	public int getCountChildBonks() {
		return countChildBonks;
	}

	/**
	 * Gets the number of zaps on the board
	 * @return the number of zaps as an integer
	 */
	public int getCountZaps() {
		return countZaps;
	}
	/**
	 * Gets the number of  bonks on the board
	 * @return the number of  bonks as an integer
	 */
	public int getCountBonks(){
		return (getCountAliveBonks() + getCountDeadBonks());
	}
	
	/**
	 * Reduces the alive bonk count by one and increases the dead bonk count by one
	 */
	public void addDeadBonk(){
		this.countDeadBonks ++;
		this.countAliveBonks --;
	}
	
	/**
	 * Increases the count of alive bonks by one 
	 * NOTE: this is not the method to add a bonk to the board - to do this pass a bonk to addCreature
	 */
	public void addAliveBonk(){
		this.countAliveBonks ++;
	}
	
	/**
	 * Increases the count of alive bonks and child bonks by one
	 * NOTE: this is not the method to add a bonk to the board - to do this pass a bonk to addCreature
	 */
	public void addChildBonk(){
		this.countChildBonks ++;
		this.countAliveBonks ++;
		}
	
	/**
	 * Reduces the count of child bonks by one
	 */
	public void growChildBonk(){
		this.countChildBonks--;
		//If we child bonks not to be normal bonks then uncoment the following line
		//this.countAliveBonks++
	}
	
	/**
	 * Increases the count of zaps by one
	 */
	public void addZap(){
		this.countZaps ++;
	}


	/**
	 * Adds a creature to the board and to the creatures array
	 * Requires a Creature and the position to place it
	 * @param b1 The creature to add
	 * @param pos The position at which to add the Creature
	 */
	public void addCreature(Creature b1, Position pos){
		world[pos.getX()][pos.getY()].add(b1);
		b1.setLocation(pos);
		b1.setName(b1.getName()+creatureTracker.getID());
		this.creatureList.add(b1);
		System.out.println("A creature " + b1.getName() + " has been created at position " + b1.getLocation().toString());
	}
	
	/**
	 * Creates multiple bonks at random locations around the board. 
	 * @param numberOfBonksToCreate The amount of bonks to spawn on the board
	 */
	public void createLotsOfBonks(int numberOfBonksToCreate){
		for (int a = 0; a < numberOfBonksToCreate; a++){
			Position p = new Position();
			p.genRandomPos();
			Bonk b = new Bonk(Application.currentRound, p, this);
			this.addCreature(b, p);
		}
	}
	/**
	 * Creates multiple zaps at random locations around the board. 
	 * @param numberOfZapsToCreate The amount of zaps to spawn on the board
	 */
	public void createLotsOfZaps(int numberOfZapsToCreate){
		for (int a = 0; a < numberOfZapsToCreate; a++){
			Position p = new Position();
			p.genRandomPos();
			Zaps b = new Zaps(Application.currentRound, this);
			b.setLocation(p);
			b.setBoard(this);
			this.addCreature(b, p);
		}
	}

	/**
	 * Gets the room at the specified location in the array
	 * @param x the X coordinate of the room
	 * @param y the Y coordinate of the room
	 * @return The room object
	 */
	public Room getRoom(int x, int y){
		return world[x][y];
	}
	
	/**
	 * Gets the room at the specified location in the array
	 * @param pos - A position object storing the location of the room
	 * @return The room object
	 */
	public Room getRoom(Position pos){
		return world[pos.getX()][pos.getY()];
	}
	/**
	 * DO NOT CALL THIS DIRECTLY - rather use creature.move to move a creature around!
	 * Moves a creature around the board
	 * @param creature the creature to move
	 * @param oldPos the old position of the creature
	 * @param newPos the new position of the creature
	 * @see uk.ac.aber.dcs.users.aaw13.bonksandzaps.main.Creature.move
	 */
	public void moveCreature(Creature creature, Position oldPos, Position newPos){
		this.world[oldPos.getX()][oldPos.getY()].remove(creature);
		this.world[newPos.getX()][newPos.getY()].add(creature);
		System.out.println("Creature "+creature.getName() + " has moved from " + oldPos.toString() + " to " + newPos.toString()+".");
	}
	
	/**
	 * returns the contents of a room as a string
	 * @param x the X coordinate of the room
	 * @param y the Y coordinate of the room
	 * @return a String with the contents of the room
	 * @
	 */
	public String roomToString(int x, int y){
		StringBuilder sb = new StringBuilder();
		Room room = getRoom(x,y);
		for (Creature creature: room){
			sb.append(creature.toString());
			sb.append("\n");
		}
		String string = sb.toString();
		return string;
	}
}
