package edu.gwu.cs.ai.csp.coloring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import edu.gwu.cs.ai.csp.CSPSearchState;
import edu.gwu.cs.ai.csp.DomainValue;
import edu.gwu.cs.ai.csp.Variable;

public class ColoringState extends CSPSearchState
{

    public ColoringState(int vars, int colors) {
        this.vars = new HashMap<>();
        for (int i = 0; i < vars; i++) {
            List<DomainValue> domValList = new ArrayList<>();
            for (int j = 0; j < colors; j++) {
                domValList.add(new DomainValue(j));
            }
            this.vars.put(new Variable(i), domValList);
        }
    }

    /** Gets successors by using the FIRST unassigned variable. */
    @Override
    public Collection<CSPSearchState> getSuccessors() {
        for (Variable var : vars.keySet()) {
            if (var.getValue() == null) {
                return getSuccessors(var);
            }
        }

        return null;
    }

    /** Gets successors by using the given unassigned variable. */
    @Override
    public Collection<CSPSearchState> getSuccessors(Variable var) {
        if (var.getValue() != null) {
            throw new IllegalArgumentException("Variable is not unassigned: " + var);
        }
        Collection<CSPSearchState> successors = new ArrayList<>();
        Collection<DomainValue> possValues = var.getPossibleValues();
        for (DomainValue domVal : possValues) {
            try {
                ColoringState cs = (ColoringState) this.clone();
                // get the variable var and set the value to domVal
                successors.add(cs);
            } catch (CloneNotSupportedException cnse) {
                throw new RuntimeException("Exception in getting successors", cnse);
            }
        }
        return successors;
    }
}