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

        for (int i = 0; i < 10; i++) {
            TilePlacementProblem tpp = tppg.generateProblem(20);
            String fileContent = tpp.getProblemDescription() + System.lineSeparator() + System.lineSeparator() + tpp.getSolutionKey();

            // Writes everything to a file.
            FileWriter fw = new FileWriter("tilesproblem_" + System.nanoTime() + ".txt");
            fw.write(fileContent);
            fw.close();
        }
    }

    public TilePlacementProblem generateProblem(int landscape_size) {
        TilePlacementProblem tilePlacementProblem = new TilePlacementProblem();

        // Generates the landscape.
        Landscape landscape = new Landscape(landscape_size, landscape_size);
        tilePlacementProblem.setLandscape(landscape);

        // Generates some tiles.
        int num_tiles = (landscape_size * landscape_size) / (Tile.TILE_SIZE * Tile.TILE_SIZE);
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < num_tiles; i++) {
            Tile tile = new Tile();
            tile.setID(i);
            tile.setSize(Tile.TILE_SIZE);
            tile.setShape(TileShape.getRandomTileShape());
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
            Point bottomLeft = getBLCorner(landscape_size, counter);
            int x = (int) bottomLeft.getX();
            int y = (int) bottomLeft.getY();
            // System.err.println("counter: " + counter + ", x: " + x + ", y: " + y);
            for (int i = 0; i < Tile.TILE_SIZE; i++) {
                for (int j = 0; j < Tile.TILE_SIZE; j++) {
                    int currentBush = landscape.getBushes()[x + i][y + j];
                    if (currentBush > 0 && !tile.cover(i, j)) {
                        // System.err.println("Bush visible: " + currentBush);
                        colorsTarget.put(currentBush, colorsTarget.get(currentBush) + 1);
                    }
                }
            }
            counter++;
        }
        tilePlacementProblem.setColorsTarget(colorsTarget);

        return tilePlacementProblem;
    }

    public Point getBLCorner(int landscape_size, int counter) {
        int numTiles = landscape_size / Tile.TILE_SIZE;
        int xdistance = counter % numTiles;
        int ydistance = counter / numTiles;
        return new Point(xdistance * Tile.TILE_SIZE, ydistance * Tile.TILE_SIZE);
    }
}
