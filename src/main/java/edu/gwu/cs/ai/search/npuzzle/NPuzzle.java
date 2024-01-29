package edu.gwu.cs.ai.search.npuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private int[][] stateMatrix;
    private int blankRow;
    private int blankCol;
    
    // navigation/search related properties
    // Level/Distance from root (root is at level/distance 0)
    private double distanceFromRoot = 0;
    private List<SearchState> successors;
    private int currSucessorIndex;
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
        if (!Arrays.deepEquals(stateMatrix, other.stateMatrix))
            return false;
        return true;
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
        return nPuzzleNewState;
    }

    @Override
    public NPuzzle clone() throws CloneNotSupportedException {
        NPuzzle np2 = (NPuzzle) super.clone();
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

	public int getCurrSucessorIndex() {
		return currSucessorIndex;
	}

	public void setCurrSucessorIndex(int currSucessorIndex) {
		this.currSucessorIndex = currSucessorIndex;
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

    @Override
    public Map<SearchState, Double> generateSuccessors() {
    	this.successors = new ArrayList<>();
    	this.setCurrSucessorIndex(0);
        Map<SearchState, Double> successorsMap = new HashMap<>();
        for (Direction dir : Direction.getAllDirections()) {
            if (this.movePossible(dir)) {
                NPuzzle nextState = this.moveBlank(dir);
                nextState.setDistanceFromRoot(this.distanceFromRoot + 1.0d);
                successorsMap.put(nextState, 1.0d);
                this.successors.add(nextState);
            }
        }
        return successorsMap;
    }
}