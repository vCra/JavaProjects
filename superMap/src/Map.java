import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * @author Chris Loftus 
 * @version 1.0 (25th February 2016)
 * @revision 1.1 by Aaron Walker
 */
public class Map {
	private ArrayList<Settlement> settlements = new ArrayList<Settlement>();
	private ArrayList<Road> roads = new ArrayList<Road>();
	private File roadFile = new File("roads.txt");
	private File settlementsFile = new File("settlements.txt");
	public Map() {

	}
	/**
	 * In this version we display the result of calling toString on the command
	 * line. Future versions may display graphically
	 */
	public void display() {
		System.out.println(toString());
	}
	
	/**
	 * Adds a settlement into the settlements array
	 * @param newSettlement the Settlement object
	 * @throws IllegalArgumentException
	 */
	public void addSettlement(Settlement newSettlement) throws IllegalArgumentException {
		settlements.add(newSettlement);
	}
	
	/**
	 * Deletes a settlement object in settlements
	 * This will also delete any roads that lead to the destination
	 * @param index the index of the array where the item is
	 */
	public void deleteSettlement(int index){
		Settlement s = settlements.get(index);
		settlements.remove(index);
		for (Road road: s.getAllRoads()){
			this.deleteRoad(road);
		}
		
	}
	/**
	 * Searches for a road object by looking for matching settlements
	 * The order of the settlemets does not matter
	 * @param set1 Any settlment in the road
	 * @param set2 Ant Settlement in the road
	 * @return Road the road found, or null if not found
	 */
	public Road getRoad(Settlement set1, Settlement set2){
		for (Road road: roads){
			if (road.getDestinationSettlement().equals(set1) || road.getSourceSettlement().equals(set1)){
				if (road.getDestinationSettlement().equals(set2) || road.getSourceSettlement().equals(set2)){
					return road;
				}
			}
		}
		return null;
	}
	/**
	 * Gets a settlement by ID
	 * @param id the array location of the settlement
	 * @return Settlement the settlement
	 */
	public Settlement getSettlement(int id){
		return settlements.get(id);
	}
	
	/**
	 * Gets a settlement by name
	 * @param nm the name of the settlement
	 * @return the settlement object, or null if no settlemt is found
	 */
	public Settlement getSettlement(String nm){
		//Iterate over all the settlements in the arraylist in order to try and find the correct settlement
		for (Settlement settlement : settlements){
			if (settlement.getName().equals(nm)){
				return settlement;
			}
		}
		return null;
	}
	
	/**
	 * gets the index of a Settlemnt by the settlement name
	 * @param nm the name of the settlement
	 * @return id the array index of the Settlement
	 */
	public int getSettlementIndexByName(String nm){
		for (Settlement settlement : settlements){
			if (settlement.getName().equals(nm)){
				return settlements.indexOf(settlement);
			}
		}
		return -1;
	}
	
	/**
	 * Lists all of the settlements to console
	 */
	public void listSettlements(){

		for (Settlement settlement : settlements){	
			System.out.println(String.format("%04d", settlements.lastIndexOf(settlement)) + " - " + settlement.toString());
		}
	}
	
	/**
	 * checks if a settlement exists by array index
	 * @param id the array index
	 * @return true if the settlement exists, otherwise false
	 */
	public boolean settlementExists(int id){
		try {
		    settlements.get( id );
		    return true;
		} catch ( IndexOutOfBoundsException e ) {
		    return false;
		}
	}
	

	
	/**
	 * checks if a settlement exists by settlement name
	 * @param name the name of the settlement
	 * @return true if the settlement exists
	 */
	public boolean settlementExists(String name){
		    for (Settlement settlement: settlements){
		    	if (settlement.getName().equals(name)){
		    		return true;
		    	}
		    }
		    return false;
		}
	
	/**
	 * checks if a road exists by name
	 * @param name the name of the road
	 * @return true if the road exists
	 */
	public boolean roadExists(String name){
	    for (Road road: roads){
	    	if (road.getName().equals(name)){
	    		return true;
	    	}
	    }
	    return false;
	}
	
	/**
	 * Adds a road to the roads arrayList
	 * @param newRoad the road to add
	 * @throws IllegalArgumentException if the Road is not type Road
	 */
	public void addRoad(Road newRoad) throws IllegalArgumentException{
		
			roads.add(newRoad);
		
	}
	
