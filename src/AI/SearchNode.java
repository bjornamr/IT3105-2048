package AI;

import Game.CompareTiles;

import java.util.ArrayList;
import Game.GameState;
import java.util.Arrays;

/**
 * Created by Bjornars on 13.10.2015.
 */


public class SearchNode {

   // private int[][] state;
    private GameState state;
    private int movement;
    private double[][] weightMatrix1;
    private double[][] weightMatrix2;
    private double[][] weightMatrix3;
    private double[][] weightMatrix4;

    private SearchNode parent;


    public SearchNode(int score, ArrayList emptycells, int[][] gridVal) {

        int[][] board = new int[gridVal.length][];
        for (int i = 0; i < gridVal.length; i++) {
            board[i] = new int[gridVal[i].length];
            for (int j = 0; j < gridVal[i].length; j++) {
                board[i][j] = gridVal[i][j];
            }
        }
        this.state = new GameState(board,score,false, emptycells.size());
        this.state.setEmptyTiles(emptycells.size());
        this.parent = null;
        weightMatrix1 = new double[4][4];
        /*weightMatrix1[0][0] = 0.135759;
        weightMatrix1[0][1] = 0.121925;
        weightMatrix1[0][2] = 0.102812;
        weightMatrix1[0][3] = 0.099937;

        weightMatrix1[1][0] = 0.0997992;
        weightMatrix1[1][1] = 0.0888405;
        weightMatrix1[1][2] = 0.076711;
        weightMatrix1[1][3] = 0.0724143;

        weightMatrix1[2][0] = 0.060654;
        weightMatrix1[2][1] = 0.0562579;
        weightMatrix1[2][2] = 0.037116;
        weightMatrix1[2][3] = 0.0161889;

        weightMatrix1[3][0] = 0.0125498;
        weightMatrix1[3][1] = 0.00992495;
        weightMatrix1[3][2] = 0.00575871;
        weightMatrix1[3][3] = 0.00335193;*/
        weightMatrix1[0][0] = 10;
        weightMatrix1[0][1] = 9;
        weightMatrix1[0][2] = 8;
        weightMatrix1[0][3] = 7;

        weightMatrix1[1][0] = 9;
        weightMatrix1[1][1] = 8;
        weightMatrix1[1][2] = 7;
        weightMatrix1[1][3] = 6;

        weightMatrix1[2][0] = 8;
        weightMatrix1[2][1] = 7;
        weightMatrix1[2][2] = 6;
        weightMatrix1[2][3] = 5;

        weightMatrix1[3][0] = 7;
        weightMatrix1[3][1] = 6;
        weightMatrix1[3][2] = 5;
        weightMatrix1[3][3] = 4;


        weightMatrix2 = rotateArray(weightMatrix1);
        weightMatrix3 = rotateArray(weightMatrix2);
        weightMatrix4 = rotateArray(weightMatrix3);
    }

    public SearchNode(SearchNode copy, int movement) {
        this.movement = movement;

        this.state = copy.state.clone();

        this.weightMatrix1 = copy.weightMatrix1;
        this.weightMatrix2 = copy.weightMatrix2;
        this.weightMatrix3 = copy.weightMatrix3;
        this.weightMatrix4 = copy.weightMatrix4;
        this.parent = copy;
    }

    public SearchNode(SearchNode copy) {
        this.movement = copy.movement;
        this.state = copy.state.clone();

        this.weightMatrix1 = copy.weightMatrix1;
        this.weightMatrix2 = copy.weightMatrix2;
        this.weightMatrix3 = copy.weightMatrix3;
        this.weightMatrix4 = copy.weightMatrix4;
        this.parent = copy;
    }

    public int getScore() {
        return state.getScore();
    }

    public double[][] rotateArray(double[][] array) {
        int M = array.length;
        int N = array[0].length;
        double[][] temp = new double[M][N];
        for (int i = 0; i < array[0].length; i++) {
            for (int j = 0; j < M; j++) {
                temp[j][M - 1 - i] = array[i][j];
            }
        }
        return temp;
    }


    public void setState(GameState newState){
        this.state = newState;
    }



