package edu.gwu.cs.ai.csp.coloring;

import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.generate.NamedGraphGenerator;
import org.jgrapht.graph.DefaultEdge;

public class ColoringProblemGenerator {
    public static void main(String args[]) throws IOException {
        ColoringProblemGenerator cpg = new ColoringProblemGenerator();

        // Random secureRandom = new SecureRandom();
        for (int i = 1; i <= 4; i++) {
            // Set n = 20 + secureRandom.nextInt() % 200;
            ColoringProblem cp = cpg.generateProblem(i);
            String fileContent = cp.getProblemDescription(); /// + System.lineSeparator() + System.lineSeparator() + tpp.getSolutionKey();

            // Writes everything to a file.
            FileWriter fw = new FileWriter("src/main/resources/csp/coloring/gc_" + System.nanoTime() + ".txt");
            fw.write(fileContent);
            fw.close();
        }
    }

    public ColoringProblem generateProblem(int type) {
        ColoringProblem coloringProblem = new ColoringProblem();
        coloringProblem.setNumColors(4);
        Graph<Integer, DefaultEdge> graph = null;
        switch (type) {
        case 1:
            graph = NamedGraphGenerator.buckyBallGraph();
            break;
        case 2:
            graph = NamedGraphGenerator.kittellGraph();
            break;
        case 3:
            graph = NamedGraphGenerator.erreraGraph();
            break;
        case 4:
            graph = NamedGraphGenerator.poussinGraph();
            break;
        }
        coloringProblem.initGraph(graph);
        return coloringProblem;
    }
}
