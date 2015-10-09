package Game;

/**
 * Created by Bjornars on 09.10.2015.
 */

import GUI.GUI;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


public class Game{


    private int Score;


    private static final String WIN_GAME = "You WON! Tile is 2048";
    private static final String LOSE_GAME = "You lost. Retry by pressing -R-";



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
        frame.setTileColor(0,0,0,0,0);
        frame.setTileText(0, 0, "2048");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        //this.Grid = new int[x][y];


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






