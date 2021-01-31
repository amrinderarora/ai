package edu.gwu.cs.ai.search.npuzzle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.gwu.cs.ai.search.SearchAlgorithm;
import edu.gwu.cs.ai.search.SearchHeuristic;
import edu.gwu.cs.ai.search.SearchState;
import edu.gwu.cs.ai.search.SearchStatistics;
import edu.gwu.cs.ai.search.Strategy;

public class NPuzzleSearchAlgorithm implements SearchAlgorithm {

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
    @Override
    public SearchStatistics solveTreeSearch(SearchState searchState, Strategy strategy, SearchHeuristic heuristicAlgorithm) throws Exception {

        NPuzzle nPuzzle = (NPuzzle) searchState;
        SearchStatistics searchStats = new SearchStatistics();
        searchStats.setStartTime();

        if (nPuzzle.isSolved()) {
            searchStats.setFound(true);
            return searchStats;
        }

        Deque<SearchState> openSet = new ArrayDeque<>();
        openSet.addLast(nPuzzle);
        searchStats.incrementOpen();

        searchWhile: while (!openSet.isEmpty()) {
            NPuzzle bestNode = (NPuzzle) openSet.removeFirst();
            Map<SearchState, Double> successors = bestNode.getSuccessors();
            for (SearchState nextState : successors.keySet()) {
                openSet.addLast(nextState);
                searchStats.incrementOpen();
                searchStats.setCurrentOpen(openSet.size());
                if (nextState.isGoalState()) {
                    int distanceToRoot = ((NPuzzle) nextState).getDistanceToRoot();
                    searchStats.setFound(true);
                    searchStats.setDistanceToRoot(distanceToRoot);
                    break searchWhile;
                }
            }
        }
        searchStats.setFinishTime();
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
    @Override
    public SearchStatistics solveGraphSearch(SearchState searchState, Strategy strategy, SearchHeuristic heuristicAlgorithm) throws Exception {

        NPuzzle nPuzzle = (NPuzzle) searchState;
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
            Map<SearchState, Double> successors = bestNode.getSuccessors();
            for (SearchState nextState : successors.keySet()) {
                if (!closedSet.contains(nextState)) {
                    openSet.addLast((NPuzzle) nextState);
                    searchStats.incrementOpen();
                    searchStats.setCurrentOpen(openSet.size());
                    if (nextState.isGoalState()) {
                        int distanceToRoot = ((NPuzzle) nextState).getDistanceToRoot();
                        searchStats.setFound(true);
                        searchStats.setDistanceToRoot(distanceToRoot);
                        break searchWhile;
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