	/**
	 * Deletes a road from roads
	 * note that the object may exist in other arrays too
	 * @param road the road to remove
	 */
	public void deleteRoad(Road road){
		road.getDestinationSettlement().delete(road);
		road.getSourceSettlement().delete(road);
		roads.remove(road);
	}
	
	/**
	 * returns a road by the roads name
	 * @param nm the name of the road
	 * @return the road
	 */
	public Road getRoad(String nm){
		//Iterate over all the settlements in the arraylist in order to try and find the correct settlement
		for (Road road : roads){
			if (road.getName().equals(nm)){
				return road;
			}
		}
		return null;
	}
	
	/** returns an arraylist of all the roads in map
	 * 
	 * @return ArrayLsist<Road> an array of roads
	 */
	public ArrayList<Road> getAllRoads() {
		// Notice how we create a separate array list object
		// and return that instead of the roads. This is so
		// that we don't break encapsulation and data hiding.
		// If I returned roads, then the calling code could change
		// change it directly which would be dangerous
		ArrayList<Road> result = new ArrayList<>();
		for(Road rd: roads){
			result.add(rd);
		}
		return result;
	}
	/**
	 * calls the loaders for each file
	 */
	public void load() {
		try {
			loadSettlements();
			loadRoads();
			System.out.println("Map Loaded!\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Loads the settlemnts from the file and adds it into the Settlements array
	 * @throws FileNotFoundException
	 */
	private void loadSettlements() throws FileNotFoundException{
		Scanner infile = new Scanner(new FileReader(this.settlementsFile));
		infile.useDelimiter(":|\r?\n|\r");
		int num = infile.nextInt();
		for (int i = 0;i < num; i++) {
			String n = infile.next();
			String p = infile.next();
			String t = infile.next();
			
			Settlement s = new Settlement(n, SettlementType.valueOf(t));
			s.setPopulation(Integer.parseInt(p));
			settlements.add(s);
		}
		infile.close();
	}
	/**
	 * Loads the roads from the file and adds it to the Roads array
	 * @throws FileNotFoundException
	 */
	private void loadRoads() throws FileNotFoundException{
		Scanner infile = new Scanner(new FileReader(this.roadFile));
		infile.useDelimiter(":|\r?\n|\r");
		int num = infile.nextInt();
		for (int i = 0;i < num; i++) {
			String n = infile.next();
			String c = infile.next();
			String l = infile.next();
			String s = infile.next();
			String d = infile.next();
			
			Road r = new Road(n, Classification.valueOf(c), this.getSettlement(s), this.getSettlement(d), Double.parseDouble(l));
			roads.add(r);
		}
		infile.close();
	}
	
	/**
	 * Saves the settlement to the settlements.txt file
	 * 
	 * file location is defined in maps.settlementsFile
	 * @throws IOException
	 */
	private void saveSettlements() throws IOException{
		PrintWriter outfile = new PrintWriter(new FileWriter(this.settlementsFile));
		outfile.println(settlements.size());
		for (Settlement s:settlements) {
			outfile.println(s.toFileString());
		}
		outfile.close();
	}
	/** saves the roads from the array to roads.txt
	 * file location is defined in maps.roadFile
	 * @throws IOException
	 */
	private void saveRoads() throws IOException{
		PrintWriter outfile = new PrintWriter(new FileWriter(this.roadFile));
		outfile.println(roads.size());
		for (Road r:roads) {
			outfile.println(r.toFileString());
		}
		outfile.close();
	}
	
	/**
	 * calls the save methods for each file
	 */
	public void save() {
		try {
			this.saveRoads();
			this.saveSettlements();
			System.out.println("Map Saved!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * creates a string with all of the settlements and roads linking the settlements
	 */
	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("Map Settlements: \n");
		for (Settlement set: settlements){
			sBuilder.append(set.toString());
			sBuilder.append("\n");
			sBuilder.append("Roads: \n");
			for (Road road: set.getAllRoads()){
				sBuilder.append(road.toString());
				sBuilder.append("\n");
			}
		}
		sBuilder.append("\nAll Roads: \n");
		for (Road road: this.getAllRoads()){
			sBuilder.append(road.toString());
			sBuilder.append("\n");
		}
		String result = sBuilder.toString();
		return result;
	}
}
