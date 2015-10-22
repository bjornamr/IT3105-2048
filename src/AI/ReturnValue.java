package AI;

import javax.sql.rowset.serial.SerialArray;

/**
 * Created by Joakim on 16.10.2015.
 */
public class ReturnValue {
    private SearchNode node;
    private double heuristicValue;

    public ReturnValue(SearchNode node, double heuristicValue){
        this.node = node;
        this.heuristicValue = heuristicValue; // heuristic or prob?
    }

    public SearchNode getNode() {
        return node;
    }

    public void setNode(SearchNode node) {
        this.node = node;
    }

    public double getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(double heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

}
