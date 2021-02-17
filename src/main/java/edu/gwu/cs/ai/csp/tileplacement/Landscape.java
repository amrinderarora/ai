package edu.gwu.cs.ai.csp.tileplacement;

import java.security.SecureRandom;
import java.util.Random;

/**
 * A landscape of bushes.
 * 
 * @author amrin
 */
public class Landscape {

    public static final int COLORS = 4;
    
    public static final String CELL_SEPARATOR = " ";

    public static final String LINE_SEPARATOR = System.lineSeparator();

    private int[][] bushes;

    private Random srandom = new SecureRandom();

    public Landscape(int width, int height) {
        bushes = new int[width][height];
        generateRandomBushes();
    }

    /** Randomly generates random bushes. */
    private void generateRandomBushes() {
        for (int i=0;i<bushes.length;i++) {
            for (int j = 0; j < bushes[i].length; j++) {
                generateOneBush(i, j);
            }
        }
    }

    public int[][] getBushes() {
        return bushes;
    }

    private void generateOneBush(int i, int j) {
        bushes[i][j] = (int) (srandom.nextDouble() * (COLORS + 1));
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bushes.length; i++) {
            for (int j = 0; j < bushes[i].length; j++) {
                if (bushes[i][j] > 0) {
                    sb.append(bushes[i][j] + CELL_SEPARATOR);
                } else {
                    sb.append(" " + CELL_SEPARATOR);
                }
            }
            sb.append(LINE_SEPARATOR);
        }
        return sb.toString();
    }
}
