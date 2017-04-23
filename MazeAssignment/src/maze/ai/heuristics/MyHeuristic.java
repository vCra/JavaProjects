package maze.ai.heuristics;
import maze.ai.core.BestFirstHeuristic;
import maze.core.Direction;
import maze.core.MazeExplorer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by awalker on 27/02/2017.
 */
public class MyHeuristic implements BestFirstHeuristic<MazeExplorer>{


    @Override
    public int getHeuristic(MazeExplorer node, MazeExplorer goal) {
        int dx = node.getLocation().X() - goal.getLocation().X();
        int dy = node.getLocation().Y() - goal.getLocation().Y();
        if (goingForward(node)){
            return (int)((Math.abs(dx)+Math.abs(dy))/10);
        }
        else{
            return (Math.abs(dx)+Math.abs(dy));
        }
    }

    private MazeExplorer last(MazeExplorer node){
        ArrayList<MazeExplorer> array = node.getSuccessors();
        int arraySize = array.size();
        return array.get(arraySize-1);
    }
    private boolean goingForward(MazeExplorer node){
        MazeExplorer last = last(node);
        MazeExplorer lastLast = last(node);
        Direction dir = Direction.between(last.getLocation(),lastLast.getLocation());
        Direction cur = Direction.between(node.getLocation(), last.getLocation());
        return dir.equals(cur);
    }
}