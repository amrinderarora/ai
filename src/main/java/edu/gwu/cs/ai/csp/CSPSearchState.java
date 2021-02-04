package edu.gwu.cs.ai.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CSPSearchState
        implements Cloneable {

    protected Map<Variable, List<DomainValue>> vars = null;

    public CSPSearchState() {
    }

    public CSPSearchState(int vars, Collection<DomainValue> dVals) {
        this.vars = new HashMap<>();
        for (int i = 0; i < vars; i++) {
            List<DomainValue> domValList = new ArrayList<>();
            domValList.addAll(dVals);
            this.vars.put(new Variable(i), domValList);
        }
    }

    public Map<Variable, List<DomainValue>> getVariables() {
        return vars;
    }

    public boolean isFullyAssigned() {
        for (Variable var : vars.keySet()) {
            if (var.getValue() == null) {
                return false;
            }
        }
        return true;
    }

    public abstract Collection<CSPSearchState> getSuccessors();

    /** Gets successors by using the given unassigned variable. */
    public abstract Collection<CSPSearchState> getSuccessors(Variable var);
}
