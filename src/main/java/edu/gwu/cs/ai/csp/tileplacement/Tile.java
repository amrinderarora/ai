package edu.gwu.cs.ai.csp.tileplacement;

public class Tile {

    /** An identifier. */
    private Integer id;

    /** The size of this square tile. */
    private int size = 0;

    /** Tile orientation. Generally, the tiles can be rotated freely. */
    private TileShape tileShape = null;

    static final int TILE_SIZE = 4;

    public TileShape getTileShape() {
        return tileShape;
    }

    public void setTileShape(TileShape TileShape) {
        this.tileShape = TileShape;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int sizeArg) {
        this.size = sizeArg;
    }

    public void setShape(TileShape TileShapeArg) {
        this.tileShape = TileShapeArg;
    }

    public Integer getID() {
        return id;
    }

    public void setID(int idArg) {
        this.id = idArg;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void rotateRandomly() {
        // Rotation only affects the el shape, and for now, it does not do anything.
        if (tileShape == TileShape.EL_SHAPE) {
            // TODO: Rotate this el tile
        }
    }

    public String toString() {
        if (id != null) {
            return id + " " + size + " " + tileShape;
        }
        return size + " " + tileShape;
    }

    public boolean cover(int i, int j) {
        if (tileShape == TileShape.FULL_BLOCK) {
            return true;
        }

        if (tileShape == TileShape.OUTER_BOUNDARY) {
            if ((i == 0 || i == TILE_SIZE - 1) || (j == 0 || j == TILE_SIZE - 1)) {
                return true;
            }
        }

        if (tileShape == TileShape.EL_SHAPE) {
            if (i == 0 || j == 0) {
                return true;
            }
        }

        return false;
    }
}
