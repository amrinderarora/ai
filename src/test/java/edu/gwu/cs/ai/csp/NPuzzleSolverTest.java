package edu.gwu.cs.ai.csp;

import org.junit.Assert;
import org.junit.Test;

import edu.gwu.cs.ai.search.npuzzle.Direction;
import edu.gwu.cs.ai.search.npuzzle.NPuzzle;

public class NPuzzleSolverTest {

    @Test
    public void testOneMoveSolutionRight() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 6, 7, 0, 8 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Assert.assertFalse(currentState.isSolved());
        Assert.assertFalse(currentState.moveBlank(Direction.UP).isSolved());
        Assert.assertTrue(currentState.moveBlank(Direction.RIGHT).isSolved());
        // Assert.fail(currentState.moveBlank(Direction.DOWN).isSolved());
        Assert.assertFalse(currentState.moveBlank(Direction.LEFT).isSolved());
    }

    @Test
    public void testOneMoveSolutionDown() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 0, 7, 8, 6 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Assert.assertFalse(currentState.isSolved());
        Assert.assertFalse(currentState.moveBlank(Direction.UP).isSolved());
        // Assert.assertFalse(currentState.moveBlank(Direction.RIGHT).isSolved());
        Assert.assertTrue(currentState.moveBlank(Direction.DOWN).isSolved());
        Assert.assertFalse(currentState.moveBlank(Direction.LEFT).isSolved());
    }
}