package edu.gwu.cs.ai.csp.tileplacement;

import org.junit.Assert;
import org.junit.Test;

public class TilePlacementProblemGeneratorTest {

    private final Double EPSILON = 0.001;

    private TilePlacementProblemGenerator generator = new TilePlacementProblemGenerator();

    @Test
    public void testGetBLCorner_0() {
        Assert.assertEquals(generator.getBLCorner(20, 0).getX(), 0, EPSILON);
        Assert.assertEquals(generator.getBLCorner(20, 0).getY(), 0, EPSILON);
    }

    @Test
    public void testGetBLCorner_3() {
        Assert.assertEquals(generator.getBLCorner(20, 3).getX(), 12, EPSILON);
        Assert.assertEquals(generator.getBLCorner(20, 3).getY(), 0, EPSILON);
    }

    @Test
    public void testGetBLCorner_19() {
        Assert.assertEquals(generator.getBLCorner(20, 19).getX(), 16, EPSILON);
        Assert.assertEquals(generator.getBLCorner(20, 19).getY(), 12, EPSILON);
    }

    @Test
    public void testGetBLCorner_11() {
        Assert.assertEquals(generator.getBLCorner(20, 11).getX(), 4, EPSILON);
        Assert.assertEquals(generator.getBLCorner(20, 11).getY(), 8, EPSILON);
    }
}