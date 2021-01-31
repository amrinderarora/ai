package edu.gwu.cs.ai.search;

public interface SearchHeuristic {

    double evaluate(SearchState npuzzle);

    boolean isOptimistic();

}
