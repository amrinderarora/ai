package edu.gwu.cs.ai.search;

import java.util.Map;

public interface SearchState {
	
    boolean isGoalState();

    Map<SearchState, Double> generateSuccessors();
   
    double getDistanceFromRoot();
}
