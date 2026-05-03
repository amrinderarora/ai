package edu.gwu.cs.ai.search;

public interface SearchHeuristic {

    double evaluate(SearchState searchState);

    boolean isOptimistic();
    
    String getFriendlyName();

}
