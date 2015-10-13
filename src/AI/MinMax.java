package AI;
import Game.Game;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.ArrayList;

/**
 * Created by Bjornars on 13.10.2015.
 */

public class MinMax {
    Game game;




    public int minMax(SearchNode node, int depth, boolean maximizing){
        if( depth =0 || node.isTerminal()){
            return node.getHeuristicScore();
        }
        if (maximizing){
            int bestValue = 999999999;
            for(SearchNode child: getMaxChildren()){
                bestValue = Math.max(bestValue, minMax(child, depth - 1, false));

            }
            return bestValue;
        }else{
            int bestValue = 999999999;
            for(SearchNode child: getMinChildren(node)){
                bestValue = Math.min(bestValue, minMax(child,depth-1,true));

            }
            return bestValue;
        }
        return -1;
    }

    // TODO: test if normal array is faster
    public ArrayList<SearchNode> getMinChildren(SearchNode parent){
        ArrayList<SearchNode> returnArray = new ArrayList();// {new SearchNode(parent, SearchNode.LEFT),new SearchNode(parent,SearchNode.RIGHT), new SearchNode(parent,SearchNode.UP),new SearchNode(parent,SearchNode.DOWN)};
        SearchNode temp = new SearchNode(parent, SearchNode.LEFT);
        if(game.mergeTilesLeft(temp.getGridValues())){
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, SearchNode.RIGHT);
        if(game.mergeTilesLeft(temp.getGridValues())){
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, SearchNode.UP);
        if(game.mergeTilesLeft(temp.getGridValues())){
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, SearchNode.DOWN);
        if(game.mergeTilesLeft(temp.getGridValues())){
            returnArray.add(temp);
        }
        return returnArray;

    }
    public ArrayList<SearchNode> getMaxChildren(SearchNode parent){

    }
}

