package edu.gwu.cs.ai.search;

import edu.gwu.cs.ai.game.Game;
import edu.gwu.cs.ai.game.GameResult;

class GameMatrix implements Cloneable {
    private static final String NEW_LINE = System.getProperty("line.separator");
    public static final int ROWS = 3;
    public static final int COLS = 10;
    public static final int TARGET_LENGTH = 3;
    char[][] gameMatrix = new char[COLS][ROWS];

    public GameMatrix() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                gameMatrix[i][j] = Connect4.COLOR_BLANK;
            }
        }
    }

    public String getPrintVersion() {
        StringBuffer sb = new StringBuffer();
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLS; col++) {
                sb.append(" " + gameMatrix[col][row] + " ");
            }
            sb.append(NEW_LINE);
        }
        return sb.toString();
    }

    /**
     * Drop a coin of color "color" in column "col
     * 
     * @param color
     * @param col
     * 
     * @return
     */
    public GameResult move(char color, int col) {
        // First of all, find where this this will drop to (gravity)
        int height = 0;
        while (height < ROWS) {
            if (gameMatrix[col][height] == Connect4.COLOR_BLANK) {
                break;
            }
            height++;
        }
        if (height >= ROWS) {
            throw new IllegalArgumentException("This column does not have an open slot!");
        }

        // Color the game matrix
        gameMatrix[col][height] = color;

        // Check if we have already won
        if (connect4Exists(color)) {
            return GameResult.WIN;
        }
        return GameResult.IN_PROGRESS;
    }

    /** Checks if game matrix is now solved using the given x y value. */
    private boolean connect4Exists(char color) {
        // For each value of height, Check the rows
        for (int j = 0; j < ROWS; j++) {
            for (int i = 0; i < COLS - TARGET_LENGTH; i++) {
                boolean found = true;
                for (int k = 0; k < TARGET_LENGTH; k++) {
                    if (gameMatrix[i + k][j] != color) {
                        found = false;
                        i = k;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }

        // For each value of row, Check the columns
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS - TARGET_LENGTH; j++) {
                boolean found = true;
                for (int k = 0; k < TARGET_LENGTH; k++) {
                    if (gameMatrix[i][j + k] != color) {
                        found = false;
                        j = k;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }

        // Diagonals
        // TODO: Still need to check the diagonals

        return false;
    }

    @Override
    public GameMatrix clone() {
        GameMatrix gmCopy = new GameMatrix();
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLS; col++) {
                gmCopy.gameMatrix[col][row] = this.gameMatrix[col][row];
            }
        }
        return gmCopy;
    }
}

public class Connect4 implements Game {

    public static final char COLOR_RED = 'R';
    public static final char COLOR_BLUE = 'B';
    public static final char COLOR_BLANK = '_';
    private static final int PRINT_DEPTH = 3;

    public static long globalCount = 0;

    public GameResult solve(GameMatrix gm, char color, int recDepth) {

        if (recDepth < PRINT_DEPTH) {
            System.out.println("solve(" + recDepth + "," + color + "), solving the board: \n" + gm.getPrintVersion());
        }

        globalCount++;

        // This is the best result we know so far
        GameResult currBestResult = GameResult.LOSS;

        boolean noValidMove = true;

        for (int colOption = 0; colOption < GameMatrix.COLS; colOption++) {
            // Checks the top of the column (ROWS-1) location, if empty, then in Connect 4 that column is considered an option
            if (gm.gameMatrix[colOption][GameMatrix.ROWS - 1] == COLOR_BLANK) {

                // Found a valid move: colOption
                noValidMove = false;
                if (recDepth < PRINT_DEPTH) {
                    System.out.println("solve(" + recDepth + "," + color + "), found a valid move: " + colOption + " for color: " + color);
                }

                // Creates a copy of the world state to pass along in the recursive calls
                GameMatrix gmtrial = gm.clone();

                // Makes this move!
                GameResult moveResult = gmtrial.move(color, colOption);

                // If win right away, then return that result!
                if (moveResult == GameResult.WIN) {
                    return GameResult.WIN;
                }

                // Otherwise, we recursively call as the opponent
                GameResult otherSidesResult = solve(gmtrial, otherColor(color), recDepth + 1);

                // Improve our current result, if we can..
                if (GameResult.DRAW == otherSidesResult && currBestResult == GameResult.LOSS) {
                    currBestResult = GameResult.DRAW;
                } else if (GameResult.LOSS == otherSidesResult) {
                    currBestResult = GameResult.WIN;
                }

                if (currBestResult == GameResult.WIN) {
                    return GameResult.WIN;
                }

                if (recDepth < PRINT_DEPTH) {
                    System.out.println("solve(" + recDepth + ", " + color + "), checking option " + colOption
                            + ", didn't return a win yet");
                }

            }
        }
        if (noValidMove) {
            return GameResult.DRAW;
        }

        return currBestResult;
    }

    public static char otherColor(char color) {
        if (COLOR_RED == color) {
            return COLOR_BLUE;
        }
        if (COLOR_BLUE == color) {
            return COLOR_RED;
        }
        throw new IllegalArgumentException("Unknown color: " + color);
    }

    public static void main(String[] args) {
        // GameMatrix gm1 = new GameMatrix();
        // gm1.move(COLOR_BLUE, 0);
        // gm1.move(COLOR_RED, 0);
        // gm1.move(COLOR_BLUE, 0);
        // gm1.move(COLOR_RED, 2);
        // gm1.move(COLOR_BLUE, 2);
        // gm1.move(COLOR_RED, 4);
        // gm1.move(COLOR_BLUE, 2);
        // gm1.move(COLOR_RED, 4);
        // gm1.move(COLOR_BLUE, 1);
        // gm1.move(COLOR_RED, 1);
        // System.out.println("Result: " + gm1.move(COLOR_BLUE, 1));
        // System.out.println("solve(): gameMatrix: \n" + gm1.getPrintVersion());

        Connect4 c4 = new Connect4();
        System.out.println("Solution: " + c4.solve(new GameMatrix(), COLOR_RED, 0));

        System.out.println("globalCount: " + globalCount);
    }
}
