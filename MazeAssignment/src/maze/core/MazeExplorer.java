package maze.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import maze.ai.core.BestFirstObject;

//represents an explorer collecting treasure
public class MazeExplorer implements BestFirstObject<MazeExplorer> {
	private Maze m; //the maze object
	private MazeCell location; //the current location/state
	private TreeSet<MazeCell> treasureFound;  //list of treasure found so far in the search
	
	//constructor
	public MazeExplorer(Maze m, MazeCell location) {
		this.m = m;
		this.location = location;
		treasureFound = new TreeSet<MazeCell>();
	}
	
	//return the object that represents the current state
	public MazeCell getLocation() {return location;}

	//This method generates and returns the child states
	public ArrayList<MazeExplorer> getSuccessors() {
		//This will contain the child states (the valid neighbours of the current state)
		ArrayList<MazeExplorer> result = new ArrayList<MazeExplorer>();
		
		//if the current location contains treasure then add it to the list of treasures found
		if (m.isTreasure(location)) treasureFound.add(location);
		
		// Add as a successor every adjacent, unblocked neighbour square.
		// Iterate over the children (the neighbours) and check to see if they are a wall or unblocked.
		// If not a wall, then create a new child, 'next', and add the current list of treasures found to it,,
		// and store this in 'result'.
		ArrayList<MazeCell> neighb = m.getNeighbors(location); 
		for (MazeCell mc: neighb) {
			if (!m.blocked(location, mc)) {
				MazeExplorer next = new MazeExplorer(m,mc);
				next.addTreasures(treasureFound);
				result.add(next);
			}
			
		}
        return result;
	}
	
	//This returns the number of treasures found so far. Could be useful for a heuristic.
	public int numTreasures() {
		return treasureFound.size();
	}
	
	//Get the list of treasures. Also could be useful for a heuristic
	public Set<MazeCell> getTreasures() {
		return m.getTreasures();
	}
	
	//Add the collection of treasures to the current set of treasures
	public void addTreasures(Collection<MazeCell> treasures) {
		treasureFound.addAll(treasures);
	}
	
	public String toString() {
		StringBuilder treasures = new StringBuilder();
		for (MazeCell t: treasureFound) {
			treasures.append(";");
			treasures.append(t.toString());
		}
		return "@" + location.toString() + treasures.toString();
	}
	
	@Override
	public int hashCode() {return toString().hashCode();}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MazeExplorer) {
			return achieves((MazeExplorer)other);
		} else {
			return false;
		}
	}

	//The search can stop when this condition is met - i.e. we've reached the goal state having picked up all treasures in the maze
	public boolean achieves(MazeExplorer goal) {
		return this.location.equals(goal.location) && this.treasureFound.equals(goal.treasureFound);
	}

}
