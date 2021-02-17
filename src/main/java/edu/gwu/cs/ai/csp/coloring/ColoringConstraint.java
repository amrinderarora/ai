package edu.gwu.cs.ai.csp.coloring;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.gwu.cs.ai.csp.CSPSearchState;
import edu.gwu.cs.ai.csp.Constraint;
import edu.gwu.cs.ai.csp.Variable;

public class ColoringConstraint implements Constraint {

    private int endPoint1;
    private int endPoint2;

    public ColoringConstraint(int endPoint1, int endPoint2) {
        super();
        this.endPoint1 = endPoint1;
        this.endPoint2 = endPoint2;
    }

    @Override
    public boolean validate(CSPSearchState state) {
        Set<Variable> vars = state.getVariables().keySet();
        Map<Integer, Variable> vertexIntMap = convertSetToMap(vars);
        return false;
    }

    private Map<Integer, Variable> convertSetToMap(Set<Variable> vars) {
        Map<Integer, Variable> map = new HashMap<>();
        for (Variable v : vars) {
            map.put(v.getIndex(), v);
        }
        return map;
    }

    @Override
    public String toString() {
        return endPoint1 + "," + endPoint2;
    }
}
