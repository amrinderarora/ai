package edu.gwu.cs.ai.csp.coloring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.generate.NamedGraphGenerator;
import org.jgrapht.graph.DefaultEdge;

import edu.gwu.cs.ai.csp.CSPSearchAlgorithm;
import edu.gwu.cs.ai.csp.Constraint;
import edu.gwu.cs.ai.csp.GenericCSPSolver;

public class ColoringProblemGenerator {
    public static void main(String args[]) throws IOException {
        ColoringProblemGenerator cpg = new ColoringProblemGenerator();

        CSPSearchAlgorithm solver = new GenericCSPSolver();
        
        // Random secureRandom = new SecureRandom();
        for (int i = 1; i <= 4; i++) {
            // Set n = 20 + secureRandom.nextInt() % 200;
            ColoringProblem cp = cpg.generate4ColorableProblem(i);
            
            solver.solve(cp);
            
            
//            String fileContent = cp.getProblemDescription();
//
//            // Writes everything to a file.
//            FileWriter fw = new FileWriter("src/main/resources/csp/coloring/gc_" + System.nanoTime() + ".txt");
//            fw.write(fileContent);
//            fw.close();
        }
    }

    public ColoringProblem generate4ColorableProblem(int graphType) {
        ColoringProblem coloringProblem = new ColoringProblem();
        coloringProblem.setNumColors(4);
        
        Graph<Integer, DefaultEdge> graph = null;
        switch (graphType) {
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
        
        System.err.println("vertices: " + graph.vertexSet().size());
		coloringProblem.initVariables(graph.vertexSet().size());
        
    	List<Constraint> constraints = new ArrayList<>();
    	for (DefaultEdge edge : graph.edgeSet()) {
    		// Need to create a coloring constraint, also adjusting the zero indexing to 1 indexing.
    		constraints.add(new ColoringConstraint(graph.getEdgeSource(edge) + 1, graph.getEdgeTarget(edge) + 1));
    	}

        coloringProblem.setConstraints(constraints);
        return coloringProblem;
    }
}
