package AI;

import Game.CompareTiles;

import java.util.ArrayList;

import Game.GameState;

import java.util.Arrays;
import java.util.Collections;

import Game.Game;

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
    private double[][] weightMatrix5;
    private double[][] weightMatrix6;
    private double[][] weightMatrix7;
    private double[][] weightMatrix8;

    private SearchNode parent;


    public static boolean checker;

    public SearchNode(int score, ArrayList emptycells, int[][] gridVal) {

        int[][] board = new int[gridVal.length][];
        for (int i = 0; i < gridVal.length; i++) {
            board[i] = new int[gridVal[i].length];
            for (int j = 0; j < gridVal[i].length; j++) {
                board[i][j] = gridVal[i][j];
            }
        }
        this.state = new GameState(board, score, false, emptycells.size());
        this.state.setEmptyTiles(emptycells.size());
        this.parent = null;

       /* weightMatrix1 = new double[4][4];
        weightMatrix1[0][0] = 65536;

        weightMatrix1[0][1] = 32768;
        weightMatrix1[1][0] = 32768;

        weightMatrix1[2][0] = 16384;
        weightMatrix1[1][1] = 16384;
        weightMatrix1[0][2] = 16384;

        weightMatrix1[3][0] = 8182;
        weightMatrix1[2][1] = 8192;
        weightMatrix1[1][2] = 8192;
        weightMatrix1[0][3] = 8192;

        weightMatrix1[3][1] = 4096;
        weightMatrix1[2][2] = 4096;
        weightMatrix1[1][3] = 4096;

        weightMatrix1[2][3] = 2048;
        weightMatrix1[2][3] = 2048;

        weightMatrix1[3][3] = 1024;
/*

        weightMatrix1[0][0] = 65536;
        weightMatrix1[0][1] = 32768;
        weightMatrix1[0][2] = 16384;
        weightMatrix1[0][3] = 8192;
        weightMatrix1[0][0] = 65536;
        weightMatrix1[0][1] = 32768;
        weightMatrix1[0][2] = 16384;
        weightMatrix1[0][3] = 8192;
        weightMatrix1[0][0] = 65536;
        weightMatrix1[0][1] = 32768;
        weightMatrix1[0][2] = 16384;
        weightMatrix1[0][3] = 8192;*/


        //MAIN



        weightMatrix1 = new double[4][4];
        weightMatrix1[0][0] = 65536;
        weightMatrix1[0][1] = 32768;
        weightMatrix1[0][3] = 16384;
        weightMatrix1[0][2] = 8192;

        weightMatrix1[1][3] = 4096;
        weightMatrix1[1][2] = 2048;
        weightMatrix1[1][1] = 1024;
        weightMatrix1[1][0] = 512;

        weightMatrix1[2][3] = 256;
        weightMatrix1[2][2] = 128;
        weightMatrix1[2][1] = 64;
        weightMatrix1[2][0] = 32;

        weightMatrix1[3][3] = 16;
        weightMatrix1[3][2] = 8;
        weightMatrix1[3][1] = 4;
        weightMatrix1[3][0] = 2;



        /*weightMatrix1 = new double[4][4];
        weightMatrix1[0][0] = 0.135759*1.7;
        weightMatrix1[0][1] = 0.121925*1.55;
        weightMatrix1[0][2] = 0.102812*1.4;
        weightMatrix1[0][3] = 0.099937*1.3;

        weightMatrix1[1][0] = 0.0997992*1.45;
        weightMatrix1[1][1] = 0.0888405*1.40;
        weightMatrix1[1][2] = 0.076711*1.35;
        weightMatrix1[1][3] = 0.0724143*1.2;

        weightMatrix1[2][0] = 0.060654;
        weightMatrix1[2][1] = 0.0562579;
        weightMatrix1[2][2] = 0.037116;
        weightMatrix1[2][3] = 0.0161889;

        weightMatrix1[3][0] = 0.0125498;
        weightMatrix1[3][1] = 0.00992495;
        weightMatrix1[3][2] = 0.00575871;
        weightMatrix1[3][3] = 0.00335193;
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
        weightMatrix1[3][3] = 4;*/


        weightMatrix2 = rotateArray(weightMatrix1);
        weightMatrix3 = rotateArray(weightMatrix2);
        weightMatrix4 = rotateArray(weightMatrix3);
        weightMatrix5 = rotateArray(weightMatrix4);
        Collections.reverse(Arrays.asList(weightMatrix5));
        weightMatrix6 = rotateArray(weightMatrix1);
        Collections.reverse(Arrays.asList(weightMatrix6));
        weightMatrix7 = rotateArray(weightMatrix2);
        Collections.reverse(Arrays.asList(weightMatrix7));
        weightMatrix8 = rotateArray(weightMatrix3);
        Collections.reverse(Arrays.asList(weightMatrix8));


    }

    public SearchNode(SearchNode copy, int movement) {
        this.movement = movement;

        this.state = copy.state.clone();

        this.weightMatrix1 = copy.weightMatrix1;
        this.weightMatrix2 = copy.weightMatrix2;
        this.weightMatrix3 = copy.weightMatrix3;
        this.weightMatrix4 = copy.weightMatrix4;
        this.weightMatrix5 = copy.weightMatrix5;
        this.weightMatrix6 = copy.weightMatrix6;
        this.weightMatrix7 = copy.weightMatrix7;
        this.weightMatrix8 = copy.weightMatrix8;
        this.parent = copy;
    }

    public SearchNode(SearchNode copy) {
        this.movement = copy.movement;
        this.state = copy.state.clone();

        this.weightMatrix1 = copy.weightMatrix1;
        this.weightMatrix2 = copy.weightMatrix2;
        this.weightMatrix3 = copy.weightMatrix3;
        this.weightMatrix4 = copy.weightMatrix4;
        this.weightMatrix5 = copy.weightMatrix5;
        this.weightMatrix6 = copy.weightMatrix6;
        this.weightMatrix7 = copy.weightMatrix7;
        this.weightMatrix8 = copy.weightMatrix8;
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


    public void setState(GameState newState) {
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


    public double maxValueBoard() {
        int max = 0;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (state.getBoard()[x][y] != 0) {
                    int value = state.getBoard()[x][y];
                    if (value > max) {
                        max = value;
                    }
                }
            }
        }

        return Math.log(max) / Math.log(2);
    }

    double smoothWeight = 0.1;
    double mono2weight = 1.0;
    double emptyWeight = 2.7;
    double maxweight = 1.0;

    public double score() {
        return //getSmoothness()*smoothWeight
                getHeuristicWeightScore() + smoothWeight
                        + getMonotonicity() * mono2weight
                        + Math.log(state.getEmptyTiles()) * emptyWeight
                        + maxValueBoard() * maxweight;
    }

    //  public double getHeuristicScore(Game game){
    //    return getHeuristicWeightScore();
    //}
    public double getHeuristicScore(Game game) {
        //System.out.println("mono: " +getMonotonicityHeuristic());
        // double score = (Math.log(state.getScore())) * state.getEmptyTiles()+getMonotonicity()+(getHeuristicWeightScore()*Math.log(state.getScore()))+getSmoothness();


        //double score = getMonotonicity(3)+getSmoothness()+Math.log(state.getScore())*state.getEmptyTiles();
        //change monopower

        //System.out.println("MONO: " + getMonotonicity(3) +"\nsmooth: "+ getSmoothness() +"\nScore: "+ state.getScore()*0.5);




       /*  double score = ((getHeuristicWeightScore()*Math.log(state.getEmptyTiles()))) +Math.log(state.getScore())*state.getEmptyTiles() - getClusteringScore();

        if(state.getEmptyTiles()<5){
            score*=0.80;
        }
        if (game.isGameOver(state)) {
            score = Double.MIN_VALUE;
        }
        if(game.getScore()>750){
            //System.out.println((getHeuristicWeightScore()*Math.log(state.getEmptyTiles()))+" | " +  Math.log(state.getScore())*state.getEmptyTiles() + " | " + getClusteringScore());
        }*/


        //System.out.println(getHeuristicWeightScore()+ " - " + Math.log(getHeuristicWeightScore() * state.getEmptyTiles()) + " - " + -getClusteringScore());
        //double score = (getHeuristicWeightScore()) + Math.sqrt(state.getScore())*state.getEmptyTiles() - getClusteringScore();
        //double score = getHeuristicWeightScore();
        //if(Double.isInfinite(score)){
        //      score = getHeuristicWeightScore();
        // }
        //if(state.getScore()>256){
        //System.out.println((getHeuristicWeightScore() + " + " + Math.log(state.getScore())*state.getEmptyTiles());
        //}
        //+ (state.getScore()/10)*(state.getEmptyTiles()) -getClusteringScore();
        //double score = getHeuristicWeightScore() + Math.log(state.getScore())*Math.log(getHeuristicWeightScore())*state.getEmptyTiles() - getClusteringScore(); //+ Math.log(getHeuristicWeightScore() * state.getEmptyTiles()) - getClusteringScore();
        //System.out.println(getHeuristicWeightScore() + " + " +  Math.log(getHeuristicWeightScore() * state.getEmptyTiles()) + " - " + getClusteringScore() + " || " + checker);

        //if(state.getScore() > 270){
        //System.out.println(getHeuristicWeightScore()*0.5 + " + " + (Math.log(state.getScore())*state.getEmptyTiles()) + " = " + score);
        //}
        /*f(checker==false) {
            for (int i = 0; i < state.getBoard().length; i++) {
                for (int j = 0; j < state.getBoard()[i].length; j++) {
                    if (state.getBoard()[i][j] >= 512) {

                        score = getHeuristicWeightScore() + Math.log(getHeuristicWeightScore() * state.getEmptyTiles()) - getClusteringScore() * 20;
                        // 20 ble 1024
                        // 200 ble 512
                        checker = true;
                        System.out.println("Checker is now true");
                        return Math.max(score,getScore());
                        //return score;
                       // return Math.max(score,getScore());

                    }

                }
            }
        }
        if(checker){
             score = getHeuristicWeightScore()*state.getEmptyTiles() + Math.log(getHeuristicWeightScore() * state.getEmptyTiles()) - getClusteringScore();

            //System.out.println("score: "+ score*10+ " - " +getScore());
            return Math.max(score,getScore());

            //return score;
           // return Math.max(score, getScore());
        }*/


        //double score = (Math.log(state.getScore())*state.getEmptyTiles())*1.9 - getClusteringScore() + ((Math.log(getHeuristicWeightScore()*5))*10state.getEmptyTiles())) ;
        //System.out.println("MONO: " + getMonotonicity(3));
        // double score = 2000 + (27*state.getEmptyTiles())+(getMonotonicity()*4.7) + (heur_score(1.1)*(-1.5)) + (getSmoothness()*3.5);
        double score = 0;
        double weight = getHeuristicWeightScore() * 2.2;
        if(isHighestValueInCorner()){
            weight *= 1.7;
        }
        double empty = state.getEmptyTiles() *20 * Math.sqrt(getHeuristicWeightScore());
        double clustering = -getClusteringScore() * 1.7;
        double smoothness = getSmoothness() * 2.7;
        //score += weight + empty + clustering;
        score = weight;
        score += empty;
        score += clustering;
        //score = (state.getScore()+Math.log(state.getScore())*state.getEmptyTiles() -getClusteringScore());
        //score = weight;
        if (state.getScore() > 18000) {
            //System.out.println(weight + " + " + (empty*3) + " + " + clustering + " + " + smoothness + " = " + score);
        }

        if (Double.isNaN(score)) {
            score = 0;
        }
        if (game.isGameOver(state)) {
            score -= state.getScore() * 2;
        }
        //double score = getMonotonicity(3);
        //System.out.println((getHeuristicWeightScore()*state.getEmptyTiles())*Math.log(state.getScore()));
        //System.out.println("SCORE: " + score);
        //score+= getSmoothness()*0.1;
        //System.out.println("AFTER: " + score);
        //score+= getHeuristicWeightScore()*1.6;
        //System.out.println("SCORE: " + score + "\nMONO: " + getMonotonicity(3));
        // if(state.getScore()>1000){
        // System.out.println(((state.getScore()/5) + " | " + (Math.log(state.getScore())*state.getEmptyTiles()*1.9)+ " | " + getClusteringScore() + " | " + (Math.log(getHeuristicWeightScore()*5))*10*state.getEmptyTiles()));
        //}


        //System.out.println(" - " +  getClusteringScore()  +" + " + ((getHeuristicWeightScore())*Math.pow(state.getEmptyTiles(),1.5) + " = " + score));


        /*if(Double.isInfinite(score)){
            score = 0;
        }*/


        //return score;

        //return score();

        /*System.out.println("SCORE: " + score);
        if(isHighestValueInCorner()){
           score+=state.getScore()/2;
            System.out.println("AFTER: " + score);
        }*/


        //System.out.println("SCORE: " + score);
        /*double temp = score+getHeuristicScore1();
        if(score+getHeuristicScore2()>temp){
            temp = score+getHeuristicScore2();
        }
        if(score+getHeuristicScore3()>temp){
            temp = score+getHeuristicScore3();
        }
        if(score+getHeuristicScore4()>temp){
            temp = score+getHeuristicScore4();
        }
        //System.out.println("TEMP: " + temp);*/
        // return Math.max(score, Math.min(state.getScore(), 1));
        //return state.getEmptyTiles()*getMonotocityHeuristic()-getClusteringScore();
        if (Double.isInfinite(score)) {
            score = 0;
        }
        return score;
    }

    public double heur_score(double power) {
        int[] sums = new int[4];
        double score = 0;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if (state.getBoard()[i][j] != 0) {
                    sums[i] += (Math.log(state.getBoard()[i][j]) / Math.log(2)) * Math.exp(state.getBoard().length) * Math.exp(state.getBoard().length);
                }
            }
        }

        for (int i = 0; i < state.getBoard().length; i++) {
            score += Math.pow(sums[i], power);
        }
        return score;
    }

    public int getSmoothness() {
        int smooth = 0;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                int x = i;
                int y = j;
                if (isInsideGrid(x - 1, y)) {
                    smooth += Math.abs(state.getBoard()[i][j] - state.getBoard()[x - 1][y]);
                }
                if (isInsideGrid(x + 1, y)) {
                    smooth += Math.abs(state.getBoard()[i][j] - state.getBoard()[x + 1][y]);
                }
                if (isInsideGrid(x, y - 1)) {
                    smooth += Math.abs(state.getBoard()[i][j] - state.getBoard()[x][y - 1]);
                }
                if (isInsideGrid(x, y + 1)) {
                    smooth += Math.abs(state.getBoard()[i][j] - state.getBoard()[x][y + 1]);
                }
            }
        }
        return smooth;
    }

    public boolean isInsideGrid(int x, int y) {
        if (x > -1 && x < state.getBoard().length && y > -1 && y < state.getBoard()[x].length) {
            return true;
        }
        return false;
    }

    public double getMonotonicity() {
        double[] totals = new double[4];

        for (int x = 0; x < 4; x++) {
            int current = 0;
            int next = 1;
            while (next < 4) {
                while (next < 4 && state.getBoard()[x][next] == 0) {
                    next++;
                }
                if (next >= 4) {
                    --next;
                }
                int our_cellx = x;
                int our_celly = current;
                int next_cellx = x;
                int next_celly = next;
                double cur_val = 0;
                if (-1 < our_cellx && our_cellx < state.getBoard().length && -1 < our_celly && our_celly < state.getBoard()[our_cellx].length) {

                    if (state.getBoard()[our_cellx][our_celly] != 0) {
                        cur_val = Math.log(state.getBoard()[our_cellx][our_celly]) / Math.log(2);
                    }
                }
                double next_val = 0;
                if (-1 < next_cellx && next_cellx < state.getBoard().length && -1 < next_celly && next_celly < state.getBoard()[next_celly].length) {

                    if (state.getBoard()[next_cellx][next_celly] != 0) {
                        next_val = Math.log(state.getBoard()[next_cellx][next_celly]) / Math.log(2);

                    }
                }

                if (cur_val > next_val) {
                    totals[0] += next_val - cur_val;
                } else if (next_val > cur_val) {
                    totals[1] += cur_val - next_val;
                }
                current = next;
                next++;
            }
        }

        for (int y = 0; y < 4; y++) {
            int current = 0;
            int next = 1;
            while (next < 4) {
                while (next < 4 && state.getBoard()[next][y] == 0) {
                    next++;
                }
                if (next >= 4) {
                    --next;
                }
                int our_cellx = current;
                int our_celly = y;
                int next_cellx = next;
                int next_celly = y;
                double cur_val = 0;
                if (-1 < our_cellx && our_cellx < state.getBoard().length && -1 < our_celly && our_celly < state.getBoard()[our_cellx].length) {

                    if (state.getBoard()[our_cellx][our_celly] != 0) {
                        cur_val = Math.log(state.getBoard()[our_cellx][our_celly]) / Math.log(2);
                    }
                }
                double next_val = 0;
                if (-1 < next_cellx && next_cellx < state.getBoard().length && -1 < next_celly && next_celly < state.getBoard()[next_celly].length) {

                    if (state.getBoard()[next_cellx][next_celly] != 0) {
                        next_val = Math.log(state.getBoard()[next_cellx][next_celly]) / Math.log(2);

                    }
                }


                if (cur_val > next_val) {
                    totals[2] += next_val - cur_val;
                } else if (next_val > cur_val) {
                    totals[3] += cur_val - next_val;
                }
                current = next;
                next++;
            }
        }
        //System.out.println("TOTALS: " + totals[0] + " , "+ totals[1] + " , "+ totals[2] + " , "+ totals[3]);
        //double r = Math.pow(Math.max(totals[0], totals[1]), power) + Math.pow(Math.max(totals[2], totals[3]), power);
        double r = Math.max(totals[0], totals[1]) + Math.max(totals[2], totals[3]);
        if (Double.isInfinite(r)) {
            System.out.println("NULL");
        }

        return r;
    }


    public boolean isHighestValueInCorner() {
        int largestValueFound = 0;
        int x = -1, y = -1;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if (largestValueFound < state.getBoard()[i][j]) {
                    x = i;
                    y = j;
                }

            }
        }
        if (x == 0 && y == 0 || x == 0 && y == 3 || x == 3 && y == 0 || x == 3 && y == 3) {
            return true;
        }
        return false;
    }

    public double getHeuristicWeightScore() {
        double sum = getWeightedScore(weightMatrix1);//+(state.getEmptyTiles()*state.getScore())-getClusteringScore();
        double newHeuristicScore = getWeightedScore(weightMatrix2);//+(state.getEmptyTiles()*state.getScore())-getClusteringScore();
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        newHeuristicScore = getWeightedScore(weightMatrix3);//+(state.getEmptyTiles()*state.getScore())-getClusteringScore();
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        newHeuristicScore = getWeightedScore(weightMatrix4);//+(state.getEmptyTiles()*state.getScore())-getClusteringScore() ;
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        newHeuristicScore = getWeightedScore(weightMatrix5);//+(state.getEmptyTiles()*state.getScore())-getClusteringScore() ;
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        newHeuristicScore = getWeightedScore(weightMatrix6);//+(state.getEmptyTiles()*state.getScore())-getClusteringScore() ;
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        newHeuristicScore = getWeightedScore(weightMatrix7);//+(state.getEmptyTiles()*state.getScore())-getClusteringScore() ;
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }
        newHeuristicScore = getWeightedScore(weightMatrix8);//+(state.getEmptyTiles()*state.getScore())-getClusteringScore() ;
        if (newHeuristicScore > sum) {
            sum = newHeuristicScore;
        }


        return sum;
    }

    public double getMonotonicityHeuristic() {
        double increasingScore = getMonotonicity(true);
        double descreasingScore = getMonotonicity(false);

        if (increasingScore > descreasingScore) {
            return increasingScore;
        }
        return descreasingScore;

    }



    public double getMonotonicity(boolean increasing) {
        double score = 0;
        int lastValue = 0;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if (increasing) {
                    if (lastValue <= state.getBoard()[i][j]) {
                        lastValue = state.getBoard()[i][j];
                        score += state.getBoard()[i][j];
                    } else {
                        score -= state.getBoard()[i][j];
                    }
                } else {
                    if (lastValue >= state.getBoard()[i][j]) {
                        lastValue = state.getBoard()[i][j];
                        score += state.getBoard()[i][j];
                    } else {
                        score -= state.getBoard()[i][j];
                    }
                }
            }
        }
        lastValue = 0;
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if (increasing) {
                    if (lastValue <= state.getBoard()[j][i]) {
                        lastValue = state.getBoard()[j][i];
                        score += state.getBoard()[j][i];
                    } else {
                        score -= state.getBoard()[j][i];
                    }
                } else {
                    if (lastValue >= state.getBoard()[j][i]) {
                        lastValue = state.getBoard()[j][i];
                        score += state.getBoard()[j][i];
                    } else {
                        score -= state.getBoard()[j][i];
                    }
                }
            }
        }
        return score;
    }

    public double getWeightedScore(double[][] array) {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                sum += (state.getBoard()[i][j] * (array[i][j]) / 10000);

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

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public GameState getState() {
        return state;
    }

    public void setBoard(int[][] newBoard) {
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


    @Override
    protected SearchNode clone() throws CloneNotSupportedException {
        return new SearchNode(this);
    }
}



