package edu.gwu.cs.ai.search.npuzzle;

public class ZeroHeuristic implements NPuzzleHeuristic {

    @Override
    public int evaluate(NPuzzle npuzzle) {
        return 0;
    }
}
