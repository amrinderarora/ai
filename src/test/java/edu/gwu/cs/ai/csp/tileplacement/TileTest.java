package edu.gwu.cs.ai.csp.tileplacement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileTest {

    private Tile tile_fb = new Tile();
    private Tile tile_bo = new Tile();

    @Before
    public void prepare() {
        tile_fb.setShape(TileShape.FULL_BLOCK);
        tile_bo.setShape(TileShape.OUTER_BOUNDARY);
    }

    @Test
    public void coverFB() {
        Assert.assertTrue(tile_fb.cover(0, 0));
        Assert.assertTrue(tile_fb.cover(0, 1));
        Assert.assertTrue(tile_fb.cover(0, 0));
        Assert.assertTrue(tile_fb.cover(2, 0));
        Assert.assertTrue(tile_fb.cover(3, 3));
    }

    @Test
    public void coverOuter() {
        Assert.assertTrue(tile_bo.cover(0, 0));
        Assert.assertTrue(tile_bo.cover(0, 1));
        Assert.assertFalse(tile_bo.cover(2, 2));
        Assert.assertTrue(tile_bo.cover(3, 3));
    }
}