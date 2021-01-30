package edu.gwu.cs.ai.search.npuzzle;

import edu.gwu.cs.ai.search.Strategy;

public class Main {
    public static void main(String[] args) throws Exception {
        int targetMoveCount = 12;
        int puzzleSize = 3;
        NPuzzle nPuzzle = new NPuzzleGenerator().generate(puzzleSize, targetMoveCount);
        System.out.println("targetMoveCount: " + targetMoveCount + ", puzzle:\r\n" + nPuzzle.getPrintVersion());

        // We set this puzzle as the "root"
        nPuzzle.setPrevious(null);

        NPuzzleSearchAlgorithm nPuzzleSearchAlgorithm = new NPuzzleSearchAlgorithm();
        NPuzzleHeuristic heuristicAlgorithm = new ZeroHeuristic();

        SearchStatistics searchStats = nPuzzleSearchAlgorithm.solveGraphSearch(nPuzzle, Strategy.BFS, heuristicAlgorithm);
        System.out.println(searchStats);
    }
}