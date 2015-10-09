package Game;

/**
 * Created by Bjornars on 09.10.2015.
 */

/*
https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java
 */

import GUI.GUI;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


public class Game{


    private int Score;


    private static final String WIN_GAME = "You WON! Tile is 2048";
    private static final String LOSE_GAME = "You lost. Retry by pressing -R-";

    private static final int UP = 38;
    private static final int DOWN = 40;
    private static final int RIGHT = 37;
    private static final int LEFT = 39;

    private final int GOAL = 2048;



    private int[][] GridValues; // Values
    private boolean[][] Merge; // To check if merging
    GUI frame;
    public Game(int x,int y) {
        frame = new GUI( x, y);
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


    private int[][] getEmptyCells(){
       // int [][] empty = new int[GridValues.length][GridValues[0].length];  // assuming that every line is equal in a row or coloumn.

        for( int i = 0; i<GridValues.length; i++){
            for (int j = 0; j<GridValues[i].length; j++){
                if(GridValues[i][j] ==0){

                }
            }
        }

        return null;
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






