package edu.gwu.cs.ai.search.npuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gwu.cs.ai.search.SearchState;

/**
 * Models the search state of the n-puzzle.
 * There are no algorithms or solvers here.
 * 
 * @author amrinder
 */
public class NPuzzle implements Cloneable, SearchState {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private int size = 4;
	private static final int ZERO = 0;
	private static final Double DOUBLE_ONE = Double.valueOf(1);
    private int[][] stateMatrix;
    private int blankRow;
    private int blankCol;
    
    // navigation/search related properties
    // Level/Distance from root (root is at level/distance 0)
    private double distanceFromRoot = 0;
    private List<SearchState> successors;
    private int currSuccessorIndex = 0;
    private Direction lastDirection;

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
        if (Arrays.deepEquals(stateMatrix, other.stateMatrix))
            return true;
        return false;
    }

    /** Creates an instance of n-puzzle. By default, it is solved. */
    public NPuzzle(int sideArg) {
        this.size = sideArg;
        stateMatrix = new int[size][size];

        int counter = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                stateMatrix[i][j] = counter++;
            }
        }
        stateMatrix[size - 1][size - 1] = 0; // Bottom Right is the blank
        setBlank(size - 1, size - 1); // Bottom Right is the blank
    }

    /** Creates an instance of n-puzzle by copying the given one dimensional array.*/
    public NPuzzle(int[] stateArray) {
        this.size = (int) Math.sqrt(stateArray.length);
        stateMatrix = new int[size][size];
        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
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
     * Moves the BLANK in the specified direction, and returns the NEW state.
     * The original state is not modified.
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
        return nPuzzleNewState;
    }

    @Override
    public NPuzzle clone() throws CloneNotSupportedException {
        NPuzzle np2 = (NPuzzle) super.clone();
        np2.successors = new ArrayList<>();
        np2.currSuccessorIndex = 0;
        np2.stateMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                np2.stateMatrix[i][j] = this.stateMatrix[i][j];
            }
        }

        return np2;
    }

    public boolean movePossible(Direction direction) {
        if (blankRow == 0 && direction == Direction.UP
                || blankRow == size - 1 && direction == Direction.DOWN
                || blankCol == 0 && direction == Direction.LEFT
                || blankCol == size - 1 && direction == Direction.RIGHT) {
            return false;
        }
        return true;
    }

    public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}


    public int getState(int i, int j) {
        return stateMatrix[i][j];
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public double getDistanceFromRoot() {
		return distanceFromRoot;
	}

	public void setDistanceFromRoot(double distFromRoot) {
		this.distanceFromRoot = distFromRoot;
	}

	public int getCurrSuccessorIndex() {
		return currSuccessorIndex;
	}

	public void setCurrSuccessorIndex(int currSucessorIndex) {
		this.currSuccessorIndex = currSucessorIndex;
	}

	public List<SearchState> getSuccessors() {
		return this.successors;
	}
	
	public void setSuccessors(List<SearchState> successors) {
		this.successors = successors;
	}


    @Override
    public boolean isGoalState() {
        int counter = 1;
        // Just traverses left to right, and then top to down and matches that to the counter
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // This is for the blank
                if (i == size - 1 & j == size - 1) {
                    continue;
                }
                if (this.stateMatrix[i][j] != counter++) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Returns the next successor, in the order UP, RIGHT, DOWN, LEFT.
     * Returns null if nothing possible.
     * 
     * @return
     */
    public SearchState getNextSuccessor() {
    	NPuzzle nextState = null;
    	while (nextState == null && currSuccessorIndex < 4) {
    		Direction dir = getMatchingDirection(currSuccessorIndex);
    		if (this.movePossible(dir)) {
    			nextState = this.moveBlank(dir);
                nextState.setDistanceFromRoot(this.distanceFromRoot + DOUBLE_ONE);
    			// System.out.println("Returning successor: " + this.getPrintVersion() + ", next:" + nextState.getPrintVersion());
    		}
			this.currSuccessorIndex++;
    	}
    	return nextState;
    }

    /**
     * 0 --> UP
     * 1 --> RIGHT
     * 2 --> DOWN
     * 3 --> LEFT
     * Illegal Argument
     */
    private Direction getMatchingDirection(int idx) {
    	if (idx == 0) {
    		return Direction.UP;
    	}
    	if (idx == 1) {
    		return Direction.RIGHT;
    	}
    	if (idx == 2) {
    		return Direction.DOWN;
    	}
    	if (idx == 3) {
    		return Direction.LEFT;
    	}
    	throw new IllegalArgumentException("Index out of range: " + idx);
	}
}