package edu.gwu.cs.ai.csp;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import edu.gwu.cs.ai.search.SearchStatistics;

public class GenericCSPSolver implements CSPSearchAlgorithm {

    public static final String NEW_LINE = "\r\n";

    /**
     * Solves using the starting search state using DFS tree search (does not maintain a closed list).
     * 
     * @param nPuzzle
     * @throws Exception
     */
    @Override
    public SearchStatistics solve(CSP searchProblem) {

        SearchStatistics searchStats = new SearchStatistics();

        CSPSearchState initialState = searchProblem.getInitialState();

        if (initialState.isFullyAssigned()) {
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

            Collection<CSPSearchState> successors = bestNode.getSuccessors();
            for (CSPSearchState nextNode : successors) {
                if (!openSet.contains(nextNode)) {
                    openSet.addLast(nextNode);
                    searchStats.incrementOpen();
                    searchStats.setCurrentOpen(openSet.size());
                    if (nextNode.isFullyAssigned()) {
                        int distanceToRoot = 0;
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
