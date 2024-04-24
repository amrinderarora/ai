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

    /**
     * Creates a new instance of CSPSearchState. Creates the given number of variables and
     * all of them with the given set of domain values.
     * 
     * @param vars
     * @param dVals
     */
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
    
    /** Clones this search state object. 
     * @throws CloneNotSupportedException */
    @Override
    public CSPSearchState clone() throws CloneNotSupportedException {
    	CSPSearchState myClone = (CSPSearchState) super.clone();
    	myClone.vars = new HashMap<>();
    	for (Variable var: vars.keySet()) {
    		Variable var2 = var.clone();
            List<DomainValue> domValList = vars.get(var);    		
            List<DomainValue> domVal2List = new ArrayList<>();
            for (DomainValue dval: domValList) {
            	domVal2List.add((DomainValue) dval.clone());
            }
            myClone.vars.put(var2, domVal2List);
    	}
    	return myClone;
    }

    // TODO
	protected CSPSearchState assignVariable(Variable bestVariable, DomainValue bestValue) {
		return this;
	}
}
