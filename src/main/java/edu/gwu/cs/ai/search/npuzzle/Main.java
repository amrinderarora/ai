package edu.gwu.cs.ai.search.npuzzle;

import java.util.ArrayList;
import java.util.List;

import edu.gwu.cs.ai.core.ProblemSolutionPerformance;
import edu.gwu.cs.ai.search.SearchAlgorithm;
import edu.gwu.cs.ai.search.SearchHeuristic;
import edu.gwu.cs.ai.search.SearchStatistics;
import edu.gwu.cs.ai.search.Strategy;

public class Main {

	// private static Logger lg = Logger.getLogger("abc");

	private static List<ProblemSolutionPerformance> pspList = new ArrayList<>();


	public static void main(String[] args) throws Exception {
		for (int targetMoveCount = 25; targetMoveCount <= 28; targetMoveCount++) {
			generateAndSolve(targetMoveCount);
		}
		
		// Prints the complete set of results
		for (ProblemSolutionPerformance psp: pspList) {
			System.err.println(psp.toString());
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void generateAndSolve(int targetMoveCount) throws Exception {
		int puzzleSize = 5; // 3 --> 8 Puzzle, 4 --> 15 Puzzle, 5 --> 24 puzzle, 6 --> 35 puzzle, etc.
		NPuzzle nPuzzle = new NPuzzleGenerator().generate(puzzleSize, targetMoveCount);
		System.out.println("================");
		System.out.println("targetMoveCount: " + targetMoveCount + ", puzzle:\r\n" + nPuzzle.getPrintVersion());

		// Set this puzzle as the "root"
		nPuzzle.setDistanceFromRoot(0);

		SearchAlgorithm nPuzzleSearchAlgorithm = new NPuzzleSearchAlgorithm();
		
		{/*
			System.err.println("Starting Tree DFS");
			SearchHeuristic heuristicAlgorithm = null;
			int maxSearchDepth = 0;
			boolean found = false;
			while (maxSearchDepth < 10 && !found) {
				SearchStatistics searchStatsTreeDfs = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.DFS,
						heuristicAlgorithm, maxSearchDepth);
				found = searchStatsTreeDfs.isFound();
				System.out.println("TreeSearchResults DFS: " + searchStatsTreeDfs);
				if (searchStatsTreeDfs.getDistanceFromRoot() > targetMoveCount) {
					System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
							+ searchStatsTreeDfs.getDistanceFromRoot());
				}
				maxSearchDepth++;
			} */
		}

		{ /*
			System.err.println("Starting Tree BFS");
			SearchHeuristic heuristicAlgorithm = null;
			SearchStatistics searchStatsTreeBfs = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.BFS,
					heuristicAlgorithm, 4);
			System.out.println("TreeSearchResults BFS: " + searchStatsTreeBfs);
			if (searchStatsTreeBfs.getDistanceFromRoot() > targetMoveCount) {
				System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
						+ searchStatsTreeBfs.getDistanceFromRoot());
			} */
		}

		{
			System.err.println("Starting Tree AStar Tiles");
			SearchHeuristic heuristicAlgorithm = new TilesHeuristic();
			SearchStatistics searchStatsTreeAstarTiles = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.ASTAR,
					heuristicAlgorithm, Double.POSITIVE_INFINITY);
			System.out.println("TreeSearchResults AStar Tiles: " + searchStatsTreeAstarTiles);
			if (searchStatsTreeAstarTiles.getDistanceFromRoot() > targetMoveCount) {
				System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
						+ searchStatsTreeAstarTiles.getDistanceFromRoot());
			}
			pspList.add(new ProblemSolutionPerformance("" + nPuzzle.getSize() + ":" + targetMoveCount,
						Strategy.ASTAR + ":" + heuristicAlgorithm.toString(),searchStatsTreeAstarTiles.toString()));
		}

		{
			System.err.println("Starting Tree AStar Manhattan");
			SearchHeuristic heuristicAlgorithm = new ManhattanTilesHeuristic();
			SearchStatistics searchStatsTreeAstarManhattan = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.ASTAR,
					heuristicAlgorithm, Double.POSITIVE_INFINITY);
			System.out.println("TreeSearchResults AStar Manhattan: " + searchStatsTreeAstarManhattan);
			if (searchStatsTreeAstarManhattan.getDistanceFromRoot() > targetMoveCount) {
				System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
						+ searchStatsTreeAstarManhattan.getDistanceFromRoot());
			}
			pspList.add(new ProblemSolutionPerformance("" + nPuzzle.getSize() + ":" + targetMoveCount,
					Strategy.ASTAR + ":" + heuristicAlgorithm.toString(),searchStatsTreeAstarManhattan.toString()));
		}

		{ /*
			System.err.println("Starting Graph BFS");
			SearchHeuristic heuristicAlgorithm = null;
			SearchStatistics searchStatsGraphBfs = nPuzzleSearchAlgorithm.solveGraphSearch(nPuzzle, Strategy.BFS,
					heuristicAlgorithm);
			System.out.println("GraphSearchResults BFS: " + searchStatsGraphBfs);
			if (searchStatsGraphBfs.getDistanceFromRoot() > targetMoveCount) {
				System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
						+ searchStatsGraphBfs.getDistanceFromRoot());
			}
		*/
		}
	}
}