package AI;

import java.util.ArrayList;

/**
 * Created by Bjornars on 13.10.2015.
 */



public class SearchNode {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private int emptySize;
    private int nodeValue;
    private int[][] state;
    private int movement;


    public SearchNode(int score,ArrayList emptycells,int[][] gridVal){
        this.emptySize = emptycells.size();
        this.nodeValue = score;
        this.state = new int[gridVal.length][];
        for (int i =0; i<gridVal.length; i++){
            this.state[i] = new int[gridVal[i].length];
            for(int j=0; j<gridVal[i].length; j++){
                this.state[i][j] = gridVal[i][j];
            }
        }

    }
    public int getScore(){
        return nodeValue;
    }


    public int getClusteringScore(){
        return -1;
    }


    public SearchNode(SearchNode copy, int movement){
        this.movement = movement;
        emptySize = copy.emptySize;
        nodeValue = copy.nodeValue;
        for (int i =0; i<copy.state.length; i++){
            this.state[i] = new int[copy.state[i].length];
            for(int j=0; j<copy.state[i].length; j++){
                this.state[i][j] = copy.state[i][j];
            }
        }
    }

    public int getHeuristicScore() {
        int score = (int) (nodeValue+Math.log(nodeValue)*emptySize -getClusteringScore());
        return Math.max(score, Math.min(nodeValue, 1));
    }

    public int[][] getGridValues(){
        return state;
    }

}
