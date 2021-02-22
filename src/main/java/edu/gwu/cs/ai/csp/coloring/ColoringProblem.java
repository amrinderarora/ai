package edu.gwu.cs.ai.csp.coloring;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import edu.gwu.cs.PracticeProblem;
import edu.gwu.cs.ai.csp.CSP;
import edu.gwu.cs.ai.csp.CSPSearchState;
import edu.gwu.cs.ai.csp.Constraint;

public class ColoringProblem implements CSP, PracticeProblem {

    /** Number of colors. */
    private int numColors;

    /** Coloring constraints. */
    private List<Constraint> constraints;

    public int getNumColors() {
        return numColors;
    }

    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    @Override
    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    @Override
    public CSPSearchState getInitialState() {
        // TODO Auto-generated method stub
        return null;
    }

    public void initPlanarGraph(int n) {
    }

    public void initConstraints() {
        this.constraints = new ArrayList<>();
        Constraint c1 = new ColoringConstraint(1, 2);
        constraints.add(c1);
    }

    @Override
    public String getProblemDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append("# Coloring Problem, generated at: " + new java.util.Date() + System.lineSeparator());
        sb.append("# Colors" + System.lineSeparator());
        sb.append("colors = " + this.numColors);
        sb.append(System.lineSeparator());

        sb.append("# Graph: " + System.lineSeparator());
        for (Constraint cons : constraints) {
            sb.append(cons.toString() + System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public String getSolutionKey() {
        StringBuffer sb = new StringBuffer();
        sb.append("# Coloring Problem Solution Key, generated at: " + new java.util.Date() + System.lineSeparator());

        sb.append("# Colors: " + System.lineSeparator());
        sb.append(System.lineSeparator());

        return sb.toString();
    }

    public void initGraph(Graph<Integer, DefaultEdge> graph) {
        this.constraints = new ArrayList<>();
        for (DefaultEdge edge : graph.edgeSet()) {
            constraints.add(new ColoringConstraint(graph.getEdgeSource(edge), graph.getEdgeTarget(edge)));
        }
    }

}
