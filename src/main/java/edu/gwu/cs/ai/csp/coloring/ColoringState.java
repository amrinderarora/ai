package edu.gwu.cs.ai.csp.coloring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gwu.cs.ai.csp.CSPSearchState;
import edu.gwu.cs.ai.csp.DomainValue;
import edu.gwu.cs.ai.csp.Variable;

public class ColoringState implements CSPSearchState {

    private Map<Variable, List<DomainValue>> vars = null;
    
    public ColoringState(int vars, int colors) {
        this.vars = new HashMap<>();
        for (int i = 0; i < vars; i++) {
            List<DomainValue> colorsList = new ArrayList<>();
            for (int j = 0; j < colors; j++) {
                colorsList.add(new DomainValue(j));
            }
            this.vars.put(new Variable(i), colorsList);
        }
    }

    @Override
    public Map<Variable, List<DomainValue>> getVariables() {
        return vars;
    }

    @Override
    public boolean isSolved() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Map<CSPSearchState, Double> getSuccessors() {
        // TODO Auto-generated method stub
        return null;
    }

}