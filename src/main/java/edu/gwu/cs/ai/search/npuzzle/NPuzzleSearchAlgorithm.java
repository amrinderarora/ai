package edu.gwu.cs.ai.search.npuzzle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import edu.gwu.cs.ai.search.Strategy;

public class NPuzzleSearchAlgorithm {

    public static final String NEW_LINE = "\r\n";
    public long globalCount = 0;

    /**
     * Solves using the starting npuzzle using graph search (so, it maintains a closed list).
     * 
     * Ignores Strategy for now, and uses BFS.
     * 
     * @param nPuzzle
     * @throws Exception
     */
    public SearchStatistics solveGraphSearch(NPuzzle nPuzzle, Strategy strategy, NPuzzleHeuristic heuristicAlgorithm) throws Exception {

        SearchStatistics searchStats = new SearchStatistics();

        if (nPuzzle.isSolved()) {
            searchStats.setFound(true);
            return searchStats;
        }

        Set<NPuzzle> closedSet = new HashSet<>();
        Deque<NPuzzle> openSet = new ArrayDeque<>();
        openSet.addLast(nPuzzle);
        searchStats.incrementOpen();

        searchWhile: while (!openSet.isEmpty()) {
            NPuzzle bestNode = openSet.removeFirst();
            for (Direction dir : Direction.getAllDirections()) {
                if (bestNode.movePossible(dir)) {
                    NPuzzle nextState = bestNode.moveBlank(dir);
                    if (!closedSet.contains(nextState)) {
                        openSet.addLast(nextState);
                        searchStats.incrementOpen();
                        if (nextState.isSolved()) {
                            int distanceToRoot = nextState.getDistanceToRoot();
                            searchStats.setFound(true);
                            searchStats.setDistanceToRoot(distanceToRoot);
                            break searchWhile;
                        }
                    }
                }

                // exploration of best node is finished
                closedSet.add(bestNode);
            }
        }
        return searchStats;
    }

    private void print(Direction direction, NPuzzle npuzzle, boolean terminal) {
        System.out.println("Direction: " + direction);
        System.out.println(npuzzle);
        if (terminal) {
            System.out.println("[SOLVED]");
            System.out.println("Total Moves: " + globalCount);
        }
    }
}
