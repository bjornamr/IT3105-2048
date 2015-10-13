package AI;

/**
 * Created by Bjornars on 13.10.2015.
 */
public class MinMax {


    function alphabeta(node, depth, ?, ?, maximizingPlayer)
    if depth = 0 or node is a terminal node
    return the heuristic value of node
    if maximizingPlayer
    for each child of node
    ? := max(?, alphabeta(child, depth - 1, ?, ?, FALSE))
            if ? ? ?
    break (* ? cut-off *)
            return ?
    else
            for each child of node
    ? := min(?, alphabeta(child, depth - 1, ?, ?, TRUE))
            if ? ? ?
    break (* ? cut-off *)
            return ?
            (* Initial call *)
    alphabeta(origin, depth, -?, +?, TRUE)

}
