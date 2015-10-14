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
        if( depth ==0){
            return node.getHeuristicScore();
        }
        if (maximizing){
            int bestValue = 999999999;
            for(SearchNode child: getMaxChildren(node)) {
                bestValue = Math.max(bestValue, minMax(child, depth - 1, false));

            }
            return bestValue;
        }else{
            int bestValue = 999999999;
           // for(SearchNode child: getMinChildren(node)){
            //    bestValue = Math.min(bestValue, minMax(child,depth-1,true));

           // }
            return bestValue;
        }

    }

    // TODO: test if normal array is faster
    public ArrayList<SearchNode> getMaxChildren(SearchNode parent){
        ArrayList<SearchNode> returnArray = new ArrayList();// {new SearchNode(parent, SearchNode.LEFT),new SearchNode(parent,SearchNode.RIGHT), new SearchNode(parent,SearchNode.UP),new SearchNode(parent,SearchNode.DOWN)};
        SearchNode temp = new SearchNode(parent, SearchNode.LEFT);
        if(game.mergeTilesLeft(temp.getGridValues(),temp.getScore())){ // CHECK this? Does it need to be individual ints for temp.getscore?
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, SearchNode.RIGHT);
        if(game.mergeTilesLeft(temp.getGridValues(),temp.getScore())){
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, SearchNode.UP);
        if(game.mergeTilesLeft(temp.getGridValues(),temp.getScore())){
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, SearchNode.DOWN);
        if(game.mergeTilesLeft(temp.getGridValues(),temp.getScore())){
            returnArray.add(temp);
        }
        return returnArray;

    }

    public ArrayList getEmptyTiles(int[][] state){
        return game.getEmptyTiles(state);
    }
    public ArrayList<SearchNode> getMinChildren(SearchNode parent){
        int[][] gridval = parent.getGridValues();
        ArrayList empty = getEmptyTiles(gridval);
        ArrayList<SearchNode> sn = new ArrayList<>();
        if(empty.size() >0) {
            for (int i = 0; i < empty.size(); i++) {
                int[] xy = game.i1Dto2D((int) empty.get(i));
                int[][] newArray = new int[gridval.length][gridval[0].length]; // fix 0? not fixed size?
                int[][] newArray2 = new int[gridval.length][gridval[0].length];
                for (int j = 0; j < gridval.length; j++) {
                    for (int k = 0; k < gridval[j].length; k++) {
                        newArray[j][k] = gridval[j][k];
                        newArray2[j][k] = gridval[j][k];
                    }
                }
                newArray[xy[0]][xy[1]] = 2;
                newArray2[xy[0]][xy[1]] = 4;


                /// hardcopy score....


                //sn.add(SearchNode(score,empty.size-1,newArray));
                //sn.add(SearchNode(score2,empty.size-1,newArray2));
            }
        }

        return null;



    }
}

