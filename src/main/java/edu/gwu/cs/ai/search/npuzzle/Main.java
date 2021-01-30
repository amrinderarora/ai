package edu.gwu.cs.ai.search.npuzzle;

import edu.gwu.cs.ai.search.Strategy;

public class Main {
    public static void main(String[] args) throws Exception {
        int targetMoveCount = 12;
        int puzzleSize = 4;
        NPuzzle nPuzzle = new NPuzzleGenerator().generate(puzzleSize, targetMoveCount);
        System.out.println("targetMoveCount: " + targetMoveCount + ", puzzle:\r\n" + nPuzzle.getPrintVersion());

        // We set this puzzle as the "root"
        nPuzzle.setPrevious(null);

        NPuzzleSearchAlgorithm nPuzzleSearchAlgorithm = new NPuzzleSearchAlgorithm();
        NPuzzleHeuristic heuristicAlgorithm = new ZeroHeuristic();

        SearchStatistics searchStatsTree = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.BFS, heuristicAlgorithm);
        System.out.println("TreeSearchResults: " + searchStatsTree);

        SearchStatistics searchStatsGraph = nPuzzleSearchAlgorithm.solveGraphSearch(nPuzzle, Strategy.BFS, heuristicAlgorithm);
        System.out.println("GraphSearchResults: " + searchStatsGraph);
    }
}