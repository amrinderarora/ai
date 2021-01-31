package edu.gwu.cs.ai.search;

public class ZeroHeuristic implements SearchHeuristic {

    @Override
    public double evaluate(SearchState state) {
        return 0;
    }

    @Override
    public boolean isOptimistic() {
        return true;
    }
}
