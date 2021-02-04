package edu.gwu.cs.ai.csp;

import edu.gwu.cs.ai.search.SearchStatistics;

public interface CSPSearchAlgorithm {

    SearchStatistics solve(CSPSearchProblem searchProblem, CSPSearchState initialState);

}
