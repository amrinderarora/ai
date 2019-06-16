package edu.gwu.cs.ai.search.npuzzle;

public class Main {
    public static void main(String[] args) throws Exception {
        int targetMoveCount = 200;
        NPuzzle puzzle1 = new NPuzzleGenerator().generate(targetMoveCount);
        System.out.println("targetMoveCount: " + targetMoveCount + ", puzzle:\r\n" + puzzle1.getPrintVersion());

        NPuzzleSolver solver = new NPuzzleSolver();
        solver.solve(puzzle1);
    }
}