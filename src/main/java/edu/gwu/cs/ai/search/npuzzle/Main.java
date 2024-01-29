package edu.gwu.cs.ai.search.npuzzle;

import java.util.logging.Logger;

import edu.gwu.cs.ai.search.SearchAlgorithm;
import edu.gwu.cs.ai.search.SearchHeuristic;
import edu.gwu.cs.ai.search.SearchStatistics;
import edu.gwu.cs.ai.search.Strategy;
import edu.gwu.cs.ai.search.ZeroHeuristic;

public class Main {

	private static Logger lg = Logger.getLogger("abc");

	public static void main(String[] args) throws Exception {
		for (int targetMoveCount = 10; targetMoveCount <= 15; targetMoveCount++) {
			generateAndSolve(targetMoveCount);
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void generateAndSolve(int targetMoveCount) throws Exception {
		int puzzleSize = 3; // 3 --> 8 Puzzle, 4 --> 15 Puzzle
		NPuzzle nPuzzle = new NPuzzleGenerator().generate(puzzleSize, targetMoveCount);
		System.out.println("================");
		System.out.println("targetMoveCount: " + targetMoveCount + ", puzzle:\r\n" + nPuzzle.getPrintVersion());

		// Set this puzzle as the "root"
		nPuzzle.setDistanceFromRoot(0);

		SearchAlgorithm nPuzzleSearchAlgorithm = new NPuzzleSearchAlgorithm();
		SearchHeuristic heuristicAlgorithm = new ZeroHeuristic();

		{
			int maxSearchDepth = 20;
			SearchStatistics searchStatsTreeDfs = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.DFS,
					heuristicAlgorithm, maxSearchDepth);
			System.out.println("TreeSearchResults DFS: " + searchStatsTreeDfs);
			if (searchStatsTreeDfs.getDistanceFromRoot() > targetMoveCount) {
				System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
						+ searchStatsTreeDfs.getDistanceFromRoot());
			}
		}

		{
			SearchStatistics searchStatsTreeBfs = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.BFS,
					heuristicAlgorithm, Double.POSITIVE_INFINITY);
			System.out.println("TreeSearchResults BFS: " + searchStatsTreeBfs);
			if (searchStatsTreeBfs.getDistanceFromRoot() > targetMoveCount) {
				System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
						+ searchStatsTreeBfs.getDistanceFromRoot());
			}
		}

		{
			SearchStatistics searchStatsGraphBfs = nPuzzleSearchAlgorithm.solveGraphSearch(nPuzzle, Strategy.BFS,
					heuristicAlgorithm);
			System.out.println("GraphSearchResults BFS: " + searchStatsGraphBfs);
			if (searchStatsGraphBfs.getDistanceFromRoot() > targetMoveCount) {
				System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
						+ searchStatsGraphBfs.getDistanceFromRoot());
			}
		}
	}
}