package Game;

/**
 * Created by Bjornars on 09.10.2015.
 */

/*
https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java
 */

import GUI.GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;


public class Game implements MyListener {


    private int score;


    private static final String WIN_GAME = "You WON! Tile is 2048";
    private static final String LOSE_GAME = "You lost. Retry by pressing -R-";
    private static final int UP = 38;
    private static final int DOWN = 40;
    private static final int RIGHT = 39;
    private static final int LEFT = 37;

    private int width;
    private int height;

    private final int GOAL = 2048;

    ArrayList<Integer> emptyTiles = new ArrayList<Integer>();
    Random random = new Random();

    private int[][] gridValues; // Values
    GUI frame;

    public Game(int x, int y) {
        this.width = x;
        this.height = y;

        frame = new GUI(x, y);
        frame.addListener(this);

        this.gridValues = new int[x][y]; // setting size of grid.

        score = 0;
        frame.setSize(800, 800);
        frame.setTileSize(x, y, 200, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //this.Grid = new int[x][y];
        newGame();

    }

    public void newGame(){

        chooseEmptySpot();
        chooseEmptySpot();
        score = 0;

    }

    public void redraw(int[][] Grid) {

        for (int x = 0; x < Grid.length; x++) {
            for (int y = 0; y < Grid[x].length; y++) {
                setTile(x, y, Grid[x][y]);
            }
        }


    }

    public ArrayList getEmptyTiles(int[][] gridValues) {  // getting all the empty tiles
        emptyTiles.clear();
        for (int x = 0; x < gridValues.length; x++) {
            for (int y = 0; y < gridValues[x].length; y++) {
                if (isEmpty(x, y)) emptyTiles.add(i2Dto1D(x, gridValues.length, y));
            }
        }
        return emptyTiles;
    }


    public int i2Dto1D(int x, int rowlength, int y) {
        return (x * gridValues.length) + y; // Indexes from 2D to 1D
    }

    public int[] i1Dto2D(int index) {
        int[] indexes = new int[2];
        indexes[0] = index / width; // X value
        indexes[1] = index % height; // Y value
        return indexes;
    }


    public void setTile(int x, int y, int number) {

            frame.setTileText(x, y, Integer.toString(number));  // color based on number.

        gridValues[x][y] = number; // setting number to grid
    }

    public void removeTile(int x, int y) {
        frame.removeTileText(x, y);
        gridValues[x][y] = 0;
    }

    public int[][] getgridValues(){
        return gridValues;
    }
    public int getScore(){
        return score;
    }

    public void chooseEmptySpot() { // chooses random spot from all the empty tiles.
        ArrayList arr = getEmptyTiles(gridValues);
        if (arr.size() != 0) {
            int randomIndex = random.nextInt(arr.size()); // index from emptyTiles
            int[] emptyindex = i1Dto2D((int) arr.get(randomIndex));  // index is now X and Y
            setTile(emptyindex[0], emptyindex[1], genTwosfours()); // setting tiles
        }

    }

    public boolean mergeTilesUp(int[][] gridValues) {
        boolean merged = false;
        int lastValue;
        int x;
        int y;
        for (int i = 0; i < width; i++) {
            lastValue = -1;
            x = -1;
            y = -1;
            for (int j = 0; j < height; j++) {
                if (isEmpty(j, i)) {

                } else if (gridValues[j][i] == lastValue) {
                    setTile(x, y, (lastValue * 2));
                    removeTile(j, i);
                    score += lastValue*2;
                    lastValue = 0;
                    merged = true;

                }
                if (isEmpty(j, i)) {

                } else {
                    lastValue = gridValues[j][i];
                    x = j;
                    y = i;
                }
            }
        }
        boolean moved = moveUp(gridValues);
        if(moved || merged){
            return true;
        }
        return merged;
    }

    public boolean mergeTilesDown(int[][] gridValues) {
        boolean merged = false;
        int lastValue = -1;
        int x;
        int y;
        for (int i = 0; i < width; i++) {
            lastValue = -1;
            x = -1;
            y = -1;
            for (int j = height -1; j>-1;j--) {
                if (isEmpty(j, i)) {

                } else if (gridValues[j][i] == lastValue) {
                    setTile(x, y, (lastValue * 2));
                    removeTile(j, i);
                    score+= lastValue*2;
                    lastValue = 0;
                    merged = true;
                }
                if (isEmpty(j, i)) {

                } else {
                    lastValue = gridValues[j][i];
                    x = j;
                    y = i;
                }
            }
        }
        boolean moved = moveDown(gridValues);
        if(moved || merged){
            return true;
        }
        return merged;
    }

    public boolean mergeTilesLeft(int[][] gridValues) {
        boolean merged = false;
        int lastValue;
        int x;
        int y;
        for (int i = 0; i < width; i++) {
            lastValue = -1;
            x = -1;
            y = -1;
            for (int j = 0 ; j < height;j++) {
                if (isEmpty(i, j)) {

                } else if (gridValues[i][j] == lastValue) {
                    setTile(x, y, (lastValue * 2));
                    removeTile(i, j);
                    score+= lastValue*2;
                    lastValue = 0;
                    merged = true;
                }
                if (isEmpty(i, j)) {

                } else {
                    lastValue = gridValues[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        boolean moved = moveLeft(gridValues);
        if(moved || merged){
            return true;
        }
        return merged;
    }

    public boolean mergeTilesRight(int[][] gridValues) {
        boolean merged = false;
        int lastValue;
        int x;
        int y;
        for (int i = 0; i < width; i++) {
            lastValue = -1;
            x = -1;
            y = -1;
            for (int j = height-1 ; j > -1;j--) {
                if (isEmpty(i, j)) {

                } else if (gridValues[i][j] == lastValue) {
                    setTile(x, y, (lastValue * 2));
                    removeTile(i, j);
                    score+=lastValue*2;
                    lastValue = 0;
                    merged = false;
                }
                if (isEmpty(i, j)) {

                } else {
                    lastValue = gridValues[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        boolean moved = moveRight(gridValues);
        if(moved || merged){
            return true;
        }

        return false;
    }

    public boolean moveLeft(int[][] gridValues){
        boolean moved = false;
        for(int i = 0;i<gridValues.length;i++){
            Integer[] a = new Integer[gridValues[i].length];
            for(int j = 0;j<gridValues[i].length;j++){
                a[j] = Integer.valueOf(gridValues[i][j]);
            }

            Arrays.sort(a, new CompareTiles().LEFT);
            for(int j = 0;j<gridValues[i].length;j++){
                if((int)a[j] != gridValues[i][j]){
                    moved = true;
                }
                setTile(i,j, (int)a[j]);

            }
        }
        return moved;
    }
    public boolean moveRight(int[][] gridValues){
        boolean moved = false;
        for(int i = 0;i<gridValues.length;i++){
            Integer[] a = new Integer[gridValues[i].length];
            for(int j = 0;j<gridValues[i].length;j++){
                a[j] = Integer.valueOf(gridValues[i][j]);
            }

            Arrays.sort(a, new CompareTiles().RIGHT);
            for(int j = 0;j<gridValues[i].length;j++){
                if((int)a[j] != gridValues[i][j]){
                    moved = true;
                }
                setTile(i,j, (int)a[j]);

            }
        }
        return moved;
    }

    public boolean moveUp(int[][] gridValues){
        boolean moved = false;
        for(int i = 0;i<gridValues.length;i++){
            Integer[] a = new Integer[gridValues[i].length];
            for(int j = 0;j<gridValues[i].length;j++){
                a[j] = Integer.valueOf(gridValues[j][i]);
            }

            Arrays.sort(a, new CompareTiles().UP);
            for(int j = 0;j<gridValues[i].length;j++){
                if((int)a[j] != gridValues[j][i]){
                    System.out.println("TRUE");
                    moved = true;
                }
                setTile(j,i, (int)a[j]);

            }

        }
        return moved;
    }

    public boolean moveDown(int[][] gridValues){
        boolean moved = false;
        for(int i = 0;i<gridValues.length;i++){
            Integer[] a = new Integer[gridValues[i].length];
            for(int j = 0;j<gridValues[i].length;j++){
                a[j] = Integer.valueOf(gridValues[j][i]);
            }

            Arrays.sort(a, new CompareTiles().DOWN);
            for(int j = 0;j<gridValues[i].length;j++){
                if((int)a[j] != gridValues[j][i]){
                    moved = true;
                }
                setTile(j,i, (int)a[j]);

            }
        }
        return moved;
    }



    public int genTwosfours() {
        return Math.random() < 0.9 ? 2 : 4; // generating 2s 90 perecent of the time.
    }

    public boolean isEmpty(int x, int y) {
        if (gridValues[x][y] == 0) return true;
        return false;
    }

    public boolean wonGame() {
        for (int i = 0; i < gridValues.length; i++) {
            for (int j = 0; j < gridValues[i].length; j++) {
                if (gridValues[i][j] == GOAL) return true; // Checking for 2048
            }
        }
        return false;
    }

    @Override
    public void keyMovement(KeyEvent e) {  // The callback is processed here. The merging can begin.
        boolean merge = false;
        boolean move = false;
        switch (e.getKeyCode()) {

            case UP: // UPs
                if(mergeTilesUp(gridValues)) {
                    chooseEmptySpot();
                }
                break;

            case DOWN: // DOWN
                if(mergeTilesDown(gridValues)) {
                    chooseEmptySpot();
                }
                break;

            case LEFT: // LEFT;
                if(mergeTilesLeft(gridValues)) {
                    chooseEmptySpot();
                }
                break;

            case RIGHT: // RIGHT
                if(mergeTilesRight(gridValues)) {
                    chooseEmptySpot();
                }
                break;

            default:
        }

    }

    public class CompareTiles{
        public final Comparator<Integer> LEFT = new Comparator<Integer>(){
            @Override
            public int compare(Integer a, Integer b){
                if(a == 0 && b != 0){
                    return 1;
                }else if(a != 0 && b == 0){
                    return -1;
                }else{
                    return 0;
                }
            }
        };
        public  final Comparator<Integer> RIGHT = new Comparator<Integer>(){
            @Override
            public int compare(Integer a, Integer b){
                if(a == 0 && b != 0){
                    return -1;
                }else if(a != 0 && b == 0){
                    return 1;
                }else{
                    return 0;
                }
            }
        };
        public  final Comparator<Integer> UP = new Comparator<Integer>(){
            @Override
            public int compare(Integer a, Integer b){
                if(a == 0 && b != 0){
                    return 1;
                }else if(a != 0 && b == 0){
                    return -1;
                }else{
                    return 0;
                }
            }
        };
        public  final Comparator<Integer> DOWN = new Comparator<Integer>(){
            @Override
            public int compare(Integer a, Integer b){
                if(a == 0 && b != 0){
                    return -1;
                }else if(a != 0 && b == 0){
                    return 1;
                }else{
                    return 0;
                }
            }
        };

    }
}






