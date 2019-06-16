package edu.gwu.cs.ai.search.npuzzle;

public class ManhattanTilesHeuristic implements NPuzzleHeuristic {

    @Override
    public int evaluate(NPuzzle npuzzle) {
        int manhattanDistance = 0;
        for (int i = 0; i < NPuzzle.SIDE; i++) {
            for (int j = 0; j < NPuzzle.SIDE; j++) {
                manhattanDistance += getManhattanDistance(i, j, npuzzle.getState(i, j));
            }
        }

        return manhattanDistance;
    }

    /**
     * Manhattan distance if the value is located at i-th row and j-th column.
     * How many places does it need to move.
     */
    public int getManhattanDistance(int i, int j, int value) {
        // There is a special rule for the blank.  If it is 0, then think of it as k*k
        if (value == 0) {
            value = NPuzzle.SIDE * NPuzzle.SIDE;
        }
        // values are 1...k*k - 1
        // value should be in:
        // (value - 1 / side) row + (value % side)-1 column

        // 1 -> 0. 6 -> 1. 5 -> 0. etc. if side = 5
        // 1 -> 0, 4 -> 1, 5 -> 1, 8 -> 2 if side = 3, etc.
        int expectedRow = (value - 1) / NPuzzle.SIDE;

        // 1 -> 0. 6 -> 0. 5 -> 4. 23 -> 2. etc. if side = 5
        // 1 -> 0, 6 -> 2, 8 -> 1, if size = 3.
        int expectedCol = (value - 1) % NPuzzle.SIDE;

        return Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
    }
}
