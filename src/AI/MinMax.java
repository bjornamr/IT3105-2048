package AI;

import Game.Game;
import Game.GameState;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.Map;

/**
 * Created by Bjornars on 13.10.2015.
 */

public class MinMax {
    private Game game;
    private SearchNode currentBestValue;
    private int currentDepth;
    private boolean depthChanged = false;

    public MinMax(Game game) {
        this.game = game;
    }
    public void setDepth(int depth){
        this.currentDepth = depth;
    }
    /*
    Starting game and running it.

    The depth is here set dynamicly based on number of empty tiles.
    It is searching using the expectimax.
     */
    public void start(SearchNode startNode, int depth) {
        int moves = 0;
        SearchNode currentNode = startNode;
        setDepth(depth);

        Date time = new Date();
        while (true) {
            depthChanged = false;
            try {
                //Thread.sleep(300);
            } catch (Exception e) {

            }
            long oldTime = time.getTime();
            ReturnValue ret = null;


            if(currentNode.getState().getEmptyTiles() <4){
                setDepth(6);
            }else {
                setDepth(5);
            }
            ret = expectimax(currentNode, currentDepth, true);

            //ReturnValue ret = expectimax(currentNode,currentDepth,true);
            time = new Date();
            long currentTime = time.getTime() - oldTime;
            System.out.println("The AI used " + (currentTime / 1000) + " seconds to think");

            if (ret == null) {
                System.out.println("GAME OVER, total moves: " + moves);
                return;

            }

            currentBestValue = ret.getNode();

                while (currentBestValue.getParent().getParent() != null) {
                    currentBestValue = currentBestValue.getParent();
                }

            //System.out.println("Current movement: " + currentBestValue.getMovement());
            game.movement(currentBestValue.getMovement());
            moves++;
            game.getEmptyTiles(game.getBoardValues()).size();
            currentNode = new SearchNode(game.getScore(), game.getEmptyTiles(game.getBoardValues()), game.getBoardValues());
        }
    }

    public class TreeThread extends Thread {
        private SearchNode startNode;
        private int totalDepth;
        private boolean aiTurn;
        private ReturnValue returnValue;
        private Game game;

        public TreeThread(SearchNode startNode, int totalDepth, boolean aiTurn, Game game) {
            this.startNode = startNode;
            this.totalDepth = totalDepth;
            this.aiTurn = aiTurn;
            this.game = game;

        }

        public ReturnValue expectimax(SearchNode node, int depth, boolean maximizing) {
            if (new SearchNode(node, 0).isGameOver()) {
                return new ReturnValue(node, Double.MIN_VALUE);
            }
            if (depth == 0) {
                return new ReturnValue(node, node.getHeuristicScore(game));
            }
            if (maximizing) {
                double bestValue = Double.MIN_VALUE;
                ReturnValue ret = null;
                for (SearchNode child : getMaxChildren(node)) {
                    double nodeValue = expectimax(child, depth - 1, false).getHeuristicValue();
                    //bestValue = Math.max(bestValue, minMax(child, depth - 1, false));
                    if (bestValue < nodeValue) {

                        bestValue = nodeValue;
                        currentBestValue = child;
                        ret = new ReturnValue(child, nodeValue);
                        //System.out.println(bestValue + " " + currentBestValue.getMovement());
                    }
                }
                if(ret==null){
                }
                return ret;

            } else {
                double bestScore = 0;
                ReturnValue ret = null;

                for (SearchNode child : getMinChildren(node)) {
                    double nodeValue = expectimax(child, depth - 1, true).getHeuristicValue();
                    double probability = 0.9;
                    if (child.getMovement() == 4) {
                        probability = 0.1;
                    }
                    nodeValue = nodeValue * probability;
                    if (nodeValue > bestScore) {
                        ret = new ReturnValue(child, nodeValue);
                        bestScore = nodeValue;
                    }
                }
                if (ret == null) {
                    return new ReturnValue(node, bestScore);
                }


                return ret;

            }
        }

        @Override
        public void run() {
            returnValue = expectimax(startNode, totalDepth, aiTurn);
        }

        public ReturnValue getValues() {
            return returnValue;
        }
    }


