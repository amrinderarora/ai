package edu.gwu.cs.ai.search.npuzzle;

import edu.gwu.cs.ai.search.SearchAlgorithm;
import edu.gwu.cs.ai.search.SearchHeuristic;
import edu.gwu.cs.ai.search.SearchStatistics;
import edu.gwu.cs.ai.search.Strategy;
import edu.gwu.cs.ai.search.ZeroHeuristic;

public class Main {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int targetMoveCount = 15;
        int puzzleSize = 4;
        NPuzzle nPuzzle = new NPuzzleGenerator().generate(puzzleSize, targetMoveCount);
        System.out.println("targetMoveCount: " + targetMoveCount + ", puzzle:\r\n" + nPuzzle.getPrintVersion());

        // We set this puzzle as the "root"
        // Ensures the algorithm cannot find "home" just by following the previous links. :-)
        nPuzzle.setPrevious(null);

        SearchAlgorithm nPuzzleSearchAlgorithm = new NPuzzleSearchAlgorithm();
        SearchHeuristic heuristicAlgorithm = new ZeroHeuristic();

        // SearchStatistics searchStatsTreeDfs = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.DFS, heuristicAlgorithm);
        // System.out.println("TreeSearchResults: " + searchStatsTreeDfs);

        // SearchStatistics searchStatsTreeBfs = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.BFS, heuristicAlgorithm);
        // System.out.println("TreeSearchResults BFS: " + searchStatsTreeBfs);

        // SearchStatistics searchStatsGraphDfs = nPuzzleSearchAlgorithm.solveGraphSearch(nPuzzle, Strategy.DFS, heuristicAlgorithm);
        // System.out.println("GraphSearchResults DFS: " + searchStatsGraphDfs);

        SearchStatistics searchStatsGraphBfs = nPuzzleSearchAlgorithm.solveGraphSearch(nPuzzle, Strategy.BFS, heuristicAlgorithm);
        System.out.println("GraphSearchResults BFS: " + searchStatsGraphBfs);
    }
}