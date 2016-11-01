package uk.ac.aber.dcs.users.aaw13.bonksandzaps.main;
import java.util.Random;

import uk.ac.aber.dcs.users.aaw13.bonksandzaps.except.CannotActException;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.Gender;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.Position;
import uk.ac.aber.dcs.users.aaw13.bonksandzaps.utils.State;

/**
 * Bonk.java
 * Provides Bonks - extends from Creature.
 * Bonks and Zaps Application
 * Created for the CS12320 main Assignment
 * @author Aaron Walker - aaw13@aber.ac.uk
 *
 */
public class Bonk extends Creature{
	private Gender gender; //The gender of the bonk as defined in Types.Gender
	private State state; //The current state of the bonk - is it alive or dead
	/**
	 * Constructor for bonks
	 * @param created - the round it was created on
	 * @param pos - the position of the bonk when created
	 * @param board - the board the bonk is on
	 */
	public Bonk(int created, Position pos, Board board){
		super.setCreated(created);
		super.setLocation(pos);
		super.setBoard(board);
		this.state=State.ALIVE;
		this.genRandomGender();
		super.getBoard().addChildBonk();
		super.setName("B");
	}
	

	/**
	 * Returns the object as a string in plain english
	 */
	@Override
	public String toString(){
		String string = "Bonk " + super.getName() + " is a " + this.getGender().toString().toLowerCase() + " that is currently " + this.getState().toString().toLowerCase() + ". It was created on round " + super.getCreated() + ".";
		return string;
	}
	
	//GENDER
	/**
	 * Returns the gender of the bonk
	 * @return Gender - the gender of the bonk
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the Bonk
	 * @param gender - the gender of the bonk - must be of type gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	/**
	 * Generates and assigns the gender of the bonk
	 * Note that this will only set male or female - if they are other genders in Gender.java you may (or may not) want to get them randomly generated here
	 */
	public void genRandomGender(){
		Random rn = new Random();
		if (rn.nextBoolean()){
			setGender(Gender.MALE);
		}
		else{
			setGender(Gender.FEMALE);
		}
	}
	
	//STATE
	/**
	 * Sets the state (Dead or alive) of the bonk
	 * @param state - the state of the bonk of type State
	 */
	public void setState(State state) {
		this.state = state;
	}
	/**
	 * Gets the state (Dead or alive) of the bonk
	 * @return the state of the bonk of type state
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Sets the current state of the bonk to dead
	 */
	public void kill(){
		this.setState(State.DEAD);
		super.getBoard().addDeadBonk();
		//soz
	}

	/**
	 * The main act function of the bonk
	 * @throws a CannotActException if the bonk cannot "Act"
	 * Technically it can always act, its just that in some cases it doesent actually do anything.
	 */
	@Override
	public void act() throws CannotActException {
		boolean hadBaby = false;
		if (this.state == State.ALIVE){
			super.moveRandom();
			if (this.isBaby() == false){ //Are we old enough to be having babies?
				if (Application.currentRound== this.getCreated() +1){
					super.getBoard().growChildBonk();
				}
				if (this.getGender() == Gender.FEMALE){ //Are we able to have a child (dudes cant produce baby bonks - sorry)
					Room currentRoom = super.getBoard().getRoom(super.getLocation()); //Get the current room that the bonk is in
					//for (Creature thing: currentRoom){ //Iterate through all of the creatures in the room
					int length = currentRoom.size();
					for (int i = 0; i < length; i++){
						Creature thing = currentRoom.get(i);
						if (! hadBaby){//we should probably do this before we get the things in the current room
							if (thing instanceof Bonk){ // We want bonks
								if (((Bonk) thing).getGender() == Gender.MALE){ //We want a male bonk
									if (! thing.isBaby()){ //We want a bonk that hasn't just been spawned
										Bonk baby = new Bonk(Application.currentRound, super.getLocation(), super.getBoard());
										baby.setBoard(this.getBoard()); //Add the board to the being
										baby.genRandomGender(); //
										super.getBoard().addCreature(baby, this.getLocation());//Add the baby to the game board
										hadBaby = true;
									}
								}
							}
						}
					}
				}
			}
		}
		else{ //If the bonk is not alive then it can not really do much - I'm going to throw an exception here for the bants
			throw new CannotActException();
		}
	}
}


