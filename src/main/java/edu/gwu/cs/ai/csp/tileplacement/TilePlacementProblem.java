package edu.gwu.cs.ai.csp.tileplacement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gwu.cs.PracticeProblem;

public class TilePlacementProblem implements PracticeProblem {

    private Landscape landscape;
    private List<Tile> tiles = new ArrayList<>();
    private Map<Integer, Integer> colorsTarget = new HashMap<>();
    private Map<TileShape, Integer> tilesMap = new HashMap<>();

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
        updateTilesMap();
    }

    private void updateTilesMap() {
        tilesMap.clear();
        for (Tile tile : tiles) {
            Integer currentCount = tilesMap.get(tile.getTileShape());
            if (currentCount == null) {
                currentCount = 0;
            }
            tilesMap.put(tile.getTileShape(), currentCount + 1);
        }
    }

    public Map<Integer, Integer> getColorsTarget() {
        return colorsTarget;
    }

    public void setColorsTarget(Map<Integer, Integer> colorsTargetArg) {
        this.colorsTarget = colorsTargetArg;
    }

    @Override
    public String getProblemDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append("# Tiles Problem, generated at: " + new java.util.Date() + System.lineSeparator());
        sb.append("# Landscape" + System.lineSeparator());
        sb.append(this.getLandscape());
        sb.append(System.lineSeparator());

        sb.append("# Tiles: " + System.lineSeparator());
        sb.append(tilesMap + System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("# Targets: " + System.lineSeparator());
        for (Integer color : this.getColorsTarget().keySet()) {
            if (color > 0) {
                sb.append(color + ":" + this.getColorsTarget().get(color) + System.lineSeparator());
            }
        }
        sb.append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public String getSolutionKey() {
        StringBuffer sb = new StringBuffer();
        sb.append("# Tiles Problem Solution Key, generated at: " + new java.util.Date() + System.lineSeparator());

        sb.append("# Tiles: " + System.lineSeparator());
        for (Tile tile : this.getTiles()) {
            sb.append(tile + System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        return sb.toString();
    }

}

