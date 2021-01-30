package edu.gwu.cs.ai.search.npuzzle;

/**
 * Generates n puzzles.
 * 
 * @author amrinder
 */
public class NPuzzleGenerator {

    /**
     * Uses the given target move count.
     * Because of some weird cycles, it is possible that
     * actual move solution is less than targetMoveCount, but it
     * can never be more than that.
     * 
     * @throws CloneNotSupportedException
     */
    public NPuzzle generate(int size, int targetMoveCount) throws CloneNotSupportedException {

        int moveCount = 0;

        NPuzzle npuzzle = new NPuzzle(size);
        Direction previousDirection = null;

        while (moveCount < targetMoveCount) {
            Direction currentDirection = Direction.getRandomDirection();
            if (Direction.getOppositeDirection(currentDirection) == previousDirection) {
                continue;
            }
            if (!npuzzle.movePossible(currentDirection)) {
                continue;
            }
            npuzzle = npuzzle.moveBlank(currentDirection);
            moveCount++;
            previousDirection = currentDirection;
        }

        return npuzzle;
    }

}