    ///
    /*
     Start should be alpha = -inf beta=inf
    Minmax with alpha beta pruning
    Alternates between max and min, where max is used when maximixing(the AI)
     */
    public ReturnValue alphabeta(SearchNode node, int depth, double alpha, double beta, boolean maximizing) {
        if (depth == 0 || new SearchNode(node, 0).isGameOver()) {
            return new ReturnValue(node, node.getHeuristicScore(game));
        }
        if (maximizing) {
            double value = Integer.MIN_VALUE;
            ReturnValue ret = null;
            for (SearchNode child : getMaxChildren(node)) {

                double nodeValue = alphabeta(child, depth - 1, alpha, beta, false).getHeuristicValue();


                if (nodeValue > value) {
                    value = nodeValue;
                    ret = new ReturnValue(child, nodeValue);
                }
                //bestValue = Math.max(bestValue, minMax(child, depth - 1, false));
                if (value >= beta) {
                    currentBestValue = child;
                    return new ReturnValue(child, value);


                }
                //alpha = Math.max(alpha,value);
                if (value > alpha) {
                    alpha = value;
                    ret = new ReturnValue(child, alpha);
                }
            }

            return ret;
        } else {
            double value = Integer.MAX_VALUE;
            ReturnValue ret = null;
            for (SearchNode child : getMinChildren(node)) {
                double nodeValue = alphabeta(child, depth - 1, alpha, beta, true).getHeuristicValue();

                if (nodeValue < value) {
                    value = nodeValue;
                    ret = new ReturnValue(child, nodeValue);
                }
                //bestValue = Math.max(bestValue, minMax(child, depth - 1, false));
                if (value <= alpha) {
                    currentBestValue = child;
                    return new ReturnValue(child, value);

                }
                //beta = Math.min(beta,value);
                if (value < beta) {
                    beta = value;
                    ret = new ReturnValue(child, value);
                }
            }
            if (ret == null) {
                return new ReturnValue(null, value);
            }
            return ret;
        }

    }
    /*
    Minimax without pruning
     */

    public ReturnValue minMax(SearchNode node, int depth, boolean maximizing) {
        if (depth == 0 || new SearchNode(node, 0).isGameOver()) {
            return new ReturnValue(node, node.getHeuristicScore(game));
        }
        if (maximizing) {
            double bestValue = -999999999;
            ReturnValue ret = null;
            for (SearchNode child : getMaxChildren(node)) {
                double nodeValue = minMax(child, depth - 1, false).getHeuristicValue();
                //bestValue = Math.max(bestValue, minMax(child, depth - 1, false));
                if (bestValue < nodeValue) {

                    bestValue = nodeValue;
                    currentBestValue = child;
                    ret = new ReturnValue(child, nodeValue);
                    //System.out.println(bestValue + " " + currentBestValue.getMovement());
                }
            }
            return ret;
        } else {
            double bestValue = 999999999;
            ReturnValue ret = null;
            for (SearchNode child : getMinChildren(node)) {
                double nodeValue = minMax(child, depth - 1, true).getHeuristicValue();
                // bestValue = Math.min(bestValue, minMax(child,depth-1,true));
                if (bestValue > nodeValue) {
                    bestValue = nodeValue;
                    currentBestValue = child;
                    ret = new ReturnValue(child, nodeValue);
                    // System.out.println(bestValue + " " + currentBestValue.getMovement());
                }
            }
            return ret;
        }

    }

