package edu.gwu.cs.ai.csp;

import java.util.Collection;

/** 
 * CSP models a constraint Satisfaction Problem, with variables,
 * constraints and a goal test.
 * 
 * @author Amrinder Arora
 */
public interface CSP {

	public enum VariableStrategy {
		MRV, RANDOM;
	}

	public enum ValueStrategy {
		LCV, RANDOM;
	}

	Collection<Variable> getVariables();
	
    Collection<Constraint> getConstraints();

    boolean isGoalState();
    
    CSPSearchState getInitialState();

	String getProblemDescription();
}
