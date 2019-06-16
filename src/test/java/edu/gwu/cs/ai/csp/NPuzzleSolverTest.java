package edu.gwu.cs.ai.csp;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import edu.gwu.cs.ai.search.npuzzle.Direction;
import edu.gwu.cs.ai.search.npuzzle.NPuzzle;
import edu.gwu.cs.ai.search.npuzzle.NPuzzleSolver;

public class NPuzzleSolverTest {

    private NPuzzleSolver solver = new NPuzzleSolver();

    @Test
    public void testOneMoveSolutionRight() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 6, 7, 0, 8 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Map<Direction, NPuzzle> storedMaps = new HashMap<>();
        Assert.assertFalse(solver.evaluateMove(currentState, Direction.DOWN, storedMaps));
        Assert.assertFalse(solver.evaluateMove(currentState, Direction.UP, storedMaps));
        Assert.assertFalse(solver.evaluateMove(currentState, Direction.LEFT, storedMaps));
        Assert.assertTrue(solver.evaluateMove(currentState, Direction.RIGHT, storedMaps));
    }

    @Test
    public void testOneMoveSolutionDown() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 0, 7, 8, 6 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Map<Direction, NPuzzle> storedMaps = new HashMap<>();
        Assert.assertFalse(solver.evaluateMove(currentState, Direction.UP, storedMaps));
        Assert.assertTrue(solver.evaluateMove(currentState, Direction.DOWN, storedMaps));
        Assert.assertFalse(solver.evaluateMove(currentState, Direction.LEFT, storedMaps));
        Assert.assertFalse(solver.evaluateMove(currentState, Direction.RIGHT, storedMaps));
    }
}