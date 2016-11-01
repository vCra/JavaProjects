package uk.ac.aber.dcs.users.aaw13.bonksandzaps.main;

import uk.ac.aber.dcs.users.aaw13.bonksandzaps.except.CannotActException;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.State;
/**
 * Zaps class
 * Zaps basically try and eat/kill all of the bonks
 * they cannot be killed
 * 
 * Bonks and Zaps Application
 * Created for the CS12320 main Assignment
 * @author Aaron Walker - aaw13@aber.ac.uk
 *
 */
public class Zaps extends Creature{

	/**
	 * Constructor for a zap
	 * @param currentRound the zap was created on
	 * @param board  the board the zap is on
	 */
	public Zaps(int currentRound, Board board) {
		super.setBoard(board);
		super.setCreated(currentRound);
		super.setName("Z");
		super.getBoard().addZap();
	}

	@Override
	/**
	 * The act method for the zap
	 * Moves to a random location and then tries to kill everything in the same room
	 * @throws CannotActException - zaps can always act
	 */
	public void act() throws CannotActException {
		super.moveRandom();
		this.tryToKill();
	}
	
	/**
	 * Returns a string representation of a zap.
	 * @return the String stating the properties of a zap
	 */
	@Override
	public String toString(){
		String string = "Zap " + super.getName() + " was created on round " + super.getCreated() + ".";
		return string;

	}
	
	/**
	 * Tries to kill all the bonks in the same room
	 */
	private void tryToKill(){
		Board board = super.getBoard();
		Room currentRoom = board.getRoom(super.getLocation().getX(), super.getLocation().getY());
		for (Creature thing: currentRoom){
			if (thing instanceof Bonk){
				if (((Bonk) thing).getState() == State.ALIVE){ //We can not kill bonks that are already dead
					((Bonk) thing).kill();
				}

			}
		}
	}
}
