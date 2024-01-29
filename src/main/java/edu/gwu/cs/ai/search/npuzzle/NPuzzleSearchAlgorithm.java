package edu.gwu.cs.ai.search.npuzzle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
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
	 * Uses the strategy that is given - BFS or DFS.
	 * 
	 * Does not use priority queue for cost, so heuristic is for your mental
	 * satisfaction only. Please provide a good one, and please be consistent,
	 * positive and optimistic.
	 * 
	 * @param nPuzzle
	 * @throws Exception
	 */
	@Override
	public SearchStatistics solveTreeSearch(SearchState searchState, Strategy strategy,
			SearchHeuristic heuristicAlgorithm, double maxSearchDepth) throws Exception {

		NPuzzle nPuzzle = (NPuzzle) searchState;
		if (nPuzzle.isGoalState()) {
			SearchStatistics searchStats = new SearchStatistics();
			searchStats.setFound(true);
			return searchStats;
		}

		if (strategy == Strategy.BFS) {
			return solveTreeSearchBFS(searchState, heuristicAlgorithm);
		} else if (strategy == Strategy.DFS) {
			return solveTreeSearchDFS(searchState, heuristicAlgorithm, maxSearchDepth);
		} else {
			throw new IllegalArgumentException("Strategy not supported: " + strategy);
		}
	}

	private SearchStatistics solveTreeSearchBFS(SearchState searchState, SearchHeuristic heuristicAlgorithm)
			throws Exception {
		NPuzzle nPuzzle = (NPuzzle) searchState;
		SearchStatistics searchStats = new SearchStatistics();

		Deque<SearchState> openSet = new ArrayDeque<>();
		openSet.addLast(nPuzzle);
		searchStats.incrementOpen();

		searchWhile: while (!openSet.isEmpty()) {
			NPuzzle bestNode = (NPuzzle) openSet.removeFirst();

			Map<SearchState, Double> successors = bestNode.generateSuccessors();
			for (SearchState nextNode : successors.keySet()) {
				if (!openSet.contains(nextNode)) {
					openSet.addLast(nextNode); // deque.addLast or deque.add
					searchStats.incrementOpen();
					searchStats.setCurrentOpen(openSet.size());
					if (nextNode.isGoalState()) {
						double distanceToRoot = ((NPuzzle) nextNode).getDistanceFromRoot();
						searchStats.setFound(true);
						searchStats.setDistanceFromRoot(distanceToRoot);
						break searchWhile;
					}
				}
			}
		}
		searchStats.stopTimer();
		return searchStats;
	}

	private SearchStatistics solveTreeSearchDFS(SearchState searchState, SearchHeuristic heuristicAlgorithm, double maxSearchDepth)
			throws Exception {
		SearchStatistics searchStats = new SearchStatistics();

		NPuzzle nPuzzle = (NPuzzle) searchState;
		nPuzzle.generateSuccessors();
		nPuzzle.setCurrSucessorIndex(0);

		Deque<SearchState> openSet = new ArrayDeque<>();
		openSet.push(nPuzzle);
		searchStats.incrementOpen();

		// The element at the top of the stack is what we are exploring
		// Every element already has its successors generated and current = 0 before
		// being pushed to the stack
		// The element once popped can surely come in again because we don't know what
		// is closed
		// Same state matrix should not be on the stack, as we check before pushing
		searchWhileLoopLabel: while (!openSet.isEmpty()) {
			NPuzzle bestNode = (NPuzzle) openSet.getFirst(); // Stack.top or deque.getFirst
//            System.out.println("Currently exploring: ");
//            System.out.println(bestNode.getPrintVersion());
//            Thread.sleep(1000);
			if ((bestNode.getCurrSucessorIndex() < bestNode.getSuccessors().size()) && 
					(bestNode.getDistanceFromRoot() <= maxSearchDepth)) {
				NPuzzle childNode = (NPuzzle) bestNode.getSuccessors().get(bestNode.getCurrSucessorIndex());
				if (childNode.isGoalState()) {
					double distanceFromRoot = childNode.getDistanceFromRoot();
					searchStats.setFound(true);
					searchStats.setDistanceFromRoot(distanceFromRoot);
					break searchWhileLoopLabel;
				}

				// If the open set contains the child node, then we simply skip it and continue the exploration of the current node
				// If the open set does not contain child node, then we add it, and start its exploration
				if (openSet.contains(childNode)) {
					bestNode.setCurrSucessorIndex(bestNode.getCurrSucessorIndex() + 1);
				} else {
					childNode.generateSuccessors();
					openSet.push(childNode); // Stack.push or deque.addFirst
					searchStats.setCurrentDistance(childNode.getDistanceFromRoot());
					searchStats.setCurrentOpen(openSet.size());
					bestNode.setCurrSucessorIndex(bestNode.getCurrSucessorIndex() + 1);
				}

			} else {
				openSet.pop(); // Stack.pop or deque.removeFirst
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
			Map<SearchState, Double> successors = bestNode.generateSuccessors();
			for (SearchState nextNode : successors.keySet()) {
				if (!closedSet.contains(nextNode) && !openSet.contains(nextNode)) {
					openSet.addLast((NPuzzle) nextNode);
					searchStats.incrementOpen();
					searchStats.setCurrentOpen(openSet.size());
					if (nextNode.isGoalState()) {
						double distanceFromRoot = ((NPuzzle) nextNode).getDistanceFromRoot();
						searchStats.setFound(true);
						searchStats.setDistanceFromRoot(distanceFromRoot);
						break searchWhile;
					}
				}
			}
			// exploration of best node is finished
			searchStats.incrementClosed();
			closedSet.add(bestNode);
		}
		return searchStats;
	}
}
