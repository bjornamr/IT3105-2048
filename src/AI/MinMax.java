package AI;
import Game.Game;
import Game.GameState;

import java.util.ArrayList;

/**
 * Created by Bjornars on 13.10.2015.
 */

public class MinMax {
    private Game game;
    private SearchNode currentBestValue;


    public MinMax(Game game){
        this.game = game;
    }

    public void start(SearchNode startNode, int depth){
        SearchNode currentNode = startNode;
        while(true) {
            try{
               // Thread.sleep(2000);
            }catch (Exception e){

            }

            //minMax(currentNode, depth, true);
            alphabeta(currentNode,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,true);
            while(currentBestValue.getParent().getParent() != null){
                currentBestValue = currentBestValue.getParent();
            }
            //System.out.println("Current movement: " + currentBestValue.getMovement());
            game.movement(currentBestValue.getMovement());
            game.getEmptyTiles(game.getBoardValues()).size();
            currentNode = new SearchNode(game.getScore(), game.getEmptyTiles(game.getBoardValues()), game.getBoardValues());
        }
    }

    /// Start should be alpha = -inf beta=inf
    public int alphabeta(SearchNode node, int depth,int alpha, int beta, boolean maximizing){
        if( depth ==0 || new SearchNode(node, 0).isGameOver()){
            return node.getHeuristicScore();
        }
        if (maximizing){
            for(SearchNode child: getMaxChildren(node)) {
                alpha = Math.max(alpha,alphabeta(child, depth-1 , alpha,beta,false));
                //bestValue = Math.max(bestValue, minMax(child, depth - 1, false));
                if(alpha>=beta){
                    currentBestValue = child;
                    return beta;

                }
            }
        }else{
            for(SearchNode child: getMaxChildren(node)) {
                beta = Math.min(beta, alphabeta(child, depth - 1, alpha, beta, true));
                //bestValue = Math.max(bestValue, minMax(child, depth - 1, false));
                if (alpha >= beta) {
                    currentBestValue = child;
                    return alpha;

                }
            }
        }
        return beta;

    }



    public double minMax(SearchNode node, int depth, boolean maximizing){
        if( depth ==0 || new SearchNode(node, 0).isGameOver()){
            return node.getHeuristicScore();
        }
        if (maximizing){
            double bestValue = -999999999;
            for(SearchNode child: getMaxChildren(node)) {
                double nodeValue = minMax(child, depth-1, false);
                //bestValue = Math.max(bestValue, minMax(child, depth - 1, false));
                if(bestValue<nodeValue){

                    bestValue = nodeValue;
                    currentBestValue = child;
                    //System.out.println(bestValue + " " + currentBestValue.getMovement());
                }
            }
            return bestValue;
        }else{
            double bestValue = 999999999;
            for(SearchNode child: getMinChildren(node)){
                double nodeValue = minMax(child, depth-1, true);
               // bestValue = Math.min(bestValue, minMax(child,depth-1,true));
                if(bestValue>nodeValue){
                    bestValue = nodeValue;
                    currentBestValue = child;
                   // System.out.println(bestValue + " " + currentBestValue.getMovement());
                }
            }
            return bestValue;
        }

    }

    // TODO: test if normal array is faster and check if score is updated
    public ArrayList<SearchNode> getMaxChildren(SearchNode parent){
        ArrayList<SearchNode> returnArray = new ArrayList();// {new SearchNode(parent, SearchNode.LEFT),new SearchNode(parent,SearchNode.RIGHT), new SearchNode(parent,SearchNode.UP),new SearchNode(parent,SearchNode.DOWN)};
        SearchNode temp = new SearchNode(parent, Game.LEFT);
        temp.setState(game.mergeTilesLeft(temp.getState(), false));
        if(temp.getState().isChanged()){
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, Game.RIGHT);
        temp.setState(game.mergeTilesRight(temp.getState(), false));
        if(temp.getState().isChanged()){
            returnArray.add(temp);
        }

        temp = new SearchNode(parent, Game.UP);
        temp.setState(game.mergeTilesUp(temp.getState(),false));
        if(temp.getState().isChanged()) {
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, Game.DOWN);
        temp.setState(game.mergeTilesDown(temp.getState(),false));
        if (temp.getState().isChanged()) {
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
                int[][] newArray = new int[gridval.length][]; // fix 0? not fixed size?
                int[][] newArray2 = new int[gridval.length][];
                for (int j = 0; j < gridval.length; j++) {
                    newArray[j] = new int[gridval[j].length];
                    newArray2[j] = new int[gridval[j].length];
                    for (int k = 0; k < gridval[j].length; k++) {
                        newArray[j][k] = gridval[j][k];
                        newArray2[j][k] = gridval[j][k];
                    }
                }
                newArray[xy[0]][xy[1]] = 2;
                newArray2[xy[0]][xy[1]] = 4;


                /// hardcopy score....

                SearchNode temp = new SearchNode(parent);
                temp.setBoard(newArray);
                sn.add(temp);
                temp = new SearchNode(parent);
                temp.setBoard(newArray2);
                sn.add(temp);
            }
        }

        return sn;



    }
}

