package edu.gwu.cs.ai.search.npuzzle;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import edu.gwu.cs.ai.search.SearchAlgorithm;
import edu.gwu.cs.ai.search.SearchHeuristic;
import edu.gwu.cs.ai.search.SearchState;
import edu.gwu.cs.ai.search.SearchStatistics;
import edu.gwu.cs.ai.search.Strategy;

public class NPuzzleSearchAlgorithm implements SearchAlgorithm {

	public static final String NEW_LINE = "\r\n";

	/**
	 * Solves using the starting npuzzle using tree search (does not maintain a
	 * closed list).
	 * 
	 * Uses the strategy that is given - BFS, DFS or AStar.
	 * 
	 * In case of AStar, uses a priority queue for cost. Regarding the heuristic,
	 * please provide a good one, and please be consistent, positive and optimistic.
	 * 
	 * @param nPuzzle
	 * @throws Exception
	 */
	@Override
	public SearchStatistics solveTreeSearch(SearchState searchState, Strategy strategy,
			SearchHeuristic heuristicAlgorithm, double maxSearchDepth) throws Exception {

		if (searchState.isGoalState()) {
			SearchStatistics searchStats = new SearchStatistics();
			searchStats.setFound(true);
			return searchStats;
		}

		if (strategy == Strategy.ASTAR) {
			return solveTreeSearchAstar(searchState, heuristicAlgorithm);
		} else if (strategy == Strategy.BFS) {
			return solveTreeSearchBFS((NPuzzle) searchState);
		} else if (strategy == Strategy.DFS) {
			return solveTreeSearchDFS((NPuzzle) searchState, maxSearchDepth);
		} else {
			throw new IllegalArgumentException("Strategy not supported: " + strategy);
		}
	}

	private SearchStatistics solveTreeSearchAstar(SearchState searchState, SearchHeuristic heuristicAlgorithm)
			throws Exception {
		NPuzzle nPuzzle = (NPuzzle) searchState;
		SearchStatistics searchStats = new SearchStatistics();

		Comparator<SearchState> searchStateComparator = new Comparator<SearchState>() {
			public int compare(SearchState ss1, SearchState ss2) {
				// f = g + h, that is, f = backward cost + forward cost
				double f1 = ss1.getDistanceFromRoot() + heuristicAlgorithm.evaluate(ss1);
				double f2 = ss2.getDistanceFromRoot() + heuristicAlgorithm.evaluate(ss2);
				return Double.compare(f1, f2);
			}
		};
		PriorityQueue<SearchState> openSet = new PriorityQueue<>(searchStateComparator);
		openSet.add(nPuzzle);
		searchStats.incrementOpen();

		searchWhile: while (!openSet.isEmpty()) {
			NPuzzle bestNode = (NPuzzle) openSet.poll(); // The lowest f value

			NPuzzle childNode = (NPuzzle) bestNode.getNextSuccessor();
			while (childNode != null) {
				if (!openSet.contains(childNode)) {
					openSet.offer(childNode); // add to priority queue, let it take care of it.
					searchStats.incrementOpen();
					searchStats.setCurrentOpen(openSet.size());
					if (childNode.isGoalState()) {
						double distanceToRoot = childNode.getDistanceFromRoot();
						searchStats.setFound(true);
						searchStats.setDistanceFromRoot(distanceToRoot);
						break searchWhile;
					}
				}
				childNode = (NPuzzle) bestNode.getNextSuccessor();
			}
		}
		searchStats.stopTimer();
		return searchStats;
	}

	private SearchStatistics solveTreeSearchBFS(NPuzzle nPuzzle) throws Exception {
		SearchStatistics searchStats = new SearchStatistics();

		Deque<SearchState> openSet = new ArrayDeque<>();
		openSet.push(nPuzzle);
		searchStats.incrementOpen();
		int loopCounter = 0;

		searchWhile: while (!openSet.isEmpty()) {
			NPuzzle bestNode = (NPuzzle) openSet.removeFirst();
			if (loopCounter++ % 10000 == 0) {
				System.out.println("while(): current open: " + openSet.size() + ", bestNode distance: " + bestNode.getDistanceFromRoot());
			}


			NPuzzle childNode = (NPuzzle) bestNode.getNextSuccessor();
			while (childNode != null) {
				if (!openSet.contains(childNode)) {
					openSet.addLast(childNode); // deque.addLast or deque.add
					searchStats.incrementOpen();
					searchStats.setCurrentOpen(openSet.size());

					if (childNode.isGoalState()) {
						double distanceToRoot = ((NPuzzle) childNode).getDistanceFromRoot();
						searchStats.setFound(true);
						searchStats.setDistanceFromRoot(distanceToRoot);
						break searchWhile;
					}
				}
				childNode = (NPuzzle) bestNode.getNextSuccessor();
			}
		}
		searchStats.stopTimer();
		return searchStats;
	}

