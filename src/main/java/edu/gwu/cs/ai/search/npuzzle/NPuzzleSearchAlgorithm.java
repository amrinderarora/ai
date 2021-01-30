package edu.gwu.cs.ai.search.npuzzle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import edu.gwu.cs.ai.search.Strategy;

public class NPuzzleSearchAlgorithm {

    public static final String NEW_LINE = "\r\n";

    /**
     * Solves using the starting npuzzle using tree search (does not maintain a closed list).
     * 
     * Ignores Strategy, and uses BFS (Deque, FIFO).
     * 
     * Does not use priority queue for cost, so heuristic is for your mental satisfaction only. Please provide a good one, and please be
     * consistent, positive and optimistic.
     * 
     * @param nPuzzle
     * @throws Exception
     */
    public SearchStatistics solveTreeSearch(NPuzzle nPuzzle, Strategy strategy, NPuzzleHeuristic heuristicAlgorithm) throws Exception {

        SearchStatistics searchStats = new SearchStatistics();

        if (nPuzzle.isSolved()) {
            searchStats.setFound(true);
            return searchStats;
        }

        Deque<NPuzzle> openSet = new ArrayDeque<>();
        openSet.addLast(nPuzzle);
        searchStats.incrementOpen();

        searchWhile: while (!openSet.isEmpty()) {
            NPuzzle bestNode = openSet.removeFirst();
            for (Direction dir : Direction.getAllDirections()) {
                if (bestNode.movePossible(dir)) {
                    NPuzzle nextState = bestNode.moveBlank(dir);
                    openSet.addLast(nextState);
                    searchStats.incrementOpen();
                    searchStats.setCurrentOpen(openSet.size());
                    if (nextState.isSolved()) {
                        int distanceToRoot = nextState.getDistanceToRoot();
                        searchStats.setFound(true);
                        searchStats.setDistanceToRoot(distanceToRoot);
                        break searchWhile;
                    }
                }
            }
        }
        return searchStats;
    }

    /**
     * Solves using the starting npuzzle using graph search (maintains a closed list).
     * 
     * Ignores Strategy, and uses BFS (Deque, FIFO).
     * 
     * Does not use priority queue for cost, so heuristic is for your mental satisfaction only. Please provide a good one, and please be
     * consistent, positive and optimistic.
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
                        searchStats.setCurrentOpen(openSet.size());
                        if (nextState.isSolved()) {
                            int distanceToRoot = nextState.getDistanceToRoot();
                            searchStats.setFound(true);
                            searchStats.setDistanceToRoot(distanceToRoot);
                            break searchWhile;
                        }
                    }
                }
            }
            // exploration of best node is finished
            searchStats.incrementClosed();
            closedSet.add(bestNode);
        }
        return searchStats;
    }
}