    //https://github.com/datumbox/Game-2048-AI-Solver/blob/master/src/com/datumbox/opensource/ai/AIsolver.java
    private int getClusteringScore() {
        int clusteringScore = 0;

        int[] neighbors = {-1, 0, 1};

        for (int i = 0; i < state.getBoard().length; ++i) {
            for (int j = 0; j < state.getBoard().length; ++j) {
                if (state.getBoard()[i][j] == 0) {
                    continue; //ignore empty cells
                }

                //clusteringScore-=boardArray[i][j];

                //for every pixel find the distance from each neightbors
                int numOfNeighbors = 0;
                int sum = 0;
                for (int k : neighbors) {
                    int x = i + k;
                    if (x < 0 || x >= state.getBoard().length) {
                        continue;
                    }
                    for (int l : neighbors) {
                        int y = j + l;
                        if (y < 0 || y >= state.getBoard().length) {
                            continue;
                        }

                        if (state.getBoard()[x][y] > 0) {
                            ++numOfNeighbors;
                            sum += Math.abs(state.getBoard()[i][j] - state.getBoard()[x][y]);
                        }

                    }
                }

                clusteringScore += sum / numOfNeighbors;
            }
        }
        return clusteringScore;
    }

   /* public int getHeuristicScore() {
        int score = (int) (nodeValue + Math.log(nodeValue) * emptySize - getClusteringScore());
        System.out.println(score);
        return Math.max(score, Math.min(nodeValue, 1));
    }*/

    public double getHeuristicScore() {
        double sum = getHeuristicScore1()+(state.getEmptyTiles()*10)-getClusteringScore() + (state.getScore()*2);
        double newHeuristicScore = getHeuristicScore2()+(state.getEmptyTiles()*10)-getClusteringScore() + (state.getScore()*2);
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        newHeuristicScore = getHeuristicScore3()+(state.getEmptyTiles()*10)-getClusteringScore() + (state.getScore()*2);
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        newHeuristicScore = getHeuristicScore4()+(state.getEmptyTiles()*10)-getClusteringScore() + (state.getScore()*2);
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        return sum;
    }

    public double getHeuristicScore1() {
        double sum = 0;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                sum += (state.getBoard()[i][j] * weightMatrix1[i][j]) * 100;
            }
        }
        //sum += emptySize - getClusteringScore()*2;
        return sum;
    }

    public double getHeuristicScore2() {
        double sum = 0;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                sum += (state.getBoard()[i][j] * weightMatrix2[i][j]) * 100;
            }
        }
        //sum += emptySize - getClusteringScore()*2;
        return sum;
    }

    public double getHeuristicScore3() {
        double sum = 0;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                sum += (state.getBoard()[i][j] * weightMatrix3[i][j]) * 100;
            }
        }
        //sum += emptySize - getClusteringScore()*2;
        return sum;
    }

    public double getHeuristicScore4() {
        double sum = 0;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                sum += (state.getBoard()[i][j] * weightMatrix4[i][j]) * 100;
            }
        }
        //sum += emptySize - getClusteringScore()*2;
        return sum;
    }

    public int[][] getGridValues() {
        return state.getBoard();
    }

    public SearchNode getParent() {
        return parent;
    }

    public int getMovement() {
        return movement;
    }

    public GameState getState(){
        return state;
    }

    public void setBoard(int[][] newBoard){
        state.setBoard(newBoard);
    }

    public boolean isGameOver() {
        if (state.getEmptyTiles() == 0) {
            //TODO: FIX THIS
           /* if (!mergeTilesDown()
                    && !mergeTilesUp()
                    && !mergeTilesLeft()
                    && !mergeTilesRight()) {
                return true;
            }*/
        }
        return false;
    }

    public String toString() {
        String ret = "emptySize : " + state.getEmptyTiles() + " nodeValue : " + state.getScore() + " movement: " + movement;
        for (int i = 0; i < state.getBoard().length; i++) {
            ret += "\n|";
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                ret += " " + state.getBoard()[i][j] + " |";
            }
        }
        return ret;
    }
}
