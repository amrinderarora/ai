package edu.gwu.cs.ai.csp.tileplacement;

import java.security.SecureRandom;
import java.util.Random;

public enum TileOrientation {

    OUTER_BOUNDARY, // The tile, that only covers the outer boundary
    FULL_BLOCK, // The tile, that covers the entire area
    EL_SHAPE // The tile that covers one side and one adjacent side, in form of "L"
    ;

    private static Random random = new SecureRandom();

    public static TileOrientation getRandomTileOrientation() {
        double rndDouble = random.nextDouble();
        if (rndDouble < 1.0 / 3.0) {
            return OUTER_BOUNDARY;
        }
        if (rndDouble < 2.0 / 3.0) {
            return FULL_BLOCK;
        }
        return EL_SHAPE;
    }
}
