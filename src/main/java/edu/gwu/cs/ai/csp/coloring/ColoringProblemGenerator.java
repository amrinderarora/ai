package edu.gwu.cs.ai.csp.coloring;

import java.io.FileWriter;
import java.io.IOException;

public class ColoringProblemGenerator {
    public static void main(String args[]) throws IOException {
        ColoringProblemGenerator cpg = new ColoringProblemGenerator();

        for (int i = 0; i < 10; i++) {
            ColoringProblem cp = cpg.generateProblem();
            String fileContent = cp.getProblemDescription(); /// + System.lineSeparator() + System.lineSeparator() + tpp.getSolutionKey();

            // Writes everything to a file.
            FileWriter fw = new FileWriter("src/main/resources/csp/coloring/gc_" + System.nanoTime() + ".txt");
            fw.write(fileContent);
            fw.close();
        }
    }

    public ColoringProblem generateProblem() {
        ColoringProblem coloringProblem = new ColoringProblem();
        coloringProblem.initConstraints();
        return coloringProblem;
    }
}
