package edu.gwu.cs.ai.search.npuzzle;

/**
 * Models the state of the n-puzzle.
 * There are no algorithms or solvers here.
 * 
 * @author amrinder
 */
public class NPuzzle implements Cloneable {
    private static final String NEW_LINE = System.getProperty("line.separator");
    public static final int SIDE = 3;
    private static final int ZERO = 0;
    private int[][] stateMatrix = new int[SIDE][SIDE];
    private int blankRow;
    private int blankCol;

    private int heurEvaluation;
    private Direction lastDirection;

    /** Creates an instance of n-puzzle. By default, it is solved. */
    public NPuzzle() {
        int counter = 1;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                stateMatrix[i][j] = counter++;
            }
        }
        stateMatrix[SIDE - 1][SIDE - 1] = 0; // Bottom Right is the blank
        setBlank(SIDE - 1, SIDE - 1); // Bottom Right is the blank
    }

    public NPuzzle(int[] stateArray) {
        int counter = 0;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
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
        for (int row = 0; row < SIDE; row++) {
            for (int col = 0; col < SIDE; col++) {
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
     */
    public void moveBlank(Direction direction) {
        if (!movePossible(direction)) {
            throw new IllegalArgumentException("Move not possible: " + direction);
        }
        if (direction == Direction.UP) {
            stateMatrix[blankRow][blankCol] = stateMatrix[blankRow - 1][blankCol];
            setBlank(blankRow - 1, blankCol);
        }
        if (direction == Direction.DOWN) {
            stateMatrix[blankRow][blankCol] = stateMatrix[blankRow + 1][blankCol];
            setBlank(blankRow + 1, blankCol);
        }
        if (direction == Direction.LEFT) {
            stateMatrix[blankRow][blankCol] = stateMatrix[blankRow][blankCol - 1];
            setBlank(blankRow, blankCol - 1);
        }
        if (direction == Direction.RIGHT) {
            stateMatrix[blankRow][blankCol] = stateMatrix[blankRow][blankCol + 1];
            setBlank(blankRow, blankCol + 1);
        }
        this.lastDirection = direction;
    }


    /**
     * Checks if the npuzzle is in solved state.
     * 
     * @return True if the puzzle is solved after this move.
     */

    public boolean isSolved() {
        // For each value of height, Check the rows
        int counter = 1;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (i == SIDE - 1 & j == SIDE - 1) {
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
    public NPuzzle clone() throws CloneNotSupportedException {
        NPuzzle np2 = (NPuzzle) super.clone();
        np2.stateMatrix = new int[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                np2.stateMatrix[i][j] = this.stateMatrix[i][j];
            }
        }
        
        return np2;
    }

    public boolean movePossible(Direction direction) {
        if (blankRow == 0 && direction == Direction.UP
                || blankRow == SIDE - 1 && direction == Direction.DOWN
                || blankCol == 0 && direction == Direction.LEFT
                || blankCol == SIDE - 1 && direction == Direction.RIGHT) {
            return false;
        }
        return true;
    }

    public int getState(int i, int j) {
        return stateMatrix[i][j];
    }

    public void recalculateHeuristic(NPuzzleHeuristic heuristicAlgorithm) {
        this.heurEvaluation = heuristicAlgorithm.evaluate(this);
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public int getHeurEvaluation() {
        return heurEvaluation;
    }
}