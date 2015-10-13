package AI;

import java.util.ArrayList;

/**
 * Created by Bjornars on 13.10.2015.
 */
public class SearchNode {

    private int emptySize;
    private int nodeValue;
    private int[][] state;

    public SearchNode(int score,ArrayList emptycells,int[][] gridVal){
        this.emptySize = emptycells.size();
        this.nodeValue = score;

        for (int i =0; i<gridVal.length; i++){
            for(int j=0; j<gridVal[i].length; j++){
                this.state = new int[gridVal.length][gridVal[i].length];
                this.state[i][j] = gridVal[i][j];
            }
        }

    }


    public int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
    }

}
