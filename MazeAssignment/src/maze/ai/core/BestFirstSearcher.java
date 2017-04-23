package maze.ai.core;
import java.util.*;

// This performs the A* search. A priority queue is used to store the nodes, sorted on the basis of the path cost + heuristic.
public class BestFirstSearcher<T extends BestFirstObject<T>> {
    private ArrayList<T> solution; // the path to the solution
    private int depth, numNodes; // some information on how the search fared - the depth of the solution and the number of nodes explored
    private BestFirstHeuristic<T> h; // the heuristic that is currently being used (selectable via the GUI).
    private boolean solutionFound; // whether a solution has been found or not.
    
    private final static boolean debug = false;
    
    //constructor
    public BestFirstSearcher(BestFirstHeuristic<T> bfh) {
        h = bfh; 
        reset();
    }
    
    //This is the main A* algorithm
    public void solve(T start, T target) {
        reset();
        
        //We need to avoid loops, so a closed/explored list needs to be used that we check against.
        Set<T> explored = new HashSet<T>();
        SearchNode best = new SearchNode(null, start, target);
        PriorityQueue<SearchNode> frontier = new PriorityQueue<SearchNode>();
        frontier.add(best);
        
        solutionFound = false;
        while (frontier.size() > 0 && !solutionFound) {
            best = frontier.poll(); // get the most promising child
            if (debug) {System.out.println("best: " + best.getObject());}
            if (!explored.contains(best.getObject())) { // if we've not encountered this child before add it.
                explored.add(best.getObject());
                if (best.getObject().achieves(target)) {
                    solutionFound = true; // we've reached the goal state (the end of the maze and picked up the treasures)
                } else {
                	addSuccessors(best, frontier, target);
                }
            }
        }
        
        if (solutionFound) {
            reconstructMoves(best); // work out the path for the GUI
        }
    }
    
    // add the children (successors) to the priority queue.
    private void addSuccessors(SearchNode best, Queue<SearchNode> frontier, T target) {
        for (T p: best.getObject().getSuccessors()) {
            numNodes++; // increment the counter
            SearchNode newNode = new SearchNode(best, p, target);
            int h_value = h.getHeuristic(p, target); // get the heuristic for the child, h(n)
            depth = Math.max(newNode.getDepth(), depth); // keeping track of the depth of the search tree to output to the GUI
            newNode.setEval(h_value+newNode.getDepth()); // this is just f(n) = h(n) + g(n), where g(n) is calculated by calling newNode.getDepth()
            frontier.add(newNode);
        }    	
    }
    
    // Reconstruct the path for the GUI to display it
    private void reconstructMoves(SearchNode searcher) { 
        while (searcher != null) {
            solution.add(searcher.getObject());
            searcher = searcher.getParent();
        }
        
        for (int i = 0; i < solution.size() / 2; ++i) {
            T temp = solution.get(i);
            int other = solution.size() - i - 1;
            solution.set(i, solution.get(other));
            solution.set(other, temp);
        }
    }
    
    // A node in the search, with some useful information such as its depth, parent and f(n) evaluation
    private class SearchNode implements Comparable<SearchNode> {
        private int depth;
        private T node;
        private SearchNode parent;
        private int f;
        
        public SearchNode(SearchNode parent, T pNode, T goal) {
            node = pNode;
            this.parent = parent;
            depth = (parent == null) ? 0 : parent.depth + 1;
        }
        
        public void setEval(int f_val) {
        	f = f_val;	
        }
        
        public int getDepth() {return depth;}
        
        public T getObject() {return node;}
        
        public SearchNode getParent() {return parent;}

		@Override
		public int compareTo(SearchNode o) {
			// TODO Auto-generated method stub
			return f-o.f;
		}
    }
    
    private void reset() {
        solution = new ArrayList<T>();
        depth = -1;
        numNodes = 0;
        solutionFound = false;
    }
    
    // Pre: 0 <= n <= getMaxStep(); success()
    public T get(int n) {
        return solution.get(n);
    }
    
    public boolean success() {return solutionFound;}

    // Pre: success()
    public int numSteps() {return solution.size();}
    
    // Pre: success()
    public int getMaxDepth() {return depth;}
    
    // Pre: success()
    public int getNumNodes() {return numNodes;}
    
    // Pre: success()
    public double getBranchingFactor(double maxError) {
        double lo = 0;
        double hi = (double)numNodes / (double)depth;
        double error = 0;
        double bGuess = 0;
        do {
            bGuess = (lo + hi) / 2;
            error = computeError(bGuess);
            if (error > 0) {
                hi = bGuess;
            } else {
                lo = bGuess;
            }
        } while (Math.abs(error) > maxError);
        return bGuess;
    }
    
    private double computeError(double bGuess) {
        double sum = 0;
        for (int d = 1; d <= depth; ++d) {
            sum += Math.pow(bGuess, d);
        }
        return sum - numNodes;
    }
}
