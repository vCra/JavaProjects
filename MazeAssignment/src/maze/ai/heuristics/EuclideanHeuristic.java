package maze.ai.heuristics;

import maze.ai.core.BestFirstHeuristic;
import maze.core.MazeExplorer;

//Using the current node object and the goal node object, we can access their coordinates
//and calculate the straight line distance from the current node to the goal
//
//This is an estimate of how much work is left to get to the goal. It's a big underestimate but works!
public class EuclideanHeuristic implements BestFirstHeuristic<MazeExplorer> {
    public int getHeuristic(MazeExplorer node, MazeExplorer goal) {
    	   	
    	double x = node.getLocation().X()-goal.getLocation().X();
    	double y = node.getLocation().Y()-goal.getLocation().Y();
    	
    	return (int)Math.sqrt(x*x + y*y);
    }
}
