package edu.gwu.cs.ai.search;

public interface SearchAlgorithm {

    /**
     * Solves using the starting npuzzle using tree search (does not maintain a closed list).
     * 
     * Ignores Strategy, and uses BFS (Deque, FIFO).
     * 
     * Does not use priority queue for cost, so heuristic is for your mental satisfaction only. Please provide a good one, and please be
     * consistent, positive and optimistic.
     * 
     * @param nPuzzle
     * @throws Exception
     */
    SearchStatistics solveTreeSearch(SearchState nPuzzle, Strategy strategy, SearchHeuristic heuristicAlgorithm) throws Exception;

    /**
     * Solves using the starting npuzzle using graph search (maintains a closed list).
     * 
     * Ignores Strategy, and uses BFS (Deque, FIFO).
     * 
     * Does not use priority queue for cost, so heuristic is for your mental satisfaction only. Please provide a good one, and please be
     * consistent, positive and optimistic.
     * 
     * @param nPuzzle
     * @throws Exception
     */
    SearchStatistics solveGraphSearch(SearchState nPuzzle, Strategy strategy, SearchHeuristic heuristicAlgorithm) throws Exception;

}