package edu.gwu.cs.ai.search.npuzzle;

import org.junit.Assert;
import org.junit.Test;

import edu.gwu.cs.ai.search.npuzzle.ManhattanTilesHeuristic;
import edu.gwu.cs.ai.search.npuzzle.NPuzzle;

public class ManhattanTilesHeuristicTest {

    private final Double EPSILON = 0.001;

    private ManhattanTilesHeuristic heuristic = new ManhattanTilesHeuristic();

    @Test
    public void testSameLocation() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 0, 0, 1), 0, EPSILON);
    }

    @Test
    public void testDifferentLocationNotZero() {
        Assert.assertNotEquals(heuristic.getManhattanDistance(3, 0, 0, 2), 0, EPSILON);
    }

    @Test
    public void testSameRowLess() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 0, 4, 3), 2, EPSILON);
    }

    @Test
    public void testSameRowMore() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 0, 2, 8), 3, EPSILON);
    }

    @Test
    public void testSameColLess() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 1, 2, 5), 1, EPSILON);
    }

    @Test
    public void testLastColSameRow() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 1, 1, 4), 1, EPSILON); // Should be 1,4
    }

    @Test
    public void testSameColsMore() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 1, 0, 9), 3, EPSILON); // Should be 1, 3
    }

    @Test
    public void testDiffRowCol1() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 1, 2, 21), 5, EPSILON); // Should be 4,0
    }

    @Test
    public void testDiffRowCol2() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 3, 3, 1), 6, EPSILON); // Should be 0,0
    }

    @Test
    public void testDiffRowCol3() {
        Assert.assertEquals(heuristic.getManhattanDistance(3, 2, 2, 5), 2, EPSILON); // Should be 0,4
    }

    // 2, 6, 4 --> 1 + 2 + 3 = 6
    // 3, 1, 7 --> 3 + 2 + 3 = 8
    // 8, 5, 0 --> 1 + 1 + 0 = 2
    @Test
    public void testSample1() {
        int[] stateArray = { 2, 6, 4, 3, 1, 7, 8, 5, 0 };
        NPuzzle nPuzzle = new NPuzzle(stateArray);
        double h = heuristic.evaluate(nPuzzle);
        Assert.assertEquals(h, 16, EPSILON);
    }
}