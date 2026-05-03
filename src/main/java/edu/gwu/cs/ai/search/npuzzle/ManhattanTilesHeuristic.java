package edu.gwu.cs.ai.search.npuzzle;

import edu.gwu.cs.ai.search.SearchHeuristic;
import edu.gwu.cs.ai.search.SearchState;

public class ManhattanTilesHeuristic implements SearchHeuristic {

    @Override
    public double evaluate(SearchState searchState) {
        NPuzzle npuzzle = (NPuzzle) searchState;
        int manhattanDistance = 0;
        for (int i = 0; i < npuzzle.getSize(); i++) {
            for (int j = 0; j < npuzzle.getSize(); j++) {
                manhattanDistance += getManhattanDistance(npuzzle.getSize(), i, j, npuzzle.getState(i, j));
            }
        }

        return manhattanDistance;
    }

    /**
     * Manhattan distance if the value is located at i-th row and j-th column.
     * How many places does it need to move.
     * For Blank (0), we always return 0.
     */
    public int getManhattanDistance(int size, int i, int j, int value) {
        // Special rule for the blank.
        if (value == 0) {
        	return 0;
        }
        // values are 1...k*k - 1
        // value should be in:
        // (value - 1 / side) row + (value % side)-1 column

        // 1 -> 0. 6 -> 1. 5 -> 0. etc. if side = 5
        // 1 -> 0, 4 -> 1, 5 -> 1, 8 -> 2 if side = 3, etc.
        int expectedRow = (value - 1) / size;

        // 1 -> 0. 6 -> 0. 5 -> 4. 23 -> 2. etc. if side = 5
        // 1 -> 0, 6 -> 2, 8 -> 1, if size = 3.
        int expectedCol = (value - 1) % size;

        return Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
    }

    @Override
    public boolean isOptimistic() {
        return true;
    }

	@Override
	public String getFriendlyName() {
		return "Manhattan";
	}
}
