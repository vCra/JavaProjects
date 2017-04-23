package maze.ai.heuristics;


import maze.ai.core.BestFirstHeuristic;
import maze.core.MazeExplorer;
/**
 * Created by awalker on 27/02/2017.
 */
public class ManhattenHeuristic implements BestFirstHeuristic<MazeExplorer> {

    @Override
    public int getHeuristic(MazeExplorer node, MazeExplorer goal) {
        int dx = node.getLocation().X() - goal.getLocation().X();
        int dy = node.getLocation().Y() - goal.getLocation().Y();
        return (Math.abs(dx)+Math.abs(dy));
    }
}
