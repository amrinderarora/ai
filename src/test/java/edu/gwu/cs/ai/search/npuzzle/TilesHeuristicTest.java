package edu.gwu.cs.ai.search.npuzzle;
import org.junit.Assert;
import org.junit.Test;

public class TilesHeuristicTest {

    private final Double EPSILON = 0.001;

    private TilesHeuristic heuristic = new TilesHeuristic();

    @Test
    public void testIsTileMisplaced() {
        Assert.assertFalse(heuristic.isTileMisplaced(5, 0, 0, 1));
        Assert.assertTrue(heuristic.isTileMisplaced(5, 0, 1, 3));
    }

    @Test
    public void testEvaluate1() {
    	NPuzzle npuzzle = new NPuzzle( new int[] {1,2,3,4,5,7,6,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,0});
        Assert.assertEquals(heuristic.evaluate(npuzzle),2, EPSILON);
    }
    
    @Test
    public void testEvaluate2() {
    	NPuzzle npuzzle = new NPuzzle( new int[] {3,1,2,4,5,7,6,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,0});
        Assert.assertEquals(heuristic.evaluate(npuzzle),5, EPSILON);
    }
}