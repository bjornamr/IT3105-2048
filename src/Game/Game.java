package Game;

/**
 * Created by Bjornars on 09.10.2015.
 */

/*
https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java
 */

import GUI.GUI;
import java.awt.*;
import java.util.Random;
import javax.swing.*;


public class Game{


    private int Score;


    private static final String WIN_GAME = "You WON! Tile is 2048";
    private static final String LOSE_GAME = "You lost. Retry by pressing -R-";

    private static final int UP = 38;
    private static final int DOWN = 40;
    private static final int RIGHT = 37;
    private static final int LEFT = 39;

    private final int GOAL = 2048;


    Random random = new Random();


    private int[][] GridValues; // Values
    private boolean[][] Merge; // To check if merging
    GUI frame;
    public Game(int x,int y) {
        frame = new GUI( x, y);
        //frame.AddObserver(Game.this);
        GridValues = new int[x][y]; // setting size of grid.
        Merge = new boolean[x][y]; // false on startup


        Score = 0;


        frame.setSize(800,800);
        frame.setTileSize(x, y, 200, 200);
        frame.setTileText(0, 0, "64");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        //this.Grid = new int[x][y];


    }

    public void Merge(){

    }



    public void setTile(int x, int y, int number) {
        frame.setTileText(x,y,Integer.toString(number));  // color based on number.
        GridValues[x][y] =number; // setting number to grid
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





    public void MergeTiles(int key){
        switch (key){

            case 38: // UP


                break;
            case 40: // DOWN


                break;
            case 37: // RIGHT

                break;
            case 39: // LEFT

                break;

            default:
                System.out.println("WRONG FUCKING KEY");
        }


    }

}






