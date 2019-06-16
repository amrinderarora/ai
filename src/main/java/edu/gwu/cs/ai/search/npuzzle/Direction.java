package edu.gwu.cs.ai.search.npuzzle;

import java.security.SecureRandom;
import java.util.Random;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction getRandomDirection() {
        Random random = new SecureRandom();
        double rValue = random.nextDouble();
        if (rValue < 0.25) {
            return UP;
        }
        if (rValue < 0.5) {
            return DOWN;
        }
        if (rValue < 0.75) {
            return LEFT;
        }
        return RIGHT;
    }

    public static Direction getOppositeDirection(Direction direction) {
        if (direction == UP) {
            return DOWN;
        }
        if (direction == DOWN) {
            return UP;
        }
        if (direction == LEFT) {
            return RIGHT;
        }
        return LEFT;
    }

    public static Direction getRandomDirectionExcluding(Direction direction) {
        Random random = new SecureRandom();
        double rValue = random.nextDouble();
        if (rValue < 1 / 3) {
            return UP;
        }
        if (rValue < 2 / 3) {
            return DOWN;
        }
        return RIGHT;
    }
}
