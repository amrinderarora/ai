package edu.gwu.cs.ai.csp.tileplacement;

import java.util.List;
import java.util.Map;

public class TilePlacementProblem {

    private Landscape landscape;
    private List<Tile> tiles;
    private Map<Integer, Integer> colorsTarget;

    public Landscape getLandscape() {
        return landscape;
    }

    public void setLandscape(Landscape landscapeArg) {
        this.landscape = landscapeArg;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tilesArg) {
        this.tiles = tilesArg;
    }

    public Map<Integer, Integer> getColorsTarget() {
        return colorsTarget;
    }

    public void setColorsTarget(Map<Integer, Integer> colorsTargetArg) {
        this.colorsTarget = colorsTargetArg;
    }
}