	private SearchStatistics solveTreeSearchDFS(NPuzzle nPuzzle, double maxSearchDepth) throws Exception {
		SearchStatistics searchStats = new SearchStatistics();

		Deque<SearchState> openSet = new ArrayDeque<>();
		openSet.push(nPuzzle);
		searchStats.incrementOpen();

		// The element once popped can surely come in again because we don't know what
		// is closed
		// Same state should not be on the stack, as we check before pushing
		searchWhileLoopLabel: while (!openSet.isEmpty()) {
			NPuzzle bestNode = (NPuzzle) openSet.getFirst(); // Stack.top or deque.getFirst
//            System.out.println("Currently exploring: " + bestNode.getPrintVersion() + 
//				", curr distance: " + bestNode.getDistanceFromRoot() + ", openSet.size: " + openSet.size());
			if (bestNode.getDistanceFromRoot() > maxSearchDepth) {
				openSet.pop();
				continue searchWhileLoopLabel;
			}
			NPuzzle childNode = (NPuzzle) bestNode.getNextSuccessor();
			if (childNode == null) {
				openSet.pop(); // Stack.pop or deque.removeFirst
				continue searchWhileLoopLabel;
			}

			if (childNode.isGoalState()) {
				double distanceFromRoot = childNode.getDistanceFromRoot();
				searchStats.setFound(true);
				searchStats.setDistanceFromRoot(distanceFromRoot);
				break searchWhileLoopLabel;
			}

			// If the open set contains the child node, then we simply skip it and continue
			// the exploration of the current node
			// If the open set does not contain child node, then we add it
			if (!openSet.contains(childNode)) {
				openSet.push(childNode); // Stack.push or deque.addFirst
				searchStats.setCurrentDistance(childNode.getDistanceFromRoot());
				searchStats.setCurrentOpen(openSet.size());
			}
		}
		searchStats.stopTimer();
		return searchStats;
	}

	/**
	 * Solves using the starting npuzzle using graph search (maintains a closed
	 * list).
	 * 
	 * Ignores Strategy, and uses BFS (Deque, FIFO).
	 * 
	 * Does not use priority queue for cost, so heuristic is for your mental
	 * satisfaction only. Please provide a good one, and please be consistent,
	 * positive and optimistic.
	 * 
	 * @param nPuzzle
	 * @throws Exception
	 */
	@Override
	public SearchStatistics solveGraphSearch(SearchState searchState, Strategy strategy,
			SearchHeuristic heuristicAlgorithm) throws Exception {

		NPuzzle nPuzzle = (NPuzzle) searchState;
		SearchStatistics searchStats = new SearchStatistics();

		if (nPuzzle.isGoalState()) {
			searchStats.setFound(true);
			return searchStats;
		}

		Set<NPuzzle> closedSet = new HashSet<>();
		Deque<NPuzzle> openSet = new ArrayDeque<>();
		openSet.addLast(nPuzzle);
		searchStats.incrementOpen();

		searchWhile: while (!openSet.isEmpty()) {
			NPuzzle bestNode = null;
			if (strategy == Strategy.BFS) {
				bestNode = (NPuzzle) openSet.removeFirst();
			} else if (strategy == Strategy.DFS) {
				bestNode = (NPuzzle) openSet.removeLast();
			} else {
				throw new IllegalArgumentException("Strategy not supported: " + strategy);
			}
			
			NPuzzle childNode = (NPuzzle) bestNode.getNextSuccessor();
			while (childNode != null) {
				if (!closedSet.contains(childNode) && !openSet.contains(childNode)) {
					openSet.addLast(childNode);
					searchStats.incrementOpen();
					searchStats.setCurrentOpen(openSet.size());
					if (childNode.isGoalState()) {
						double distanceFromRoot = childNode.getDistanceFromRoot();
						searchStats.setFound(true);
						searchStats.setDistanceFromRoot(distanceFromRoot);
						break searchWhile;
					}
				}
				childNode = (NPuzzle) bestNode.getNextSuccessor();
			}
			// exploration of best node is finished
			searchStats.incrementClosed();
			closedSet.add(bestNode);
		}
		return searchStats;
	}
}
