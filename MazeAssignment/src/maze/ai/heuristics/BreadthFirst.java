package maze.ai.heuristics;

import maze.ai.core.BestFirstHeuristic;
import maze.core.MazeExplorer;

// This 'heuristic' produces breadth first search by always returning 0. The reason is as follows...
// In an A* search, the value of a node n is given by f(n):
//  f(n) = g(n) + h(n)
// Setting h(n) to 0 every time means that this is just:
//  f(n) = g(n)
// where g(n) is the number of steps to reach the node n. This makes the algorithm behave like BFS - shallower nodes are explored first.
public class BreadthFirst implements BestFirstHeuristic<MazeExplorer> {
    public int getHeuristic(MazeExplorer node, MazeExplorer goal) {
        return 0;
    }
}
