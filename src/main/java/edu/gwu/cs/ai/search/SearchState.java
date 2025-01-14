package edu.gwu.cs.ai.search;

public interface SearchState {
	
    boolean isGoalState();

    double getDistanceFromRoot();
}
