
package uk.ac.aber.dcs.users.aaw13.bonksandzaps.main;

/**
 * Bonks and Zaps Application
 * Created for the CS12320 main Assignment
 * @author Aaron Walker - aaw13@aber.ac.uk
 *
 */
public class Application {
	public static final int BOARDX = 21;
	public static final int BOARDY = 21;
	public static final int NUMBEROFROUNDSTORUNFOR = 20;
	public static int currentRound = 1;
	private static Board GameBoard;
	private static long executionTime; 
	private GUI gui;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Application app = new Application();
		GameBoard = new Board();
		
		app.mainLoop();
		
	}
	
	/**
	 * Main Loop - Runs the main game logic
	 *  - Creating the key objects
	 *    - The Gameboard
	 *    - The GUI Interface
	 *  - Creating the initial bonks and Zaps
	 *  - Running the main loop
	 *    - Acting all of the creatures
	 *    - Updating the GUI
	 *  - Keeping track of execution time
	 *  - Keeping track of round numbering
	 */
	private void mainLoop(){
		int delayTime = 1000;
		long startTime;
		gui = new GUI();
		gui.addBoard(GameBoard);

		GameBoard.createLotsOfBonks(20);
		GameBoard.createLotsOfZaps(5);
		//long time; //This was used when our time taken calculator actually worked
		gui.onUpdate();
		//I tried to get threads working, but apparantly java swing is not thread safe
		// For more info see https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html
		/*Executors.newSingleThreadExecutor().execute(new Runnable(){
		    @Override
		    public void run(){
		        while(true){
			            gui.onUpdate();
		        }
		    }
		});*/
		//We changed the execution time calculator when we were experimenting with threads - they is little point in making it work again, it was more of a novelty tbh
		while (currentRound <= Application.NUMBEROFROUNDSTORUNFOR){
			startTime = System.currentTimeMillis();
			GameBoard.actAllCreatures();
			executionTime = System.currentTimeMillis() - startTime;
			while (executionTime < delayTime){
				gui.onUpdate();
				executionTime = System.currentTimeMillis() - startTime;

			}
			currentRound++;
		}
		System.out.println("The program has ended");
	}
	
	/**
	 * Returns the game board object
	 * We really shouldnt be doing this, and rather be passing the board to objects
	 * @return Board the game board object used by the Application
	 */
	public static Board getBoard(){
		return GameBoard;
	}
	/**
	 * Returns the Execution time for the code to run
	 * Does not include the GUI updates or the construction of objects
	 * @return executionTime The Execution time for the bonk logic 
	 */
	public static long getExecutionTime(){
		return executionTime;
	}
	


}
