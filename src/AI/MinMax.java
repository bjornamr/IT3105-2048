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


    public MinMax(Game game) {
        this.game = game;
    }

    public void start(SearchNode startNode, int depth) {
        int moves = 0;
        SearchNode currentNode = startNode;
        int currentDepth = depth;
        Date time = new Date();
        while (true) {
            try {
                //Thread.sleep(300);
            } catch (Exception e) {

            }
            long oldTime = time.getTime();

            //currentBestValue = minMax(currentNode, depth, true).getNode();'
            /*int emptyTiles = game.getEmptyTiles(game.getBoardValues()).size();
            if(emptyTiles>5){
                currentDepth=depth;
            }else{
                currentDepth = depth+2;
            }*/
            ReturnValue ret = null;
            /*ArrayList<SearchNode> startingMoves = getMaxChildren(currentNode);
            if (startingMoves.size() == 4) {
                TreeThread thread1 = new TreeThread(startingMoves.get(0), currentDepth, false, );
                TreeThread thread2 = new TreeThread(startingMoves.get(1), currentDepth, false, game);
                TreeThread thread3 = new TreeThread(startingMoves.get(2), currentDepth, false, game);
                TreeThread thread4 = new TreeThread(startingMoves.get(3), currentDepth, false, game);

                thread1.start();
                thread2.start();
                thread3.start();
                thread4.start();

                try {
                    thread1.join();
                    thread2.join();
                    thread3.join();
                    thread4.join();
                } catch (InterruptedException e) {
                    System.out.println("EXCEPTION");
                }
                ret = thread1.getValues();
                if (thread2.getValues().getHeuristicValue() > ret.getHeuristicValue()) {
                    ret = thread2.getValues();
                }
                if (thread3.getValues().getHeuristicValue() > ret.getHeuristicValue()) {
                    ret = thread3.getValues();
                }
                if (thread4.getValues().getHeuristicValue() > ret.getHeuristicValue()) {
                    ret = thread4.getValues();
                }

            } else if (startingMoves.size() == 3) {
                TreeThread thread1 = new TreeThread(startingMoves.get(0), currentDepth, false, game);
                TreeThread thread2 = new TreeThread(startingMoves.get(1), currentDepth, false, game);
                TreeThread thread3 = new TreeThread(startingMoves.get(2), currentDepth, false, game);

                thread1.start();
                thread2.start();
                thread3.start();

                try {
                    thread1.join();
                    thread2.join();
                    thread3.join();
                } catch (InterruptedException e) {
                    System.out.println("EXCEPTION");
                }
                ret = thread1.getValues();
                if (thread2.getValues().getHeuristicValue() > ret.getHeuristicValue()) {
                    ret = thread2.getValues();
                }
                if (thread3.getValues().getHeuristicValue() > ret.getHeuristicValue()) {
                    ret = thread3.getValues();
                }

            }else if(startingMoves.size() == 2){
                TreeThread thread1 = new TreeThread(startingMoves.get(0), currentDepth, false, game);
                TreeThread thread2 = new TreeThread(startingMoves.get(1), currentDepth, false, game);

                thread1.start();
                thread2.start();

                try {
                    thread1.join();
                    thread2.join();
                } catch (InterruptedException e) {
                    System.out.println("EXCEPTION");
                }
                ret = thread1.getValues();
                if (thread2.getValues().getHeuristicValue() > ret.getHeuristicValue()) {
                    ret = thread2.getValues();
                }

            }else if(startingMoves.size() == 1){
                TreeThread thread1 = new TreeThread(startingMoves.get(0), currentDepth, false, game);

                thread1.start();

                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    System.out.println("EXCEPTION");
                }
                ret = thread1.getValues();

            }else{
                System.out.println("GAME OVER");
                System.out.println("TOTAL MOVES: " + moves);
                return;
            }*/

            ret = expectimax(currentNode, currentDepth, true);
            //ReturnValue ret = expectimax(currentNode,currentDepth,true);
            time = new Date();
            long currentTime = time.getTime() - oldTime;
            System.out.println("The AI used " + (currentTime / 1000) + " seconds to think");

            if (ret == null) {
                System.out.println("GAME OVER, total moves: " + moves);

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
            if(new SearchNode(node,0).isGameOver()){
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
                return ret;

            } else {
                double bestScore = 0;
                ReturnValue ret = null;

                for(SearchNode child : getMinChildren(node)){
                    double nodeValue = expectimax(child, depth-1, true).getHeuristicValue();
                    double probability = 0.9;
                    if(child.getMovement() == 4){
                        probability = 0.1;
                    }
                    nodeValue = nodeValue * probability;
                    if(nodeValue>bestScore){
                        ret = new ReturnValue(child,nodeValue);
                        bestScore = nodeValue;
                    }
                }
                if(ret==null){
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


    /// Start should be alpha = -inf beta=inf
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
    function expectiminimax(node, depth)
   ## if node is a terminal node or depth = 0
    ##    return the heuristic value of node
    if the adversary is to play at node
        // Return value of minimum-valued child node
        let ? := +?
        foreach child of node
            ? := min(?, expectiminimax(child, depth-1))
    else if we are to play at node
        // Return value of maximum-valued child node
        let ? := -?
        foreach child of node
            ? := max(?, expectiminimax(child, depth-1))
    else if random event at node
        // Return weighted average of all child nodes' values
        let ? := 0
        foreach child of node
            ? := ? + (Probability[child] * expectiminimax(child, depth-1))
    return ?
     */

    public ReturnValue expectimax(SearchNode node, int depth, boolean maximizing) {

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
            if(ret == null){
                return new ReturnValue(node, bestValue);
            }
            return ret;

        } else {
            double bestScore = 0;
            ReturnValue ret = null;
            ArrayList<SearchNode> newStates = getMinChildren(node);
            for(SearchNode child : newStates){
                double nodeValue = expectimax(child, depth-1, true).getHeuristicValue();
                double probability = 0.9;
                if(child.getMovement()==4){
                    probability = 0.1;
                }
                nodeValue = (nodeValue * probability);
                /*if(bestScore>nodeValue) {
                    bestScore = nodeValue;
                    currentBestValue = node;
                }*/
                //if(nodeValue>bestScore){
                    ret = new ReturnValue(child,nodeValue);
                    bestScore += nodeValue;
                //}
            }

            if(newStates.size()==0){
                return new ReturnValue(node, Double.MAX_VALUE);
            }
            bestScore = bestScore/(newStates.size());

            return new ReturnValue(node, bestScore);

        }
    }

    // TODO: test if normal array is faster and check if score is updated
    public ArrayList<SearchNode> getMaxChildren(SearchNode parent) {
        ArrayList<SearchNode> returnArray = new ArrayList();// {new SearchNode(parent, SearchNode.LEFT),new SearchNode(parent,SearchNode.RIGHT), new SearchNode(parent,SearchNode.UP),new SearchNode(parent,SearchNode.DOWN)};
        SearchNode temp = new SearchNode(parent, Game.LEFT);
        temp.setState(game.mergeTilesLeft(temp.getState(), false));
        if (temp.getState().isChanged()) {
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

    public ArrayList getEmptyTiles(int[][] state) {
        return game.getEmptyTiles(state);
    }

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
                temp.getState().addToEmptyTiles(1);
                temp.setMovement(2);
                sn.add(temp);
                temp = new SearchNode(parent);
                temp.setBoard(newArray2);
                temp.setMovement(4);
                temp.getState().addToEmptyTiles(1);
                sn.add(temp);
            }
        }

        return sn;


    }
}

