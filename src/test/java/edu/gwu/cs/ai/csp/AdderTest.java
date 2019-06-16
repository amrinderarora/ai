package edu.gwu.cs.ai.csp;

import org.junit.Assert;
import org.junit.Test;

public class AdderTest {

    private final Double EPSILON = 0.001;

    @Test
    public void testAdd() {

        Adder adder = new Adder();

        Assert.assertEquals(adder.calculate(3, 5), 8, EPSILON);
    }

}