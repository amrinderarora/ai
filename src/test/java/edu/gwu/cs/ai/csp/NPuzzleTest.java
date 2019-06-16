package edu.gwu.cs.ai.csp;

import org.junit.Assert;
import org.junit.Test;

import edu.gwu.cs.ai.search.npuzzle.Direction;
import edu.gwu.cs.ai.search.npuzzle.NPuzzle;

public class NPuzzleTest {

    @Test
    public void testIsSolved() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Assert.assertTrue(currentState.isSolved());
    }

    @Test
    public void testNotIsSolved() throws Exception {

        int[] stateArray = { 1, 2, 3, 5, 4, 6, 7, 8, 0 };

        NPuzzle currentState = new NPuzzle(stateArray);
        Assert.assertFalse(currentState.isSolved());
    }

    @Test
    public void testTwoMoveSolutionRightRight() throws Exception {

        int[] stateArray = { 1, 2, 3, 4, 5, 6, 0, 7, 8 };

        NPuzzle currentState = new NPuzzle(stateArray);
        currentState.moveBlank(Direction.RIGHT);
        currentState.moveBlank(Direction.RIGHT);
        Assert.assertTrue(currentState.isSolved());
    }
}