    /*
    Expectimax without pruning.

    Output: ReturnValue(SearchNode,heuristic/)
    It can search a number of depth.

    It stops when depth = 0
     */
    public ReturnValue expectimax(SearchNode node, int depth, boolean maximizing) {
        if (depth == 0) {
            return new ReturnValue(node, node.getHeuristicScore(game));
        }
        /*
        Calculates the best heuristic and returns object with best searchnode and heurstic
         */
        if (maximizing) {

            double bestValue = Double.MIN_VALUE;
            ReturnValue ret = null;
            for (SearchNode child : getMaxChildren(node)) {
                ReturnValue temp = expectimax(child, depth - 1, false);
                if (temp == null || temp.getNode() == null) {
                    continue;
                }
                double nodeValue = temp.getHeuristicValue();
                //bestValue = Math.max(bestValue, minMax(child, depth - 1, false));
                if (bestValue < nodeValue) {

                    bestValue = nodeValue;
                    ret = new ReturnValue(child, nodeValue);
                    //System.out.println(bestValue + " " + currentBestValue.getMovement());
                }
            }
            if (ret == null) {
                return new ReturnValue(node, node.getState().getScore() * -1);
            }

            return ret;
            /*
            Calculates the posibility times the heuristic of the state of the board.
            probability is 0.9 for 2 and 0.1 for 4.

            returns the one with highest heurstic *probability.
             */
        } else {

            double bestScore = 0;
            ReturnValue ret = null;
            ArrayList<SearchNode> newStates = getMinChildren(node);

            for (SearchNode child : newStates) {
                ret = expectimax(child, depth - 1, true);
                if (ret == null || ret.getNode() == null) {
                    continue;
                }
                double nodeValue = ret.getHeuristicValue();
                double probability = 0.9;
                if (child.getMovement() == 4) {
                    probability = 0.1;
                }
                nodeValue = (nodeValue * probability);
                /*if(bestScore>nodeValue) {
                    bestScore = nodeValue;
                    currentBestValue = node;
                }*/
                //if(nodeValue>bestScore){
                ret = new ReturnValue(child, nodeValue);
                bestScore += nodeValue;
                //}
            }

            if (newStates.size() == 0) {
                return null;
            }
            bestScore = bestScore / (newStates.size());

            return new ReturnValue(node, bestScore);

        }
    }
    /*
    Checks if two boards is the same.
     */
    public boolean isTheSame(int[][] array1, int[][] array2){
        for(int i = 0;i<array1.length;i++){
            for(int j = 0;j<array1[i].length;j++){
                if(array1[i][j]!=array2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    // TODO: test if normal array is faster and check if score is updated
    /*
    Returns array of Searchnodes that can take left/ right/up/down.
    Just the ones that actually is true.
     */
    public ArrayList<SearchNode> getMaxChildren(SearchNode parent){
        ArrayList<SearchNode> returnArray = new ArrayList();// {new SearchNode(parent, SearchNode.LEFT),new SearchNode(parent,SearchNode.RIGHT), new SearchNode(parent,SearchNode.UP),new SearchNode(parent,SearchNode.DOWN)};
        SearchNode temp = new SearchNode(parent, Game.LEFT);
        int[][] board = new int[0][];
        try {
            board = temp.clone().getState().getBoard();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        temp.setState(game.mergeTilesLeft(temp.getState(), false));
        if (temp.getState().isChanged()) {
            if(isTheSame(board,temp.getState().getBoard())){
                //System.out.println("YESSSSS");
            }
            returnArray.add(temp);
        }

        temp = new SearchNode(parent, Game.RIGHT);
        temp.setState(game.mergeTilesRight(temp.getState(), false));
        if (temp.getState().isChanged()) {
            returnArray.add(temp);
        }

        temp = new SearchNode(parent, Game.UP);
        temp.setState(game.mergeTilesUp(temp.getState(), false));
        if (temp.getState().isChanged()) {
            returnArray.add(temp);
        }
        temp = new SearchNode(parent, Game.DOWN);
        temp.setState(game.mergeTilesDown(temp.getState(), false));
        if (temp.getState().isChanged()) {
            returnArray.add(temp);
        }
        return returnArray;
    }
    /*
    Returns empty tiles of the current state.
     */
    public ArrayList getEmptyTiles(int[][] state) {
        return game.getEmptyTiles(state);
    }
    /*
    Adds 2s and 4s to all empty places and returns arraylist with it.
     */
    public ArrayList<SearchNode> getMinChildren(SearchNode parent) {
        int[][] gridval = parent.getGridValues();
        ArrayList<Integer> empty = getEmptyTiles(gridval);

        ArrayList<SearchNode> sn = new ArrayList<>();
        if (empty.size() > 0) {
            for (int i = 0; i < empty.size(); i++) {

                int[] xy = game.i1Dto2D(empty.get(i));
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
                temp.getState().addToEmptyTiles(-1);
                temp.setMovement(2);
                sn.add(temp);
                temp = new SearchNode(parent);
                temp.setBoard(newArray2);
                temp.setMovement(4);
                temp.getState().addToEmptyTiles(-1);
                sn.add(temp);
            }
        }

        return sn;


    }
}

