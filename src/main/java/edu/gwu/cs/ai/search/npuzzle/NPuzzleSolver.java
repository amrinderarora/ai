package edu.gwu.cs.ai.search.npuzzle;

import java.util.HashMap;
import java.util.Map;

public class NPuzzleSolver {

    public static final String NEW_LINE = "\r\n";
    public long globalCount = 0;
    private NPuzzleHeuristic heuristicAlgorithm = new ManhattanTilesHeuristic();

    public void solve(NPuzzle nPuzzle) throws Exception {
        nPuzzle.recalculateHeuristic(heuristicAlgorithm);

        boolean found = false;
        while (!found) {
            int currentValue = nPuzzle.getHeurEvaluation();
            System.err.println("solve(): currentValue: " + currentValue + NEW_LINE + nPuzzle.getPrintVersion());

            globalCount++;
            if (globalCount % 1 == 0) {
                Thread.sleep(500);
            }

            Map<Direction, NPuzzle> storedMaps = new HashMap<>();
            if (evaluateMove(nPuzzle, Direction.LEFT, storedMaps)) {
                return;
            }

            if (evaluateMove(nPuzzle, Direction.RIGHT, storedMaps)) {
                return;
            }

            if (evaluateMove(nPuzzle, Direction.DOWN, storedMaps)) {
                return;
            }

            if (evaluateMove(nPuzzle, Direction.UP, storedMaps)) {
                return;
            }

            int minValue = getMinValue(storedMaps);
            // System.out.println("minValue: " + minValue + ", storedMaps size: " + storedMaps.size());

            if (minValue < currentValue) {
                System.out.println("currentValue: " + currentValue + ", minValue: " + minValue);
                nPuzzle = getMatchingMove(storedMaps, minValue);
                System.out.println("New puzzle: " + NEW_LINE + nPuzzle.getPrintVersion());
            } else {
                // We are in a local minima, take one randomly, but not the opposite of the last one
                while (true) {
                    Direction direction = Direction.getRandomDirection();
                    // System.out.println("direction: " + direction);
                    if (storedMaps.get(direction) != null && Direction.getOppositeDirection(direction) != nPuzzle.getLastDirection()) {
                        nPuzzle = storedMaps.get(direction);
                        break;
                    }
                }
            }
        }
    }

    /** Finds the puzzle state that matches the given value. */
    private NPuzzle getMatchingMove(Map<Direction, NPuzzle> storedMaps, int value) {
        if (storedMaps.get(Direction.LEFT) != null && storedMaps.get(Direction.LEFT).getHeurEvaluation() == value) {
            return storedMaps.get(Direction.LEFT);
        }
        if (storedMaps.get(Direction.RIGHT) != null && storedMaps.get(Direction.RIGHT).getHeurEvaluation() == value) {
            return storedMaps.get(Direction.RIGHT);
        }
        if (storedMaps.get(Direction.UP) != null && storedMaps.get(Direction.UP).getHeurEvaluation() == value) {
            return storedMaps.get(Direction.UP);
        }
        if (storedMaps.get(Direction.DOWN) != null && storedMaps.get(Direction.DOWN).getHeurEvaluation() == value) {
            return storedMaps.get(Direction.DOWN);
        }
        throw new IllegalArgumentException("No matching value found");
    }

    private int getMinValue(Map<Direction, NPuzzle> states) {
        int currMin = Integer.MAX_VALUE;
        currMin = adjustCurrMin(states, currMin, Direction.LEFT);
        currMin = adjustCurrMin(states, currMin, Direction.RIGHT);
        currMin = adjustCurrMin(states, currMin, Direction.UP);
        currMin = adjustCurrMin(states, currMin, Direction.DOWN);
        return currMin;
    }

    private int adjustCurrMin(Map<Direction, NPuzzle> states, int currMin, Direction direction) {
        NPuzzle next = states.get(direction);
        if (next != null) {
            currMin = Math.min(currMin, next.getHeurEvaluation());
        }
        return currMin;
    }

    /**
     * Evaluates the given move from the current state. Returns true if the given move entirely solves the puzzle.
     * Stores the new state in the given store. If the move is not even possible, then it returns false immediately.
     * 
     * @param currentState
     * @param storedMaps
     * @param direction
     * @return
     * @throws Exception
     */
    public boolean evaluateMove(NPuzzle currentState, Direction direction, Map<Direction, NPuzzle> storedMaps)
            throws Exception {
        if (!currentState.movePossible(direction)) {
            return false;
        }
        NPuzzle newState = currentState.clone();
        newState.moveBlank(direction);
        boolean isNewStateSolved = newState.isSolved();
        newState.recalculateHeuristic(heuristicAlgorithm);
        storedMaps.put(direction, newState);
        if (isNewStateSolved) {
            print(direction, newState, true);
            return true;
        }
        return false;
    }

    private void print(Direction direction, NPuzzle npuzzle, boolean terminal) {
        System.out.println("Direction: " + direction);
        System.out.println(npuzzle);
        if (terminal) {
            System.out.println("[SOLVED]");
            System.out.println("Total Moves: " + globalCount);
        }
    }
}
