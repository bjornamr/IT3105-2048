package Game;

/**
 * Created by Bjornars on 09.10.2015.
 */

/*
https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java
 */

import GUI.GUI;

import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;


public class Game implements MyListener {


    private int score;


    private static final String WIN_GAME = "You WON! Tile is 2048";
    private static final String LOSE_GAME = "You lost. Retry by pressing -R-";
    public static final int UP = 38;                                                // Keyevent value for keys
    public static final int DOWN = 40;                                              // Keyevent value for keys
    public static final int RIGHT = 39;                                             // Keyevent value for keys
    public static final int LEFT = 37;                                              // Keyevent value for keys

    private int width;                                                              // width for array / game
    private int height;                                                             // height for array / game

    private final int GOAL = 2048;

    private ArrayList<Integer> emptyTiles = new ArrayList<>();                      // arraylist to save index of empty Tiles.
    private Random random = new Random();

    private int[][] board; // Values
    private GUI frame;

    public Game(int x, int y) {
        this.width = x;                                                         // Sets width and height of game
        this.height = y;

        frame = new GUI(x, y);                                                  // makes frame.
        frame.addListener(this);                                                // adds listener to game.

        this.board = new int[x][y]; // setting size of grid.

        score = 0;                                                          // score
        frame.setSize(800, 800);
        frame.setTileSize(x, y, 200, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //this.Grid = new int[x][y];
        newGame();

    }

    public void newGame() {                                             // starts with two empty tiles in game

        chooseEmptySpot();                                              // random generated place of empty tiles.
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
    /*
    Gets emptytiles and saves it into arraylist. Converts the x,y values to 1D index
     */
    public ArrayList<Integer> getEmptyTiles(int[][] gridValues) {  // getting all the empty tiles
        ArrayList<Integer> emptyTiles = new ArrayList<>();
        for (int x = 0; x < gridValues.length; x++) {
            for (int y = 0; y < gridValues[x].length; y++) {
                if (isEmpty(x, y, gridValues)) emptyTiles.add(i2Dto1D(x, gridValues.length, y));
            }
        }
        return emptyTiles;
    }

    /*
    Converting between indexes - 2D to 1D
     */
    public int i2Dto1D(int x, int rowlength, int y) {
        return (x * board.length) + y; // Indexes from 2D to 1D
    }
    /*
    Converting between indexes - 1D to 2D
     */

    public int[] i1Dto2D(int index) {
        int[] indexes = new int[2];
        indexes[0] = index / width; // X value
        indexes[1] = index % height; // Y value
        return indexes;
    }
    /*
    Sets tile inside 2D integer array + paints on GUI.
     */

    public void setTile(int x, int y, int number) {
        frame.setTileText(x, y, Integer.toString(number));  // color based on number.
        board[x][y] = number; // setting number to grid
    }
    /*
    Removes tiles from int array and GUI
     */

    public void removeTile(int x, int y) {
        frame.removeTileText(x, y);
        board[x][y] = 0;
    }

    public int[][] getBoardValues() {
        return board;
    }
    /*
    Game score
     */
    public int getScore() {
        return score;
    }
    /*
    chooses random spot from all the empty tiles.
     */
    public void chooseEmptySpot() { //
        ArrayList arr = getEmptyTiles(board); // all the empty spots.
        if (arr.size() != 0) {
            int randomIndex = random.nextInt(arr.size()); // index from emptyTiles
            int[] emptyindex = i1Dto2D((int) arr.get(randomIndex));  // index is now X and Y
            setTile(emptyindex[0], emptyindex[1], genTwosfours()); // setting tiles
        }

    }
    /*
    Merges all tiles that can be merged + moves
     */
    public GameState mergeTilesUp(GameState state, boolean move) {
        int lastValue;
        int x;
        int y;
        for (int i = 0; i < width; i++) {
            lastValue = -1;
            x = -1;
            y = -1;
            for (int j = 0; j < height; j++) {
                if (state.getBoard()[j][i]==0) {

                } else if (state.getBoard()[j][i] == lastValue) {
                    //Moves game board
                    if (move) {
                        setTile(x, y, (lastValue * 2));
                        removeTile(j, i);
                        score+= (lastValue * 2);

                    }
                    state.setTile(x,y,(lastValue * 2));
                    state.removeTile(j,i);
                    state.addToScore(lastValue*2);
                    state.addToEmptyTiles(1);
                    lastValue = 0;
                    state.setChanged(true);

                }
                if (state.getBoard()[j][i] == 0) {

                } else {
                    lastValue = state.getBoard()[j][i];
                    x = j;
                    y = i;
                }
            }
        }
        return moveUp(state, move);
    }
    /*
      Merges all tiles that can be merged + moves
     */
    public GameState mergeTilesDown(GameState state, boolean move) {
        int lastValue = -1;
        int x;
        int y;
        for (int i = 0; i < width; i++) {
            lastValue = -1;
            x = -1;
            y = -1;
            for (int j = height - 1; j > -1; j--) {
                if (state.getBoard()[j][i] == 0) {

                } else if (state.getBoard()[j][i] == lastValue) {
                    //Moves game board
                    if (move) {
                        setTile(x, y, (lastValue * 2));
                        removeTile(j, i);
                        score+= (lastValue * 2);
                    }
                    state.setTile(x,y,(lastValue * 2));
                    state.removeTile(j,i);
                    state.addToScore(lastValue * 2);
                    state.addToEmptyTiles(1);
                    lastValue = 0;
                    state.setChanged(true);
                }
                if (state.getBoard()[j][i]==0) {

                } else {
                    lastValue = state.getBoard()[j][i];
                    x = j;
                    y = i;
                }
            }
        }
        return moveDown(state, move);

    }
    /*
      Merges all tiles that can be merged + moves
     */
    public GameState mergeTilesLeft(GameState state, boolean move) {
        int lastValue;
        int x;
        int y;
        for (int i = 0; i < width; i++) {
            lastValue = -1;
            x = -1;
            y = -1;
            for (int j = 0; j < height; j++) {
                if (state.getBoard()[i][j]==0) {

                } else if (state.getBoard()[i][j] == lastValue) {
                    //Moves game board
                    if (move) {
                        setTile(x, y, (lastValue * 2));
                        removeTile(i, j);
                        score+= (lastValue * 2);
                    }
                    state.setTile(x,y,(lastValue * 2));
                    state.removeTile(i, j);
                    state.addToScore(lastValue * 2);
                    state.addToEmptyTiles(1);
                    lastValue = 0;
                    state.setChanged(true);
                }
                if (state.getBoard()[i][j] == 0) {

                } else {
                    lastValue = state.getBoard()[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        return moveLeft(state, move);

    }
    /*
      Merges all tiles that can be merged + moves
    */
    public GameState mergeTilesRight(GameState state, boolean move) {
        int lastValue;
        int x;
        int y;
        for (int i = 0; i < width; i++) {
            lastValue = -1;
            x = -1;
            y = -1;
            for (int j = height - 1; j > -1; j--) {
                if (state.getBoard()[i][j] == 0) {

                } else if (state.getBoard()[i][j] == lastValue) {
                    //Moves game board
                    if (move) {
                        setTile(x, y, (lastValue * 2));
                        removeTile(i, j);
                        score+=(lastValue*2);
                    }
                    state.setTile(x,y,(lastValue * 2));
                    state.removeTile(i,j);
                    state.addToScore(lastValue * 2);
                    state.addToEmptyTiles(1);
                    lastValue = 0;
                    state.setChanged(true);
                }
                if (state.getBoard()[i][j] == 0) {

                } else {
                    lastValue = state.getBoard()[i][j];
                    x = i;
                    y = j;
                }
            }
        }

        return moveRight(state, move);
    }
    /*
    Moves tiles left
     */
    public GameState moveLeft(GameState state, boolean move) {
        for (int i = 0; i < state.getBoard().length; i++) {
            Integer[] rowCopy = new Integer[state.getBoard()[i].length];
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                rowCopy[j] = Integer.valueOf(state.getBoard()[i][j]);
            }

            Arrays.sort(rowCopy, new CompareTiles().LEFT);
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if (rowCopy[j] != state.getBoard()[i][j]) {
                    state.setChanged(true);
                }
                if (move) {
                    setTile(i, j, rowCopy[j]);
                }
                state.setTile(i,j,rowCopy[j]);
            }
        }
        return state;
    }
    /*
    Moves tiles right
     */

    public GameState moveRight(GameState state, boolean move) {
        for (int i = 0; i < state.getBoard().length; i++) {
            Integer[] rowCopy = new Integer[state.getBoard()[i].length];
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                rowCopy[j] = Integer.valueOf(state.getBoard()[i][j]);
            }

            Arrays.sort(rowCopy, new CompareTiles().RIGHT);
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if (rowCopy[j] != state.getBoard()[i][j]) {
                    state.setChanged(true);
                }
                if (move) {
                    setTile(i, j, rowCopy[j]);
                }
                state.setTile(i,j,rowCopy[j]);
            }
        }
        return state;
    }
    /*
    Moves tiles up
    */
    public GameState moveUp(GameState state, boolean move) {
        for (int i = 0; i < state.getBoard().length; i++) {
            Integer[] rowCopy = new Integer[state.getBoard()[i].length];
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                rowCopy[j] = Integer.valueOf(state.getBoard()[j][i]);
            }

            Arrays.sort(rowCopy, new CompareTiles().UP);
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if ( rowCopy[j] != state.getBoard()[j][i]) {
                    state.setChanged(true);
                }
                if (move) {
                    setTile(j, i, rowCopy[j]);
                }
                state.setTile(j,i,rowCopy[j]);
            }

        }
        return state;
    }
    /*
    Moves tiles down
     */
    public GameState moveDown(GameState state, boolean move) {
        for (int i = 0; i < state.getBoard().length; i++) {
            Integer[] rowCopy = new Integer[state.getBoard()[i].length];
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                rowCopy[j] = Integer.valueOf(state.getBoard()[j][i]);
            }

            Arrays.sort(rowCopy, new CompareTiles().DOWN);
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if ( rowCopy[j] != state.getBoard()[j][i]) {
                    state.setChanged(true);
                }
                if (move) {
                    setTile(j, i, rowCopy[j]);
                }
                state.setTile(j,i,rowCopy[j]);
            }
        }
        return state;
    }
    /*
    Checks if the current game is game over.
     */
    public boolean isGameOver(GameState state){
        if(getEmptyTiles(state.getBoard()).size() == 0){

            GameState up = mergeTilesUp(state.clone(),false);
            GameState down = mergeTilesDown(state.clone(),false);
            GameState left = mergeTilesLeft(state.clone(),false);
            GameState right = mergeTilesRight(state.clone(),false);
            if(!up.isChanged() && !down.isChanged() && !left.isChanged() && !right.isChanged()){
                return true;
            }

        }
        return false;
    }

    public GameState getGameStateTest(int[][] board){
        return new GameState(board,score,false, getEmptyTiles(getBoardValues()).size());
    }

    public int[][] copyBoard() {
        int[][] temp = new int[board.length][];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new int[board[i].length];
            for (int j = 0; j < board[i].length; i++) {
                temp[i][j] = board[i][j];
            }
        }
        return temp;
    }
    /*
    Generating 2s 90 percent of the time and 4s 10 percent.
     */
    public int genTwosfours() {
        return Math.random() < 0.9 ? 2 : 4;
    }
    /*
    Checks if board is empty.

     */
    public boolean isEmpty(int x, int y, int[][] board) {
        return board[x][y] == 0;
    }
    /*

     */
    public boolean wonGame() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == GOAL) return true; // Checking for 2048
            }
        }
        return false;
    }
    /*
    Returns the current gamestate.
     */
    public GameState getGameState(){
        return new GameState(board,score,false, getEmptyTiles(getBoardValues()).size());
    }
    /*
    Method used for movement in the desired directions.
     */

    public void movement(int move) {
        GameState currentState;
        switch (move) {

            case UP: // UPs
                currentState = mergeTilesUp(getGameState(), true);
                if(currentState.isChanged()){
                    chooseEmptySpot();
                }
                break;

            case DOWN: // DOWN
                currentState = mergeTilesDown(getGameState(), true);
                if(currentState.isChanged()){
                    chooseEmptySpot();
                }
                break;

            case LEFT: // LEFT;
                currentState = mergeTilesLeft(getGameState(), true);
                if(currentState.isChanged()){
                    chooseEmptySpot();
                }
                break;

            case RIGHT: // RIGHT
                currentState = mergeTilesRight(getGameState(), true);
                if(currentState.isChanged()){
                    chooseEmptySpot();
                }
                break;

            default:
        }
    }
    /*
    Used for test purpose. To check if it was made right.
     */
    @Override
    public void keyMovement(KeyEvent e) {  // The callback is processed here. The merging can begin.
        movement(e.getKeyCode());

    }


}






