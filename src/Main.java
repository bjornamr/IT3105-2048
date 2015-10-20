
import AI.MinMax;
import AI.SearchNode;
import Game.Game;
public class Main {

    public static int depth = 8;

    public static void main(String[] args) {
        Game twenty40eight = new Game(4,4);
        MinMax minMax = new MinMax(twenty40eight);
        SearchNode startNode = new SearchNode(0, twenty40eight.getEmptyTiles(twenty40eight.getBoardValues()), twenty40eight.getBoardValues());
        minMax.start(startNode,depth);

    }
}
