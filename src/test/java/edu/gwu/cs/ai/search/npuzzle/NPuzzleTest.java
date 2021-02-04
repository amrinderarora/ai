package edu.gwu.cs.ai.search.npuzzle;

import org.junit.Assert;
import org.junit.Test;

public class NPuzzleTest {

    @Test
    public void testIsSolved() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Assert.assertTrue(currentState.isGoalState());
    }

    @Test
    public void testNotIsSolved() throws Exception {

        int[] stateArray = { 1, 2, 3, 5, 4, 6, 7, 8, 0 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Assert.assertFalse(currentState.isGoalState());
    }

    @Test
    public void testTwoMoveSolutionRightRight() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 6, 0, 7, 8 };

        NPuzzle currentState = new NPuzzle(stateArray);
        currentState.moveBlank(Direction.RIGHT);
        currentState.moveBlank(Direction.RIGHT);
        Assert.assertTrue(currentState.isGoalState());
    }
}