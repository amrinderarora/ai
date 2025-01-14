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
		int puzzleSize = 4; // 3 --> 8 Puzzle, 4 --> 15 Puzzle, 5 --> 24 puzzle, 6 --> 35 puzzle, etc.
		for (int targetMoveCount = 15; targetMoveCount <= 17; targetMoveCount++) {
			generateAndSolve(puzzleSize, targetMoveCount);
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
	public static void generateAndSolve(int puzzleSize, int targetMoveCount) throws Exception {
		NPuzzle nPuzzle = new NPuzzleGenerator().generate(puzzleSize, targetMoveCount);
		System.out.println("================");
		System.out.println("targetMoveCount: " + targetMoveCount + ", puzzle:\r\n" + nPuzzle.getPrintVersion());

		// Set this puzzle as the "root"
		nPuzzle.setDistanceFromRoot(0);

		SearchAlgorithm nPuzzleSearchAlgorithm = new NPuzzleSearchAlgorithm();
		
		{
			System.err.println("Starting Tree DFS");
			SearchHeuristic heuristicAlgorithm = null;
			int maxSearchDepth = 1;
			boolean found = false;
			SearchStatistics searchStatsTreeDfs = null;
			while (maxSearchDepth < 1000 && !found) {
				System.err.println("TreeSearch, starting DFS: " + maxSearchDepth);

				searchStatsTreeDfs = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle.clone(), Strategy.DFS,
						heuristicAlgorithm, maxSearchDepth);
				found = searchStatsTreeDfs.isFound();
				if (found) {
					System.out.println("TreeSearchResults DFS: " + searchStatsTreeDfs);
				}
				if (searchStatsTreeDfs.getDistanceFromRoot() > targetMoveCount) {
					System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
							+ searchStatsTreeDfs.getDistanceFromRoot());
				}
				maxSearchDepth++;
				
//				Runtime runtime = Runtime.getRuntime();
//				System.out.println("RuntimeStats:\r\nUsed memory: " + (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024) + " MB");
//				try {Thread.sleep(2000);} catch (Exception ignored) {}
//				System.out.flush();
			} 
			pspList.add(new ProblemSolutionPerformance(nPuzzle.getSize() + ":" + targetMoveCount,
					Strategy.DFS.toString(),searchStatsTreeDfs.toString()));
		}

		{ 
			System.err.println("Starting Tree BFS");
			SearchHeuristic heuristicAlgorithm = null;
			SearchStatistics searchStatsTreeBfs = nPuzzleSearchAlgorithm.solveTreeSearch(nPuzzle, Strategy.BFS,
					heuristicAlgorithm, 4);
			System.out.println("TreeSearchResults BFS: " + searchStatsTreeBfs);
			if (searchStatsTreeBfs.getDistanceFromRoot() > targetMoveCount) {
				System.err.println("Error: expected distance: " + targetMoveCount + ", found: "
						+ searchStatsTreeBfs.getDistanceFromRoot());
			} 
			pspList.add(new ProblemSolutionPerformance(nPuzzle.getSize() + ":" + targetMoveCount,
					Strategy.BFS.toString(),searchStatsTreeBfs.toString()));
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
			pspList.add(new ProblemSolutionPerformance(nPuzzle.getSize() + ":" + targetMoveCount,
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
			pspList.add(new ProblemSolutionPerformance(nPuzzle.getSize() + ":" + targetMoveCount,
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