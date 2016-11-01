package uk.ac.aber.dcs.users.aaw13.bonksandzaps.main;
import java.util.Random;

import uk.ac.aber.dcs.users.aaw13.bonksandzaps.ifaces.Being;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.Direction;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.Position;

public abstract class Creature implements Being {
	private String name; //The name of the Creature
	private int created; //The round in which the Creature was created
	private Position position; //The position of the Creature
	private Board board; //The board that the Creature is on
	
	///NAMES
	/**
	 * @return name the name of the creature
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the creature
	 * @param name the name of the creature
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Sets which board the creature is on, so it can access properties of that board
	 * @param board The board the creature is on
	 */
	public void setBoard(Board board){
		this.board = board;
	}
	
	/**
	 * Gets the board assigned to the creature
	 * @return the board
	 */
	public Board getBoard(){
		return this.board;
	}
	/**
	 * Moves a Creature to the specified Location
	 * Note that this is not safe, and should be checked to see if a move can be made beforhe
	 * Note it also moves it on the gameboard
	 * @param position The position to move to
	 */
	public void move(Position position ){
		board.moveCreature(this, this.position, position);
		this.setLocation(position);
	}
	
	/**
	 * Moves a creature in a random direction one square away
	 * It may stay in the same position
	 * Directions are all balanced and have the same probability
	 */
	public void moveRandom(){
		boolean moved = false;
		while (! moved){
			int pick = getRandomDirection();
			if (canMove(Direction.values()[pick])){
				move(this.getMoveLocation(Direction.values()[pick]));
				moved = true;
				
			}
		}
	}
	
	/**
	 * Generates a random direction from the values in utils.Direction
	 * @return a random direction
	 */
	private int getRandomDirection() {
		int pick = new Random().nextInt(Direction.values().length);
		return pick;
	}
	
	@Override
	/**
	 * Gets the current location of the creature
	 */
	public Position getLocation() {
		return position;
	}
	

	@Override
	/**
	 * Sets the location of the creature#
	 * this doesent move the creature
	 * @param location the the creature should be set to
	 */
	public void setLocation(Position location){
		if (location.isValid()){
			
			this.position = location;
		}
		else {
			System.out.print("The location is invalid");
		}
	}
	
	/**
	 * Calculates a new Position for a creature to go to
	 * @param direction The direction the creature is going to move
	 * @return A new loction for the creature
	 */
	private Position getMoveLocation(Direction direction){
		Position location = new Position();
		int x = this.getLocation().x;
		int y = this.getLocation().y;

		switch (direction){
			case N: y--; break;
			case E: x++; break;
			case S: y++; break;
			case W: x--; break;
			case NE: y--; x++; break;
			case NW: y--; x--; break;
			case SE: y++; x++; break;
			case SW: y++; x--; break;
			case C: break;
		}
		location.setX(x);
		location.setY(y);
		return location;
	}
	
	/**
	 * Checks if a creature is able to move if it goes in the direction given
	 * Used for boundry checking
	 * @param direction the direction the creature wants to move in
	 * @return If the creature is allowed to move in the specified direction - true if allowed
	 */
	private boolean canMove(Direction direction){
		if ((direction == Direction.N || direction == Direction.NE || direction == Direction.NW) && (this.position.getY() <= 1)){
			return false;
		}
		if ((direction == Direction.E || direction == Direction.NE || direction == Direction.SE) && (this.position.getX() >= 20)){
			return false;
		}
		if ((direction == Direction.S || direction == Direction.SE || direction == Direction.SW) && (this.position.getY() >= 20)){
			return false;
		}
		if ((direction == Direction.W || direction == Direction.NW || direction == Direction.SW) && (this.position.getX() <= 1)){
			return false;
		}
		System.out.println("We can move");

		return true;
	}
	
	
	//CREATED
	/**
	 * @return the round created
	 */
	public int getCreated() {
		return created;
	}
	/**
	 * @param created the round created to set
	 */
	public void setCreated(int created){
		this.created = created;
	}
	
	/**
	 * Checks if the creature was created on the current round
	 * @return True if it was created on the current round, otherwise false
	 */
	public boolean isBaby(){
		if (this.getCreated() == Application.currentRound){
			return true;
		}
		else {
			return false;
		}
		
	}
}
