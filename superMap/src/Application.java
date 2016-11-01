import java.util.Scanner;

/**
 * Main class to test the Road and Settlement classes
 * 
 * @author Chris Loftus (add your name and change version number/date)
 * @version 1.0 (24th February 2016)
 *
 */
public class Application {

	private Scanner scan;
	private Map map;
	public Application() {
		scan = new Scanner(System.in);
		map = new Map();
	}
	
	/**
	 *Main loop that allows user to make choice of what to do in the program
	 *Displays menu using showMenu(), listens for answer and then passes this to runMenuChoice(input from user) 
	 */
	private void runMenu() {
		boolean programEnded = false;
		int choice = 0;
		while (! programEnded){
			showMenu();
			choice = menuToInt(getInput());
			runMenuChoice(choice);
			
		}
	}
		
	/**
	 * Prints the menu to the local console
	 */
	private void showMenu(){
		// Maybe change this so that it returns the text rather than prints it to the console in the future
		System.out.println("#########################");
		System.out.println("#     Super Map v0.1    #");
		System.out.println("#########################");
		System.out.println("");
		System.out.println("1 . Create Settlement");
		System.out.println("2 . Delete Settlement");
		System.out.println("3 . Find Settlement  ");
		System.out.println("4 . List Settlements ");
		System.out.println("5 . Create Road      ");
		System.out.println("6 . Delete Road      ");
		System.out.println("7 . Display Map      ");
		System.out.println("8 . Save             ");
		System.out.println("9 . Load             ");
		System.out.println("10. Settlement Pop.  ");
		System.out.println("99. Quit             ");

		System.out.println("");
		System.out.print("Option: ");
	}
	/**
	 * Runs the method relating to the choice made by the user from the menu
	 * @param choice the choice as an integer
	 */
	private void runMenuChoice(int choice){
		switch (choice) {
		case 1:
			onCreateSettlement();
			break;
		case 2:
			onDeleteSettlement();
			break;
		case 3:
			onFindSettlement();
			break;
		case 4:
			onListSettlement();
			break;
		case 5:
			onCreateRoad();
			break;
		case 6:
			onDeleteRoad();
			break;
		case 7:
			onDisplaymap();
			break;
		case 8:
			onSave();
			break;
		case 9:
			onLoad();
			break;
		case 10:
			onPop();
		case 99:
			onQuit();
			break;
			
		}
	}
	
	/**
	 * Changes the population of a Settlement
	 * Not yet implamented
	 */
	private void onPop() {
		// TODO Auto-generated method stub
		
	}
	/** 
	 * Lists the lettlements in the map
	 */
	private void onListSettlement() {
		map.listSettlements();
		
	}

	
	/**
	 * Attempts to find a settlemen, and prints the ID
	 */
	private void onFindSettlement() {
		// TODO Auto-generated method stub
		System.out.println("Enter the name of the settlement to find: ");
		String settlementName = getInput();
		int settlementID = map.getSettlementIndexByName(settlementName);
		if (settlementID == -1){
			System.out.println("The Settlement can not be found!");
		}
		else{
			System.out.println("Settlement " + settlementName + " has ID " + settlementID);
			System.out.println(map.getSettlement(settlementID).toString());
		}
	}

	/** 
	 * Gets the input from the global Scanner scan and returns this as a string
	 * @return String The string that the user inputs
	 */
	private String getInput(){
		String choice = scan.nextLine();
		return choice;
	}
	
	/** Creates a settlement
	 * 
	 */
	private void onCreateSettlement(){
		System.out.println("Enter the Settlement's name: ");
		String settlementName = getInput();
		if (map.getSettlementIndexByName(settlementName) != -1){
			System.out.println("Settlement Exists - choose another name!");
		}
		else {
			SettlementType settlementType = askForSettlementType();
			Settlement newSettlement = new Settlement(settlementName, settlementType);
			map.addSettlement(newSettlement);
			System.out.println("Settlement Created!");
		}
	}
	
