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
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class Game implements MyListener{


    private int Score;


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


    private int[][] GridValues; // Values
    private boolean[][] Merge; // To check if merging
    GUI frame;
    public Game(int x,int y) {
        this.width = x;
        this.height =y;

        frame = new GUI( x, y);
        frame.addListener(this);

        GridValues = new int[x][y]; // setting size of grid.
        Merge = new boolean[x][y]; // false on startup
        Score = 0;
        frame.setSize(800, 800);
        frame.setTileSize(x, y, 200, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        //this.Grid = new int[x][y];


    }

    public void redraw(int[][] Grid){

        for(int x = 0; x<Grid.length; x++){
            for (int y = 0; y<Grid[x].length; y++){
                setTile(x,y,Grid[x][y]);
            }
        }


    }


    public ArrayList getEmptyTiles(){  // getting all the empty tiles
        emptyTiles.clear();
        for(int x = 0; x < GridValues.length; x++){
            for(int y=0; y <GridValues[x].length; y++){
                if(isEmpty(x,y)) emptyTiles.add(i2Dto1D(x,GridValues.length,y));
            }
        }
        return emptyTiles;
    }



    public int i2Dto1D(int x,int rowlength,int y){
        return (x * GridValues.length) + y; // Indexes from 2D to 1D
    }
    public int[] i1Dto2D(int index){
        int [] indexes = new int[2];
        indexes[0] = index / width; // X value
        indexes[1] = index % height; // Y value
        return indexes;
    }







    public void setTile(int x, int y, int number) {
        frame.setTileText(x,y,Integer.toString(number));  // color based on number.
        GridValues[x][y] =number; // setting number to grid
    }

    public void removeTile(int x, int y){
        frame.removeTileText(x,y);
        GridValues[x][y] = 0;
    }

    public void chooseEmptySpot(){ // chooses random spot from all the empty tiles.
       ArrayList arr= getEmptyTiles();
        if( arr.size() !=0) {
            int randomIndex = random.nextInt(arr.size()); // index from emptyTiles
            int[] emptyindex = i1Dto2D((int) arr.get(randomIndex));  // index is now X and Y
            setTile(emptyindex[0], emptyindex[1], genTwosfours()); // setting tiles
        }

    }

    public int genTwosfours(){
        return Math.random() < 0.9 ? 2 : 4; // generating 2s 90 perecent of the time.
    }

    public boolean isEmpty(int x,int y){
        if(GridValues[x][y] ==0) return true;
        return false;
    }


    public boolean wonGame(){
        for(int i=0; i<GridValues.length; i++){
            for(int j=0; j<GridValues[i].length; j++){
                if(GridValues[i][j]==2048) return true;
            }
        }
        return false;
    }



    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }







    @Override
    public void keyMovement(KeyEvent e) {  // The callback is processed here. The merging can begin.
        switch (e.getKeyCode()){

            case UP: // UPs
                System.out.println("UP");
                chooseEmptySpot();

                break;

            case DOWN: // DOWN
                System.out.println("DOWN");
                chooseEmptySpot();
                break;

            case LEFT: // LEFT
                System.out.println("LEFT");
                chooseEmptySpot();
                break;

            case RIGHT: // RIGHT
                System.out.println("RIGHT");
                chooseEmptySpot();
                break;

            default:
                System.out.println("WRONG FUCKING KEY");
        }
    }
}






