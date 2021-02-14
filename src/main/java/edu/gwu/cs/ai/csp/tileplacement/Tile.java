package edu.gwu.cs.ai.csp.tileplacement;

public class Tile {

    /** The size of this square tile. */
    private int size = 0;

    /** Tile orientation. Generally, the tiles can be rotated freely. */
    private TileOrientation tileOrientation = null;

    public TileOrientation getTileOrientation() {
        return tileOrientation;
    }

    public void setTileOrientation(TileOrientation tileOrientation) {
        this.tileOrientation = tileOrientation;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int sizeArg) {
        this.size = sizeArg;
    }

    public void setOrientation(TileOrientation tileOrientationArg) {
        this.tileOrientation = tileOrientationArg;
    }

    public String toString() {
        return size + " " + tileOrientation;
    }

}
