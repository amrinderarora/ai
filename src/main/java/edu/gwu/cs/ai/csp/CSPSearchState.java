package edu.gwu.cs.ai.csp;

import java.util.List;
import java.util.Map;

public interface CSPSearchState {

    Map<Variable, List<DomainValue>> getVariables();

    boolean isSolved();

    Map<CSPSearchState, Double> getSuccessors();
}
