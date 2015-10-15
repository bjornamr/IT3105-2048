package AI;

import java.util.ArrayList;

/**
 * Created by Bjornars on 13.10.2015.
 */



public class SearchNode {

    private int emptySize;
    private int nodeValue;
    private int[][] state;
    private int movement;
    private double[][] weightMatrix;
    private SearchNode parent;


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
        this.parent = null;
        weightMatrix = new double[4][4];
        weightMatrix[0][0] = 0.135759;
        weightMatrix[0][1] = 0.121925;
        weightMatrix[0][2] = 0.102812;
        weightMatrix[0][3] = 0.099937;

        weightMatrix[1][0] = 0.0997992;
        weightMatrix[1][1] = 0.0888405;
        weightMatrix[1][2] = 0.076711;
        weightMatrix[1][3] = 0.0724143;

        weightMatrix[2][0] = 0.060654;
        weightMatrix[2][1] = 0.0562579;
        weightMatrix[2][2] = 0.037116;
        weightMatrix[2][3] = 0.0161889;

        weightMatrix[3][0] = 0.0125498;
        weightMatrix[3][1] = 0.00992495;
        weightMatrix[3][2] = 0.00575871;
        weightMatrix[3][3] = 0.00335193;


    }
    public SearchNode(SearchNode copy, int movement){
        this.movement = movement;
        this.emptySize = copy.emptySize;
        this.nodeValue = copy.nodeValue;

        this.state = new int[copy.state.length][];
        for (int i =0; i<copy.state.length; i++){
            this.state[i] = new int[copy.state[i].length];
            for(int j=0; j<copy.state[i].length; j++){
                this.state[i][j] = copy.state[i][j];
            }
        }
        this.weightMatrix = copy.weightMatrix;
        this.parent = copy;
    }

    public SearchNode(SearchNode copy, int[][] state){
        this.movement = copy.movement;
        this.emptySize = copy.emptySize-1;
        this.nodeValue = copy.nodeValue;
        this.state = state;
        this.weightMatrix = copy.weightMatrix;
        this.parent = copy;
    }

    public int getScore(){
        return nodeValue;
    }


    //https://github.com/datumbox/Game-2048-AI-Solver/blob/master/src/com/datumbox/opensource/ai/AIsolver.java
    private int getClusteringScore() {
        int clusteringScore=0;

        int[] neighbors = {-1,0,1};

        for(int i=0;i<state.length;++i) {
            for(int j=0;j<state.length;++j) {
                if(state[i][j]==0) {
                    continue; //ignore empty cells
                }

                //clusteringScore-=boardArray[i][j];

                //for every pixel find the distance from each neightbors
                int numOfNeighbors=0;
                int sum=0;
                for(int k : neighbors) {
                    int x=i+k;
                    if(x<0 || x>=state.length) {
                        continue;
                    }
                    for(int l : neighbors) {
                        int y = j+l;
                        if(y<0 || y>=state.length) {
                            continue;
                        }

                        if(state[x][y]>0) {
                            ++numOfNeighbors;
                            sum+=Math.abs(state[i][j]-state[x][y]);
                        }

                    }
                }

                clusteringScore+=sum/numOfNeighbors;
            }
        }

        return clusteringScore;
    }

    public int getHeuristicScore() {
        int score = (int) (nodeValue+Math.log(nodeValue)*emptySize -getClusteringScore());
        return Math.max(score, Math.min(nodeValue, 1));
    }

    public int[][] getGridValues(){
        return state;
    }

    public SearchNode getParent(){
        return parent;
    }

    public int getMovement(){
        return movement;
    }
}
