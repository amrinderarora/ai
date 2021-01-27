package edu.gwu.cs.ai.search.npuzzle;

import edu.gwu.cs.ai.search.Strategy;

public class Main {
    public static void main(String[] args) throws Exception {
        int targetMoveCount = 13;
        NPuzzle puzzle1 = new NPuzzleGenerator().generate(targetMoveCount);
        System.out.println("targetMoveCount: " + targetMoveCount + ", puzzle:\r\n" + puzzle1.getPrintVersion());

        NPuzzleSearchAlgorithm nPuzzleSearchAlgorithm = new NPuzzleSearchAlgorithm();
        NPuzzleHeuristic heuristicAlgorithm = new ZeroHeuristic();

        SearchStatistics searchStats = nPuzzleSearchAlgorithm.solveGraphSearch(puzzle1, Strategy.BFS, heuristicAlgorithm);
        System.out.println(searchStats);
    }
}