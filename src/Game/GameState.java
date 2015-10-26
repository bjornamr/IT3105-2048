package Game;

/**
 * Created by Joakim on 15.10.2015.
 */
public class GameState implements Cloneable{
    private int[][] board;
    private int score;
    private boolean changed;
    private int emptyTiles;
    /*
    Game state with the board, score and a boolean that says if the state was change or not
     */
    public GameState(int[][] board, int score, boolean changed){
        this.board = board;
        this.score = score;
        this.changed = changed;

    }
    /*
    Game state with the board, score, if changed between states and number of empty tiles.
     */
    public GameState(int[][] board, int score, boolean changed, int emptyTiles){
        this.board = board;
        this.score = score;
        this.changed = changed;
        this.emptyTiles = emptyTiles;
    }
    /*
    Constructor for copying states.
     */
    public GameState(GameState copy){
        this.score = copy.score;
        this.changed = false;
        this.emptyTiles = copy.emptyTiles;
        this.board = new int[copy.board.length][];
        for(int i = 0;i<board.length;i++){
            this.board[i] = new int[copy.board[i].length];
            for(int j = 0;j<board[i].length;j++){
                this.board[i][j] = copy.board[i][j];
            }
        }
    }



    public int getEmptyTiles() {
        return emptyTiles;
    }

    public void setEmptyTiles(int emptyTiles) {

        this.emptyTiles = emptyTiles;
    }


    public void addToEmptyTiles(int amount){
        this.emptyTiles+= amount;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setTile(int x, int y, int value){
        board[x][y] = value;

    }

    public void removeTile(int x, int y){

        board[x][y] = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addToScore(int valueToAdd){
        score+= valueToAdd;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public GameState clone(){
        return new GameState(this);
    }
}
