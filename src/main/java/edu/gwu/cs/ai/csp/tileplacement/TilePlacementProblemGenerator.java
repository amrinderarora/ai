package edu.gwu.cs.ai.csp.tileplacement;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TilePlacementProblemGenerator {
    public static void main(String args[]) throws IOException {
        TilePlacementProblemGenerator tppg = new TilePlacementProblemGenerator();

        TilePlacementProblem tpp = tppg.generateProblem();
        String fileContent = tpp.getProblemDescription() + System.lineSeparator() + System.lineSeparator() + tpp.getSolutionKey();

        /** Writes everything to a file. */
        FileWriter fw = new FileWriter("tilesproblem_" + System.currentTimeMillis() + ".txt");
        fw.write(fileContent);
        fw.close();
    }

    public TilePlacementProblem generateProblem() {
        TilePlacementProblem tilePlacementProblem = new TilePlacementProblem();

        // Generates the landscape.
        int landscape_size = 20;
        Landscape landscape = new Landscape(landscape_size, landscape_size);
        tilePlacementProblem.setLandscape(landscape);

        // Generates some tiles.
        int num_tiles = 25;
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < num_tiles; i++) {
            Tile tile = new Tile();
            tile.setID(i);
            tile.setSize(4);
            tile.setOrientation(TileShape.getRandomTileShape());
            tile.rotateRandomly();
            tiles.add(tile);
        }
        tilePlacementProblem.setTiles(tiles);

        // Generates the "puzzle" - the color target
        Map<Integer, Integer> colorsTarget = new HashMap<>();
        colorsTarget.put(1, 0);
        colorsTarget.put(2, 0);
        colorsTarget.put(3, 0);
        colorsTarget.put(4, 0);
        int counter = 0;
        for (Tile tile : tiles) {
            counter++;
            Point bottomLeft = getBLCorner(counter);
            int[][] exposedCells = getExposedCells(tile, bottomLeft);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (exposedCells[i][j] > 0) {
                        colorsTarget.put(exposedCells[i][j], colorsTarget.get(exposedCells[i][j]) + 1);
                    }
                }
            }
        }
        tilePlacementProblem.setColorsTarget(colorsTarget);

        return tilePlacementProblem;
    }

    private int[][] getExposedCells(Tile tile, Point bottomLeft) {
        int[][] exposedCells = new int[4][4];
        return exposedCells;
    }

    private Point getBLCorner(int counter) {
        return new Point(0, 0);
    }
}
