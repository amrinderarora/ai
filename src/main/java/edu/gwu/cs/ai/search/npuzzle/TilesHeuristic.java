package edu.gwu.cs.ai.search.npuzzle;

import edu.gwu.cs.ai.search.SearchHeuristic;
import edu.gwu.cs.ai.search.SearchState;

public class TilesHeuristic implements SearchHeuristic {

	@Override
	public double evaluate(SearchState searchState) {
		NPuzzle npuzzle = (NPuzzle) searchState;
		int numTilesMisplaced = 0;
		for (int i = 0; i < npuzzle.getSize(); i++) {
			for (int j = 0; j < npuzzle.getSize(); j++) {
				numTilesMisplaced += isTileMisplaced(npuzzle.getSize(), i, j, npuzzle.getState(i, j)) ? 1 : 0;
			}
		}

		return numTilesMisplaced;
	}

	/**
	 * Is the tile with value "value" misplaced if it is found in the i-th row and j-th column?
	 * i and j are both 0 indexed.
	 * Examples:
	 * isTileMisplaced(n,0,0,1) should return false for all values of n.
	 * isTileMisplaced(5,3,3,19) should return false.
	 * The "Blank" tile is never considered to be misplaced.
	 */
	public boolean isTileMisplaced(int size, int i, int j, int value) {
		// If blank, we return false
		if (value == 0) {
			return false;
		}
		int expectedVal = size * i + j + 1;
		if (expectedVal == size * size) {
			expectedVal = 0;
		}
		return expectedVal != value;
	}

	@Override
	public boolean isOptimistic() {
		return true;
	}

	@Override
	public String getFriendlyName() {
		return "Tiles";
	}
}