	/** Deletes a settlemnt
	 * 
	 */
	private void onDeleteSettlement(){
		System.out.println("Enter the name of the settlement to delete");
		
		try{
			String settlementName = this.getInput();
			if (map.settlementExists(settlementName)){
				map.deleteSettlement(map.getSettlementIndexByName(settlementName));
				System.out.println("Settlement Deleted!");
			} else {
				System.out.println("Settlement doesent Exist!");

			}
		} catch (NumberFormatException e){
			System.out.println("ID Must be a number");
			
		}
	}

	
	/**
	 * When create road is selected in the menu
	 * Asks the user for the road name, the id of the settlement source and destination
	 *   the classification and the length
	 * Includes error checking
	 * I used named block - I''m sorry if we are not allowed to do this, but it seamed the
	 *   most efficient way of doing it without throwing actual errors - sorry marker person
	 * Turns out I could do return; whoops
	 * Creates a road object, and then adds it to 3 arrays
	 *   the arrayList in map, and in the source and destination
	 *   I disagree with linking it in the settlement classes, but oh well - sorry again
 	 */
	private void onCreateRoad(){
		getData:{
			Settlement source;
			Settlement destination;
			System.out.println("Enter the road name: ");
			String name = this.getInput();
			if(name == null || name.isEmpty()){
				System.out.println("Name can not be Empty!");
				break getData;
			}
			
			Classification classification = askForRoadClassifier();
			
			System.out.println("Enter the name of the source settlement: ");
			String settlementName = this.getInput();
			if (map.settlementExists(settlementName)){
				source = map.getSettlement(settlementName);
			}
				else {
					System.out.println("Settlement does not exist!");
					break getData;
				}

			System.out.println("Enter the name of the destination settlement: ");
			settlementName = this.getInput();
			if (map.settlementExists(settlementName)){
				destination = map.getSettlement(settlementName);
			}
				else {
					System.out.println("Settlement does not exist!");
					break getData;
				}
			for (Road road: map.getAllRoads()){
				if (source.equals(road.getSourceSettlement()) || source.equals(road.getDestinationSettlement())){
					if (destination.equals(road.getSourceSettlement()) || destination.equals(road.getDestinationSettlement())){
						System.out.println("Road between these two locations already exist");
						break getData;
					}
				}
			}
			System.out.println("Enter the length of the road: ");
			double length = Double.parseDouble(this.getInput());
			Road newRoad = new Road(name, classification, source, destination, length);
			map.addRoad(newRoad);
			//source.add(newRoad); //Handled in road 
			//destination.add(newRoad); //as above
		}
	}
	
	/** Attempts to delete a road
	 * 
	 */
	private void onDeleteRoad(){
		System.out.println("Enter the name of one of the settlements connected to the road: ");
		String settlement1Name = this.getInput();
		System.out.println("Enter the name of the other settlement connected to the road: ");
		String settlement2Name = this.getInput();
		Road road = map.getRoad(map.getSettlement(settlement1Name), map.getSettlement(settlement2Name));
		if (road != null){
			map.deleteRoad(road);
			System.out.println("Road Deleted!");
		}
		else {
			System.out.println("Road not found!");
		
			
			
		}
//		if (map.roadExists(roadName)){
//			map.deleteRoad(map.getRoad(roadName));
//			System.out.println("Road Deleted!");
//		}
//		else
//		{
//				System.out.println("Settlement does not exist!");
//		}
	}
	/**
	 * Shows the map
	 */
	private void onDisplaymap(){
		map.display();
	}
	
	/**
	 * Saves the map to a file
	 * the save file is defined in map as a file object
	 */
	private void onSave() {
		map.save();
		
	}

	/** Loads the map from a file.
	 * The file is defined in map as a file object
	 * 
	 * 
	 */
	private void onLoad() {
		try {
			map.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** When the user wants to quit the program
	 * Maybe implament a save prompt, or save automatically
	 */
	private void onQuit() {
		System.out.println("~ Program terminated ~");
		System.exit(0);
	}
	
	/** 
	 * Converts a string to an integer depending on the rules set in this function
	 * Designed to menuToInt(getInput()) but any string which contains a single digit number between 1-7 or a "q" will also work
	 * In the event of an unexpected value will return 0
	 * @return int The Integer of the Menu choice 
	 */
	private int menuToInt(String choice){
		if (choice.toUpperCase().equals("Q")){
			return 99;
		}
		else {
			try {
				return Integer.parseInt(choice);				
			} catch (NumberFormatException e) {
				System.out.println("!!!!!!!!!!!!!!!!!");
				System.out.println("! Not an Option !");
				System.out.println("!!!!!!!!!!!!!!!!!");
				return 0;
			}
		}
	}
	
	/**
	 * Asks the suer for a Road Classifier, and if valid returns it
	 * if not valid, prompts the user again until a valid input is receaved
	 * @return Classification road classifier that the user entered
	 */
	private Classification askForRoadClassifier() {
		Classification result = null;
		boolean valid;
		do{
			valid = false;
			System.out.print("Enter a road classification: ");
			for (Classification cls: Classification.values()){
				System.out.print(cls + " ");
			}
			String choice = getInput().toUpperCase();
			try {
				result = Classification.valueOf(choice);
				valid = true;
			} catch (IllegalArgumentException iae){
				System.out.println(choice + " is not one of the options. Try again.");
			}
		}while(!valid);
		return result;
	}
	
	/**
	 * Like askForRoadClassifier, except it asks for a settlemet type rather than a Road Classifier
	 * if valid, returns a Classification, if not keeps on asking until it is
	 * @return Classification the road type entered by the user
	 */
	private SettlementType askForSettlementType() {
		SettlementType result = null;
		boolean valid;
		do{
			valid = false;
			System.out.print("Enter a Settlement Type: ");
			for (SettlementType settlementtype: SettlementType.values()){
				System.out.print(settlementtype + " ");
			}
			String choice = getInput().toUpperCase();
			try {
				result = SettlementType.valueOf(choice);
				valid = true;
			} catch (IllegalArgumentException iae){
				System.out.println(choice + " is not one of the options. Try again.");
			}
		}while(!valid);
		return result;
	}
	
	/**
	 * loads the map - Don't use
	 */
	private void load(){
		onLoad();
	}
	/**
	 * Saves the map - Dont use
	 */
	private void save(){
		onSave();
	}

	public static void main(String args[]) {
		Application app = new Application();
		app.load();
		app.runMenu();
		app.save();
	}

}
