package maze.ai.core;


public interface BestFirstHeuristic<T extends BestFirstObject<T>> {
    // Pre: node and goal have a defined distance
    // Post: Returns estimate of distance from node to goal (this is the heuristic)
    public int getHeuristic(T node, T goal);
}
