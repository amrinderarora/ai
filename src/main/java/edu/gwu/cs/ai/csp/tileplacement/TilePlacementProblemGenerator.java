package edu.gwu.cs.ai.csp.tileplacement;

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
        String fileContent = getFileContent(tpp);

        /** Writes everything to a file. */
        FileWriter fw = new FileWriter("tilesproblem_" + System.currentTimeMillis() + ".txt");
        fw.write(fileContent);
        fw.close();
    }

    public TilePlacementProblem generateProblem() {
        TilePlacementProblem tilePlacementProblem = new TilePlacementProblem();

        /** Generates the landscape. */
        int landscape_size = 20;
        Landscape landscape = new Landscape(landscape_size, landscape_size);
        tilePlacementProblem.setLandscape(landscape);

        /** Generates some tiles. */
        int num_tiles = 25;
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < num_tiles; i++) {
            Tile tile = new Tile();
            tile.setSize(4);
            tile.setOrientation(TileOrientation.getRandomTileOrientation());
            tiles.add(tile);
        }
        tilePlacementProblem.setTiles(tiles);

        /** Generates the target. */
        Map<Integer, Integer> colorsTarget = new HashMap<>();
        colorsTarget.put(1, 100);
        colorsTarget.put(2, 100);
        colorsTarget.put(3, 100);
        colorsTarget.put(4, 100);
        tilePlacementProblem.setColorsTarget(colorsTarget);

        return tilePlacementProblem;
    }

    /** Prepares the file content. */
    private static String getFileContent(TilePlacementProblem tpp) {
        StringBuffer sb = new StringBuffer();
        sb.append("# Tiles Problem, generated at: " + new java.util.Date() + System.lineSeparator());
        sb.append("# Landscape" + System.lineSeparator());
        sb.append(tpp.getLandscape());
        sb.append(System.lineSeparator());

        sb.append("# Tiles: " + System.lineSeparator());
        for (Tile tile : tpp.getTiles()) {
            sb.append(tile + System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        sb.append("# Targets: " + System.lineSeparator());
        for (Integer color : tpp.getColorsTarget().keySet()) {
            if (color > 0) {
                sb.append(color + ":" + tpp.getColorsTarget().get(color) + System.lineSeparator());
            }
        }
        sb.append(System.lineSeparator());

        return sb.toString();
    }

}
