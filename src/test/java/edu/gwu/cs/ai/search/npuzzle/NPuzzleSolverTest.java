package edu.gwu.cs.ai.search.npuzzle;

import org.junit.Assert;
import org.junit.Test;

public class NPuzzleSolverTest {

    @Test
    public void testOneMoveSolutionRight() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 6, 7, 0, 8 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Assert.assertFalse(currentState.isGoalState());
        Assert.assertFalse(currentState.moveBlank(Direction.UP).isGoalState());
        Assert.assertTrue(currentState.moveBlank(Direction.RIGHT).isGoalState());
        // Assert.fail(currentState.moveBlank(Direction.DOWN).isSolved());
        Assert.assertFalse(currentState.moveBlank(Direction.LEFT).isGoalState());
    }

    @Test
    public void testOneMoveSolutionDown() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 0, 7, 8, 6 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Assert.assertFalse(currentState.isGoalState());
        Assert.assertFalse(currentState.moveBlank(Direction.UP).isGoalState());
        // Assert.assertFalse(currentState.moveBlank(Direction.RIGHT).isSolved());
        Assert.assertTrue(currentState.moveBlank(Direction.DOWN).isGoalState());
        Assert.assertFalse(currentState.moveBlank(Direction.LEFT).isGoalState());
    }
}