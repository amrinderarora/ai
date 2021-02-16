package edu.gwu.cs.ai.csp.tileplacement;

public class Tile {

    /** An identifier. */
    private Integer id;

    /** The size of this square tile. */
    private int size = 0;

    /** Tile orientation. Generally, the tiles can be rotated freely. */
    private TileShape TileShape = null;

    public TileShape getTileShape() {
        return TileShape;
    }

    public void setTileShape(TileShape TileShape) {
        this.TileShape = TileShape;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int sizeArg) {
        this.size = sizeArg;
    }

    public void setOrientation(TileShape TileShapeArg) {
        this.TileShape = TileShapeArg;
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
        if (TileShape == TileShape.EL_SHAPE) {
            // TODO: Rotate this el tile
        }
    }

    public String toString() {
        if (id != null) {
            return id + " " + size + " " + TileShape;
        }
        return size + " " + TileShape;
    }
}
