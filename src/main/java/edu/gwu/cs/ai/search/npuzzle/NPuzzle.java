package edu.gwu.cs.ai.search.npuzzle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.gwu.cs.ai.search.SearchState;

/**
 * Models the state of the n-puzzle.
 * There are no algorithms or solvers here.
 * 
 * @author amrinder
 */
public class NPuzzle implements Cloneable, SearchState {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private int side = 4;
    private static final int ZERO = 0;
    private int[][] stateMatrix;
    private int blankRow;
    private int blankCol;

    private Direction lastDirection;
    private NPuzzle previous;


    @Override
    public int hashCode() {
        return Arrays.deepHashCode(stateMatrix);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NPuzzle other = (NPuzzle) obj;
        if (!Arrays.deepEquals(stateMatrix, other.stateMatrix))
            return false;
        return true;
    }

    /** Creates an instance of n-puzzle. By default, it is solved. */
    public NPuzzle(int sideArg) {
        this.side = sideArg;
        stateMatrix = new int[side][side];

        int counter = 1;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                stateMatrix[i][j] = counter++;
            }
        }
        stateMatrix[side - 1][side - 1] = 0; // Bottom Right is the blank
        setBlank(side - 1, side - 1); // Bottom Right is the blank
    }

    public NPuzzle(int[] stateArray) {
        this.side = (int) Math.sqrt(stateArray.length);
        stateMatrix = new int[side][side];
        int counter = 0;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (stateArray[counter] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
                stateMatrix[i][j] = stateArray[counter++];
            }
        }
    }

    private void setBlank(int i, int j) {
        stateMatrix[i][j] = ZERO;
        blankRow = i;
        blankCol = j;
    }

    public String getPrintVersion() {
        StringBuffer sb = new StringBuffer();
        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                if (stateMatrix[row][col] == 0) {
                    sb.append("    ");
                } else {
                    sb.append(String.format(" %2d ", stateMatrix[row][col]));
                }
            }
            sb.append(NEW_LINE);
        }
        return sb.toString();
    }

    /**
     * Moves the BLANK in the specified direction.
     * 
     * @return
     * 
     * @throws CloneNotSupportedException
     */
    public NPuzzle moveBlank(Direction direction) {
        if (!movePossible(direction)) {
            throw new IllegalArgumentException("Move not possible: " + direction);
        }
        NPuzzle nPuzzleNewState = null;
        try {
            nPuzzleNewState = this.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Exception cloning, program will terminate");
            throw new RuntimeException("Exception in cloning a state");
        }
        if (direction == Direction.UP) {
            nPuzzleNewState.stateMatrix[blankRow][blankCol] = stateMatrix[blankRow - 1][blankCol];
            nPuzzleNewState.setBlank(blankRow - 1, blankCol);
        }
        if (direction == Direction.DOWN) {
            nPuzzleNewState.stateMatrix[blankRow][blankCol] = stateMatrix[blankRow + 1][blankCol];
            nPuzzleNewState.setBlank(blankRow + 1, blankCol);
        }
        if (direction == Direction.LEFT) {
            nPuzzleNewState.stateMatrix[blankRow][blankCol] = stateMatrix[blankRow][blankCol - 1];
            nPuzzleNewState.setBlank(blankRow, blankCol - 1);
        }
        if (direction == Direction.RIGHT) {
            nPuzzleNewState.stateMatrix[blankRow][blankCol] = stateMatrix[blankRow][blankCol + 1];
            nPuzzleNewState.setBlank(blankRow, blankCol + 1);
        }
        nPuzzleNewState.lastDirection = direction;
        nPuzzleNewState.setPrevious(this);
        return nPuzzleNewState;
    }

    @Override
    public NPuzzle clone() throws CloneNotSupportedException {
        NPuzzle np2 = (NPuzzle) super.clone();
        np2.stateMatrix = new int[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                np2.stateMatrix[i][j] = this.stateMatrix[i][j];
            }
        }

        return np2;
    }

    public boolean movePossible(Direction direction) {
        if (blankRow == 0 && direction == Direction.UP
                || blankRow == side - 1 && direction == Direction.DOWN
                || blankCol == 0 && direction == Direction.LEFT
                || blankCol == side - 1 && direction == Direction.RIGHT) {
            return false;
        }
        return true;
    }

    public int getState(int i, int j) {
        return stateMatrix[i][j];
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public NPuzzle getPrevious() {
        return previous;
    }

    public void setPrevious(NPuzzle prevArg) {
        this.previous = prevArg;
    }

    public int getDistanceToRoot() {
        int dist = 0;
        NPuzzle curr = this;
        while (curr.previous != null) {
            curr = curr.previous;
            dist++;
        }
        return dist;
    }

    public int getSize() {
        return this.side;
    }

    @Override
    public boolean isGoalState() {
        int counter = 1;
        // Just traverses left to right, and then top to down and matches that to the counter
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                // This is for the blank
                if (i == side - 1 & j == side - 1) {
                    continue;
                }
                if (this.stateMatrix[i][j] != counter++) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Map<SearchState, Double> getSuccessors() {
        Map<SearchState, Double> successors = new HashMap<>();
        for (Direction dir : Direction.getAllDirections()) {
            if (this.movePossible(dir)) {
                NPuzzle nextState = this.moveBlank(dir);
                successors.put(nextState, 1.0d);
            }
        }
        return successors;
    }
}