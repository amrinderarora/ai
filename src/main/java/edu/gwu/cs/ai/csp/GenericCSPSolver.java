package edu.gwu.cs.ai.csp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import edu.gwu.cs.ai.search.SearchStatistics;
import edu.gwu.cs.ai.search.npuzzle.NPuzzle;

public class GenericCSPSolver implements CSPSearchAlgorithm {

    public static final String NEW_LINE = "\r\n";

    /**
     * Solves using the starting npuzzle using tree search (does not maintain a closed list).
     * 
     * @param nPuzzle
     * @throws Exception
     */
    @Override
    public SearchStatistics solve(CSPSearchProblem searchProblem, CSPSearchState initialState) {

        SearchStatistics searchStats = new SearchStatistics();

        if (initialState.isSolved()) {
            searchStats.setFound(true);
            searchStats.stopTimer();
            return searchStats;
        }

        Deque<CSPSearchState> openSet = new ArrayDeque<>();
        openSet.addLast(initialState);
        searchStats.incrementOpen();

        searchWhile: while (!openSet.isEmpty()) {
            CSPSearchState bestNode = null;
            bestNode = (CSPSearchState) openSet.removeLast();

            Map<CSPSearchState, Double> successors = bestNode.getSuccessors();
            for (CSPSearchState nextNode : successors.keySet()) {
                if (!openSet.contains(nextNode)) {
                    openSet.addLast(nextNode);
                    searchStats.incrementOpen();
                    searchStats.setCurrentOpen(openSet.size());
                    if (nextNode.isSolved()) {
                        int distanceToRoot = ((NPuzzle) nextNode).getDistanceToRoot();
                        searchStats.setFound(true);
                        searchStats.setDistanceToRoot(distanceToRoot);
                        break searchWhile;
                    }
                }
            }
        }
        searchStats.stopTimer();
        return searchStats;
    }

}
