package AI;

import java.util.ArrayList;

/**
 * Created by Bjornars on 13.10.2015.
 */
public class SearchNode {

    private int EmptySize;
    private int NodeValue;
    private int[][] State;

    public SearchNode(int score,ArrayList emptycells,int[][] Gridval){
        this.EmptySize = emptycells.size();
        this.NodeValue = score;

        for (int i =0; i<Gridval.length; i++){
            for(int j=0; j<Gridval[i].length; j++){
                this.State = new int[Gridval.length][Gridval[i].length];
                this.State[i][j] = Gridval[i][j];
            }
        }

    }




    public int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
    }

}
