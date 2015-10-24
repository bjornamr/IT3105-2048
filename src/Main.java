
import AI.MinMax;
import AI.SearchNode;
import Game.Game;
public class Main {

    public static int depth = 6; /// depth is 7 when 1024 + 256 + 128 + 64 +64

    public static void main(String[] args) {
        Game twenty40eight = new Game(4,4);
        MinMax minMax = new MinMax(twenty40eight);
        SearchNode startNode = new SearchNode(0, twenty40eight.getEmptyTiles(twenty40eight.getBoardValues()), twenty40eight.getBoardValues());
       minMax.start(startNode,depth);


    }
}
