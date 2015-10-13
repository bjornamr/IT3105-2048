package AI;

/**
 * Created by Bjornars on 13.10.2015.
 */

public class MinMax {





    public int minMax(SearchNode node, int depth, boolean maximizing){
        if( depth =0 || node.isTerminal()){
            return node.getHeuristicValue();
        }
        if (maximizing){
            int bestValue = 999999999;
            SearchNode[] children = node.getChildren();
            for(SearchNode child: getMaxChildren()){
                bestValue = Math.max(bestValue, minMax(child, depth - 1, false));

            }
            return bestValue;
        }else{
            int bestValue = 999999999;
            SearchNode[] children = node.getChildren();
            for(SearchNode child: getMinChildren()){
                bestValue = Math.min(bestValue, minMax(child,depth-1,true));

            }
            return bestValue;
        }
        return -1;
    }


    public  SearchNode[] getMinChildren(int[][] State){


    }
}
